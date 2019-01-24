package com.ingsw1718.ingswinspectorapp.logic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.ingsw1718.ingswinspectorapp.ui.fragments.mainmenu.*;

import com.ingsw1718.ingswinspectorapp.R;

public class MainMenuActivity extends InspectorAppActivity implements MainMenuEventsListener {
    Fragment mainMenuFragment = null;
    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(mainMenuFragment == null) mainMenuFragment = MainMenuFragment.newInstance();
        fragmentTransaction.replace(R.id.fragmentContainer, mainMenuFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mainMenuFragment = MainMenuFragment.newInstance();
        openFragment(mainMenuFragment);
    }

    public void startDownloadingTickets() {
        Intent intent = new Intent(this, DownloadTicketsActivity.class);
        startActivity(intent);
    }

    public void startControllingTickets() {
        Intent intent = new Intent(this, ControlTicketsActivity.class);
        startActivity(intent);
    }

    public void startUploadingTickets() {
        Intent intent = new Intent(this, UploadControlsActivity.class);
        startActivity(intent);
    }

}