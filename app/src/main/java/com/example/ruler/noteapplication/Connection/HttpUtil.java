package com.example.ruler.noteapplication.Connection;

public class HttpUtil {
    private String BASEURL;

    public HttpUtil(String baseurl) {
        BASEURL = baseurl;
    }

    public String getBASEURL() {
        return BASEURL;
    }

    public void setBASEURL(String BASEURL) {
        this.BASEURL = BASEURL;
    }
}
