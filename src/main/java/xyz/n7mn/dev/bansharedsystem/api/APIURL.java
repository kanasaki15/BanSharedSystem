package xyz.n7mn.dev.bansharedsystem.api;

public class APIURL {

    public static final String BaseURL = "https://api.n7mn.xyz/banshare/";
    public static final String Version = "v1";

    public static final String GetAuthCode = "/create.php?name=";
    public static final String GetAuthData = "/create.php?code=";

    public static final String BanShareAdd    = "/ban.php?add=";
    public static final String BanShareRemove = "/ban.php?del=";

    public static final String BanList         = "/list.php";
    public static final String BanListByServer = "/list.php?uuid=";
    public static final String BanListByUser   = "/list.php?user=";

    public static final String APIServerIPv4 = "139.162.102.164";
    public static final String APIServerIPv6 = "2400:8902::f03c:92ff:fe7a:f18b";
}
