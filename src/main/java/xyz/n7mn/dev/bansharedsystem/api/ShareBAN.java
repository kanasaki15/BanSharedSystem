package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.entity.Player;

public class ShareBAN implements BANInterface {

    private AuthData authData = null;

    @Override
    public void init(AuthData data) {
        this.authData = data;
    }

    @Override
    public boolean run(Player fromPlayer, Player targetPlayer, String reason) {

        if (authData == null){
            return false;
        }

        if (new LocalBAN().run(fromPlayer, targetPlayer, reason)){

            // https://api.n7mn.xyz/banshare/v1/ban.php?add=<UUID>
        }

        return false;
    }
}
