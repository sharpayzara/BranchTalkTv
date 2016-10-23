package com.sumavision.branch.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.sumavision.branch.presenter.base.BasePresenter;
import com.sumavision.branch.ui.fragment.Base.BaseFragment;

import butterknife.ButterKnife;
/**
 *  desc  基于mvp模式的基类activity
 *  @author  yangjh
 *  created at  16-5-23 下午1:59
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    protected String TAG = this.getClass().getSimpleName();
    protected T presenter;
    private BaseFragment mBackHandedFragment;
    private boolean hadIntercept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initPresenter();
        Log.i(TAG, "onCreate");
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //在Action Bar的最左边，就是Home icon和标题的区域
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  ButterKnife.unbind(this);
        Log.i(TAG, "onDestroy");

    }
}
