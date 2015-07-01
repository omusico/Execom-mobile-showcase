package eu.execom.toolbox2contacts;

import android.app.Application;
import android.content.Context;

public class Toolbox extends Application {

    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
