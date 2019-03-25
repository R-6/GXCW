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

public class ScrollFragment_singleLot extends Fragment {

    @BindView(R.id.scrollViewSingleLot)
    NestedScrollView mScrollView;

    @BindView(R.id.bt_singleLot_edition)
    Button buttonEdition;

    @BindView(R.id.text_singleLot_name)
    TextView textName;

    @BindView(R.id.text_singleLot_location)
    TextView textLocation;

    @BindView(R.id.text_singleLot_start)
    TextView textStart;

    @BindView(R.id.text_singleLot_end)
    TextView textEnd;

    public ScrollFragment_singleLot newInstance() {
        return new ScrollFragment_singleLot();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scroll_view_single_lot, container, false);
        ButterKnife.bind(this,view);

        buttonEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),UpdataSingleLotActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",textName.getText()+"");
                bundle.putString("location",textLocation.getText()+"");
                bundle.putString("start",textStart.getText()+"");
                bundle.putString("end",textEnd.getText()+"");
                intent.putExtras(bundle);

                startActivityForResult(intent,1);
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
                textStart.setText(bundle.getString("start"));
                textEnd.setText(bundle.getString("end"));
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
