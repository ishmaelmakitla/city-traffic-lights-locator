package app.defensivethinking.co.za.smartcitizentrafficlightspotter.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public static String getData(String uri) {

        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
