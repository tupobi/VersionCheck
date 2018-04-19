package cn.hhit.canteen.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/1/2.
 * 不要忘记在manifest文件中注册！！！！！！
 * 不要忘记在manifest文件中注册！！！！！！
 * 不要忘记在manifest文件中注册！！！！！！
 */

public class MyApplication extends Application {
    private static Context mContext;

    public static Context getGlobalApplication() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo(mContext).versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo(mContext).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
