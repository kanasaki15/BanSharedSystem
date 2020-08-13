package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.entity.Player;

interface BANInterface {

    void init(AuthData data);

    boolean run(Player fromPlayer, Player targetPlayer, String reason);
}
