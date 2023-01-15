package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;

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
        RecyclerView add_session_recycler_view = view.findViewById(R.id.add_session_recycler_view);


        ObjectMapper objectMapper = new ObjectMapper();

        fab_refresh_route_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                            int debug_length_int = responseRouteList.length;

                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    //あとで
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

}