package info.accolade.ocr_nexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import info.accolade.ocr_nexus.fragments.PoliceHomeFragment;
import info.accolade.ocr_nexus.fragments.PoliceLicenseFragment;
import info.accolade.ocr_nexus.fragments.PoliceRenewFragment;
import info.accolade.ocr_nexus.fragments.ProfileFragment;
import info.accolade.ocr_nexus.fragments.SettingsFragment;

public class PoliceHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home);

        //toolbar
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("OCR For Nexus - Home");
        setSupportActionBar(toolbar);

        //drawer
        navigationView = findViewById(R.id.navigationview3);
        drawerLayout = findViewById(R.id.drawer_layout3);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new PoliceHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homepage3);
            toolbar.setTitle("OCR For Nexus - Home");
        }
    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Download OCR For Nexus app here https://play.google.com/store/apps/details?id=info.accolade.ocr_nexus";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "OCR For Nexus");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
        }
        return super.onOptionsItemSelected(item);
    }

    //    drawer
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.homepage3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new PoliceHomeFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Home");
                break;

            case R.id.update3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new SettingsFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Settings");
                break;

            case R.id.profile3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new ProfileFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Profile");
                break;

            case R.id.plicense:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new PoliceLicenseFragment()).commit();
                toolbar.setTitle("OCR For Nexus - License Request");
                break;

            case R.id.prenew:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new PoliceRenewFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Renew License");
                break;

            case R.id.logout3:
                logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        editor.putString("id", "");
        editor.putString("type", "");
        editor.apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.alert_icon)
                    .setTitle("Alert..!")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }
}