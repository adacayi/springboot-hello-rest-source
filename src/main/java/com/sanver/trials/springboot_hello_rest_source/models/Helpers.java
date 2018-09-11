package com.sanver.trials.springboot_hello_rest_source.models;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Helpers {
    public static String getIp() {
        String hostname;

        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }

        return hostname;
    }
}

