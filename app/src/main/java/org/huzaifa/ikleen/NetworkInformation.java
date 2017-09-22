package org.huzaifa.ikleen;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Huzaifa on 22-Aug-17.
 */

class NetworkInformation extends AsyncTask<Void, Void, Boolean> {

    interface AsyncResponse {
        void hasInternetAccess(Boolean output);
    }

    AsyncResponse asyncResponse = null;

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        asyncResponse.hasInternetAccess(aBoolean);
        Log.d("NetwrkInformation.class", "in onPostExecute......" + String.valueOf(aBoolean));
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d("NetwrkInformation.class", "in doInBackground......");
        try {
            HttpURLConnection urlc = (HttpURLConnection)
                    (new URL("http://clients3.google.com/generate_204")
                            .openConnection());
            urlc.setRequestProperty("User-Agent", "Android");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            Log.d("NetwrkInformation.class", String.valueOf(urlc.getResponseCode() == 204 && urlc.getContentLength() == 0));
            return (urlc.getResponseCode() == 204 &&
                    urlc.getContentLength() == 0);
        } catch (IOException e) {
            Log.e("NetwrkInformation.class", "Error checking internet connection", e);
        }
        return false;
    }
}


