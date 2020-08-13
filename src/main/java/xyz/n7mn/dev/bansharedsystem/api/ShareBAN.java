package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;

import java.io.*;
import java.util.Base64;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class ShareBAN implements BANInterface {

    private AuthData authData = null;

    @Override
    public void init(AuthData data) {
        this.authData = data;
    }

    @Override
    public boolean run(Player fromPlayer, Player targetPlayer, String reason) {

        Date parse = new Date();
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        return run(fromPlayer, targetPlayer, reason, parse, true);
    }

    public boolean run(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate, boolean isBan){

        if (authData == null){
            return false;
        }

        try {
            if (isBan){
                boolean run = new LocalBAN().run(fromPlayer, targetPlayer, reason, expirationDate, isBan);

                if (!run){
                    return false;
                }

                String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(expirationDate);

                BanShareJson banShareJson = new BanShareJson(authData.getServerUUID(), targetPlayer.getUniqueId(), reason, format, fromPlayer.getUniqueId());

                byte[] encode = Base64.getEncoder().encode(new Gson().toJson(banShareJson).getBytes(StandardCharsets.UTF_8));

                boolean json = json = new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareAdd + URLEncoder.encode(new String(encode), "UTF-8")), boolean.class);

                if (!json){
                    Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(targetPlayer.getName());
                }
                return json;
            } else {
                return new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareRemove + URLEncoder.encode(fromPlayer.getUniqueId().toString(),"UTF-8")+"&s_uuid="+URLEncoder.encode(authData.getServerUUID().toString(), "UTF-8")), boolean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
