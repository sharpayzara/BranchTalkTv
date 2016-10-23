package com.sumavision.branch.model.entity.decor;


import com.sumavision.branch.model.entity.ClassifyItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 后台分类数据模型
 * Created by zhoutao on 2016/5/27.
 */
public class ClassifyData extends BaseData {
    @SerializedName("items")
    public ArrayList<ClassifyItem> results;
    @Override
    public String toString() {
        return "ClassifyData{" +
                "results=" + results +
                '}';
    }
}
