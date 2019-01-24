package com.ingsw1718.ingswinspectorapp.logic;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ingsw1718.ingswinspectorapp.R;
import com.ingsw1718.ingswinspectorapp.logic.MainMenuActivity;

/**
 * Created by rierv on 10/02/2018.
 */

public abstract class InspectorAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed()   {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    protected boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void showNoConnectionToast() {
        Toast.makeText(this, getText(R.string.connectionUnavailable), Toast.LENGTH_SHORT).show();
    }

    public void removeAllFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment: fm.getFragments()) {
            ft.remove(fragment);
        }
        ft.commit();
    }

    public void openFragment(Fragment fragment) {
        openFragment(fragment, R.id.fragmentContainer);
    }

    public void openFragment(Fragment fragment, int fragmentContainer) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(fragmentContainer, fragment).addToBackStack("").commit();
    }
}
