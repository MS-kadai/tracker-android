package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


        setRoutePointsToRecyclerView(String.valueOf(clientConfigs.preview_route_id), view, handler); //仮置きの1


        return view;
    }
    public void setRoutePointsToRecyclerView(String route_id, View view, Handler handler) { //とりあえず流用して動作確認
        OkHttpClient okHttpClient = new OkHttpClient();
        String target_url = "http://10.0.2.2:8000/route/"+route_id; //URL組み立て
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
                        pointIdListStr.add(String.valueOf(responseRoutePoint.route.get(i).point_id));
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
                            RecyclerView.Adapter<PointListAdapter.PointListViewHolder> pointListAdapter = new PointListAdapter(pointNameList, pointCoordinateList, pointIdListStr);
                            route_overview_recyclerView.setAdapter(pointListAdapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}