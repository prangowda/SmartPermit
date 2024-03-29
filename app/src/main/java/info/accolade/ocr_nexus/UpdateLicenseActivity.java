package info.accolade.ocr_nexus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLicenseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String status, exp;
    Spinner sp_status;
    Calendar myCalendar;
    EditText ed_exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_license);

        progressDialog = new ProgressDialog(this, R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);

        sp_status = findViewById(R.id.upl_status);
        ed_exp = findViewById(R.id.expdate1);
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ed_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateLicenseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_exp.setText(sdf.format(myCalendar.getTime()));
        exp = sdf.format(myCalendar.getTime());
    }

    public void btn_upcl(View view) {
        _chackValidation();
    }

    private void _chackValidation() {
        status = sp_status.getSelectedItem().toString().trim();

        if (status.equals("---Choose Status----")) {
            Toast.makeText(this, "Kindly choose status..!", Toast.LENGTH_SHORT).show();
        }
        else if(exp.isEmpty()){
            ed_exp.setError("Kindly choose expire date.");
            ed_exp.requestFocus();
        }
        else {
            progressDialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("id", "");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getUpdateLicenseResponse(status, uid, getIntent().getStringExtra("licenseNumber"), exp);
        modalCall.enqueue(new Callback<DefaultModal>() {
            @Override
            public void onResponse(Call<DefaultModal> call, Response<DefaultModal> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getSuccess()) {
                                Log.e("Insertion success", "");
                                Toast.makeText(UpdateLicenseActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("Insertion error", "unable to insert on database");
                                showAlertDialog(response.body().getMessage());
                            }
                        } else {
                            Log.e("Empty body response", "");
                            showAlertDialog("Unable to process your request, Kindly try after sometimes..");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateLicenseActivity.this);
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