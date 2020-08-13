package xyz.n7mn.dev.bansharedsystem.api;

public class APIURL {

    static final String BaseURL = "https://api.n7mn.xyz/banshare/";
    static final String Version = "v1";

    static final String GetAuthCode = "/create.php?name=";
    static final String GetAuthData = "/create.php?code=";

    static final String BanShareAdd    = "/ban.php?add=";
    static final String BanShareRemove = "/ban.php?del=";

    static final String BanList         = "/list.php";
    static final String BanListByServer = "/list.php?uuid=";
    static final String BanListByUser   = "/list.php?user=";

    public static final String APIServerIPv4 = "139.162.102.164";
    public static final String APIServerIPv6 = "2400:8902::f03c:92ff:fe7a:f18b";
}
