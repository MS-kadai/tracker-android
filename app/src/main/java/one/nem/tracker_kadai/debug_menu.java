package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class debug_menu extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void okHttpRequest (Request request,View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        TextView textView = view.findViewById(R.id.debug_server_version);
        TextView debug_textView_response = view.findViewById(R.id.debug_textView_response);
        final Handler handler = new Handler(Looper.getMainLooper());
        ObjectMapper objectMapper = new ObjectMapper();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull  Call call, @NonNull IOException e){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        debug_textView_response.setText("onFailure: "+ e.getMessage());
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
//                try{
//                    final String jsonStr = response.body().string();
//
//
//                    ResponseServerDateTime responseServerDateTime = objectMapper.readValue(jsonStr, ResponseServerDateTime.class);
//
//                    Log.d("DEBUG", responseServerDateTime.date_time);
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText(responseServerDateTime.date_time);
//                        }
//                    });
//
//                }
//                catch ( IOException e){
//                    Log.d("debug", "IOException: " + e);
//                }
                Log.d("debug", "onResponse: " + response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            debug_textView_response.setText("onResponse: "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_menu, container, false);

        final Handler handler = new Handler(Looper.getMainLooper());
        //サーバーのバージョンを取得するテストのやつ
        Button button_server_version = view.findViewById(R.id.debug_execute_server_version);
        button_server_version.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8000/meta/time")
                    .build();

            okHttpRequest(request, view);
        });


        //右のビュー切り替えるテストのやつ
        Button debug_r_change_debug_nemu = view.findViewById(R.id.debug_r_change_debug_menu);
        Button debug_r_change_no_content = view.findViewById(R.id.debug_r_change_no_content);
        Button debug_r_change_route_list = view.findViewById(R.id.debug_r_change_route_list);
        Button debug_r_change_route = view.findViewById(R.id.debug_r_change_route);
        Button debug_r_change_debug_session_update = view.findViewById(R.id.debug_r_change_debug_session_update);

        debug_r_change_debug_nemu.setOnClickListener(v -> {
            changeRightFrame(new debug_menu());
        });

        debug_r_change_no_content.setOnClickListener(v -> {
            changeRightFrame(new no_content());
        });

        debug_r_change_route_list.setOnClickListener(v -> {
            changeRightFrame(new route_list());
        });

        debug_r_change_route.setOnClickListener(v -> {
            changeRightFrame(new route());
        });
        debug_r_change_debug_session_update.setOnClickListener(v -> {
            changeRightFrame(new debug_session_update());
        });

        //セッション作るテストのやつ

        Button debug_create_session_button = view.findViewById(R.id.debug_create_session_button);
        TextView debug_select_route_id = view.findViewById(R.id.debug_select_route_id);
        TextView debug_generated_session_id = view.findViewById(R.id.debug_generated_session_id);
        debug_create_session_button.setOnClickListener(v -> {

            ObjectMapper objectMapper = new ObjectMapper();
            String session_id_generated = UUID.randomUUID().toString();
            RequestCreateSession requestCreateSession = new RequestCreateSession(session_id_generated, debug_select_route_id.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    debug_generated_session_id.setText(session_id_generated);
                }
            });
            try {
                String requestBodyStr = objectMapper.writeValueAsString(requestCreateSession);
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8000/session/create")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBodyStr))
                        .build();
                        okHttpRequest(request, view);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });

        //変数読み出すテストのやつ

        Button debug_read_read_variable = view.findViewById(R.id.debug_button_read_variable);

        debug_read_read_variable.setOnClickListener(v -> {
            ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
            Log.d("DEBUG",clientConfigs.target_url);
        });
        return view;
    }

    public void changeRightFrame(Fragment targetFragment){ //渡されたfragmentを右のフレームに表示する
        getParentFragmentManager().beginTransaction().replace(
                R.id.rightFrame , targetFragment).commit();
    }

}