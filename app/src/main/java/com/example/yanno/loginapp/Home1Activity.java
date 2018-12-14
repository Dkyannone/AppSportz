package com.example.yanno.loginapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Home1Activity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    setTitle("Profile"); // set the title of action bar
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fram, profileFragment, "FragmentName"); // fram is id for home activity frame layout
                    fragmentTransaction1.commit(); // commit fragment
                    return true;
                case R.id.navigation_home:
                    setTitle("Feed");
                    FeedFragment feedFragment = new FeedFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fram, feedFragment, "FragmentName");
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_myTeam:
                    setTitle("Profile");
                    MyTeamFragment myTeamFragment = new MyTeamFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fram, myTeamFragment, "FragmentName");
                    fragmentTransaction3.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //when activity starts profile fragment will be visible
        setTitle("Profile");
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.fram, profileFragment, "FragmentName");
        fragmentTransaction1.commit();
    }

}
