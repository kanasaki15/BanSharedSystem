package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.Bukkit;

class SystemInfo {
    private final String Version = Bukkit.getPluginManager().getPlugin("BanSharedSystem").getDescription().getVersion();

    private final int UseLibVer = 1;

    public String getVersion() {
        return Version;
    }

    public int getUseLibVer() {
        return UseLibVer;
    }
}
