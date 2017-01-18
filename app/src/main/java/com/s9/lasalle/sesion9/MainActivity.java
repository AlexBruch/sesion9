package com.s9.lasalle.sesion9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***** SPINNER *****/

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        WebView webview = (WebView) findViewById(R.id.webview);

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


    }
}
