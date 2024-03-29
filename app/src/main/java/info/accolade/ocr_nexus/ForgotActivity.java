package info.accolade.ocr_nexus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {
    EditText edemail;
    String email;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        edemail = findViewById(R.id.forgot_email);

        dialog = new ProgressDialog(this, R.style.AlertDialog);
        dialog.setIcon(R.drawable.alert_icon);
        dialog.setMessage(getString(R.string.alert_message));
        dialog.setTitle(R.string.alert_title);
        dialog.setCancelable(false);
    }

    public void btn_forgot(View view) {
        email = edemail.getText().toString().trim();

        _checkValidation();
    }

    public void red_login(View view) {
        startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
    }

    private void _checkValidation() {
        if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            edemail.setError("You have entered an invalid email address, Please try again..");
            edemail.requestFocus();
        }
        else
        {
            dialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        ApiInterface apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getForgotResponse(email);
        modalCall.enqueue(new Callback<DefaultModal>() {
            @Override
            public void onResponse(Call<DefaultModal> call, Response<DefaultModal> response) {
                dialog.dismiss();
                try {
                    if(response.isSuccessful())
                    {
                        if(response.body()!=null)
                        {
                            if(response.body().getSuccess())
                            {
                                Log.e("Email sent success", "");
                                Toast.makeText(ForgotActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                Log.e("email error", "unable to send email");
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
            public void onFailure(Call<DefaultModal> call, Throwable t) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this);
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