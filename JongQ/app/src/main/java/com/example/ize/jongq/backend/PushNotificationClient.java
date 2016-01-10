package com.example.ize.jongq.backend;

import com.example.ize.jongq.model.Device;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;

public class PushNotificationClient implements PushNotificationBackendService {
    private PushNotificationBackendService client;

    public PushNotificationClient(String backendBaseUrl) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(backendBaseUrl)
                .build();
        client = restAdapter.create(PushNotificationBackendService.class);
    }

    @Override
    public void registerDevice(@Body Device device, Callback<Device> callback) {
        client.registerDevice(device, callback);
    }
}
