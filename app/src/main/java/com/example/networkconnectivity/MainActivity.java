package com.example.networkconnectivity;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.NetworkStateReceiverListener {

    private static final String TAG = "NetworkStateReceiverM";
    private ConnectivityReceiver networkStateReceiver;      // Receiver that detects network state changes

    ImageView imgViewConnected, imgViewDisConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgViewConnected = findViewById(R.id.imgViewConnected);
        imgViewDisConnected = findViewById(R.id.imgViewDisConnected);

        startNetworkBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        unregisterNetworkBroadcastReceiver(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerNetworkBroadcastReceiver(this);
        super.onResume();
    }

    @Override
    public void networkAvailable() {
        Log.i(TAG, "networkAvailable()");
        //Proceed with online actions in activity (e.g. hide offline UI from user, start services, etc...)
        imgViewConnected.setVisibility(View.VISIBLE);
        imgViewDisConnected.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        Log.i(TAG, "networkUnavailable()");
        //Proceed with offline actions in activity (e.g. sInform user they are offline, stop services, etc...)
        imgViewConnected.setVisibility(View.GONE);
        imgViewDisConnected.setVisibility(View.VISIBLE);
    }

    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new ConnectivityReceiver();
        networkStateReceiver.addListener((ConnectivityReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    /**
     * Register the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     Unregister the NetworkStateReceiver with your activity
     * @param currentContext
     */
    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }
}