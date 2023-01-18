package one.nem.tracker_kadai;

import android.app.Application;

public class ClientConfigs extends Application {

    public String target_url = "http://10.0.2.2:8080/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
