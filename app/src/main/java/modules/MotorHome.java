package modules;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.insurance.elephant.myinsurer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import config.MotorServices;
import config.StatusConfig;
import uimodels.MotorHomeUI;
import utilities.MotorHomeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MotorHomeInteraction} interface
 * to handle interaction events.
 * Use the {@link MotorHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotorHome extends Fragment implements MotorHomeAdapter.MotorHomeAdapterInteraction {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.topImg)
    public ImageView topImage;
    @BindView(R.id.motorServicesRecycler)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private MotorHomeInteraction mListener;

    public MotorHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotorHome.
     */
    public static MotorHome newInstance(String param1, String param2) {
        MotorHome fragment = new MotorHome();
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
        return inflater.inflate(R.layout.motor_home_ui, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        loadUI();
    }
    private void loadUI(){
        List<MotorHomeUI> uis=new ArrayList<>();

        MotorHomeUI ui=new MotorHomeUI(R.drawable.moto, "Two wheeler", "Vehicles that runs on two wheels", MotorServices.TWO_WHEELER);
        uis.add(ui);

        ui=new MotorHomeUI(R.drawable.three_wheel, "Three wheelers", "Vehicles that runs on three wheels", MotorServices.THREE_WHEELEER);
        uis.add(ui);

        ui=new MotorHomeUI(R.drawable.car, "Private car", "Private service car", MotorServices.PRIVATE_CAR);
        uis.add(ui);

        ui=new MotorHomeUI(R.drawable.taxi, "Taxi car", "Public services cars", MotorServices.TAXI_CAR);
        uis.add(ui);

        ui=new MotorHomeUI(R.drawable.truck, "Commercial vehicle", "Commercial services cars", MotorServices.COMMERCIAL_VEH);
        uis.add(ui);

        ui=new MotorHomeUI(R.drawable.tractor, "Miscellaneous type vehicles", "Diverse  services cars", MotorServices.MISCELLANEOUS_VEH);
        uis.add(ui);

        mAdapter = new MotorHomeAdapter(this, getContext(), uis);
        mRecyclerView.setAdapter(mAdapter);
    }

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MotorHomeInteraction) {
            mListener = (MotorHomeInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void uiPop(String message){
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(message)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMotorAdapterInteraction(int status, MotorHomeUI motorHomeUI) {
        if(status != StatusConfig.PROCEED){
            uiPop("Under construction");
            return;
        }
        int motorService=motorHomeUI.getServiceCode();
        //check which service selected
        switch (motorService){
            case MotorServices.TWO_WHEELER:
                break;
            case MotorServices.THREE_WHEELEER:
                break;
            case MotorServices.PRIVATE_CAR:
                break;
            case MotorServices.TAXI_CAR:
                break;
            case MotorServices.COMMERCIAL_VEH:
                break;
            case MotorServices.MISCELLANEOUS_VEH:
                break;
            default:
                uiPop("Under construction");
        }
    }

    private void cubicModule(int serviceCode, int motorService){

    }

    private void passengerModule(int serviceCode, int motorService){

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MotorHomeInteraction {
        void onMotorHomeInteraction(int status, Object object);
    }
}
