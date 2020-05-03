package com.example.drive360.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;


public class ManualActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_manual);

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
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        Intent launchWebview = new Intent(this, ManualWebviewActivity.class);
        launchWebview.putExtra("url", url);
        startActivity(launchWebview);
    }

    public void onClick(View v){
        goToUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get username and set text of menu item to welcome user.
        String username = sharedPreferences.getString("username", "");
        if (username != null && !username.equals("")) {
            MenuItem item = menu.findItem(R.id.welcome);
            item.setTitle("Welcome " + username);
        }

        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        if (!isAdmin) {
            menu.findItem(R.id.admin_dashboard).setVisible(false);
        }
        //menu.findItem(R.id.add).setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    public void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //public void goToMainScreen() {
    //    Intent intent = new Intent(this, MainActivity.class);
    //    startActivity(intent);
    //}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
            sharedPreferences.edit().putBoolean("isAdmin", false).apply();
            sharedPreferences.edit().putBoolean("isInstructor", false).apply();
            sharedPreferences.edit().putBoolean("isAdminTest", false).apply();
            sharedPreferences.edit().remove("username").apply();
            sharedPreferences.edit().remove("testId").apply();
            sharedPreferences.edit().remove("questionId").apply();

            // Redirect the user to login screen.
            goToLoginScreen();
            Toast.makeText(ManualActivity.this, "Sign out successful!", Toast.LENGTH_LONG).show();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
