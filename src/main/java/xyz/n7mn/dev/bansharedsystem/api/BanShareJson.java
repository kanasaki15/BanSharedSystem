package xyz.n7mn.dev.bansharedsystem.api;

import java.util.UUID;

class BanShareJson {
    private UUID ServerUUID;
    private UUID TargetUserUUID;
    private String Reason;
    private String ExpirationDate;
    private UUID FromUserUUID;

    BanShareJson(UUID serverUUID, UUID targetUserUUID, String reason, String expirationDate, UUID fromUserUUID){
        this.ServerUUID = serverUUID;
        this.TargetUserUUID = targetUserUUID;
        this.Reason = reason;
        this.ExpirationDate = expirationDate;
        this.FromUserUUID = fromUserUUID;
    }
}
