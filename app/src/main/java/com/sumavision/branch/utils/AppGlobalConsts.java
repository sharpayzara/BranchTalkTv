package com.sumavision.branch.utils;

import android.os.Environment;

/**
 *  desc  全局常量
 *  @author  yangjh
 *  created at  16-5-23 上午10:21 
 */
public class AppGlobalConsts {
    public final class EventType {
        public final static String TAG_A = "tag_a";
        public final static String TAG_B = "tag_b";
        public final static String TAG_C = "tag_c";
    }
    // 主目录
    public static String USER_ALL_SDCARD_FOLDER = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/TVFan/";
}

