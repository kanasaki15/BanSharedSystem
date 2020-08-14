package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;

import java.util.Base64;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.bansharedsystem.event.BanExecuteEvent;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ShareBAN implements BANInterface {

    private AuthData authData = null;

    public ShareBAN(AuthData data) {
        this.authData = data;
    }

    @Deprecated
    public void init(AuthData data) {
        this.authData = data;
    }

    @Deprecated
    public boolean run(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate, boolean isBan){

        if (fromPlayer == null && targetPlayer == null) {
            return false;
        } else if (fromPlayer == null) {
            return false;
        } else if (targetPlayer == null) {
            return false;
        } else {
            return run(targetPlayer.getUniqueId(), fromPlayer.getUniqueId(), reason, expirationDate, isBan);
        }
    }

    public boolean run(UUID targetPlayer, UUID fromPlayer, String reason, Date expirationDate, boolean isBan){

        if (authData == null){
            return false;
        }

        Date maxDate = new Date();
        try {
            maxDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        // System.out.println("Debug1");
        // System.out.println("Debug1-1 : " + expirationDate.getTime());
        // System.out.println("Debug1-1 : " + new Date().getTime());
        if (expirationDate.getTime() <= new Date().getTime()){
            return false;
        }

        // System.out.println("Debug1-2");

        if (expirationDate.getTime() >= maxDate.getTime()){
            expirationDate = null;
        }

        // System.out.println("Debug1-3 : " + (expirationDate == null));

        BanExecuteEvent event = new BanExecuteEvent(fromPlayer, targetPlayer, reason, expirationDate, "ShareBan", isBan);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return false;
        }

        // System.out.println("Debug2");
        try {

            if (isBan){
                String format = "9999-12-31 23:59:59";
                if (event.getExpirationDate() != null){
                    format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(event.getExpirationDate());
                }

                BanShareJson banShareJson;
                // System.out.println("Debug3");
                if (event.getFromUserUUID() != null){
                    // System.out.println("Debug4 : " + new Function().UUID2UserName(event.getTargetUserUUID()));
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getTargetUserUUID()), event.getReason(), event.getExpirationDate(), new Function().UUID2UserName(event.getFromUserUUID()));
                    banShareJson = new BanShareJson(authData.getServerUUID(), event.getTargetUserUUID(), event.getReason(), format, fromPlayer);
                } else {
                    // System.out.println("Debug4");
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getFromUserUUID()), event.getReason(), event.getExpirationDate(), "console");
                    banShareJson = new BanShareJson(authData.getServerUUID(), targetPlayer, reason, format, null);
                }

                byte[] encode = Base64.getEncoder().encode(new Gson().toJson(banShareJson).getBytes(StandardCharsets.UTF_8));

                if (Bukkit.getPlayer(targetPlayer) != null){
                    Bukkit.getPlayer(targetPlayer).kickPlayer("You've been banned. Reason : "+reason);
                }

                // System.out.println(APIURL.BaseURL + APIURL.Version + APIURL.BanShareAdd + URLEncoder.encode(new String(encode), "UTF-8"));

                return new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareAdd + URLEncoder.encode(new String(encode), "UTF-8")), boolean.class);
            } else {
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(new Function().UUID2UserName(targetPlayer));
                return new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareRemove + URLEncoder.encode(fromPlayer.toString(),"UTF-8")+"&s_uuid="+URLEncoder.encode(authData.getServerUUID().toString(), "UTF-8")), boolean.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        return run(targetPlayer, fromPlayer, reason, expirationDate, true);
    }

    @Override
    public boolean unban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        return run(targetPlayer, fromPlayer, reason, expirationDate, false);
    }
}
