package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;

public class Auth {

    private String code = null;
    private AuthData auth = null;

    public Auth(String code, AuthData data){
        this.code = code;
        this.auth = data;
    }

    public String getCode(String ServerName){
        if (code == null){
            code = new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.GetAuthCode+ServerName);
            return code;
        }else{
            return code;
        }

    }

    public AuthData getAuthData(String Code){

        if (auth != null){
            return auth;
        } else {
            String s = new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.GetAuthData+Code);
            auth = new Gson().fromJson(s, AuthData.class);
            return auth;
        }
    }

    public AuthData getAuthData(){
        return getAuthData(code);
    }

}
