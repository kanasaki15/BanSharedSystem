package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Function {
    private final String Version = Bukkit.getPluginManager().getPlugin("BanSharedSystem").getDescription().getVersion();

    private final int UseLibVer = 1;

    public String getVersion() {
        return Version;
    }

    public int getUseLibVer() {
        return UseLibVer;
    }

    public String UUID2UserName(UUID uuid) {

        if (uuid == null){ return null; }

        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return player.getName();
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer != null && offlinePlayer.getPlayer() != null) {
            return offlinePlayer.getPlayer().getName();
        }

        String uuidString = uuid.toString().replaceAll("-", "");

        UUID2NameHistoryJsonData[] data = new Gson().fromJson(new Http().get("https://api.mojang.com/user/profiles/" + uuidString + "/names"), UUID2NameHistoryJsonData[].class);
        // System.out.println(data[0].getName());
        return data[data.length - 1].getName();
    }

    public UUID UserName2UUID(String username){
        Player player = Bukkit.getPlayer(username);
        if (player != null){
            return player.getUniqueId();
        }

        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer off : offlinePlayers){
            if (off.getPlayer() != null){
                return off.getPlayer().getUniqueId();
            }
        }

        String s = new Http().get("https://api.mojang.com/users/profiles/minecraft/" + username);
        byte[] id = new Gson().fromJson(s, Username2UUID.class).getId().getBytes(StandardCharsets.UTF_8);

        // if (i == 7 || i == 11 || i == 15 || i == 19){
        if (id.length == 36) {
            String s1 = id[0] + id[1] + id[2] + id[3] + id[4] + id[5] + id[6] + id[7] + "-" + id[8] + id[9] + id[10] + id[11] + "-" + id[12] + id[13] + id[14] + id[15] + "-" + id[16] + id[17] + id[18] + id[19] + "-" + id[20] + id[21] + id[22] + id[23] + id[24] + id[25] + id[26] + id[27] + id[28] + id[29] + id[30] + id[31] + id[32] + id[33] + id[34] + id[35];
            return UUID.fromString(s1);
        }

        return null;
    }

    public String fileRead(String filepass){

        String filePass = filepass;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            filePass = filePass.replaceAll("/", "\\\\");
        }

        File file = new File(filePass);
        BufferedReader buffer = null;
        if (!file.exists()){
            return "";
        }

        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input, "UTF-8");
            buffer = new BufferedReader(stream);

            String str = buffer.readLine();
            while (str != null) {
                sb.append(str);
                str = buffer.readLine();
            }

            buffer.close();
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        } finally {
            try {
                if (buffer != null){
                    buffer.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

    public boolean fileWrite(String filePass, String Content){
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            filePass = filePass.replaceAll("/", "\\\\");
        }

        File file = new File(filePass);
        PrintWriter p_writer = null;

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(Content);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }
}


