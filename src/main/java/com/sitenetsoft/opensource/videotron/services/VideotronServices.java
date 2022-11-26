package com.sitenetsoft.opensource.videotron.services;

import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.ServiceUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VideotronServices {

    public static final String module = VideotronServices.class.getName();

    public static Map<String, Object> pullDataVideotron(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();
        try {

            try {
                Document doc = Jsoup.connect("http://example.com").get();
                doc.select("p").forEach(System.out::println);
                System.out.println(doc.select("p").toString());
            } catch (Exception exception) {
                System.out.println(exception);
            }

            GenericValue videotron = delegator.makeValue("VideotronInvoices");
            // Auto generating next sequence of ofbizDemoId primary key
            videotron.setNextSeqId();
            // Setting up all non primary key field values from context map
            videotron.setNonPKFields(context);
            // Creating record in database for OfbizDemo entity for prepared value
            videotron = delegator.create(videotron);
            result.put("videotronId", videotron.getString("videotronId"));
            Debug.log("==========This is my first Java Service implementation in Apache OFBiz. Videotron record created successfully with ofbizDemoId: " + videotron.getString("videotronId"));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError("Error in creating record in Videotron Invoice entity ........" +module);
        }
        return result;

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
    }

}