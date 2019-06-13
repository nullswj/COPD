package com.swj.copd.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swj.copd.R;
import com.swj.copd.view.LineView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.swj.copd.service.DataIntentService.TIWEN_RECEIVE_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TemperatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureFragment extends Fragment {

    private LineView temperatureChart;

    private List<String> xValues = new ArrayList<>();

    private List<Float> yValues = new ArrayList<>();

    private List<String> yLables = new ArrayList<>();   //y轴显示

    private MessageReceiver receiver;

    private void initData()
    {
//        xValues.add("12.01");
//        xValues.add("12.02");
//        xValues.add("12.03");
//        xValues.add("12.04");
//        xValues.add("12.05");
//        xValues.add("12.06");
//        xValues.add("12.07");
//        xValues.add("12.08");
//        xValues.add("12.09");
//        xValues.add("12.10");
//        xValues.add("12.11");
        yLables.add("1");
        yLables.add("2");
        yLables.add("3");
        yLables.add("4");
        yLables.add("5");
        yLables.add("6");
        yLables.add("7");
        yLables.add("8");
        yLables.add("9");
        yLables.add("10");
        yLables.add("11");
//        yValues.add(5f);
//        yValues.add(14f);
//        yValues.add(8f);
//        yValues.add(12f);
//        yValues.add(7f);
//        yValues.add(17f);
//        yValues.add(17f);
//        yValues.add(17f);
//        yValues.add(17f);
//        yValues.add(17f);
//        yValues.add(17f);
    }

    public TemperatureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     * @return A new instance of fragment TemperatureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureFragment newInstance() {
        TemperatureFragment fragment = new TemperatureFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for *this fragment
        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        temperatureChart = getActivity().findViewById(R.id.tem_lineView);
        temperatureChart.setXValues(xValues);
        temperatureChart.setYLables(yLables);
        temperatureChart.setYValues(yValues);
        temperatureChart.setLableCountY(yLables.size());
        temperatureChart.invalidate();

        IntentFilter filter = new IntentFilter(TIWEN_RECEIVE_MESSAGE);
        receiver = new MessageReceiver();
        getActivity().registerReceiver(receiver,filter);

    }

    private class MessageReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            try {
                JSONArray jsonArray = new JSONArray(msg);
                int len = jsonArray.length();
                for(int i = 0; i < len; i++)
                {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            yValues.add(Float.parseFloat(value));
//            xValues.add(time);
//            temperatureChart.setXValues(xValues);
//            temperatureChart.setYLables(yLables);
//            temperatureChart.setYValues(yValues);
//            temperatureChart.invalidate();
        }
    }
}
