package one.nem.tracker_kadai;

import android.app.Application;

public class ClientConfigs extends Application {
    public String target_url = "http://10.0.2.2:8000/";

    //設計最悪すぎてconfig以外もバリバリ入ってるけど動くのでヨシ
    public ResponseRouteList responseRouteList;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
