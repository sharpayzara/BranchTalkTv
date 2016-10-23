package com.sumavision.branch.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

import com.sumavision.branch.R;
import com.sumavision.branch.utils.Settings;
import com.sumavision.branch.ui.fragment.TracksFragment;
import com.sumavision.branch.model.entity.ProgramDetail;
import com.sumavision.branch.presenter.ProgramDetailPresenter;
import com.sumavision.branch.ui.activity.base.BaseActivity;
import com.sumavision.branch.ui.iview.IProgranDetailView;
import com.sumavision.branch.ui.widget.LoadingLayout;
import com.sumavision.branch.utils.CommonUtil;
import com.sumavision.branch.ui.widget.media.AndroidMediaController;
import com.sumavision.branch.ui.widget.media.IjkVideoView;

public class MainActivity extends BaseActivity<ProgramDetailPresenter> implements TracksFragment.ITrackHolder,IProgranDetailView {
    private static final String TAG = "VideoActivity";
    private ProgramDetailPresenter presenter;
    private String mVideoPath;
    private Uri mVideoUri;
    private AndroidMediaController mMediaController;
    private IjkVideoView mVideoView;
    private TextView mToastTextView;
    private TableLayout mHudView;
    private DrawerLayout mDrawerLayout;
    private Settings mSettings;
    private boolean mBackPressed;
    private String idStr;
    @BindView(R.id.video_layout)
    RelativeLayout videoLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    public static Intent newIntent(Context context, String idStr) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("idStr", idStr);
        return intent;
    }

    public static void intentTo(Context context, String idStr) {
        context.startActivity(newIntent(context, idStr));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_player;
    }

    @Override
    protected void initPresenter() {
        presenter = new ProgramDetailPresenter(this,this);
        presenter.init();
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        if (mVideoView == null)
            return null;

        return mVideoView.getTrackInfo();
    }

    @Override
    public void selectTrack(int stream) {
        mVideoView.selectTrack(stream);
    }

    @Override
    public void deselectTrack(int stream) {
        mVideoView.deselectTrack(stream);
    }

    @Override
    public int getSelectedTrack(int trackType) {
        if (mVideoView == null)
            return -1;

        return mVideoView.getSelectedTrack(trackType);
    }

    @Override
    public void showProgressBar() {
        loadingLayout.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        loadingLayout.hideProgressBar();
    }

    @Override
    public void showErrorView() {
        loadingLayout.showErrorView();
    }

    @Override
    public void showEmptyView() {
        loadingLayout.showEmptyView();
    }

    @Override
    public void showWifiView() {
        loadingLayout.showWifiView();
    }

    @Override
    public void fillDetailValue(ProgramDetail programDetail) {
        presenter.crackUrl(programDetail.getSrc());
    }

    @Override
    public void playVideo(String crackUrl) {
        Uri u = Uri.parse(crackUrl);
        rx.Observable.just(u).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Action1<Uri>() {
                    @Override
                    public void call(Uri s) {
                        mVideoView.setVideoURI(s);
                        mVideoView.start();
                    }
                }
        );
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        idStr =intent.getStringExtra("idStr");
        ViewGroup.LayoutParams params = videoLayout.getLayoutParams();
        params.height = CommonUtil.screenWidth(this)*9/16;
        videoLayout.setLayoutParams(params);
        mSettings = new Settings(this);
        mMediaController = new AndroidMediaController(this, true);
        mHudView = (TableLayout) findViewById(R.id.hud_view);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);
        presenter.loadDetailData(idStr,"","");
    }
}
