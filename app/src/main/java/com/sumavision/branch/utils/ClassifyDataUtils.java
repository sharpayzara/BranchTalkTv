package com.sumavision.branch.utils;

import com.sumavision.branch.model.entity.ClassifyItem;
import com.sumavision.branch.model.entity.decor.ClassifyData;

import java.util.ArrayList;

/**
 * Created by sharpay on 16-8-10.
 */
public class ClassifyDataUtils {
    private static ClassifyData classifyData;
    private ClassifyDataUtils(){}

    public static ClassifyData getInstance(){
        if(classifyData == null){
            classifyData = new ClassifyData();
            classifyData.results = new ArrayList<>();
            ClassifyItem item1 = new ClassifyItem("电影","cark3x");
            ClassifyItem item2 = new ClassifyItem("电视剧","calufd");
            ClassifyItem item3 = new ClassifyItem("综艺","caww4e");
            ClassifyItem item4 = new ClassifyItem("纪录片","cafaix");
            ClassifyItem item5 = new ClassifyItem("新闻","ca1z3e");
            ClassifyItem item6 = new ClassifyItem("动漫","ca1i2s");
            ClassifyItem item7 = new ClassifyItem("微电影","ca45x4");
            ClassifyItem item8 = new ClassifyItem("游戏","caqul4");
            ClassifyItem item9 = new ClassifyItem("体育","caex50");
            ClassifyItem item10 = new ClassifyItem("娱乐","calp5t");
            classifyData.results.add(item1);
            classifyData.results.add(item2);
            classifyData.results.add(item3);
            classifyData.results.add(item4);
            classifyData.results.add(item5);
            classifyData.results.add(item6);
            classifyData.results.add(item7);
            classifyData.results.add(item8);
            classifyData.results.add(item9);
            classifyData.results.add(item10);
        }
        return classifyData;
    }

}

