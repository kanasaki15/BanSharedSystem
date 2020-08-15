package xyz.n7mn.dev.bansharedsystem.api.ban;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.bansharedsystem.api.auth.AuthData;
import xyz.n7mn.dev.bansharedsystem.api.BANInterface;
import xyz.n7mn.dev.bansharedsystem.api.Function;
import xyz.n7mn.dev.bansharedsystem.event.BanExecuteEvent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocalBAN implements BANInterface {

    private AuthData authData = null;

    private Plugin plugin = Bukkit.getPluginManager().getPlugin("BanSharedSystem");

    @Deprecated
    public LocalBAN(){
        File folder = plugin.getDataFolder();
        try{
            folder.createNewFile();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public LocalBAN(Plugin plugin) {
        this.plugin = plugin;
    }

    private boolean run(UUID targetPlayer, UUID fromPlayer, String reason, Date expirationDate, boolean isBan){
        if (authData == null){
            return false;
        }

        Date maxDate = new Date();
        try {
            maxDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        if (expirationDate.getTime() <= new Date().getTime()){
            return false;
        }

        if (expirationDate.getTime() >= maxDate.getTime()){
            expirationDate = null;
        }


        BanExecuteEvent event = new BanExecuteEvent(fromPlayer, targetPlayer, reason, expirationDate, "ShareBan", isBan);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return false;
        }

        try {

            if (isBan){

                if (Bukkit.getPlayer(event.getTargetUserUUID()) != null){
                    Bukkit.getPlayer(event.getTargetUserUUID()).kickPlayer("You've been banned. Reason : "+reason);
                }

                if (fromPlayer != null){
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getTargetUserUUID()), event.getReason(), event.getExpirationDate(), new Function().UUID2UserName(event.getFromUserUUID()));
                } else {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getTargetUserUUID()), event.getReason(), event.getExpirationDate(), "console");
                }
                return true;
            } else {
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(new Function().UUID2UserName(targetPlayer));
                return true;
            }

        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        boolean run = run(targetPlayer, fromPlayer, reason, expirationDate, true);

        String pass = "./" + plugin.getDataFolder().getPath() + "/LocalBanList.json";

        String s = new Function().fileRead(pass);
        List<LocalBANList> list = null;
        if (s != null && s.length() > 0 && run){
            list = new Gson().fromJson(s, new TypeToken<Collection<LocalBANList>>(){}.getType());
        }

        if (list == null){
            list = new ArrayList<>();
        }

        BanEntry banEntry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(new Function().UUID2UserName(targetPlayer));

        Date maxDate = new Date();

        LocalBANList banList;
        try {
            maxDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        if (maxDate.getTime() >= expirationDate.getTime()){
            banList = new LocalBANList(targetPlayer, banEntry.getTarget(), banEntry.getCreated(), banEntry.getSource(), "forever", banEntry.getReason());
        } else {
            banList = new LocalBANList(targetPlayer, banEntry.getTarget(), banEntry.getCreated(), banEntry.getSource(), banEntry.getExpiration().toString(), banEntry.getReason());
        }

        list.add(banList);

        new Function().fileWrite(pass, new GsonBuilder().setPrettyPrinting().create().toJson(list));

        return run;
    }

    @Override
    public boolean unban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        return run(targetPlayer, fromPlayer, reason, expirationDate, false);
    }
}
