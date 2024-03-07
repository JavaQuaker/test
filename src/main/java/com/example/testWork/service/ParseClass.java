package com.example.testWork.service;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

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
//        String normalised = String.format("%s://%%s",
//                        parsedUrl.getProtocol(),
//                        parsedUrl.getHost(),
//                        parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()
//                )
//                .toLowerCase();
//        return normalised;


    }
}
