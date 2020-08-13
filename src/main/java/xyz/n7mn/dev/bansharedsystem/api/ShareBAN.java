package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;
import java.util.Base64;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
            parse = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        return run(fromPlayer, targetPlayer, reason, parse, true);
    }

    public boolean run(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate, boolean isBan){

        try {
            if (isBan){

                if (!new LocalBAN().run(fromPlayer, targetPlayer, reason, expirationDate, isBan)){
                    return false;
                }

                String format = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").format(expirationDate);
                String str = Base64.getEncoder().encode(new Gson().toJson(new BanShareJson(targetPlayer.getUniqueId(), reason, format, fromPlayer.getUniqueId())).getBytes()).toString();

                ByteArrayOutputStream obj = new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(obj);
                gzip.write(str.getBytes(StandardCharsets.UTF_8));
                gzip.close();
                str = obj.toString("UTF-8");
                obj.close();

                return new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareAdd + URLEncoder.encode(str,"UTF-8")), boolean.class);
            } else {
                return new Gson().fromJson(new Http().get(APIURL.BaseURL + APIURL.Version + APIURL.BanShareRemove + URLEncoder.encode(fromPlayer.getUniqueId().toString(),"UTF-8")), boolean.class);
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        return false;
    }
}
