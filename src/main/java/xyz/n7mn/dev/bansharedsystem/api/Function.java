package xyz.n7mn.dev.bansharedsystem.api;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
}
