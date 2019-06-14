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

import static com.swj.copd.service.DataIntentService.XUEYANG_RECEIVE_MESSAGE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SpoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpoFragment extends Fragment {

    private static final String TAG = "SpoFragment";

    private LineView spoChart;

    private List<String> xValues = new ArrayList<>();

    private List<Float> yValues = new ArrayList<>();

    private List<String> yLables = new ArrayList<>();   //y轴显示


    private MessageReceiver receiver;

    private void initData()
    {
        yLables.add("0.1");
        yLables.add("0.2");
        yLables.add("0.3");
        yLables.add("0.4");
        yLables.add("0.5");
        yLables.add("0.6");
        yLables.add("0.7");
        yLables.add("0.8");
        yLables.add("0.9");
        yLables.add("1");
    }

    public SpoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SpoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpoFragment newInstance() {
        SpoFragment fragment = new SpoFragment();
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
        return inflater.inflate(R.layout.fragment_spo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spoChart = getActivity().findViewById(R.id.spo_lineView);
        spoChart.setXValues(xValues);
        spoChart.setYValues(yValues);
        spoChart.setYLables(yLables);
        spoChart.setLableCountY(yLables.size());
        spoChart.invalidate();

        IntentFilter filter = new IntentFilter(XUEYANG_RECEIVE_MESSAGE);
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
                    Log.e(TAG, "JSON "+object );
                    String date = (String)object.get("dateTime");
                    char[] chars = date.toCharArray();
                    yValues.add(Float.parseFloat((String)object.get("value")));

                    String mindate = ""+chars[11]+chars[12]+chars[13]+chars[14]+chars[15];
                    xValues.add(mindate);
                }
                spoChart.setXValues(xValues);
                spoChart.setYValues(yValues);
                spoChart.invalidate();
                Log.e(TAG, "设置成功" );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
