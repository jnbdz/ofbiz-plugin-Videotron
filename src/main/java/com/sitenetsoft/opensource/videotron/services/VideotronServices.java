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

    static class CustomerCenterSections {
        public static String dashboard = "https://www.videotron.com/client/affaires/dashboard.do";
        public static String facture = "https://www.videotron.com/client/user-management/affaires/facture.do?dispatch=displayFacture";
        public static String paymentPreautorise = "https://www.videotron.com/client/affaires/secur/ppa/start.do";
        public static String rechercherNumerosPourUsage = "https://www.videotron.com/client/affaires/secur/wireless/rechercherNumerosPourUsage.do";
        public static String rechercherUsageDetails = "https://www.videotron.com/client/affaires/secur/wireless/rechercherUsageDetails.do";
        public static String profile = "https://www.videotron.com/client/affaires/profile.do?dispatch=initProfile&appId=EC";
        public static String displayAgreements = "https://www.videotron.com/client/affaires/agreements/secur/DisplayAgreements.action";
        public static String manageDomain = "https://www.videotron.com/client/user-management/affaires/secur/ManageDomain.do?dispatch=initDomainManagement";

        public static String listdevicesimAction = "https://www.videotron.com/client/affaires/linkdevicesim/listdevicesim.action";
    }

    static class CustomerCenterAuth {
        public static String authentication = "https://id.videotron.com/oam/server/authentication";
        public static String authenticationHttpMethod = "POST";

        public static String loginAppResult = "https://id.videotron.com/vl-sso-bin/login-app-result.pl?postAuthn=1";
        public static String loginAppResultHttpMethod = "GET";

        public static String ping = "https://www.videotron.com/client/commun/ping.jsp";
        public static String pingHttpMethod = "GET";

        public static String obrareq = "https://id.videotron.com/obrareq.cgi?encquery="
        public static String obrareqHttpMethod = "GET";

        public static String obrar = "https://www.videotron.com/obrar.cgi?encreply="
        public static String obrarHttpMethod = "GET";

        public static String loginDo = "https://www.videotron.com/client/user-management/affaires/secur/Login.do"
        public static String loginDoHttpMethod = "POST";
    }

    static class LoginFormInputs {
        public static String tokenName = "token_name";
        public static String tokenNameValue = "btoken";

        public static String btoken = "btoken";
        public static String btokenValue = "mwurcAAwpuJZIvjdkNAbiA9hL8ogy6sqEvnr";

        public static String dispatch = "dispatch";
        public static String dispatchValue = "login";

        public static String appId = "appId";
        public static String appIdValue = "EC";

        public static String domain = "domain";
        public static String domainValue = "domainType.business";

        public static String cookieEnabled = "cookieEnabled";
        public static String cookieEnabledValue = "false";

        public static String type = "type";
        public static String typeValue = "SSO-AFF-V1";

        public static String username = "username";
        public static String password = "password";
    }

    public static Map<String, Object> pullDataVideotron(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();

        CustomerCenterSections customerCenterSections = new CustomerCenterSections();
        LoginFormInputs loginFormInputs = new LoginFormInputs();

        try {

            try {
                // Get the Cookie that is initialized in the login form page
                Connection.Response loginForm = Jsoup.connect(customerCenterSections.dashboard)
                        .method(Connection.Method.GET)
                        .execute();

                Document document = Jsoup.connect(customerCenterSections.dashboard)
                        //.data("cookieexists", "false")
                        .data(LoginFormInputs.tokenName, LoginFormInputs.tokenNameValue)
                        .data(LoginFormInputs.btoken, LoginFormInputs.btokenValue)
                        .data(LoginFormInputs.dispatch, LoginFormInputs.dispatchValue)
                        .data(LoginFormInputs.appId, LoginFormInputs.appIdValue)
                        .data(LoginFormInputs.domain, LoginFormInputs.domainValue)
                        .data(LoginFormInputs.cookieEnabled, LoginFormInputs.cookieEnabledValue)
                        .data(LoginFormInputs.type, LoginFormInputs.typeValue)
                        .data(LoginFormInputs.username, username)
                        .data(LoginFormInputs.password, password)
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