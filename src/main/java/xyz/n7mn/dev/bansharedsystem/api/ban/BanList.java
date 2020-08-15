package xyz.n7mn.dev.bansharedsystem.api.ban;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import xyz.n7mn.dev.bansharedsystem.api.APIURL;
import xyz.n7mn.dev.bansharedsystem.api.Function;
import xyz.n7mn.dev.bansharedsystem.api.auth.AuthData;
import xyz.n7mn.dev.bansharedsystem.api.Http;
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

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getBanExecuteDate().getTime() < new Date().getTime()){
                list.remove(i);
            }
        }

        return list;
    }

    public List<BanShareData> getShareListByServer(UUID serverUUID){
        List<BanShareData> list = new ArrayList<>();

        if (serverUUID != null){
            list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverUUID.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getBanExecuteDate().getTime() < new Date().getTime()){
                list.remove(i);
            }
        }

        return list;
    }

    public List<BanShareData> getShareListByServer(String serverName){
        List<BanShareData> list = new ArrayList<>();

        if (serverName != null){
            list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByServer+serverName+"&mode=name"), new TypeToken<Collection<BanShareData>>(){}.getType());
        }

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getBanExecuteDate().getTime() < new Date().getTime()){
                list.remove(i);
            }
        }

        return list;
    }

    public List<BanShareData> getShareListByUser(UUID uuid){
        if (uuid != null){
            List<BanShareData> list = gson.fromJson(new Http().get(APIURL.BaseURL+APIURL.Version+APIURL.BanListByUser+uuid.toString()), new TypeToken<Collection<BanShareData>>(){}.getType());

            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getBanExecuteDate().getTime() < new Date().getTime()){
                    list.remove(i);
                }
            }

            return list;
        }

        return new ArrayList<>();
    }

    public List<BanShareData> getShareLocalList(){
        List<BanShareData> list = new ArrayList<>();

        Set<BanEntry> entryList = Bukkit.getBanList(org.bukkit.BanList.Type.NAME).getBanEntries();

        int i = 1;
        for (BanEntry entry : entryList){
            BanShareData data = new BanShareData(i, new Function().UserName2UUID(entry.getTarget()),"ちのなみ鯖", new Function().UserName2UUID(entry.getSource()), entry.getReason(), entry.getExpiration(), entry.getCreated(), true);
            list.add(data);
        }

        return list;
    }


    public List<BanShare> getAllBanList(AuthData auth){
        return getAllBanList(auth.getServerUUID());
    }

    public List<BanShare> getAllBanList(UUID serverUUID){

        List<BanShareData> shareList = getShareListByServer(serverUUID);
        List<BanShareData> localList = getShareLocalList();

        List<BanShare> tempList = new ArrayList<>();
        int i = 1;
        for (BanShareData temp : shareList){
            tempList.add(new BanShare(i, temp.getBanUserUUID(), temp.getServerName(), temp.getBanExecuteUserUUID(), temp.getReason(), temp.getBanExpirationDate(), temp.getBanExecuteDate(), "Share", temp.isActive()));
            i++;
        }
        for (BanShareData temp : localList){
            tempList.add(new BanShare(i, temp.getBanUserUUID(), temp.getServerName(), temp.getBanExecuteUserUUID(), temp.getReason(), temp.getBanExpirationDate(), temp.getBanExecuteDate(), "Local", temp.isActive()));
            i++;
        }

        tempList.sort((obj1, obj2) -> (int) (obj1.getBanExecuteDate().getTime() - obj2.getBanExecuteDate().getTime()));

        for (int x = 0; i < tempList.size(); i++){
            if (tempList.get(x).getBanExecuteDate().getTime() < new Date().getTime()){
                tempList.remove(x);
            }
        }

        return tempList;

    }
}

