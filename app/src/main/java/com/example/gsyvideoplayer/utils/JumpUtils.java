package com.example.gsyvideoplayer.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.gsyvideoplayer.InputUrlDetailActivity;

/**
 * Created by shuyu on 2016/11/11.
 */

public class JumpUtils {
    /**
     * 跳到可输入
     *
     * @param activity
     */
    public static void gotoInput(Activity activity) {
        Intent intent = new Intent(activity, InputUrlDetailActivity.class);
        activity.startActivity(intent);
    }
}
