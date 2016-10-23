package com.sumavision.branch.utils;

import com.hwangjr.rxbus.Bus;

/**
 * Created by sharpay on 16-5-31.
 */
public class BusProvider {

    private static Bus bus;

    private BusProvider() {
    }

    public static Bus getInstance() {
        if(bus == null){
            synchronized (BusProvider.class){
                if(bus == null){
                    bus = new Bus();
                }
            }
        }
        return bus;
    }
}
