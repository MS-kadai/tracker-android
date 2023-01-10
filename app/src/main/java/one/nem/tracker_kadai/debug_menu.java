package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


public class debug_menu extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public void okHttpRequest (Request request,View view) {
        OkHttpClient okHttpClient = new OkHttpClient();
        TextView textView = view.findViewById(R.id.debug_server_version);
        final Handler handler = new Handler(Looper.getMainLooper());

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull  Call call, @NonNull IOException e){

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try{
                    final String jsonStr = response.body().string();
                    JSONObject json = new JSONObject(jsonStr);
                    final String status = json.getString("date_time");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(status);
                        }
                    });

                }
                catch (JSONException | IOException e){

                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_menu, container, false);

        Button button_server_version = view.findViewById(R.id.debug_execute_server_version);
        button_server_version.setOnClickListener(v -> {
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8000/meta/time")
                    .build();

            okHttpRequest(request, view);
        });

//        mHandler = new Handler(Looper.getMainLooper());
        final Handler handler = new Handler();

        //右のビュー切り替えるテストのやつ
        Button debug_r_change_debug_nemu = view.findViewById(R.id.debug_r_change_debug_menu);
        Button debug_r_change_no_content = view.findViewById(R.id.debug_r_change_no_content);
        Button debug_r_change_route_list = view.findViewById(R.id.debug_r_change_route_list);
        Button debug_r_change_route = view.findViewById(R.id.debug_r_change_route);

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



        return view;
    }

    public void changeRightFrame(Fragment targetFragment){
        getParentFragmentManager().beginTransaction().replace(
                R.id.rightFrame , targetFragment).commit();
    }

}