package xyz.n7mn.dev.bansharedsystem.api;

import java.util.UUID;

public class AuthData {
    private UUID ServerUUID;
    private String ServerName;

    public AuthData(){

    }

    public AuthData(UUID serverUUID, String serverName){
        this.ServerUUID = serverUUID;
        this.ServerName = serverName;
    }

    public UUID getServerUUID() {
        return ServerUUID;
    }

    public void setServerUUID(UUID serverUUID) {
        ServerUUID = serverUUID;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }
}
