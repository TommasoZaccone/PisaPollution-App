package iet.unipi.computerenginnering.pisapollution;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetTask extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... params) {
        String result="";

        try {
            String webServerLink="http://131.114.216.142:80/scriptQueryDB.php";

            InputStream is = null;
            URL url = new URL(webServerLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod( "GET" );
            conn.setDoInput( true );
            conn.connect();

            if( conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
                is = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();

            }

            if( is != null ) is.close();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        return result;
    }
    //This Method is called when Network-Request finished
    protected void onPostExecute(String serverData) {
    }
}