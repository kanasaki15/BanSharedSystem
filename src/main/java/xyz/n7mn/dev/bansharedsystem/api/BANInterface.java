package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

interface BANInterface {

    boolean run(UUID targetPlayer, UUID fromPlayer, String reason, Date expirationDate, boolean isBan);

    boolean ban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate);

    boolean unban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate);

}
