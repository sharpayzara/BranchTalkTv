package com.sumavision.branch.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sumavision.branch.R;
import com.sumavision.branch.model.CallBackListener;
import com.sumavision.branch.model.PlayerDetailModel;
import com.sumavision.branch.model.entity.ProgramDetail;
import com.sumavision.branch.model.impl.PlayerDetailModelImpl;
import com.sumavision.branch.mycrack.CrackCompleteListener;
import com.sumavision.branch.mycrack.CrackResult;
import com.sumavision.branch.mycrack.ParserUtil;
import com.sumavision.branch.mycrack.UpdateCrackDialog;
import com.sumavision.branch.presenter.base.BasePresenter;
import com.sumavision.branch.ui.iview.IProgranDetailView;
import com.sumavision.branch.utils.BaseApp;
import com.sumavision.branch.utils.NetworkUtil;
import com.jiongbull.jlog.JLog;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by sharpay on 16-6-12.
 */
public class ProgramDetailPresenter extends BasePresenter<IProgranDetailView> implements CrackCompleteListener {
    private PlayerDetailModel model;
    ParserUtil pu;
    UpdateCrackDialog updateCrackDialog;

    public ProgramDetailPresenter(Context context, IProgranDetailView iView) {
        super(context, iView);
        model = new PlayerDetailModelImpl();
    }

    @Override
    public void release() {
        model.release();
    }

    public void loadDetailData(String id, String mid, String cpId) {
        iView.showProgressBar();
        model.getDetailData(id, mid, cpId, new CallBackListener<ProgramDetail>() {
            @Override
            public void onSuccess(ProgramDetail programDetail) {
                iView.hideProgressBar();
                iView.fillDetailValue(programDetail);
            }

            @Override
            public void onFailure(Throwable throwable) {
                iView.hideProgressBar();
                if (!NetworkUtil.isConnectedByState(BaseApp.getContext())) {
                    iView.showWifiView();
                } else {
                    iView.showErrorView();
                }
                Log.e("error", throwable.toString());
            }
        });
    }

    public void judgeNetwork() {
        if (NetworkUtil.isConnectedByState(context) && !NetworkUtil.isWIFIConnected(context)) {
            Toast.makeText(context, "你当前使用的是非WIFI网络，请注意流量!", Toast.LENGTH_LONG).show();
        }
    }

    public void crackUrl(String url) {
        if (pu != null) {
            pu.stop();
        }
        pu = new ParserUtil(context, this, url, 1);
    }
    public void crackUrl(String url,int playType) {
        if (pu != null) {
            pu.stop();
        }
        pu = new ParserUtil(context, this, url, playType);
    }

    @Override
    public void onCrackComplete(CrackResult result) {
        if (result.type.equals("updatePlugins")) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (updateCrackDialog != null) {
                        updateCrackDialog.dismiss();
                    }
                    updateCrackDialog = new UpdateCrackDialog(context, R.style.UpdateDialog);
//            if (!isFinishing()) {
                    updateCrackDialog.show();
                    updateCrackDialog.setCancelable(false);
                }
            });

        } else if (result.type.equals("complete")) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (updateCrackDialog != null) {
                        updateCrackDialog.dismiss();
                    }
                }
            });

        } else {
            Log.i(this.getClass().getName(), "破解完成，url=" + result.path);
            if(!TextUtils.isEmpty(result.superUrl)){
                iView.playVideo(result.superUrl);
            }else if(!TextUtils.isEmpty(result.highUrl)){
                iView.playVideo(result.highUrl);
            }else if(!TextUtils.isEmpty(result.standUrl)){
                iView.playVideo(result.standUrl);
            }else{
                    iView.playVideo(result.path);
            }

        }
    }

    @Override
    public void onCrackFailed (HashMap < String, String > arg0){
        JLog.e(arg0.toString());
        rx.Observable.just("").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(context,"破解失败，无法观看",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public void onJarDownLoading ( int process){

    }
}
