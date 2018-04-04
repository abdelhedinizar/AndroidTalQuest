package com.forsyslab.talquest10.dagger;

import android.app.Application;

import com.forsyslab.talquest10.dagger.component.DaggerNetComponent;
import com.forsyslab.talquest10.dagger.component.NetComponent;
import com.forsyslab.talquest10.dagger.module.AppModule;
import com.forsyslab.talquest10.dagger.module.NetModule;



import static com.forsyslab.talquest10.constant.ApiAdresse.JOB_LEADS_API2;

/**
 * Created by LENOVO on 16/03/2017.
 */

public class AndroidApplication extends Application {

    NetComponent netComponent;

    public NetComponent getNetComponent() {
        return netComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
   netComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(JOB_LEADS_API2))
                .build();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
