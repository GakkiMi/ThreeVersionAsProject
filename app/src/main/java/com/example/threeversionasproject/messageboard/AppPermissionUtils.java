package com.example.threeversionasproject.messageboard;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Created by Ocean on 2018/10/15.
 */

public class AppPermissionUtils {


    public static final int PERMISSION_REQUEST_CODE_CAMERA = 101;
    public static final int PERMISSION_REQUEST_CODE_ALBUM = 102;
    public static final int PERMISSION_REQUEST_CODE_AUDIO = 103;
    public static final int PERMISSION_REQUEST_CODE_VIDEO = 104;
    public static final int PERMISSION_REQUEST_CODE_DOWNLOAD_APK = 105;
    public static final int PERMISSION_REQUEST_CODE_CALENDAR = 106;

    /*
    * 检查权限是否授权
    * */
    public static boolean checkPermisssion(Context context, String[] permission) {
        boolean flag = true;
        for (int i = 0; i < permission.length; i++) {
            if (ActivityCompat.checkSelfPermission(context, permission[i]) == PackageManager.PERMISSION_DENIED) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public static void requestPermission(Activity activity, Fragment fragment, String[] permission, int requestCode) {
        if (activity == null && fragment != null) {
            fragment.requestPermissions(permission, requestCode);
        } else if (activity != null && fragment == null) {
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        }
    }


    /**
     * 判断权限组是否全部通过，只要有一个没有授权，就返回false，
     *
     * @param grantResults 权限回调的结果
     * @return
     */
    public static boolean isPermissionAllAccess(int[] grantResults) {
        boolean flag = false;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }


}
