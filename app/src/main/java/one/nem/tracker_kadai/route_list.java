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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class route_list extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_route_list, container, false);

        setRouteListFromServer();
        setRouteListToRecyclerView(view, convertingToList());

        return view;
    }

    public void setRouteListFromServer(){
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(clientConfigs.target_url + "route/list")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //エラー処理書かないといけない
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String json = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    clientConfigs.responseRouteList = mapper.readValue(json, ResponseRouteList.class);
                    Log.d("route_list", "onResponse: " + clientConfigs.responseRouteList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public List<String> convertingToList(){ //取得失敗したときにnull投げ込むことにならないようにするべきかも

        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        List<String> routeNameList = new ArrayList<>();
        for (int i = 0; i < clientConfigs.responseRouteList.routes.size(); i++){
            routeNameList.add(clientConfigs.responseRouteList.routes.get(i).route_name);
        }
        return routeNameList;
    }

    public void setRouteListToRecyclerView(View view, List<String> routeNameList){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView_route_list);
                RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> routeListViewHolderAdapter = new RouteListAdapter(routeNameList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(routeListViewHolderAdapter);
            }
        });

    }
}
