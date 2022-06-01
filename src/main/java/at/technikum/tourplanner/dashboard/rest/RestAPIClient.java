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
        String response = doRequest(buildGetRequest(url));
        return jsonConverter.fromJsonAndTypeRef(response, responseClass);
    }

    public <T> T getRequest(String url, Class<T> responseClass) {
        String response = doRequest(buildGetRequest(url));
        return jsonConverter.fromJsonAndClass(response, responseClass);
    }

    public <T> T postRequest(String url, Object bodyObject, TypeReference<T> responseClass) {
        String jsonBody = jsonConverter.toJson(bodyObject);
        String response = doRequest(buildPostRequest(url, jsonBody));
        return jsonConverter.fromJsonAndTypeRef(response, responseClass);
    }

    public <T> T postRequest(String url, Object bodyObject, Class<T> responseClass) {
        String jsonBody = jsonConverter.toJson(bodyObject);
        String response = doRequest(buildPostRequest(url, jsonBody));
        return jsonConverter.fromJsonAndClass(response, responseClass);
    }

    private HttpRequest buildGetRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    private HttpRequest buildPostRequest(String url, String jsonBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    private String doRequest(HttpRequest request) {
        try {
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
