package com.sumavision.branch.ui.fragment.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumavision.branch.presenter.base.BasePresenter;

import butterknife.ButterKnife;
/**
 *  desc  fragment基类
 *  @author  yangjh
 *  created at  16-5-24 下午8:57
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected View view;
    protected T presenter;
    public String tid,currentType,total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }
    public void set(String currentType,String tid){
        this.tid = tid;
        this.currentType = currentType;
        this.total = total;
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public abstract boolean onBackPressed();
}
