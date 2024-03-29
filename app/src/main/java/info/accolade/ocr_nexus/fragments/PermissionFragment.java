package info.accolade.ocr_nexus.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PermissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermissionFragment extends Fragment {
    FloatingActionButton click;
    ProgressDialog progressDialog;
    String status, validfrom, validto, desc, name, number, address;
    Spinner sp_status;
    Button complaint_submit;
    EditText ed_from, ed_to, ed_desc, ed_name, ed_number, ed_address;
    Calendar myCalendar, myCalendar2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PermissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PermissionFragment newInstance(String param1, String param2) {
        PermissionFragment fragment = new PermissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_permission, container, false);

        click = view.findViewById(R.id.viewpermission);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewUserPermissionFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        progressDialog = new ProgressDialog(getContext(), R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);

        sp_status = view.findViewById(R.id.pertype);
        complaint_submit = view.findViewById(R.id.permi_submit);
        ed_address = view.findViewById(R.id.permission_address);
        ed_from = view.findViewById(R.id.datefrom);
        ed_desc = view.findViewById(R.id.permission_desc);
        ed_name = view.findViewById(R.id.permission_name);
        ed_number = view.findViewById(R.id.permission_contact);
        ed_to = view.findViewById(R.id.dateto);

        complaint_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _chackValidation();
            }
        });

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();
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

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        ed_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ed_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_to.setText(sdf.format(myCalendar.getTime()));
        validto = sdf.format(myCalendar.getTime());
    }

    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed_from.setText(sdf.format(myCalendar2.getTime()));
        validfrom = sdf.format(myCalendar2.getTime());
    }

    private void _chackValidation() {
        status = sp_status.getSelectedItem().toString().trim();
        validfrom = ed_from.getText().toString().trim();
        name = ed_name.getText().toString().trim();
        number = ed_number.getText().toString().trim();
        desc = ed_desc.getText().toString().trim();
        validto = ed_to.getText().toString().trim();
        address = ed_address.getText().toString().trim();

        if (status.equals("--Choose Permission--")) {
            Toast.makeText(getContext(), "Kindly choose permission type..!", Toast.LENGTH_SHORT).show();
        }
        else if(desc.isEmpty())
        {
            ed_desc.setError("Kindly enter description..!");
            ed_desc.requestFocus();
        }
        else if(ed_from.getText().toString().trim().isEmpty())
        {
            ed_from.setError("Kindly choose from date..!");
            ed_from.requestFocus();
        }
        else if(ed_to.getText().toString().trim().isEmpty())
        {
            ed_to.setError("Kindly choose to date..!");
            ed_to.requestFocus();
        }
        else if(name.isEmpty())
        {
            ed_name.setError("Kindly enter name..!");
            ed_name.requestFocus();
        }
        else if(number.isEmpty())
        {
            ed_number.setError("Kindly enter contact number..!");
            ed_number.requestFocus();
        }
        else if(!ed_number.getText().toString().trim().matches("[0-9]{10}"))
        {
            ed_number.setError("You have entered an invalid contact number, Kindly enter 10 digit number..");
            ed_number.requestFocus();
        }
        else if(address.isEmpty())
        {
            ed_address.setError("Kindly enter address..!");
            ed_address.requestFocus();
        }
        else {
            progressDialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("id", "");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getAddPermissionResponse(uid,status, desc, name, number, address, validfrom, validto);
        modalCall.enqueue(new Callback<DefaultModal>() {
            @Override
            public void onResponse(Call<DefaultModal> call, Response<DefaultModal> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getSuccess()) {
                                Log.e("Insertion success", "");
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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