package mybaiduuse.mybaiduuse.cn.bavoice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import java.util.ArrayList;

import mybaiduuse.mybaiduuse.cn.bavoice.R;
import mybaiduuse.mybaiduuse.cn.bavoice.SpeechUtil;


/**
 * Created by smyh on 2015/4/28.
 */
public class VoiceFragment extends Fragment {
    private SpeechUtil speechUtil;
    private EditText et_voice_main_voice_identify;
    //开始按钮
    private Button BtnStart;
    //文本框
    private TextView InputBox;
    //百度语音识别对话框
    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    //应用授权信息 ，这里使用了官方SDK中的参数，如果需要，请自行申请，并修改为自己的授权信息
    private String API_KEY = "8MAxI5o7VjKSZOKeBzS4XtxO";
    private String SECRET_KEY = "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha";
    private Button bt_voice_main_voice_identify;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragement_voice,null);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initEvent();
        initButton();

    }

    private void initEvent() {
        if (mDialog == null) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Bundle params = new Bundle();
            //设置API_KEY, SECRET_KEY
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);
            //设置语音识别对话框为蓝色高亮主题
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
            //实例化百度语音识别对话框
            mDialog = new BaiduASRDigitalDialog(getActivity(), params);
            //设置百度语音识别回调接口
            mDialogListener=new DialogRecognitionListener()
            {

                @Override
                public void onResults(Bundle mResults)
                {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        InputBox.setText(rs.get(0));
                    }

                }

            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }
        //设置语音识别模式为输入模式
        mDialog.setSpeechMode(BaiduASRDigitalDialog.SPEECH_MODE_INPUT);
        //禁用语义识别
        mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE, false);


        BtnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog.show();
            }
        });
    }

    private void initButton() {
        bt_voice_main_voice_identify.setOnClickListener(new View.OnClickListener() {//语音合成
            @Override
            public void onClick(View v) {
                speechUtil.speak(et_voice_main_voice_identify.getText().toString().trim());
            }
        });

    }

    private void initView() {
        //界面元素
        BtnStart=(Button)getActivity().findViewById(R.id.BtnStart);
        bt_voice_main_voice_identify = (Button)getActivity().findViewById(R.id.bt_voice_main_voice_identify);
        InputBox=(TextView)getActivity().findViewById(R.id.InputBox);
        speechUtil = new SpeechUtil(getActivity());
        et_voice_main_voice_identify = (EditText) getActivity().findViewById(R.id.et_voice_main_voice_identify);
    }


    //重写setMenuVisibility方法，不然会出现叠层的现象
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
    }
}

