package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        TextView routes_textView = view.findViewById(R.id.add_session_debug);
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

                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                final String jsonStr;
                                try {
                                    jsonStr = response.body().string();
                                    JSONObject json = new JSONObject(jsonStr);
                                    int length = json.getInt("length");
                                    JSONArray routesJson = json.getJSONArray("routes");

                                        routes_textView.setText(length);


//                                    for (int i = 0; i > length; i++){
//
//                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
            }
        });

        return view;

    }

}