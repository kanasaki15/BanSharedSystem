package xyz.n7mn.dev.bansharedsystem.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.Bukkit;
import xyz.n7mn.dev.bansharedsystem.api.result.BanShareData;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BanList {

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

        @Override
        public Date deserialize(JsonElement dateElement, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("JST"));
            String date = dateElement.getAsString();
            try {
                Date d = sdf.parse(date);
                return d;
            } catch (ParseException e) {
                Bukkit.getLogger().info(String.format("Gsonの処理内で日付のパースに失敗しました。: %s", date));
                return null;
            }
        }
    }).setPrettyPrinting().create();

    @Deprecated
    public List<BanShareData> getShareList(AuthData data){
        return getShareListByServer(data);
    }

    public List<BanShareData> getAllList(){
        List<BanShareData> list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanList), new TypeToken<Collection<BanShareData>>(){}.getType());
        return list;
    }

    public List<BanShareData> getShareListByServer(AuthData data){
        List<BanShareData> list = new ArrayList<>();

        if (data != null && data.getServerUUID() != null){
            list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+data.getServerUUID().toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getShareListByServer(UUID serverUUID){
        List<BanShareData> list = new ArrayList<>();

        if (serverUUID != null){
            list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverUUID.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getShareListByServer(String serverName){
        List<BanShareData> list = new ArrayList<>();

        if (serverName != null){
            list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverName+"&mode=name"), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return list;
    }

    public List<BanShareData> getShareListByUser(UUID uuid){
        if (uuid != null){
            return gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByUser+uuid.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        return new ArrayList<>();
    }
}
