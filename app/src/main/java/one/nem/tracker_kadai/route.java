package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class route extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        final Handler handler = new Handler(Looper.getMainLooper());
//        EditText editText_route_id = view.findViewById(R.id.editText_route_id);
//        Button button_get_route = view.findViewById(R.id.button_get_route);
//        button_get_route.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View buttonView) {
//                setRoutePointsToRecyclerView(editText_route_id.getText().toString(), view, handler);
//            }
//        });
            initialize(view, handler);

        return view;
    }
    public void initialize(View view, Handler handler){
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        setRoutePointsToRecyclerView(clientConfigs.target_url, valueOf(clientConfigs.selected_route_id), view, handler);

        //debug
        TextView route_debug_target_uuid = view.findViewById(R.id.route_debug_target_uuid);
        route_debug_target_uuid.setText(clientConfigs.target_uuid);
    }

    public void setRoutePointsToRecyclerView(String base_url, String route_id, View view, Handler handler) {
        OkHttpClient okHttpClient = new OkHttpClient();
//        String target_url = "http://10.0.2.2:8000/route/"+route_id; //URL????????????
        String target_url = base_url + "route/" + route_id;
        Request request = new Request.Builder()
                .url(target_url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //??????????????????????????????????????????
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
                            RecyclerView route_recycler_view = view.findViewById(R.id.route_recycler_view);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            route_recycler_view.setLayoutManager(layoutManager);
                            RecyclerView.Adapter<PointListAdapter.PointListViewHolder> pointListAdapter = new PointListAdapter(pointNameList, pointCoordinateList, pointIdListStr, item -> {
                                ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
                                clientConfigs.selected_point_id = Integer.parseInt(item);
                                clientConfigs.selected_point_coordinate = pointCoordinateList.get(Integer.parseInt(item) - 1);
                                clientConfigs.selected_point_name = pointNameList.get(Integer.parseInt(item) - 1); //??????????????????ID??????????????????????????????????????????????????????????????????????????????

                                //debug
                                Log.d("debug", "selected_point_id: " + item);
                                Log.d("debug", "selected_point_coordinate: " + pointCoordinateList.get(Integer.parseInt(item) - 1));
                                Log.d("debug", "selected_point_name: " + pointNameList.get(Integer.parseInt(item) - 1));

                                //??????????????????UI???????????????????????????????????????????????????????????????????????????
                                changeRightFrame(new point_overview());

                            });
                            route_recycler_view.setAdapter(pointListAdapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void changeRightFrame(Fragment targetFragment){ //????????????fragment????????????????????????????????????
        getParentFragmentManager().beginTransaction().replace(
                R.id.rightFrame , targetFragment).commit();
    }
}
