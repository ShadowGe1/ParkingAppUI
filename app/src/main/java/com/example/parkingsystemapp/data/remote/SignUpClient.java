package com.example.parkingsystemapp.data.remote;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.parkingsystemapp.data.repository.RegisterRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpClient {
    private static final String LOGIN_URL = "http://192.168.0.127:8080/signup";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String TAG = "RegisterTask";

    private final String username;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final String password;
    private final String againPassword;
    private final RegisterRepository registerRepository;

    public SignUpClient(String username, String name, String surname, String email, String phone, String password, String againPassword, RegisterRepository registerRepository) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.againPassword = againPassword;
        this.registerRepository = registerRepository;
    }

    public interface LoginCallback {
        void onResponse(boolean success, String message, String errorCode);
    }

    public void execute(LoginCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            try {
                // Creează JSON-ul pentru request
                JSONObject requestBodyJson = new JSONObject();
                requestBodyJson.put("username", username);
                requestBodyJson.put("name", name);
                requestBodyJson.put("surname", surname);
                requestBodyJson.put("email", email);
                requestBodyJson.put("phone", phone);
                requestBodyJson.put("password", password);
                requestBodyJson.put("againPassword", againPassword);

                // Creează corpul request-ului
                RequestBody body = RequestBody.create(requestBodyJson.toString(), JSON);

                // Creează request-ul
                Request request = new Request.Builder()
                        .url(LOGIN_URL)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();

                // Trimite request-ul și obține răspunsul
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Registration failed: " + response.code());
                    Response finalResponse = response;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        registerRepository.setSuccess(false);
                        registerRepository.setMessage("Register failed !!!");
                        registerRepository.setErrorCode("Error code: " + finalResponse.code());
                        callback.onResponse(false, "Register failed !!!", "Error code: " + finalResponse.code());
                    });
                    return;
                }

                // Citește corpul răspunsului JSON
                String responseBody = response.body() != null ? response.body().string() : null;
                if (responseBody != null) {
                    // Parsează răspunsul JSON
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    boolean success = jsonResponse.optBoolean("success", false);
                    String message = jsonResponse.optString("message", "");
                    String errorCode = jsonResponse.optString("errorCode", "");

                    // Transmite răspunsul prin callback
                    new Handler(Looper.getMainLooper()).post(() -> {
                        registerRepository.setSuccess(success);
                        registerRepository.setMessage(message);
                        registerRepository.setErrorCode(errorCode);
                        callback.onResponse(success, message, errorCode);
                    });
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error during registration request", e);
                new Handler(Looper.getMainLooper()).post(() -> {
                    registerRepository.setSuccess(false);
                    registerRepository.setMessage("Error");
                    registerRepository.setErrorCode("Error Code: " + e.getMessage());
                    callback.onResponse(false, "Error" ,"Error Code: " + e.getMessage());
                });
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
    }
}
