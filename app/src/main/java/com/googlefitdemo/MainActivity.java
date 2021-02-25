package com.googlefitdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.googlefitdemo.databinding.ActivityMainBinding;
import com.googlefitdemo.permissionUtil.PermissionManager;
import com.googlefitdemo.permissionUtil.Permissions;
import com.googlefitdemo.utils.CommonUtils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements PermissionManager.PermissionListener, OnSuccessListener {

    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;

    private final String TAG = "MainActivity";

    private FitnessOptions fitnessOptions;

    private FitnessDataResponse fitnessDataResponse;

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        checkPermissions();

        fitnessDataResponse = new FitnessDataResponse();
    }

    private void checkPermissions() {
        if (!PermissionManager.hasPermissions(this, Permissions.LOCATION_PERMISSION)) {
            PermissionManager.requestPermissions(this, this, "", Permissions.LOCATION_PERMISSION);
        } else {
            checkGoogleFitPermission();
        }
    }

    private void checkGoogleFitPermission() {

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .build();
        GoogleSignInAccount account = getGoogleAccount();

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    MainActivity.this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                    account,
                    fitnessOptions);
        } else {
            getTodayData();
        }

    }

    private void getTodayData() {
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(this);
    }


    private void requestForHistory() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();

        cal.set(2014, 9, 28);
        cal.set(Calendar.HOUR_OF_DAY, 0); //so it get all day and not the current hour
        cal.set(Calendar.MINUTE, 0); //so it get all day and not the current minute
        cal.set(Calendar.SECOND, 0); //so it get all day and not the current second
        long startTime = cal.getTimeInMillis();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault());


        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED)
                .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
                .aggregate(DataType.TYPE_DISTANCE_DELTA)
                .aggregate(DataType.AGGREGATE_DISTANCE_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(this, getGoogleAccount())
                .readData(readRequest)
                .addOnSuccessListener(this);
    }

    private GoogleSignInAccount getGoogleAccount() {
        return GoogleSignIn.getAccountForExtension(MainActivity.this, fitnessOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            getTodayData();
        }
    }

    @Override
    public void onPermissionsGranted(List<String> perms) {
        if (perms != null && perms.size() == Permissions.LOCATION_PERMISSION.length) {
            checkGoogleFitPermission();
        }
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {
        if (perms.size() > 0) {
            PermissionManager.requestPermissions(this, this, "", Permissions.LOCATION_PERMISSION);
        }
    }

    @Override
    public void onPermissionNeverAsked(List<String> perms) {
        CommonUtils.openSettingForPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, this, requestCode, permissions, grantResults);
    }


    @Override
    public void onSuccess(Object o) {
        if (o instanceof DataSet) {
            DataSet dataSet = (DataSet) o;
            if (dataSet != null) {
                getDataFromDataSet(dataSet);
            }
        } else if (o instanceof DataReadResponse) {
            DataReadResponse dataReadResponse = (DataReadResponse) o;
            if (dataReadResponse.getBuckets() != null && !dataReadResponse.getBuckets().isEmpty()) {
                List<Bucket> bucketList = dataReadResponse.getBuckets();

                if (bucketList != null && !bucketList.isEmpty()) {
                    for (Bucket bucket : bucketList) {
                        DataSet stepsDataSet = bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                        getDataFromDataSet(stepsDataSet);
                        DataSet caloriesDataSet = bucket.getDataSet(DataType.TYPE_CALORIES_EXPENDED);
                        getDataFromDataSet(caloriesDataSet);
                        DataSet distanceDataSet = bucket.getDataSet(DataType.TYPE_DISTANCE_DELTA);
                        getDataFromDataSet(distanceDataSet);
                    }
                }
            }
        }

    }

    private void getDataFromDataSet(DataSet dataSet) {

        List<DataPoint> dataPoints = dataSet.getDataPoints();
        for (DataPoint dataPoint : dataPoints) {
            for (Field field : dataPoint.getDataType().getFields()) {

                float value = Float.parseFloat(dataPoint.getValue(field).toString());
                Log.e(TAG, " data : " + value);

                if (field.getName().equals(Field.FIELD_STEPS.getName())) {
                    fitnessDataResponse.steps = value+fitnessDataResponse.steps;
                } else if (field.getName().equals(Field.FIELD_CALORIES.getName())) {
                    fitnessDataResponse.calories = value+fitnessDataResponse.calories;
                } else if (field.getName().equals(Field.FIELD_DISTANCE.getName())) {
                    fitnessDataResponse.distance = value+fitnessDataResponse.distance;
                }

            }
        }
        activityMainBinding.setFitnessData(fitnessDataResponse);
    }
}