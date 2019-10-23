package com.skateboard.zxinglib.help;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckPermission {

    public static boolean checkPermission(Activity activity, int requestCode){
        ArrayList<String> permissionList = new ArrayList<String>();
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.VIBRATE);
        permissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
        permissionList.add(Manifest.permission.READ_CONTACTS);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        Iterator<String> it = permissionList.iterator();
        while (it.hasNext()){
            String permission = it.next();
            int hasPermission = ContextCompat.checkSelfPermission(activity, permission);
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                it.remove();
            }
        }
        if (permissionList.size() == 0) {
            return true;
        }
        String[] permissions = permissionList.toArray(new String[0]);
        //正式请求权限
        ActivityCompat.requestPermissions((Activity) activity, permissions, requestCode);
        return false;
    }
    public synchronized static boolean isCameraUseable(int cameraID) {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(cameraID);
            // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            canUse = false;
        } finally {
            if (mCamera != null) {
                mCamera.release();
            } else {
                canUse = false;
            }
            mCamera = null;
        }
        return canUse;
    }
}
