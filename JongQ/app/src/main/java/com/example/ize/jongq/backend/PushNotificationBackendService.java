package com.example.ize.jongq.backend;

import com.example.ize.jongq.model.Device;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.PUT;

/**
 * Created by tnpxu on 3/7/2558.
 */
public interface PushNotificationBackendService {

    @PUT("/api/device")
    void registerDevice(@Body Device device, Callback<Device> callback);
}
