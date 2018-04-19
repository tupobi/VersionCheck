package cn.hhit.canteen.main.view;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.main.presenter.IVersionCheckBiz;
import cn.hhit.canteen.main.presenter.VersionCheckBizImpl;
import cn.hhit.canteen.main.presenter.download.service.DownloadService;

public class AtyMain extends AppCompatActivity implements IVersionCheckView {
    private IVersionCheckBiz mIVersionCheckBiz;

    //add1111
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    //add1111


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);

        //add
        Intent intent = new Intent(AtyMain.this, DownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
        //add

        mIVersionCheckBiz = new VersionCheckBizImpl(AtyMain.this, AtyMain.this);
        checkUpdate();
    }

    private void checkUpdate() {
        mIVersionCheckBiz.checkVersion();
    }

    @Override
    public void showUpdateDialog(String updateVersionName, String updateVersionDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AtyMain.this);
        builder.setTitle("发现最新版本：" + updateVersionName);
        builder.setMessage("更新内容：\n" + updateVersionDescription);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIVersionCheckBiz.checkNet();

            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void startDownload(String downloadUrl) {
        if (downloadBinder == null) {
            LogUtil.e("downloadBindeer == null");
            return;
        }
        downloadBinder.startDownload(downloadUrl);
    }

    @Override
    public void promptNoNetwork() {
        ToastUtil.showShort(AtyMain.this, "当前网络不可用");
    }

    @Override
    public void showConfirmDownloadWithNoWiFiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AtyMain.this);
        builder.setTitle("提示：");
        builder.setMessage("非WiFi环境，是否流量下载？");
        builder.setPositiveButton("我是土豪，下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIVersionCheckBiz.startDownload();
            }
        });
        builder.setNegativeButton("忍一手", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /**
     * 1.绑定服务后需要在onDestory中解绑
     * 2.服务需要在manifest中注册！！！
     * 3.DownloadService最后选择要安装的apk，根据需要肯定会改，这里是canteen.apk
     * 4.下载的位置和安装访问本地文件的位置需要对应起来
     * 5.没有文件夹先创建一个文件夹！
     * 6.android N 访问并安装apk本地文件适配。
     * Uri contentUri = FileProvider.getUriForFile
     * (DownloadService.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
     * 这句的fileProvider和manifest中的authority属性一致
     * 7.适配android_O(android 8.0)通知栏设置channel
     * 8.适配android_O安装应用权限，只需要在manifest中添加请求install权限就好了，会在设置中打开请求权限，不需要动态申请（申请也没用）
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
