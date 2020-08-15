package xyz.n7mn.dev.bansharedsystem.api.protest;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.Bukkit;
import xyz.n7mn.dev.bansharedsystem.api.APIURL;
import xyz.n7mn.dev.bansharedsystem.api.Http;
import xyz.n7mn.dev.bansharedsystem.api.auth.AuthData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProtestList {

    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

        @Override
        public Date deserialize(JsonElement dateElement, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("JST"));
            String date = dateElement.getAsString();
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                Bukkit.getLogger().info(String.format("Gsonの処理内で日付のパースに失敗しました。: %s", date));
                return null;
            }
        }
    }).setPrettyPrinting().create();

    public List<Protest> getProtestAllList(){

        return gson.fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanProtestList), new TypeToken<Collection<Protest>>(){}.getType());
    }

    public List<Protest> getProtestList(AuthData auth){

        return getProtestList(auth.getServerUUID());
    }

    public List<Protest> getProtestList(UUID serverUUID){

        return gson.fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanProtestListByServer + serverUUID.toString()), new TypeToken<Collection<Protest>>(){}.getType());
    }

    public boolean addProtest(Protest data){

        try {
            final String s = gson.toJson(data);
            final String s2 = new String(Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8)));
            String encode = URLEncoder.encode(s2, "UTF-8");

            // Bukkit.getLogger().info(APIURL.BaseURL + APIURL.Version + APIURL.BanProtestAdd + encode); return true;
            return gson.fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanProtestAdd + encode), boolean.class);
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
        }

        return true;
    }

    public boolean delProtest(int id){

        return gson.fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanProtestDel + id), boolean.class);
    }
}
