package at.technikum.tourplanner.dashboard.rest;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestAPIClient {

    private final HttpClient httpClient;
    private final JsonConverter jsonConverter;

    public RestAPIClient(HttpClient httpClient, JsonConverter jsonConverter) {
        this.httpClient = httpClient;
        this.jsonConverter = jsonConverter;
    }

    public <T> T getRequest(String url, TypeReference<T> responseClass) {
        String response = getRequest(url);
        return jsonConverter.fromJsonAndTypeRef(response, responseClass);
    }

    public <T> T getRequest(String url, Class<T> responseClass) {
        String response = getRequest(url);
        return jsonConverter.fromJsonAndClass(response, responseClass);
    }

    public <T> T postRequest(String url, Object bodyObject, TypeReference<T> responseClass) {
        String jsonBody = jsonConverter.toJson(bodyObject);
        String response = postRequest(url, jsonBody);
        return jsonConverter.fromJsonAndTypeRef(response, responseClass);
    }

    public <T> T postRequest(String url, Object bodyObject, Class<T> responseClass) {
        String jsonBody = jsonConverter.toJson(bodyObject);
        String response = postRequest(url, jsonBody);
        return jsonConverter.fromJsonAndClass(response, responseClass);
    }

    private String getRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private String postRequest(String url, String jsonBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return null;
    }


}
