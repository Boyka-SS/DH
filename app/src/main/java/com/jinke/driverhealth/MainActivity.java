package com.jinke.driverhealth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(mBottomNavigationView.getMenu()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(mBottomNavigationView, navController);

    }

}