package com.dmitryshundrik.knowledgebase.service.integration;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SpotifyService {

    public static void main(String[] args) throws IOException, InterruptedException {
        getDisplayName();
        signIn();
    }

    public static String getDisplayName() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spotify.com/v1/users/b15i0r7vy47onucyg1j4oeg7c"))
                .GET()
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString(("baeldung:123456").getBytes()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return "";
    }


    public static String signIn() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("dmitryshundrik@mail.ru:4815162342spotify".getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return "";
    }
}
