package info.accolade.ocr_nexus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliceRenewActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String status;
    Spinner sp_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_renew);

        progressDialog = new ProgressDialog(this, R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);

        sp_status = findViewById(R.id.uplpp_status);
    }

    public void policec1(View view) {
        _chackValidation();
    }

    private void _chackValidation() {
        status = sp_status.getSelectedItem().toString().trim();

        if (status.equals("---Choose Status----")) {
            Toast.makeText(this, "Kindly choose status..!", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("id", "");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getUpdatePoliceRenewResponse(status, uid, getIntent().getStringExtra("licenseNumber"));
        modalCall.enqueue(new Callback<DefaultModal>() {
            @Override
            public void onResponse(Call<DefaultModal> call, Response<DefaultModal> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getSuccess()) {
                                Log.e("Insertion success", "");
                                Toast.makeText(PoliceRenewActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("Insertion error", "unable to insert on database");
                                showAlertDialog(response.body().getMessage());
                            }
                        } else {
                            Log.e("Empty body response", "");
                            showAlertDialog("No data found..");
                        }
                    } else {
                        Log.e("Un-success response", "");
                        showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                    }
                } catch (Exception e) {
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
                } else if (t instanceof ConnectException) {
                    showAlertDialog("Unable to connect server, make sure that Wi-Fi or mobile data is turned on..");
                } else {
                    showAlertDialog("Unable to process your request, Kindly try after sometimes..");
                }

            }
        });
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PoliceRenewActivity.this);
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