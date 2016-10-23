package com.sumavision.branch.model;
/**
 * Created by sharpay on 16-6-20.
 */
public interface PlayerDetailModel extends BaseModel {
    void getDetailData(final String id, final String mid, String cpId, final CallBackListener listener);
}
