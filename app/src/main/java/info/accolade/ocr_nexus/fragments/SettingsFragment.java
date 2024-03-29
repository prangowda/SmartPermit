package info.accolade.ocr_nexus.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import info.accolade.ocr_nexus.MainActivity;
import info.accolade.ocr_nexus.MunicipalHomeActivity;
import info.accolade.ocr_nexus.PoliceHomeActivity;
import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.modal.DefaultModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    EditText ed_new, ed_old, ed_conf;
    String newPassword, oldPassword, confirmPassword;
    MaterialButton btn;
    ProgressDialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ed_new = view.findViewById(R.id.change_password);
        ed_old = view.findViewById(R.id.change_opassword);
        ed_conf = view.findViewById(R.id.change_cpassword);
        btn = view.findViewById(R.id.update_submit);

        dialog = new ProgressDialog(getContext(), R.style.AlertDialog);
        dialog.setIcon(R.drawable.alert_icon);
        dialog.setMessage(getString(R.string.alert_message));
        dialog.setTitle(R.string.alert_title);
        dialog.setCancelable(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = ed_new.getText().toString().trim();
                oldPassword = ed_old.getText().toString().trim();
                confirmPassword = ed_conf.getText().toString().trim();
                _chackValidation();
            }
        });

        return view;
    }

    private void _chackValidation() {
        if(oldPassword.isEmpty())
        {
            ed_old.setError("Kindly enter current password..");
            ed_old.requestFocus();
        }
        else if(!newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"))
        {
            ed_new.setError("Password must be One Uppercase, Lowercase letter, One digit, One Special character and eight or more character long..");
            ed_new.requestFocus();
        }
        else if(!newPassword.matches(confirmPassword))
        {
            ed_conf.setError("Please make sure your passwords match..");
            ed_conf.requestFocus();
        }
        else
        {
            dialog.show();
            _sendData();
        }
    }

    private void _sendData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        ApiInterface apiInterface  = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DefaultModal> modalCall = apiInterface.getUpdateResponse(preferences.getString("id",""), newPassword, oldPassword);
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
                                Log.e("update success", "");
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                if(preferences.getString("type","").equals("User"))
                                {
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
                                else if(preferences.getString("type","").equals("Municipal"))
                                {
                                    startActivity(new Intent(getContext(), MunicipalHomeActivity.class));
                                }
                                else if(preferences.getString("type","").equals("Police"))
                                {
                                    startActivity(new Intent(getContext(), PoliceHomeActivity.class));
                                }
                                else
                                {
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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