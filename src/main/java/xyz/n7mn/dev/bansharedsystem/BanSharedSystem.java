package xyz.n7mn.dev.bansharedsystem;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class BanSharedSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        long start = System.currentTimeMillis();
        String s = httpGet("https://api.n7mn.xyz/banshare/v1/check.php?lib=1");
        long end = System.currentTimeMillis();

        if (new Gson().fromJson(s, boolean.class)){
            getLogger().info("API Connect (api.n7mn.xyz) : OK ("+(end - start)+" ms)");
        }else{
            getLogger().info("API Connect (api.n7mn.xyz) : NG");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private String httpGet(String url){
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try{
            URL u = new URL(url);

            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            return CharStreams.toString(bufferedReader);

        } catch (ProtocolException e) {
            // e.printStackTrace();
        } catch (MalformedURLException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            try {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }

                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }

        return "";
    }
}
