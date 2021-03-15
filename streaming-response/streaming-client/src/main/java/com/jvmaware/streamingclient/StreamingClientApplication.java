package com.jvmaware.streamingclient;

import com.jvmaware.streamingclient.clients.Client;
import com.jvmaware.streamingclient.clients.RestTemplateClient;

public class StreamingClientApplication {

    public static void main(String[] args) {
        Client restTemplateBasedClient = new RestTemplateClient();
        //Client restTemplateBasedClient = new SpringWebClient();
        restTemplateBasedClient.start();
    }
}
