package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.NameValuePair;

public class Request {
    private final String path;
    private final Map<String, String> queryParams = new HashMap<>();

    public Request(BufferedReader in) throws IOException {
        final String requestLine = in.readLine();
        final String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IOException("Invalid request line");
        }

        URI uri = URI.create(parts[1]);
        this.path = uri.getPath();

        List<NameValuePair> params = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
        for (NameValuePair param : params) {
            queryParams.put(param.getName(), param.getValue());
        }
    }

    public String getPath() {
        return path;
    }

    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
}