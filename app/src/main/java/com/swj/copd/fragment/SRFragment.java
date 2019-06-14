package com.swj.copd.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.swj.copd.R;
import com.swj.copd.io.CopdClient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SRFragment extends Fragment{

    private static final String TAG = "SRFragment";

    private TextInputLayout text_height;

    private TextInputLayout text_weight;

    private TextInputLayout text_time;

    private Button btn_submit;

    public static String message = "I'm coming";

    public SRFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SRFragment newInstance() {
        SRFragment fragment = new SRFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sr, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text_height = getActivity().findViewById(R.id.shengao);
        text_weight = getActivity().findViewById(R.id.tizhong);
        text_time = getActivity().findViewById(R.id.shijian);
        btn_submit = getActivity().findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = text_height.getEditText().getText().toString();
                String weight = text_weight.getEditText().getText().toString();
                String time = text_time.getEditText().getText().toString();

                message = height+',' +weight+',' +time;

                Log.e(TAG, "mesage"+message);

//

                Log.e(TAG, "srclient创建成功");
                try {
                    CopdClient srclient = new CopdClient("47.106.151.249", 8082,4);
                    srclient.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_LONG).show();
            }
        });
    }

}
