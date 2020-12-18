package com.timecap.apiService.Jenkins;

import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

//import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JenkinsPost {

    public void post(String from, String to, String content, String path, String opendate) throws Exception {

        String protocol = "http";
        String host = "localhost";
        int port = 8080;
        String usernName = "xtj0824";
        String password = "XTJadgjmptw123!";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getCredentialsProvider().setCredentials(
                new AuthScope(host, port),
                new UsernamePasswordCredentials(usernName, password));

        String jenkinsUrl = protocol + "://" + host + ":" + port + "/";

        try {
            // get the crumb from Jenkins
            // do this only once per HTTP session
            // keep the crumb for every coming request
            System.out.println("... issue crumb");
            HttpGet httpGet = new HttpGet(jenkinsUrl + "crumbIssuer/api/json");
            String crumbResponse= toString(httpclient, httpGet);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(crumbResponse);
            String[] date = opendate.split("-");
            String year = date[0];
            String month = date[1];
            String day = date[2];
            String url = jenkinsUrl + "job/new/buildWithParameters?"
                    + "from=" + from
                    + "&to=" + to
                    + "&content=" + content
                    + "&path=" + path
                    + "&year=" + year
                    + "&month=" + month
                    + "&day=" + day;
            System.out.println(url);
            HttpPost httpost = new HttpPost(url);
            httpost.addHeader(json.get("crumbRequestField").toString(), json.get("crumb").toString());
            toString(httpclient, httpost);

        } finally {
            httpclient.getConnectionManager().shutdown();
        }

    }

    // helper construct to deserialize crumb json into
    public static class CrumbJson {
        public String crumb;
        public String crumbRequestField;
    }

    private static String toString(DefaultHttpClient client,
                                   HttpRequestBase request) throws Exception {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = client.execute(request, responseHandler);
        System.out.println(responseBody + "\n");
        return responseBody;
    }

}