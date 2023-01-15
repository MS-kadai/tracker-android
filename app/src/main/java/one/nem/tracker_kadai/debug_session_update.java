package one.nem.tracker_kadai;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class debug_session_update extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_session_update, container, false);

        FloatingActionButton fab_execute = view.findViewById(R.id.u_debug_fab_execute);
        fab_execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //どう考えて
                    postSessionUpdate(view);
                }
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O) //どう考えてもだめな書き方なのであとでなんとかする
    public void postSessionUpdate(View view) {
        EditText u_debug_editText_session_id = view.findViewById(R.id.u_debug_editText_session_id);
        EditText u_debug_editText_point_id = view.findViewById(R.id.u_debug_editText_point_id);

        ObjectMapper objectMapper = new ObjectMapper();
        OkHttpClient okHttpClient = new OkHttpClient();

        final Handler handler = new Handler(Looper.getMainLooper());

        RequestSessionUpdate requestSessionUpdate = new RequestSessionUpdate(u_debug_editText_session_id.getText().toString(), LocalDateTime.now().toString() );
        String requestUrl = "http://10.0.2.2:8000/session/"+u_debug_editText_session_id.getText().toString()+"/add";
        try {
            String requestBody = objectMapper.writeValueAsString(requestSessionUpdate);
            Log.d("requestBody", requestBody);
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody))
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("debug", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.d("debug", "onResponse: " + response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



    }
}