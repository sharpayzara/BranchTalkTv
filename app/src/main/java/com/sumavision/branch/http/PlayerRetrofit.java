package com.sumavision.branch.http;
import com.sumavision.branch.model.entity.ProgramDetail;
import com.sumavision.branch.model.entity.ProgramListData;
import com.sumavision.branch.model.entity.ProgramListTopic;
import com.sumavision.branch.model.entity.ProgramSelection;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *  desc  网络访问的接口
 *  @author  yangjh
 *  created at  16-5-23 上午11:35
 */
public interface PlayerRetrofit {
 

    //获取节目topic]
    @GET("nav/topic")
    Observable<ProgramListTopic> getProgramListTopic(@Query("cid") String id);

    //获取节目列表数据
    @GET("series/topic/list")
    Observable<ProgramListData> getProgramListData(@Query("tid") String tid, @Query("cname") String cname, @Query("page") Integer page, @Query("size") Integer size);

    //获取节目筛选数据
    @GET("nav/fl")
    Observable<ProgramSelection> getProgramSelectionData(@Query("cid") String cid);


    //获取筛选节目列表数据
    @GET("series/filter/list")
    Observable<ProgramListData> getProgramListSelectionData(@QueryMap Map<String, String> options);


    //获取节目详情
    @GET("series/info")
    Observable<ProgramDetail> getDetailData(@Query("id") String id, @Query("mid") String mid, @Query("cpid") String cpId);

}
