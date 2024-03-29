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

import info.accolade.ocr_nexus.fragments.MunicipalHomeFragment;
import info.accolade.ocr_nexus.fragments.PoliceHomeFragment;
import info.accolade.ocr_nexus.fragments.ProfileFragment;
import info.accolade.ocr_nexus.fragments.SettingsFragment;
import info.accolade.ocr_nexus.fragments.ViewMunicipalComplaintsFragment;
import info.accolade.ocr_nexus.fragments.ViewMunicipalLicenseFragment;
import info.accolade.ocr_nexus.fragments.ViewMunicipalPermissionFragment;
import info.accolade.ocr_nexus.fragments.ViewMunicipalRenewFragment;

public class MunicipalHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipal_home);

        //toolbar
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("OCR For Nexus - Home");
        setSupportActionBar(toolbar);

        //drawer
        navigationView = findViewById(R.id.navigationview2);
        drawerLayout = findViewById(R.id.drawer_layout2);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new MunicipalHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homepage2);
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
            case R.id.homepage2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new MunicipalHomeFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Home");
                break;

            case R.id.update2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new SettingsFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Settings");
                break;

            case R.id.profile2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ProfileFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Profile");
                break;

            case R.id.licensed:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewMunicipalLicenseFragment()).commit();
                toolbar.setTitle("OCR For Nexus - License");
                break;

            case R.id.renewd:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewMunicipalRenewFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Renew");
                break;

            case R.id.complaintd:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewMunicipalComplaintsFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Complaints");
                break;

            case R.id.permissiond:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewMunicipalPermissionFragment()).commit();
                toolbar.setTitle("OCR For Nexus - Permission");
                break;

            case R.id.logout2:
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