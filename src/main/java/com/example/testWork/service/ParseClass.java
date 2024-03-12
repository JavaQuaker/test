package com.example.testWork.service;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class ParseClass {
    public Boolean validUrl(String url) throws MalformedURLException {
        URL parseUrl;

        try {
            parseUrl = new URL(url);
            String normalised = String.format(parseUrl.getProtocol(), parseUrl.getHost(), parseUrl.getPort());
            return true;
        } catch (MalformedURLException e) {
            System.out.println("incorrect Url");
            return false;
        }
    }
}
