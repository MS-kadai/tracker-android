package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class sessionAlreadyExistsDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ルートID: \""+ clientConfigs.preview_route_id +"\"にはアクティブなセッションが既に存在します。\n既存のセッションを強制的に破棄しますか？")
                .setTitle("セッションの作成に失敗しました。")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        Log.d("sessionERR", "onClick: OK");
                        deleteActiveSession();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        Log.d("sessionERR", "onClick: Cancel");
                    }
                });


        return builder.create();
    }

    public void deleteActiveSession(){
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(clientConfigs.target_url + "session/delete").newBuilder();

        //アクティブなセッションのUUIDを特定する
        Request request_get_list = new Request.Builder()
                .url(clientConfigs.target_url + "route/list")
                .build();

        okHttpClient.newCall(request_get_list).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("sessionERR", "onFailure: " + e.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.N) //どう考えても異常な実装だけど動くのでOK
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d("sessionERR", "onResponse: " + json);
                ObjectMapper mapper = new ObjectMapper();
                ResponseRouteList responseRouteList = mapper.readValue(json, ResponseRouteList.class);

                String active_session_uuid = responseRouteList.routes.get((clientConfigs.preview_route_id)-1).active_session;

                Log.d("sessionERR", "onResponse: " + active_session_uuid); //debug

                //セッション消す

                //リクエストパラメーターつくる
                Map<String, String> params = new HashMap<>();
                params.put("session_id", active_session_uuid);
                params.forEach(urlBuilder::addQueryParameter);

                Log.d("URL", "URL: " + urlBuilder.build());

                Request request_delete_session = new Request.Builder()
                        .url(urlBuilder.build())
                        .delete()
                        .build();

                okHttpClient.newCall(request_delete_session).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("sessionERR", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("sessionERR", "onResponse: " + response.body().string());
                    }
                });
            }

        });



    }

}
