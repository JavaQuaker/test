package com.example.testWork.service;
import java.net.MalformedURLException;
import java.net.URL;


public class ParseClass {
    public static String parseUrl(String url) throws MalformedURLException {
        URL parsedUrl;
        try {
            parsedUrl = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("incorrect url");
            return null;
        }
        String normalised = String.format("%s://%%s",
                        parsedUrl.getProtocol(),
                        parsedUrl.getHost(),
                        parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()
                )
                .toLowerCase();
        return normalised;
    }
}
