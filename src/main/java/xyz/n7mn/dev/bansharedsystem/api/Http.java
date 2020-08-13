package xyz.n7mn.dev.bansharedsystem.api;

import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class Http {

    public String get(String url){
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try{
            URL u = new URL(url);

            urlConnection = (HttpURLConnection) u.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

            return CharStreams.toString(bufferedReader);

        } catch (ProtocolException e) {
            // e.printStackTrace();
        } catch (MalformedURLException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            try {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }

                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }

        return "";
    }
}
