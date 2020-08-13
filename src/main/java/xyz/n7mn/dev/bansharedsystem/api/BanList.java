package xyz.n7mn.dev.bansharedsystem.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import xyz.n7mn.dev.bansharedsystem.api.result.BanShareData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BanList {

    @Deprecated
    public List<BanShareData> getList(AuthData data){
        return getListByServer(data);
    }

    public List<BanShareData> getAllList(){
        List<BanShareData> list = new Gson().fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanList), new TypeToken<Collection<BanShareData>>(){}.getType());
        return list;
    }

    public List<BanShareData> getListByServer(AuthData data){
        List<BanShareData> list = new ArrayList<>();

        if (data != null && data.getServerUUID() != null){
            list = new Gson().fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+data.getServerUUID().toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getListByServer(UUID serverUUID){
        List<BanShareData> list = new ArrayList<>();

        if (serverUUID != null){
            list = new Gson().fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverUUID.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getListByServer(String serverName){
        List<BanShareData> list = new ArrayList<>();

        if (serverName != null){
            list = new Gson().fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverName+"&mode=name"), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getListByUser(UUID uuid){
        if (uuid != null){
            return new Gson().fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByUser+uuid.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return new ArrayList<>();
    }
}
