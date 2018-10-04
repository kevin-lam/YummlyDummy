package kevinlamcs.android.com.yummlydummy.util;

import android.app.Activity;
import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.PermissionListener;

public class PermissionManager {

    public static void requestPermission(Activity activity, String permission, PermissionListener listener) {
        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(listener)
                .check();
    }
}
