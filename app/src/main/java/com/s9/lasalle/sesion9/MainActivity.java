package com.s9.lasalle.sesion9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button button;
    WebView webview;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        webview = (WebView) findViewById(R.id.webview);

        SpinnerButton();

        /***** REGISTRAR ACCONES Y FILTROS *****/

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.DOWNLOAD_ACTION);
        intentFilter.addAction(DownloadService.END_ACTION);

        DownloadProgressReceiver downloadProgressReceiver = new DownloadProgressReceiver();
        registerReceiver(downloadProgressReceiver, intentFilter);

    }

    public void SpinnerButton() {

        /***** SPINNER *****/

        ArrayList<SpinnerList> spinnerList = new ArrayList<>();
        spinnerList.add(new SpinnerList("0", "http://developer.android.com/index.html"));
        spinnerList.add(new SpinnerList("1", "http://www.salleurl.edu/"));
        spinnerList.add(new SpinnerList("2", "http://estudy.salleurl.edu/"));
        spinnerList.add(new SpinnerList("3", "http://blogs.salleurl.edu/"));
        spinnerList.add(new SpinnerList("4", "http://www.technovabarcelona.org/portal/parc/parc/Controller"));

        ArrayAdapter<SpinnerList> adapter = new ArrayAdapter<SpinnerList>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /***** PER DETECTAR QUINA ÉS LA URL ESCOLLIDA A SPINNER *****/
            public SpinnerList spinnerItem;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spinnerItem = (SpinnerList) adapterView.getSelectedItem();
                Toast.makeText(getApplicationContext(), spinnerItem.getUrl(), Toast.LENGTH_SHORT).show();

                /***** DOWNLOAD BUTTON *****/

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent downloadIntent = new Intent(getApplicationContext(), DownloadService.class);
                        downloadIntent.putExtra("Urlparam", spinnerItem.getUrl());
                        startService(downloadIntent);
                        Toast.makeText(getApplicationContext(), "Downloading", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public class DownloadProgressReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadService.DOWNLOAD_ACTION)) {

                String urlContent = intent.getStringExtra("Content");

                webview.loadData(urlContent, "text/html; charset=UTF-8", null);

            } else if(intent.getAction().equals(DownloadService.END_ACTION)) {
                Toast.makeText(getApplicationContext(), "END", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
