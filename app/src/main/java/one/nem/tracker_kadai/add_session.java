package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class add_session extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_session, container, false);

        final Handler handler = new Handler(Looper.getMainLooper());
        FloatingActionButton fab_refresh_route_list = view.findViewById(R.id.fab_refresh_route);

        ObjectMapper objectMapper = new ObjectMapper();

//        RecyclerView add_session_recycler_view = view.findViewById(R.id.add_session_recycler_view);
//        add_session_recycler_view.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> routeListAdapter = new RouteListAdapter();
//        add_session_recycler_view.setAdapter(routeListAdapter);

        fab_refresh_route_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8000/route/list")
                        .build();

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e){
                        //時間があったらエラー処理も書く
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        try {
                            final String jsonStr = response.body().string();
                            ResponseRouteList responseRouteList = objectMapper.readValue(jsonStr, ResponseRouteList.class);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    setRecyclerView(convertRouteListToList(responseRouteList), view);

                                    //debug
//                                    List<String> testList = new ArrayList<>();
//                                    testList.add ("test1");
//                                    testList.add ("test2");
//
//                                    setRecyclerView(testList, view);
                                }
                            });



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return view;

    }

    public List<String> convertRouteListToList (ResponseRouteList responseRouteList) { //まだテスト用なのでIDとかは追加してないはず
        List<String> routeNameList = new ArrayList<>();
        for(int i = 0; i < responseRouteList.routes.size(); i++) {
            routeNameList.add(responseRouteList.routes.get(i).route_name);
        }
        return routeNameList;
    }

    public void setRecyclerView(List<String> routeNameList, View view) {
        RecyclerView add_session_recycler_view = view.findViewById(R.id.add_session_recycler_view);

        if(add_session_recycler_view != null){ //debug
            Log.d("DEBUG", "add_session_recycler_view is not null");
        }
        else{
            Log.d("DEBUG", "add_session_recycler_view is null");
        }

//        add_session_recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        add_session_recycler_view.setLayoutManager(layoutManager);
        RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> routeListAdapter = new RouteListAdapter(routeNameList);
        add_session_recycler_view.setAdapter(routeListAdapter);

        //とりあえずここに置いとくだけ

    }

}