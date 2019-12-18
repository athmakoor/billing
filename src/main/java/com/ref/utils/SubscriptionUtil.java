package com.ref.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.ref.bean.ResponseRedirectDTO;
import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.bean.jpa.ProductEntity;
import com.ref.bean.jpa.RequestTrackerEntity;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public final class SubscriptionUtil {
    private SubscriptionUtil() {

    }

    public static String updateSubscribeUrl(String url, SubscriptionDTO data) {
        String updatedUrl = url;

        updatedUrl = updatedUrl.replace("@productId@", data.getPackageid());
        updatedUrl = updatedUrl.replace("@transno@", data.getTransno());

        return updatedUrl;
    }

    public static String updateSubscribeUrl(String url, RequestTrackerEntity data, ProductEntity productEntity) {
        String updatedUrl = url;

        updatedUrl = updatedUrl.replace("@productId@", productEntity.getProductId());
        updatedUrl = updatedUrl.replace("@transno@", String.valueOf(data.getId()));

        return updatedUrl;
    }

    public static String updateUnsubscribeUrl(String url, UnsubscribeDTO data) {
        String updatedUrl = url;

        updatedUrl = updatedUrl.replace("@productId@", data.getPackageid());
        updatedUrl = updatedUrl.replace("@transno@", data.getTransno());
        updatedUrl = updatedUrl.replace("@MSISDN@", data.getMsisdn());

        return updatedUrl;
    }

    public static String getRequest(String url) throws IOException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Connection", "Keep-Alive");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

            return response.toString();
        } else {
            System.out.println(url + ": GET request didn't work");
        }

        return null;
    }

    public static String updateRedirectUrl(String redirectUrl, ResponseRedirectDTO responseRedirectDTO, RequestTrackerEntity requestTrackerEntity) {
        String newUrl = redirectUrl;

        newUrl = newUrl + "?msisdn=" + responseRedirectDTO.getMsisdn() + "&status=" + responseRedirectDTO.getStatus()
                + "&transno=" + requestTrackerEntity.getProductTrackId() + "&packageid=" + requestTrackerEntity.getPackageId();

        return newUrl;
    }
}
