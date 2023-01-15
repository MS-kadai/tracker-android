package one.nem.tracker_kadai;

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
import android.widget.Button;
import android.widget.EditText;

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
        EditText editText_route_id = view.findViewById(R.id.editText_route_id);
        Button button_get_route = view.findViewById(R.id.button_get_route);
        button_get_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                setRoutePointsToRecyclerView(editText_route_id.getText().toString(), view, handler);
            }

            // OkHttp3でリクエスト飛ばす部分（後でメソッド分ける）

        });

        return view;
    }

    public void setRoutePointsToRecyclerView(String route_id, View view, Handler handler) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String target_url = "http://10.0.2.2:8000/route/"+route_id; //URL組み立て
        Request request = new Request.Builder()
                .url(target_url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("debug", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    final String response_body = response.body().string();
                    Log.d("debug", response_body);
                    ObjectMapper objectMapper = new ObjectMapper();

//                    debug
                                    List<String> testList = new ArrayList<>();
                                    testList.add ("test1");
                                    testList.add ("test2");
                                    testList.add ("test3");
                                    testList.add ("test4");

                    ResponseRoutePoint responseRoutePoint = objectMapper.readValue(response_body, ResponseRoutePoint.class);
                    List<String> pointNameList = new ArrayList<>();
                    for(int i = 0; i < responseRoutePoint.length; i++) {
                        pointNameList.add(responseRoutePoint.route.get(i).point_name);
                    }



                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView route_recycler_view = view.findViewById(R.id.route_recycler_view);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            route_recycler_view.setLayoutManager(layoutManager);
                            RecyclerView.Adapter<PointListAdapter.PointListViewHolder> pointListAdapter = new PointListAdapter(pointNameList);
                            route_recycler_view.setAdapter(pointListAdapter);
                        }
                    });

                    Log.d("debug", String.valueOf(responseRoutePoint.route.get(0).coordinate));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public List<String> convertPointListToList (ResponseRoutePoint responseRoutePoint) { //まだテスト用なのでIDとかは追加してないはず
//        List<String> pointNameList = new ArrayList<>();
//        for(int i = 0; i < responseRoutePoint.length; i++) {
//            pointNameList.add(responseRouteList.routes.get(i).route_name);
//        }
//        return pointNameList;
//    }
    //なしでできるかもしれないのでとりあえず
}
