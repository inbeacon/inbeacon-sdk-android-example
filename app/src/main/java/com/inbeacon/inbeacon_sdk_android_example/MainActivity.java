package com.inbeacon.inbeacon_sdk_android_example;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inbeacon.sdk.InbeaconManager;
import com.inbeacon.sdk.VerifiedCapability;

import java.util.HashMap;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Bundle extras=intent.getExtras();
            Log.w("receiver", "Got action=" + intent.getAction() + " extras=" + extras);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lets see if the device is capable
        VerifiedCapability verifiedCaps=InbeaconManager.getSharedInstance().verifyCapabilities();
        if (verifiedCaps!=VerifiedCapability.CAP_OK) {
            switch (verifiedCaps) {
                case CAP_BLUETOOTH_DISABLED:
                    Log.e(TAG,"This device has bluetooth turned off");
                    break;
                case CAP_BLUETOOTH_LE_NOT_AVAILABLE:
                    Log.e(TAG,"This device does not support bluetooth LE needed for iBeacons");
                    break;
                case CAP_SDK_TOO_OLD:
                    Log.e(TAG,"This device SDK is too old");
                    break;
                default:
                    Log.e(TAG,"This device does not support inBeacon for an unknown reason");
                    break;
            }
        }
        else
            Log.w(TAG,"This device supports iBeacons and the inBeacon SDK");


        // get all notifications
        IntentFilter myIntentFilter=new IntentFilter();
        myIntentFilter.addAction("com.inbeacon.sdk.event.enterregion");
        myIntentFilter.addAction("com.inbeacon.sdk.event.exitregion");
        myIntentFilter.addAction("com.inbeacon.sdk.event.enterlocation");
        myIntentFilter.addAction("com.inbeacon.sdk.event.exitlocation");
        myIntentFilter.addAction("com.inbeacon.sdk.event.regionsupdate");
        myIntentFilter.addAction("com.inbeacon.sdk.event.enterproximity");
        myIntentFilter.addAction("com.inbeacon.sdk.event.exitproximity");
        myIntentFilter.addAction("com.inbeacon.sdk.event.proximity");
        myIntentFilter.addAction("com.inbeacon.sdk.event.appevent");
        myIntentFilter.addAction("com.inbeacon.sdk.event.appaction");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                myIntentFilter );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
