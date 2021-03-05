package com.googlefitdemo.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

class GoogleFitUtil {

    private static final String TAG = "GoogleFitUtil";

    /*
     * Can get all the device details
     * DataType.TYPE_LOCATION_SAMPLE : Use this data type for the location sample from where your data is uploading
     */
    public static void getDeviceDetails(DataType dataType, GoogleSignInAccount googleSignInAccount, AppCompatActivity activity) {
        Fitness.getSensorsClient(activity, googleSignInAccount)
                .findDataSources(
                        new DataSourcesRequest.Builder()
                                .setDataTypes(dataType)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener(new OnSuccessListener<List<DataSource>>() {
                    @Override
                    public void onSuccess(List<DataSource> dataSources) {
                        Log.e(TAG, "getSensorsClient " + dataSources.size());
                        for (int i = 0; i < dataSources.size(); i++) {
                            Log.e(TAG, "getSensorsClient " + dataSources.get(i).getDevice().getManufacturer());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "getSensorsClient " + e.getLocalizedMessage());

                    }
                });
    }

    public static void readPersonalDetails() {
        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .read(DataType.TYPE_WEIGHT)
                .setLimit(1)
                .setTimeRange(1, Calendar.getInstance().getTimeInMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    /*
     * You can unsubscribe the specific DataType
     */
    public static void unSubscribe(DataType dataType, GoogleSignInAccount googleSignInAccount, AppCompatActivity activity) {
        Fitness.getRecordingClient(activity, googleSignInAccount)
                .unsubscribe(dataType)
                .addOnSuccessListener(aVoid -> {
                    Log.e(TAG, "UnSubscribed");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failure " + e.getLocalizedMessage());
                });
    }
}
