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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.modal.CategoryModal;
import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RenewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RenewFragment extends Fragment {
    FloatingActionButton click;
    ProgressDialog progressDialog;
    String status, name, number, address, trade, lino, exp;
    Spinner sp_status;
    Button complaint_submit;
    EditText ed_name, ed_number, ed_address, ed_trade, ed_exp, ed_lno;
    Calendar myCalendar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RenewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RenewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RenewFragment newInstance(String param1, String param2) {
        RenewFragment fragment = new RenewFragment();
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
        View view = inflater.inflate(R.layout.fragment_renew, container, false);

        click = view.findViewById(R.id.viewrenew);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewUserRenewFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        progressDialog = new ProgressDialog(getContext(), R.style.AlertDialog);
        progressDialog.setTitle(R.string.alert_title);
        progressDialog.setMessage(getString(R.string.alert_message));
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.alert_icon);

        sp_status = view.findViewById(R.id.retype);
        complaint_submit = view.findViewById(R.id.relicence_submit);
        ed_address = view.findViewById(R.id.relicence_holder_address);
        ed_name = view.findViewById(R.id.relicence_holder_name);
        ed_number = view.findViewById(R.id.re_holder_contact);
        ed_trade = view.findViewById(R.id.retrade_address);
        ed_exp = view.findViewById(R.id.redate);
        ed_lno = view.findViewById(R.id.relicence_number);

        _lodaLicense();

        complaint_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _chackValidation();
            }
        });

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
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ed_exp.setText(sdf.format(myCalendar.getTime()));
        exp = sdf.format(myCalendar.getTime());
    }

    private void _lodaLicense() {
        progressDialog.show();
        ApiInterface apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CategoryModal>> modalCall = apiInterface.getCategoryResponse();
        modalCall.enqueue(new Callback<List<CategoryModal>>() {
            @Override
            public void onResponse(Call<List<CategoryModal>> call, Response<List<CategoryModal>> response) {
                progressDialog.dismiss();
                try {
                    if(response.isSuccessful())
                    {
                        if(response.body()!=null)
                        {
                            List<String> list = new ArrayList<String>();
                            for(int i = 0; i<response.body().size();i++)
                            {
                                list.add(response.body().get(i).getName());
                            }
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, list);
                            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                            sp_status.setAdapter(adapter);
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setIcon(R.drawable.alert_icon);
                            builder.setTitle(R.string.alert);
                            builder.setMessage("No building found");
                            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface alertDialog, int which) {
                                    progressDialog.show();
                                    _lodaLicense();
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
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setIcon(R.drawable.alert_icon);
                        builder.setTitle(R.string.alert);
                        builder.setMessage("Unable to process your request, Kindly try after sometimes..");
                        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface alertDialog, int which) {
                                progressDialog.show();
                                _lodaLicense();
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
                catch (Exception e)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.alert_icon);
                    builder.setTitle(R.string.alert);
                    builder.setMessage("Unable to process your request, Kindly try after sometimes..");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int which) {
                            progressDialog.show();
                            _lodaLicense();
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

            @Override
            public void onFailure(Call<List<CategoryModal>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Throwable", "" + t);
                if (t instanceof SocketTimeoutException) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.alert_icon);
                    builder.setTitle(R.string.alert);
                    builder.setMessage("Unable to connect server, Kindly try after sometimes..");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int which) {
                            progressDialog.show();
                            _lodaLicense();
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
                else if (t instanceof ConnectException) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.alert_icon);
                    builder.setTitle(R.string.alert);
                    builder.setMessage("Unable to connect server, make sure that Wi-Fi or mobile data is turned on..");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int which) {
                            progressDialog.show();
                            _lodaLicense();
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
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.alert_icon);
                    builder.setTitle(R.string.alert);
                    builder.setMessage("Unable to process your request, Kindly try after sometimes..");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int which) {
                            progressDialog.show();
                            _lodaLicense();
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
        });
    }

    private void _chackValidation() {
        status = sp_status.getSelectedItem().toString().trim();
        name = ed_name.getText().toString().trim();
        number = ed_number.getText().toString().trim();
        trade = ed_trade.getText().toString().trim();
        address = ed_address.getText().toString().trim();
        exp = ed_exp.getText().toString().trim();
        lino = ed_lno.getText().toString().trim();

        if (status.isEmpty()) {
            Toast.makeText(getContext(), "Kindly choose complaint type..!", Toast.LENGTH_SHORT).show();
        }
        else if(lino.isEmpty())
        {
            ed_lno.setError("Kindly enter license number..!");
            ed_lno.requestFocus();
        }
        else if(exp.isEmpty())
        {
            ed_exp.setError("Kindly enter expire date..!");
            ed_exp.requestFocus();
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
        else if(trade.isEmpty())
        {
            ed_trade.setError("Kindly enter trade address..!");
            ed_trade.requestFocus();
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
        Call<DefaultModal> modalCall = apiInterface.getAddRenewResponse(uid,status, name, number, address, trade, exp, lino);
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