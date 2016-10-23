package com.sumavision.branch.ui.iview;

import com.sumavision.branch.model.entity.ProgramListTopic;
import com.sumavision.branch.model.entity.ProgramSelection;
import com.sumavision.branch.ui.iview.base.IBaseView;

/**
 * 发现首页界面的IView
 * Created by sharpay on 2016/6/25.
 */
public interface IProgramListView extends IBaseView {
    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showEmptyView();
    void showWifiView();
    void fillTopicAndSeletionData(ProgramSelection selectionData, ProgramListTopic programListTopic);
}
