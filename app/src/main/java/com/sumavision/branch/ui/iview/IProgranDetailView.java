package com.sumavision.branch.ui.iview;

import com.sumavision.branch.model.entity.ProgramDetail;
import com.sumavision.branch.ui.iview.base.IBaseView;

/**
 * 直播首页界面相关的IView
 * Created by zjx on 2016/5/31.
 */
public interface IProgranDetailView extends IBaseView {
    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showEmptyView();
    void showWifiView();
    void fillDetailValue(ProgramDetail programDetail);
    void playVideo(String crackUrl);
}
