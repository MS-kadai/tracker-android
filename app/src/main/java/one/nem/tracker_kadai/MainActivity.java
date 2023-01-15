package one.nem.tracker_kadai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Debug
        hideSystemUI();

        BottomNavigationView fragment = (BottomNavigationView) findViewById(R.id.bottomNavigationView); //ひだりがわ

        fragment.setOnNavigationItemSelectedListener( //なんか絶対間違ってるけどとりあえず動いてる
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int selectedItemId = item.getItemId();
                        Fragment newFragment = null;
                        if(selectedItemId == R.id.menu_add_session) {
                            newFragment = new add_session();
                        } else if (selectedItemId == R.id.menu_debug) {
                            newFragment = new debug_menu();
                        } else {
                            newFragment = new route();
                        }

                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.leftFrame, newFragment
                        ).addToBackStack(null).commit();

                        return true;
                    }
                }
        );
        //test
//        Fragment newFragment = null;
//        newFragment = new no_content();
//        getSupportFragmentManager().beginTransaction().replace(
//                R.id.rightFrame, newFragment
//        ).addToBackStack(null).commit();


    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }




}