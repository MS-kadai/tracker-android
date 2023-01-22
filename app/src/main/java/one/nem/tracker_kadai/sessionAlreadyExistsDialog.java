package one.nem.tracker_kadai;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class sessionAlreadyExistsDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ClientConfigs clientConfigs = (ClientConfigs) getActivity().getApplication();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ルートID: \""+ clientConfigs.preview_route_id +"\"にはアクティブなセッションが既に存在します。")
                .setTitle("セッションの作成に失敗しました。")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        Log.d("sessionERR", "onClick: OK");
//
//                        MainActivity mainActivity = new MainActivity();
//                        mainActivity.hideSystemUI();
                    }
                });


        return builder.create();
    }

}
