package one.nem.tracker_kadai;

import android.app.Application;

public class ClientConfigs extends Application {
    public String target_url = "http://10.0.2.2:8000/";

    //設計最悪すぎてconfig以外もバリバリ入ってるけど動くのでヨシ
    public ResponseRouteList responseRouteList;

    public int preview_route_id;

    public int selected_route_id;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
