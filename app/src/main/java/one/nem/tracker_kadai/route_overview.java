package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class route_overview extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_overview, container, false);
        final Handler handler = new Handler(Looper.getMainLooper());

        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();


        setRoutePointsToRecyclerView(clientConfigs.target_url, valueOf(clientConfigs.preview_route_id), view, handler); //仮置きの1

        Button button_create_session = view.findViewById(R.id.route_overview_button_create_session);

        button_create_session.setOnClickListener(v -> {
            createSession();
        });

        return view;
    }
    public void setRoutePointsToRecyclerView(String base_url, String route_id, View view, Handler handler) { //とりあえず流用して動作確認
        OkHttpClient okHttpClient = new OkHttpClient();
        //String target_url = "http://10.0.2.2:8000/route/"+route_id; //URL組み立て
        String target_url = base_url + "route/" + route_id;
        Request request = new Request.Builder()
                .url(target_url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //時間があったらエラー処理書く
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    final String response_body = response.body().string();
                    ObjectMapper objectMapper = new ObjectMapper();

                    ResponseRoutePoint responseRoutePoint = objectMapper.readValue(response_body, ResponseRoutePoint.class);
                    List<String> pointNameList = new ArrayList<>();
                    List<String> pointIdListStr = new ArrayList<>();
                    List<String> pointCoordinateList = new ArrayList<>();
                    for(int i = 0; i < responseRoutePoint.length; i++) {
                        pointNameList.add(responseRoutePoint.route.get(i).point_name);
                    }
                    for(int i = 0; i < responseRoutePoint.length; i++) {
                        pointIdListStr.add(valueOf(responseRoutePoint.route.get(i).point_id));
                    }
                    for(int i = 0; i < responseRoutePoint.length; i++) {
                        pointCoordinateList.add(responseRoutePoint.route.get(i).coordinate);
                    }



                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView route_overview_recyclerView = view.findViewById(R.id.route_overview_recyclerView);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            route_overview_recyclerView.setLayoutManager(layoutManager);
                            RecyclerView.Adapter<PointListAdapter.PointListViewHolder> pointListAdapter = new PointListAdapter(pointNameList, pointCoordinateList, pointIdListStr, item -> {
                                //ここにクリック時の処理を書く
                                //とりあえずいま何もしなくても問題ないので後回し
                            });
                            route_overview_recyclerView.setAdapter(pointListAdapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createSession(){ //この名前だけど画面切り替えも兼ねてます
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        clientConfigs.selected_route_id = clientConfigs.preview_route_id;

        //UUID生成してリクエストボディを組み立てるやつ
        ObjectMapper objectMapper = new ObjectMapper();
        String session_id_generated = UUID.randomUUID().toString();
        RequestCreateSession requestCreateSession = new RequestCreateSession(session_id_generated, valueOf(clientConfigs.selected_route_id));

        //debug: 生成したUUIDをClientConfigsに格納
        clientConfigs.target_uuid = session_id_generated;

        changeLeftFrame(new route());

    }
    public void changeRightFrame(Fragment targetFragment){ //渡されたfragmentを右のフレームに表示する
        getParentFragmentManager().beginTransaction().replace(
                R.id.rightFrame , targetFragment).commit();
    }

    public void changeLeftFrame(Fragment targetFragment){ //渡されたfragmentを左のフレームに表示する
        getParentFragmentManager().beginTransaction().replace(
                R.id.leftFrame , targetFragment).commit();
    }


}