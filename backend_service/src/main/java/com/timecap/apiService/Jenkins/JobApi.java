package com.timecap.apiService.Jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import java.io.IOException;
import java.util.Map;


public class JobApi {

    private JenkinsServer jenkinsServer;

    private JenkinsHttpClient jenkinsHttpClient;


    public JobApi() {
        JenkinsApi jenkinsApi = new JenkinsApi();
        // 连接 Jenkins
        jenkinsServer = JenkinsConnect.connection();
        // 设置客户端连接 Jenkins
        jenkinsHttpClient = JenkinsConnect.getClient();
    }


    public void buildParamJob(Map<String, String> map){
        try {
            jenkinsServer.getJob("new").build(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
