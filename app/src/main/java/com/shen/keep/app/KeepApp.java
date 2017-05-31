package com.shen.keep.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.shen.keep.BuildConfig;
import com.shen.keep.R;
import com.shen.keep.app.db.DaoMaster;
import com.shen.keep.app.db.DaoSession;
import com.shen.keep.core.util.LogUtils;
import com.shen.netclient.NetClient;
import com.shen.netclient.engine.NetClientLib;
import com.shen.netclient.util.FileUtils;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by edianzu on 2017/5/17.
 */
public class KeepApp extends Application{

    private static KeepApp keepApp;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        keepApp = this;
        initNetClient();
        initStetho();
        initMemLeak();
        initGreenDao();
        initByGradleFile();
    }

    private void initNetClient() {
        NetClientLib.getLibInstance().setMobileContext(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
        NetClient.addNetworkInterceptor(new StethoInterceptor());
    }

    /*
    根据主项目中的gradle配置文件开初始化不同的开发模式
   */
    private void initByGradleFile() {

        if(Constants.TEST_MODE.equals(BuildConfig.MODE)){
            NetClientLib.getLibInstance().setLogEnable(true);
            NetClientLib.getLibInstance().setUrlConfigManager(R.xml.url);
        }
        else if(Constants.DEV_MODE.equals(BuildConfig.MODE))
        {
            NetClientLib.getLibInstance().setLogEnable(true);
            NetClientLib.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
        }
        else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
            NetClientLib.getLibInstance().setLogEnable(false);
            NetClientLib.getLibInstance().setServerBaseUrl(BuildConfig.SERVER_URL);
        }
    }


    /*
    根据Raw中的mobile配置文件来初始化开发模式
     */
    private void initByRawConfigFile() {
        if(FileUtils.getProperties(this, R.raw.mobile)){
            String mode = FileUtils.getPropertyValueByKey("mode");
            String baseUrl = FileUtils.getPropertyValueByKey("baseUrl");
            LogUtils.i("开发模式为：" + mode);
            if(Constants.TEST_MODE.equals(mode)){
                NetClientLib.getLibInstance().setLogEnable(true);
                NetClientLib.getLibInstance().setUrlConfigManager(R.xml.url);
            }
            else if(Constants.DEV_MODE.equals(BuildConfig.MODE))
            {
                NetClientLib.getLibInstance().setLogEnable(true);
                NetClientLib.getLibInstance().setServerBaseUrl(baseUrl);
            }
            else if(Constants.RELEASE_MODE.equals(BuildConfig.MODE)){
                NetClientLib.getLibInstance().setLogEnable(false);
                NetClientLib.getLibInstance().setServerBaseUrl(baseUrl);
            }
        }
    }

    private void initMemLeak() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    private void initGreenDao() {

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "keep-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static KeepApp getAppInstance() {
        return keepApp;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
