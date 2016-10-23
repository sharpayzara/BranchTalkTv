package com.sumavision.branch.model.impl;
import com.sumavision.branch.http.PlayerRetrofit;
import com.sumavision.branch.http.SumaClient;
import com.sumavision.branch.model.CallBackListener;
import com.sumavision.branch.model.PlayerDetailModel;
import com.sumavision.branch.model.entity.ProgramDetail;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by sharpay on 16-6-20.
 */
public class PlayerDetailModelImpl implements PlayerDetailModel {
    private Subscription subscription;

    @Override
    public void getDetailData(final String id, final String mid, final String cpId, final CallBackListener listener) {
        subscription = SumaClient.subscribe(new Callable<Observable<ProgramDetail>>() {
            @Override
            public Observable<ProgramDetail> call() {
                return SumaClient.getRetrofitInstance(PlayerRetrofit.class).getDetailData(id,mid,cpId);
            }
        }, new Action1<ProgramDetail>() {
            @Override
            public void call(ProgramDetail programDetail) {
                listener.onSuccess(programDetail);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                listener.onFailure(throwable);
            }
        },ProgramDetail.class);
    }

    @Override
    public void release() {
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
