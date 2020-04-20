package com.example.drive360;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class LearnActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String url = null;
    private static final String[] states
            = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
            "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
            "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
            "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, states);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch (item){

            case "Alabama":
                url = "https://driving-tests.org/alabama/al-dmv-drivers-handbook-manual/";
                break;

            case "Alaska":
                url = "https://driving-tests.org/alaska/ak-dmv-drivers-handbook-manual/";
                break;

            case "Arizona":
                url = "https://driving-tests.org/arizona/az-dmv-drivers-handbook-manual/";
                break;

            case "Arkansas":
                url = "https://driving-tests.org/arkansas/ar-dmv-drivers-handbook-manual/";
                break;

            case "California":
                url = "https://driving-tests.org/california/ca-dmv-drivers-handbook-manual/";
                break;

            case "Colorado":
                url = "https://driving-tests.org/colorado/co-dmv-drivers-handbook-manual/";
                break;

            case "Connecticut":
                url = "https://driving-tests.org/connecticut/ct-dmv-drivers-handbook-manual/";
                break;

            case "Delaware":
                url = "https://driving-tests.org/delaware/de-dmv-drivers-handbook-manual/";
                break;

            case "District of Columbia":
                url = "https://driving-tests.org/district-columbia/dc-dmv-drivers-handbook-manual/";
                break;

            case "Florida":
                url = "https://driving-tests.org/florida/fl-dmv-drivers-handbook-manual/";
                break;

            case "Georgia":
                url = "https://driving-tests.org/georgia/ga-dds-drivers-handbook-manual/";
                break;

            case "Hawaii":
                url = "https://driving-tests.org/hawaii/hi-dmv-drivers-handbook-manual/";
                break;

            case "Idaho":
                url = "https://driving-tests.org/idaho/id-dmv-drivers-handbook-manual/";
                break;

            case "Illinois":
                url = "https://driving-tests.org/illinois/il-dmv-drivers-handbook-manual/";
                break;

            case "Indiana":
                url = "https://driving-tests.org/indiana/in-bmv-drivers-handbook-manual/";
                break;

            case "Iowa":
                url = "https://driving-tests.org/iowa/ia-dmv-drivers-handbook-manual/";
                break;

            case "Kansas":
                url = "https://driving-tests.org/kansas/ks-dmv-drivers-handbook-manual/";
                break;

            case "Kentucky":
                url = "https://driving-tests.org/kentucky/ky-dmv-drivers-handbook-manual/";
                break;

            case "Louisiana":
                url = "https://driving-tests.org/louisiana/la-dmv-drivers-handbook-manual/";
                break;

            case "Maine":
                url = "https://driving-tests.org/maine/me-dmv-drivers-handbook-manual/";
                break;

            case "Maryland":
                url = "https://driving-tests.org/maryland/md-mva-drivers-handbook-manual/";
                break;

            case "Massachusetts":
                url = "https://driving-tests.org/massachusetts/ma-rmv-drivers-handbook-manual/";
                break;

            case "Michigan":
                url = "https://driving-tests.org/michigan/mi-dmv-drivers-handbook-manual/";
                break;

            case "Minnesota":
                url = "https://driving-tests.org/minnesota/mn-dmv-drivers-handbook-manual/";
                break;

            case "Mississippi":
                url = "https://driving-tests.org/mississippi/ms-dmv-drivers-handbook-manual/";
                break;

            case "Missouri":
                url = "https://driving-tests.org/missouri/mo-dor-drivers-handbook-manual/";
                break;

            case "Montana":
                url = "https://driving-tests.org/montana/mt-dmv-drivers-handbook-manual/";
                break;

            case "Nebraska":
                url = "https://driving-tests.org/nebraska/ne-dmv-drivers-handbook-manual/";
                break;

            case "Nevada":
                url = "https://driving-tests.org/nevada/nv-dmv-drivers-handbook-manual/";
                break;

            case "New Hampshire":
                url = "https://driving-tests.org/new-hampshire/nh-dmv-drivers-handbook-manual/";
                break;

            case "New Jersey":
                url = "https://driving-tests.org/new-jersey/nj-mvc-drivers-handbook-manual/";
                break;

            case "New Mexico":
                url = "https://driving-tests.org/new-mexico/nm-mvd-drivers-handbook-manual/";
                break;

            case "New York":
                url = "https://driving-tests.org/new-york/ny-dmv-drivers-handbook-manual/";
                break;

            case "North Carolina":
                url = "https://driving-tests.org/north-carolina/nc-dmv-drivers-handbook-manual/";
                break;

            case "North Dakota":
                url = "https://driving-tests.org/north-dakota/nd-dmv-drivers-handbook-manual/";
                break;

            case "Ohio":
                url = "https://driving-tests.org/ohio/oh-bmv-drivers-handbook-manual/";
                break;

            case "Oklahoma":
                url = "https://driving-tests.org/oklahoma/ok-dmv-drivers-handbook-manual/";
                break;

            case "Oregon":
                url = "https://driving-tests.org/oregon/or-dmv-drivers-handbook-manual/";
                break;

            case "Pennsylvania":
                url = "https://driving-tests.org/pennsylvania/pa-dot-drivers-handbook-manual/";
                break;

            case "Rhode Island":
                url = "https://driving-tests.org/rhode-island/ri-dmv-drivers-handbook-manual/";
                break;

            case "South Carolina":
                url = "https://driving-tests.org/south-carolina/sc-dmv-drivers-handbook-manual/";
                break;

            case "South Dakota":
                url = "https://driving-tests.org/south-dakota/sd-dmv-drivers-handbook-manual/";
                break;

            case "Tennessee":
                url = "https://driving-tests.org/tennessee/tn-dmv-drivers-handbook-manual/";
                break;

            case "Texas":
                url = "https://driving-tests.org/texas/tx-dmv-drivers-handbook-manual/";
                break;

            case "Utah":
                url = "https://driving-tests.org/utah/ut-dmv-drivers-handbook-manual/";
                break;

            case "Vermont":
                url = "https://driving-tests.org/vermont/vt-dmv-drivers-handbook-manual/";
                break;

            case "Virginia":
                url = "https://driving-tests.org/virginia/va-dmv-drivers-handbook-manual/";
                break;

            case "Washington":
                url = "https://driving-tests.org/washington/wa-dol-drivers-handbook-manual/";
                break;

            case "West Virginia":
                url = "https://driving-tests.org/west-virginia/wv-dmv-drivers-handbook-manual/";
                break;

            case "Wisconsin":
                url = "https://driving-tests.org/wisconsin/wi-dmv-drivers-handbook-manual/";
                break;

            case "Wyoming":
                url = "https://driving-tests.org/wyoming/wy-dmv-drivers-handbook-manual/";
                break;

            default:
                break;

        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {}

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void onClick(View v){
        goToUrl(url);
    }


}
