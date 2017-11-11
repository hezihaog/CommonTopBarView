package com.hzh.common.view.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Package: com.hzh.common.view.util
 * FileName: TDevice
 * Date: on 2017/11/10  下午10:57
 * Auther: zihe
 * Descirbe: 设备信息工具类
 * Email: hezihao@linghit.com
 */

public class TDevice {
    /**
     * 获取状态栏的高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
