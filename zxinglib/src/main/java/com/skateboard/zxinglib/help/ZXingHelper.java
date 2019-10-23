package com.skateboard.zxinglib.help;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.skateboard.zxinglib.CaptureActivity;

public class ZXingHelper {
    private volatile static ZXingHelper instance;
    private FragmentActivity activity;

    public static ZXingHelper getInstance() {
        if (instance == null) {
            synchronized (ZXingHelper.class) {
                if (instance == null) {
                    instance = new ZXingHelper();
                }
            }
        }
        return instance;
    }
    public void open(){
        Intent intent=new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, Constant.SCAN_RESULT_CODE);
    }
    public static Builder builder(FragmentActivity activity) {
        return new Builder(activity);
    }
    public static class Builder {
        private FragmentActivity activity;

        private Builder(FragmentActivity activity) {
            this.activity = activity;
        }

        public ZXingHelper build() {
            ZXingHelper helper = getInstance();
            helper.activity = activity;

            return helper;
        }
    }

}
