package com.example.rw_l.gxcw.parkingLot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rw_l.gxcw.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollFragment_parkingLot extends Fragment{

    @BindView(R.id.scrollViewParkingLot)
    NestedScrollView mScrollView;

    @BindView(R.id.bt_parkingLot_edition)
    Button buttonEdition;

    @BindView(R.id.text_parkingLot_name)
    TextView textName;

    @BindView(R.id.text_parkingLot_location)
    TextView textLocation;

    @BindView(R.id.text_parkingLot_total)
    TextView textTotal;

    @BindView(R.id.text_parkingLot_number)
    TextView textNumber;

    public ScrollFragment_parkingLot newInstance() {
        return new ScrollFragment_parkingLot();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scroll_view_parking_lot, container, false);
        ButterKnife.bind(this,view);

        buttonEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UpdataParkingLotActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",textName.getText()+"");
                bundle.putString("location",textLocation.getText()+"");
                bundle.putString("total",textTotal.getText()+"");
                bundle.putString("number",textNumber.getText()+"");
                intent.putExtras(bundle);

                startActivityForResult(intent,0);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            Bundle bundle = data.getExtras();
            textName.setText(bundle.getString("name"));
            textLocation.setText(bundle.getString("location"));
            textTotal.setText(bundle.getString("total"));
            textNumber.setText(bundle.getString("number"));
            updataChangeToDataBase();
        }
    }


    private void updataChangeToDataBase() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MaterialViewPagerHelper.registerScrollView(getActivity(),mScrollView);
    }

}
