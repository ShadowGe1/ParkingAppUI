package com.example.parkingsystemapp.data.remote;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.parkingsystemapp.data.repository.AuthRepository;

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

public class SignInClient {

    private static final String LOGIN_URL = "http://192.168.0.127:8080/signin";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String TAG = "LoginTask";

    private final String email;
    private final String password;
    private final AuthRepository authRepository;

    public SignInClient(String email, String password, AuthRepository authRepository) {
        this.email = email;
        this.password = password;
        this.authRepository = authRepository;
    }

    public interface LoginCallback {
        void onResponse(boolean success, String message);
    }

    public void execute(LoginCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Response response = null;
            try {
                // Creează JSON-ul pentru request
                JSONObject requestBodyJson = new JSONObject();
                requestBodyJson.put("email", email);
                requestBodyJson.put("password", password);

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
                    Log.e(TAG, "Login failed: " + response.code());
                    Response finalResponse = response;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        authRepository.setSuccess(false);
                        authRepository.setMessage("Login failed with code: " + finalResponse.code());
                        callback.onResponse(false, "Login failed with code: " + finalResponse.code());
                    });
                    return;
                }

                // Citește corpul răspunsului JSON
                String responseBody = response.body() != null ? response.body().string() : null;
                if (responseBody != null) {
                    // Parsează răspunsul JSON
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    boolean success = jsonResponse.optBoolean("success", false); // Preia valoarea success
                    String message = jsonResponse.optString("message", ""); // Preia mesajul

                    // Transmite răspunsul prin callback
                    new Handler(Looper.getMainLooper()).post(() -> {
                        authRepository.setSuccess(success);
                        authRepository.setMessage(message);
                        callback.onResponse(success, message);
                    });
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error during login request", e);
                new Handler(Looper.getMainLooper()).post(() -> {
                    authRepository.setSuccess(false);
                    authRepository.setMessage("Error: " + e.getMessage());
                    callback.onResponse(false, "Error: " + e.getMessage());
                });
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
    }
}
