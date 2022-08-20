package com.qxy.demo.utils;

public class UserImformations {
    private static UserImformations userImformations;

    private  UserImformations(){

    }

    private String code="";

    private String token="";

    private String key= "awo0xxllhhzsflua";

    private String secret = "7de1fe94041b07e259d008a5cec19c8c";

    private String open_id ="";

    private String client_token="";

    private String refresh_token="";

    public static UserImformations getInstance(){
        if(userImformations==null){
            userImformations = new UserImformations();
        }

        return userImformations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret(){
        return secret;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getClient_token() {
        return client_token;
    }

    public void setClient_token(String client_token) {
        this.client_token = client_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
