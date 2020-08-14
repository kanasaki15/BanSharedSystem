package xyz.n7mn.dev.bansharedsystem.api.auth;

import com.google.gson.Gson;
import xyz.n7mn.dev.bansharedsystem.api.APIURL;
import xyz.n7mn.dev.bansharedsystem.api.Function;
import xyz.n7mn.dev.bansharedsystem.api.Http;

public class Auth {

    private String code = null;
    private AuthData auth = null;

    public Auth(){

    }

    public Auth(String code, AuthData data){
        this.code = code;
        this.auth = data;
    }

    public String getCode(String ServerName){
        if (code == null){
            code = new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.GetAuthCode+ServerName+"&lib="+new Function().getUseLibVer());
            return code;
        }else{
            return code;
        }

    }

    public AuthData getAuthData(String Code){

        if (auth != null){
            return auth;
        } else {
            String s = new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.GetAuthData+Code+"&lib="+new Function().getUseLibVer());
            auth = new Gson().fromJson(s, AuthData.class);
            return auth;
        }
    }

    public AuthData getAuthData(){
        return getAuthData(code);
    }

}
