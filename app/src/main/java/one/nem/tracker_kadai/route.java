package one.nem.tracker_kadai;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.HttpUrl.Builder;


public class route extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        EditText editText_route_id = view.findViewById(R.id.editText_route_id);
        Button button_get_route = view.findViewById(R.id.button_get_route);
        button_get_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRoutePointsToRecyclerView(editText_route_id.getText().toString(), view);
            }

            // OkHttp3でリクエスト飛ばす部分（後でメソッド分ける）

        });

        return view;
    }

    public void setRoutePointsToRecyclerView(String route_id, View view) {
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
                final String response_body = response.body().toString();
                Log.d("debug", response_body);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    ResponseRoutePoint responseRoutePoint = objectMapper.readValue(response_body, ResponseRoutePoint.class);

                    Log.d("debug", String.valueOf(responseRoutePoint.length));

                } catch (IOException e) {
                    Log.d("debug", "IOException"+e);
                }
            }
        });
    }
}
