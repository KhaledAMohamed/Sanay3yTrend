package apps.projectegy.sanay3ytrend.utils;

import android.app.Application;
import android.content.Context;



public final class MyApp extends Application
{
    private static Context context;

    public static Context getAppContext() {
        return MyApp.context;
    }

    public void onCreate()
    {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
       // Fabric.with(this, new Crashlytics());
        MyApp.context = getApplicationContext();
    }

}