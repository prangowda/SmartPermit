package info.accolade.ocr_nexus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.accolade.ocr_nexus.modal.LoginModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edemail, edpassword;
    String email, password;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edemail = findViewById(R.id.login_email);
        edpassword = findViewById(R.id.login_password);

        dialog = new ProgressDialog(this, R.style.AlertDialog);
        dialog.setIcon(R.drawable.alert_icon);
        dialog.setMessage(getString(R.string.alert_message));
        dialog.setTitle(R.string.alert_title);
        dialog.setCancelable(false);
    }

    public void red_forgot(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
    }

    public void btn_login(View view) {
        email = edemail.getText().toString().trim();
        password = edpassword.getText().toString().trim();

        _checkValidation();
    }

    public void red_register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void _checkValidation() {
        if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            edemail.setError("You have entered an invalid email address, Please try again ..");
            edemail.requestFocus();
        }
        else if(password.isEmpty())
        {
            edpassword.setError("Kindly enter password..");
            edpassword.requestFocus();
        }
        else
        {
            dialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        ApiInterface apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginModal> modalCall = apiInterface.getLoginResponse(email, password);
        modalCall.enqueue(new Callback<LoginModal>() {
            @Override
            public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                dialog.dismiss();
                try {
                    if(response.isSuccessful())
                    {
                        if(response.body()!=null)
                        {
                            if(response.body().getSuccess())
                            {
                                Log.e("Connection success", "");
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                SharedPreferences preferences = getSharedPreferences("loginDetails", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLogin", true);
                                editor.putString("id", response.body().getId());
                                editor.putString("type", response.body().getType());
                                editor.apply();

                                if(response.body().getType().equals("User"))
                                {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                                else if(response.body().getType().equals("Municipal"))
                                {
                                    startActivity(new Intent(LoginActivity.this, MunicipalHomeActivity.class));
                                    finish();
                                }
                                else if(response.body().getType().equals("Police"))
                                {
                                    startActivity(new Intent(LoginActivity.this, PoliceHomeActivity.class));
                                    finish();
                                }
                                else
                                {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                                finish();
                            }
                            else
                            {
                                Log.e("Connection error", "unable to connection database");
                                showAlertDialog(response.body().getMessage());
                            }
                        }
                        else
                        {
                            Log.e("Empty body response", "");
                            showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                        }
                    }
                    else
                    {
                        Log.e("Un-success response", "");
                        showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                    }
                }
                catch (Exception e)
                {
                    Log.e("Exception", e.getMessage());
                    showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                }
            }

            @Override
            public void onFailure(Call<LoginModal> call, Throwable t) {
                dialog.dismiss();
                Log.e("Throwable", "" + t);
                if (t instanceof SocketTimeoutException) {
                    showAlertDialog("Unable to connect server, Kindly try after sometimes..");
                }
                else if (t instanceof ConnectException) {
                    showAlertDialog("Unable to connect server, make sure that Wi-Fi or mobile data is turned on..");
                }
                else {
                    showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                }

            }
        });
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setIcon(R.drawable.alert_icon);
        builder.setTitle(R.string.alert);
        builder.setMessage(message);
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alertDialog, int which) {
                dialog.show();
                _sendData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alertDialog, int which) {
                alertDialog.cancel();
            }
        });
        builder.show();
    }
}