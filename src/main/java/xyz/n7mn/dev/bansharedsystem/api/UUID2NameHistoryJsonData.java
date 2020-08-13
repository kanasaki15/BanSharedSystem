package xyz.n7mn.dev.bansharedsystem.api;

class UUID2NameHistoryJsonData{
    private String name;
    private long changedToAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChangedToAt() {
        return changedToAt;
    }

    public void setChangedToAt(long changedToAt) {
        this.changedToAt = changedToAt;
    }
}
