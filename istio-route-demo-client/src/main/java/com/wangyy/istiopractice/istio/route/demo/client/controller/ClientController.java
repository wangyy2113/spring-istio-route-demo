package com.wangyy.istiopractice.istio.route.demo.client.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/greet")
public class ClientController {

    private static final String SERVER_IP = "istio-route-demo-server";

    private static final int SERVER_HTTP_PORT = 18080;

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    @GetMapping("/http")
    public Object http(@RequestParam(value = "featureTag", defaultValue = "v1") String featureTag) {
        String url = "http://" + SERVER_IP + ":" + SERVER_HTTP_PORT + "/http/greet";
        Request request = new Request.Builder()
                .header("featureTag", featureTag)
                .url(url)
                .get()
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return "Client receive Server msg: " + response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
