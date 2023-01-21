package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class route_list extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

        setRouteListFromServer(view);
//        setRouteListToRecyclerView(view, convertingToList());

        SelectRouteInterface selectRouteInterface = new SelectRouteInterface() {
            @Override
            public void onSelect(String item) {
                Log.d("route_list", "onSelect: " + item);
//                changeRightFrame(new add_session(item));
            }
        };
        return view;
    }

    public void setRouteListFromServer(View view){
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(clientConfigs.target_url + "route/list")
                .build();

        //debug
        Log.d("debug", clientConfigs.target_url + "route/list");

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //エラー処理書かないといけない

                //debug
                Log.d("debug", "onFailure: " + e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String json = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    clientConfigs.responseRouteList = mapper.readValue(json, ResponseRouteList.class);
                    Log.d("route_list", "onResponse: " + clientConfigs.responseRouteList);
                    setRouteListToRecyclerView(view, convertingToRouteNameList(), convertingToRouteIdList()); //めんどくさいのでとりあえずこれでごまかす
                } catch (IOException e) {
                    e.printStackTrace();

                    //debug
                    Log.d("debug", "onResponse: " + e.getMessage());
                }
            }
        });

    }

    public List<String> convertingToRouteNameList(){ //取得失敗したときにnull投げ込むことにならないようにするべきかも

        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        List<String> routeNameList = new ArrayList<>();
        for (int i = 0; i < clientConfigs.responseRouteList.routes.size(); i++){
            routeNameList.add(clientConfigs.responseRouteList.routes.get(i).route_name);
        }
        return routeNameList;
    }

    public List<String> convertingToRouteIdList(){ //取得失敗したときにnull投げ込むことにならないようにするべきかも

        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        List<String> routeIdList = new ArrayList<>();
        for (int i = 0; i < clientConfigs.responseRouteList.routes.size(); i++){
            routeIdList.add(valueOf(clientConfigs.responseRouteList.routes.get(i).id));
        }
        return routeIdList;
    }

    public void setRouteListToRecyclerView(View view, List<String> routeNameList, List<String> routeIdList){
        final Handler handler = new Handler(Looper.getMainLooper());
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        handler.post(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView_route_list);
                RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> routeListViewHolderAdapter = new RouteListAdapter(routeNameList, routeIdList,  item -> {
                    Log.d("route_list", "onSelect: " + item);
                    clientConfigs.preview_route_id = Integer.parseInt(item);

                });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(routeListViewHolderAdapter);
            }
        });

    }


}
