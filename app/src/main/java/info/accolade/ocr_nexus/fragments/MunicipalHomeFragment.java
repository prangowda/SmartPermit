package info.accolade.ocr_nexus.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.adapter.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MunicipalHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MunicipalHomeFragment extends Fragment {
    SliderView sliderView;
    int[] images = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4};

    CardView card1, card2, card3, card4;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MunicipalHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MunicipalHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MunicipalHomeFragment newInstance(String param1, String param2) {
        MunicipalHomeFragment fragment = new MunicipalHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_municipal_home, container, false);

        SliderView sliderView = view.findViewById(R.id.imageSlider3);

        SliderAdapter adapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();

        card1 = view.findViewById(R.id.card11);
        card2 = view.findViewById(R.id.card21);
        card3 = view.findViewById(R.id.card31);
        card4 = view.findViewById(R.id.card41);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewMunicipalLicenseFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewMunicipalRenewFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewMunicipalPermissionFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new ViewMunicipalComplaintsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            }
        });
        return view;
    }
}