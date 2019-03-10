package com.barman.barman.schedule.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

import static com.barman.barman.util.Constants.*;

@Service
public class HttpService implements IHttpService {

    private static final Logger LOG = LoggerFactory.getLogger(HttpService.class);

    @Override
    public String scanImage(byte[] image) {

        String image64 = Base64.getEncoder().encodeToString(image);
        String requestUrl = URL_PREFIX + "?api_key=" + API_KEY + "&api_secret=" + API_SECRET;
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(requestUrl);

        NameValuePair pair = new NameValuePair("image_base64", image64);

        NameValuePair[] pairs = {pair};
        post.setRequestBody(pairs);

        try {
            client.executeMethod(post);
            return post.getResponseBodyAsString();
        } catch (IOException e) {
            LOG.error("Request Failed",e);
        }
        return null;
    }

    static {
        // for localhost testing only
        javax.net.ssl.HttpsURLConnection
                .setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        return true;
                    }
                });
    }
}
