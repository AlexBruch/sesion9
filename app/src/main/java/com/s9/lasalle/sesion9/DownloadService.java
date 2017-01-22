package com.s9.lasalle.sesion9;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by alexbruch on 18/1/17.
 */

public class DownloadService extends IntentService {

    public static final String DOWNLOAD_ACTION = "com.s9.lasalle.sesion9.action.DOWNLOAD";
    public static final String END_ACTION = "com.s9.lasalle.sesion9.action.END";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        intent.setAction(DOWNLOAD_ACTION);

        if (intent != null) {
            final String action = intent.getAction();

            if(DOWNLOAD_ACTION.equals(action)) {
                final String URLparam = intent.getStringExtra("Urlparam");

                String receivedParam = handleDownloadAction(URLparam);

                Intent downloadIntent = new Intent();
                downloadIntent.setAction(DOWNLOAD_ACTION);
                downloadIntent.putExtra("content", receivedParam);
                sendBroadcast(downloadIntent);

                Intent endIntent = new Intent();
                endIntent.setAction(END_ACTION);
                sendBroadcast(endIntent);

            } else if (END_ACTION.equals(action)){
                handleEndAction();
            }
        }

    }

    private String handleDownloadAction(String urlParam) {
        try {
            URL url = new URL(urlParam);
            URLConnection urlConnection = url.openConnection();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());

            StringBuilder stringBuilder = new StringBuilder();

            int byteRead;

            while((byteRead = bufferedInputStream.read()) != -1)
                stringBuilder.append((char) byteRead);

            bufferedInputStream.close();

            String URLContent = stringBuilder.toString();

            Log.e("Content", URLContent);
            /** Enviar a la activity una vez descargado **/
            return URLContent;

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void handleEndAction(){

    }
}
