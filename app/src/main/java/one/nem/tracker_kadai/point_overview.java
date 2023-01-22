package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class point_overview extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_overview, container, false);

        changeMapFrame(new point_map());
        setPointData(view);

        Button button_submit_event = view.findViewById(R.id.button_submit_event);
        button_submit_event.setOnClickListener(v -> {
            submitEvent();
        });

        return view;
    }

    public void changeMapFrame(Fragment targetFragment){ //渡されたfragmentを右のフレームに表示する
        getParentFragmentManager().beginTransaction().replace(
                R.id.map_frame , targetFragment).commit();
    }

    public void setPointData(View view){
        TextView point_name = view.findViewById(R.id.textView_point_name);
        TextView point_id = view.findViewById(R.id.textView_point_id);
        TextView point_coordinates = view.findViewById(R.id.textView_point_coordinates);

        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();

        point_name.setText(clientConfigs.selected_point_name);
        point_id.setText(valueOf(clientConfigs.selected_point_id));
        point_coordinates.setText(clientConfigs.selected_point_coordinate); //名前を統一しやがれ
    }

    public void submitEvent() {
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        ObjectMapper objectMapper = new ObjectMapper();
        OkHttpClient okHttpClient = new OkHttpClient();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //異常な実装
            RequestSessionUpdate requestSessionUpdate = new RequestSessionUpdate(valueOf(clientConfigs.selected_point_id), LocalDateTime.now().toString());
            try {
                String requestBody = objectMapper.writeValueAsString(requestSessionUpdate);
                Request request = new Request.Builder()
                        .url(clientConfigs.target_url+"session/"+clientConfigs.target_uuid+"/add")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody))
                        .build();

                okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}