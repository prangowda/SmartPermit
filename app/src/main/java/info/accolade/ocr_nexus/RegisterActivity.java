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

public class RegisterActivity extends AppCompatActivity {
    EditText ed_name, ed_email, ed_contact, ed_password, ed_cpassword;
    String name, email, password, cpassword, contact;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_name = findViewById(R.id.reg_name);
        ed_email = findViewById(R.id.reg_email);
        ed_contact = findViewById(R.id.reg_contact);
        ed_password = findViewById(R.id.reg_password);
        ed_cpassword = findViewById(R.id.reg_cpassword);

        progressDialog = new ProgressDialog(this, R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);
    }

    public void btn_register(View view) {
        name = ed_name.getText().toString().trim();
        email = ed_email.getText().toString().trim();
        contact = ed_contact.getText().toString().trim();
        password = ed_password.getText().toString().trim();
        cpassword = ed_cpassword.getText().toString().trim();

        _checkValidation();
    }

    public void red_login2(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private void _checkValidation() {
        if(name.isEmpty())
        {
            ed_name.setError("Kindly enter name..!");
            ed_name.requestFocus();
        }
        else if(name.length()<3)
        {
            ed_name.setError("You have entered an invalid name, Name must be three or more character long..");
            ed_name.requestFocus();
        }
        else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            ed_email.setError("You have entered an invalid email address, Please try again..");
            ed_email.requestFocus();
        }

        else if(contact.isEmpty())
        {
            ed_contact.setError("Kindly enter contact..!");
            ed_contact.requestFocus();
        }
        else if(!ed_contact.getText().toString().trim().matches("[0-9]{10}"))
        {
            ed_contact.setError("You have entered an invalid contact number, Kindly enter 10 digit number..");
            ed_contact.requestFocus();
        }
        else if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"))
        {
            ed_password.setError("Password must be One Uppercase, Lowercase letter, One digit, One Special character and eight or more character long..");
            ed_password.requestFocus();
        }
        else if(!cpassword.matches(password))
        {
            ed_cpassword.setError("Please make sure your passwords match..");
            ed_cpassword.requestFocus();
        }
        else
        {
            progressDialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        ApiInterface apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getRegisterResponse(name, email, contact, password);
        modalCall.enqueue(new Callback<DefaultModal>() {
            @Override
            public void onResponse(Call<DefaultModal> call, Response<DefaultModal> response) {
                progressDialog.dismiss();
                try {
                    if(response.isSuccessful())
                    {
                        if(response.body()!=null)
                        {
                            if(response.body().getSuccess())
                            {
                                Log.e("Insertion success", "");
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                Log.e("Insertion error", "unable to insert on database");
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
                progressDialog.dismiss();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setIcon(R.drawable.alert_icon);
        builder.setTitle(R.string.alert);
        builder.setMessage(message);
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alertDialog, int which) {
                progressDialog.show();
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