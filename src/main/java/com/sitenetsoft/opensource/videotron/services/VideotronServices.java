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

    class CustomerCenterSections {
        public String dashboard = "https://www.videotron.com/client/affaires/dashboard.do";
        public String facture = "https://www.videotron.com/client/user-management/affaires/facture.do?dispatch=displayFacture";
        public String paymentPreautorise = "https://www.videotron.com/client/affaires/secur/ppa/start.do";
        public String rechercherNumerosPourUsage = "https://www.videotron.com/client/affaires/secur/wireless/rechercherNumerosPourUsage.do";
        public String rechercherUsageDetails = "https://www.videotron.com/client/affaires/secur/wireless/rechercherUsageDetails.do";
        public String profile = "https://www.videotron.com/client/affaires/profile.do?dispatch=initProfile&appId=EC";
        public String displayAgreements = "https://www.videotron.com/client/affaires/agreements/secur/DisplayAgreements.action";
        public String manageDomain = "https://www.videotron.com/client/user-management/affaires/secur/ManageDomain.do?dispatch=initDomainManagement";

        public String = "https://www.videotron.com/client/affaires/linkdevicesim/listdevicesim.action"
    }

    class LoginFormInputs {
        public String tokenName = "token_name";
        public String tokenNameValue = "btoken";

        public String btoken = "btoken"
        public String btokenValue = "mwurcAAwpuJZIvjdkNAbiA9hL8ogy6sqEvnr";

        public String dispatch = "dispatch";
        public String dispatchValue = "login";

        public String appId = "appId";
        public String appIdValue = "EC";

        public String domain = "domain";
        public String domainValue = "domainType.business";

        public String cookieEnabled = "cookieEnabled";
        public String cookieEnabledValue = "false";

        public String type = "type";
        public String typeValue = "SSO-AFF-V1";

        public String username = "username";
        public String password = "password";
    }

    public static Map<String, Object> pullDataVideotron(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();

        try {

            try {
                /*Connection.Response loginForm = Jsoup.connect(CustomerCenterSections.class.dashboard)
                        .method(Connection.Method.GET)
                        .execute();*/

                Document document = Jsoup.connect(CustomerCenterSections.class.dashboard)
                        .data("cookieexists", "false")
                        .data(LoginFormInputs.class.username, this.username)
                        .data(LoginFormInputs.class.password, this.password)
                        .cookies(loginForm.cookies())
                        .post();
                System.out.println(document);
            } catch (Exception exception) {
                System.out.println(exception);
            }

            /*try {
                Document doc = Jsoup.connect("http://example.com").get();
                doc.select("p").forEach(System.out::println);
                System.out.println(doc.select("p").toString());
            } catch (Exception exception) {
                System.out.println(exception);
            }*/

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