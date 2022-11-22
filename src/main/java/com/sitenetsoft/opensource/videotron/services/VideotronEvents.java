package com.sitenetsoft.opensource.videotron.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VideotronEvents {

    public static final String module = VideotronEvents.class.getName();

    public static String createOfbizVideotronEvent(HttpServletRequest request, HttpServletResponse response) {

        String result = "";
        try {
            Document doc = Jsoup.connect("http://example.com").get();
            doc.select("p").forEach(System.out::println);
            result = doc.select("p").toString();
        } catch (Exception exception) {
            System.out.println(exception);
        }

        /*Connection.Response loginForm = Jsoup.connect("https://www.desco.org.bd/ebill/login.php")
        .method(Connection.Method.GET)
        .execute();

        Document document = Jsoup.connect("https://www.desco.org.bd/ebill/authentication.php")
        .data("cookieexists", "false")
        .data("username", "32007702")
        .data("login", "Login")
        .cookies(loginForm.cookies())
        .post();
        System.out.println(document);*/

        return result;
    }

}