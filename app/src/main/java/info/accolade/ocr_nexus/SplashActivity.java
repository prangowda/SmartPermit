package info.accolade.ocr_nexus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.e("token", token);
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("loginDetails", MODE_PRIVATE);
                if(preferences.getBoolean("isLogin",false))
                {
                    if(preferences.getString("type","").equals("User"))
                    {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                    else if(preferences.getString("type","").equals("Municipal"))
                    {
                        startActivity(new Intent(SplashActivity.this, MunicipalHomeActivity.class));
                        finish();
                    }
                    else if(preferences.getString("type","").equals("Police"))
                    {
                        startActivity(new Intent(SplashActivity.this, PoliceHomeActivity.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}