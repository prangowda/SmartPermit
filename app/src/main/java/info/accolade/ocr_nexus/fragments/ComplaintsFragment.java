package info.accolade.ocr_nexus.fragments;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.UpdatePermissionActivity;
import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintsFragment extends Fragment {
    FloatingActionButton click;
    ProgressDialog progressDialog;
    String status, title, desc, name, number, address, city, pin;
    Spinner sp_status;
    Button complaint_submit;
    EditText ed_titile, ed_desc, ed_name, ed_number, ed_address, ed_city, ed_pin;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComplaintsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintsFragment newInstance(String param1, String param2) {
        ComplaintsFragment fragment = new ComplaintsFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        click = view.findViewById(R.id.viewcomplaints);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewUserComplaintFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        progressDialog = new ProgressDialog(getContext(), R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);

        sp_status = view.findViewById(R.id.comtype);
        complaint_submit = view.findViewById(R.id.complaint_submit);
        ed_address = view.findViewById(R.id.complaint_address);
        ed_city = view.findViewById(R.id.complaint_city);
        ed_desc = view.findViewById(R.id.complaint_description);
        ed_name = view.findViewById(R.id.complaint_name);
        ed_number = view.findViewById(R.id.re_holder_contact);
        ed_pin = view.findViewById(R.id.complaint_pin);
        ed_titile = view.findViewById(R.id.complaint_title);

        complaint_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _chackValidation();
            }
        });
        return view;
    }

    private void _chackValidation() {
        status = sp_status.getSelectedItem().toString().trim();
        title = ed_titile.getText().toString().trim();
        name = ed_name.getText().toString().trim();
        number = ed_number.getText().toString().trim();
        desc = ed_desc.getText().toString().trim();
        city = ed_city.getText().toString().trim();
        pin = ed_pin.getText().toString().trim();
        address = ed_address.getText().toString().trim();

        if (status.equals("---Choose Type----")) {
            Toast.makeText(getContext(), "Kindly choose complaint type..!", Toast.LENGTH_SHORT).show();
        }
        else if(title.isEmpty())
        {
            ed_titile.setError("Kindly enter title..!");
            ed_titile.requestFocus();
        }
        else if(desc.isEmpty())
        {
            ed_desc.setError("Kindly enter description..!");
            ed_desc.requestFocus();
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
        else if(city.isEmpty())
        {
            ed_city.setError("Kindly enter city..!");
            ed_city.requestFocus();
        }
        else if(pin.isEmpty())
        {
            ed_pin.setError("Kindly enter pin code..!");
            ed_pin.requestFocus();
        }
        else if(!ed_pin.getText().toString().trim().matches("[0-9]{6}"))
        {
            ed_pin.setError("You have entered an invalid pin number, Kindly enter 6 digit number..");
            ed_pin.requestFocus();
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
        Call<DefaultModal> modalCall = apiInterface.getAddComplaintResponse(uid,status, title, desc, name, number, address, city, pin);
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