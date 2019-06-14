package com.swj.copd.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swj.copd.R;
import com.swj.copd.view.LineView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.swj.copd.service.PMIntentService.PM25_RECEIVE_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PMFragment extends Fragment {

    private static final String TAG = "PMFragment";

    private LineView pmChart;

    private List<String> xValues = new ArrayList<>();

    private List<Float> yValues = new ArrayList<>();

    private List<String> yLables = new ArrayList<>();   //y轴显示

    private MessageReceiver receiver;

    private void initData()
    {
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
    }

    public PMFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PMFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PMFragment newInstance() {
        PMFragment fragment = new PMFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pm, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pmChart = getActivity().findViewById(R.id.pm_lineView);
        pmChart.setYLables(yLables);
        pmChart.setYValues(yValues);
        pmChart.setXValues(xValues);
        pmChart.setLableCountY(yLables.size());
        pmChart.invalidate();

        IntentFilter filter = new IntentFilter(PM25_RECEIVE_MESSAGE);
        receiver = new MessageReceiver();
        getActivity().registerReceiver(receiver,filter);
    }

    private class MessageReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Log.e(TAG, "收到广播" );
            try {
                JSONArray jsonArray = new JSONArray(msg);
                int len = jsonArray.length();
                for(int i = 0; i < len; i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    yValues.add(((float) object.getDouble("value"))*10);
                    String date = object.getString("dateTime");
                    char[] chars = date.toCharArray();
                    String mindate = ""+chars[11]+chars[12]+chars[13]+chars[14]+chars[15];
                    xValues.add(mindate);
                }
                pmChart.setXValues(xValues);
                pmChart.setYValues(yValues);
                pmChart.invalidate();
                Log.e(TAG, "设置成功" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
