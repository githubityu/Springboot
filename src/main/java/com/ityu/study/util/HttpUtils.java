package com.ityu.study.util;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author lihe
 */
@SuppressWarnings("unused")
@UtilityClass
@Slf4j
public class HttpUtils {


    public static String doGet(String url) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return send(req);

    }

    public static String doPostForm(String url, Map<String, String> params) {
        var sb = new StringBuilder();
        params.forEach((key, value) -> sb.append(URLEncoder.encode(key, StandardCharsets.UTF_8)).append("=")
                .append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append("&"));
        sb.deleteCharAt(sb.length() - 1);
        var body = sb.toString();

        var req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return send(req);
    }

    public static String doPostJson(String url, Map<String, Object> params) {
        var req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(params)))
                .build();
        return send(req);
    }


    private static String send(HttpRequest req) {
        var client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.body();
        } catch (Exception e) {
            log.error("err happened when request send", e);
        }
        return null;
    }


}
