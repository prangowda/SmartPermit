package info.accolade.ocr_nexus.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.UpdateLicenseActivity;
import info.accolade.ocr_nexus.UpdatePermissionActivity;
import info.accolade.ocr_nexus.adapter.ComplaintAdapter;
import info.accolade.ocr_nexus.adapter.PermissionAdapter;
import info.accolade.ocr_nexus.modal.ComplaintsModal;
import info.accolade.ocr_nexus.modal.PermissionModal;
import info.accolade.ocr_nexus.utils.ApiClient;
import info.accolade.ocr_nexus.utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewMunicipalPermissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMunicipalPermissionFragment extends Fragment {
    ProgressDialog dialog;
    ArrayList<PermissionModal> modals = new ArrayList<>();
    private PermissionAdapter adapter;
    RecyclerView recyclerView;
    PermissionAdapter.RecyclerViewClickListener listener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewMunicipalPermissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMunicipalPermissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMunicipalPermissionFragment newInstance(String param1, String param2) {
        ViewMunicipalPermissionFragment fragment = new ViewMunicipalPermissionFragment();
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
        View root = inflater.inflate(R.layout.fragment_view_municipal_permission, container, false);

        recyclerView = root.findViewById(R.id.municipalPermissionRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //progress bar
        dialog = new ProgressDialog(getContext(), R.style.AlertDialog);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert_title);
        dialog.setIcon(R.drawable.alert_icon);
        dialog.setMessage(getString(R.string.alert_message));
        dialog.show();

        _loadData();
        return root;
    }

    private void _loadData() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<PermissionModal>> artModal = apiInterface.getMunicipalPermissionResponse();
        artModal.enqueue(new Callback<List<PermissionModal>>() {
            @Override
            public void onResponse(Call<List<PermissionModal>> call, Response<List<PermissionModal>> response) {
                dialog.dismiss();

                try {
                    List<PermissionModal> postResponses = response.body();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            modals = new ArrayList<>(response.body());

                            listener = new PermissionAdapter.RecyclerViewClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent i = new Intent(getContext(), UpdatePermissionActivity.class);
                                    i.putExtra("permissionNumber", modals.get(position).getPnumber());
                                    startActivity(i);
                                }
                            };
                            adapter = new PermissionAdapter(getContext(), modals, listener);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("Empty body response", "");
                            showAlertDialog("No data found, Kindly try after sometimes..");
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
            public void onFailure(Call<List<PermissionModal>> call, Throwable t) {
                dialog.dismiss();
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
                dialog.show();
                _loadData();
            }
        });
        builder.show();
    }
}