package one.nem.tracker_kadai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        } else if (selectedItemId == R.id.menu_route) {
                            newFragment = new route();
                        }

                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.leftFrame, newFragment
                        ).addToBackStack(null).commit();

                        return true;
                    }
                }
        );


    }
}