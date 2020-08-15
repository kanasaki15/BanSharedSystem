package xyz.n7mn.dev.bansharedsystem.api.ban;

import java.util.Date;
import java.util.UUID;

class LocalBANList {
    private UUID uuid;
    private String name;
    private Date created;
    private String source;
    private String expires;
    private String reason;

    public LocalBANList(UUID uuid, String name, Date created, String source, String expires, String reason) {
        this.uuid = uuid;
        this.name = name;
        this.created = created;
        this.source = source;
        this.expires = expires;
        this.reason = reason;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
