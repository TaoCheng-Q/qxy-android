package com.qxy.demo.utils;

public class UserImformations {
    private static UserImformations userImformations;

    private  UserImformations(){

    }

    private String code;

    private String token;

    private String key= "awo0xxllhhzsflua";

    private String secret = "7de1fe94041b07e259d008a5cec19c8c";

    private String open_id ;

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
}