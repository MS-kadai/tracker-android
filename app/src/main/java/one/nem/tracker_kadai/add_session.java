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
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class add_session extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_session, container, false);

        //URLをClientConfigsにつっこむやつ
        FloatingActionButton fab_connect_server = view.findViewById(R.id.fab_connect_server);
        fab_connect_server.setOnClickListener(v -> {
            setConfigToVariable(view);
            changeRightFrame(new route_list());
        });



        return view;
    }

    public void setConfigToVariable(View view) {
        EditText editText_target_address = view.findViewById(R.id.editText_target_url);
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();
        clientConfigs.target_url = editText_target_address.getText().toString();
    }

    public void changeRightFrame(Fragment targetFragment){ //渡されたfragmentを右のフレームに表示する
        getParentFragmentManager().beginTransaction().replace(
                R.id.rightFrame , targetFragment).commit();
    }


}