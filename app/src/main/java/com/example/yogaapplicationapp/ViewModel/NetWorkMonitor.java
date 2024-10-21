package com.example.yogaapplicationapp.ViewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.widget.Toast;

import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NetWorkMonitor extends ConnectivityManager.NetworkCallback {
    private Context context;
    synchronizeData synchronizeData;
    public NetWorkMonitor(Context context) {
        this.context = context;
        this.synchronizeData = new synchronizeData(context);
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Toast.makeText(context, "Net Work Connected ", Toast.LENGTH_SHORT).show();
        onNetworkAvailable();
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        // Network is lost
//        Log.d("NetworkMonitor", "Network is lost");
        // Call your function here
        Toast.makeText(context, "LOST NETWOKE ", Toast.LENGTH_SHORT).show();
        onNetworkLost();
    }

    private void onNetworkAvailable() {
        // Your code when network is available
        synchronizeData.Synchronize();
        synchronizeData.synDeleteCourses();
        synchronizeData.synDeleteYogaClasses();
    }

    private void onNetworkLost() {
        // Your code when network is lost
    }

    public static void implementCheckInternet (Activity activity,Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        NetWorkMonitor networkMonitor = new NetWorkMonitor(context);
        connectivityManager.registerNetworkCallback(networkRequest, networkMonitor);
    }


}
