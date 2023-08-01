package com.example.fyp;

public class GetIP {


    String ip;
    private static final GetIP instance = new GetIP();

    public static GetIP getInstance(){
        return instance;

    }
    public GetIP(){
        super();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
