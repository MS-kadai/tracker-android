package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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
}