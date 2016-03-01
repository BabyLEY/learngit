package mybaiduuse.mybaiduuse.cn.bavoice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import mybaiduuse.mybaiduuse.cn.bavoice.R;
import mybaiduuse.mybaiduuse.cn.bavoice.view.RoutePlan;
import mybaiduuse.mybaiduuse.cn.bavoice.view.TextActivity;


/**
 * Created by smyh on 2015/4/28.
 */
public class MapFagment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initButton();

    }
    private void initButton() {
        Button button = (Button) getActivity().findViewById(R.id.bt_fragment_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("已经按下按钮"+getActivity());
                Intent intent2 = new Intent(getActivity(), RoutePlan.class);
                //必须加标记哦

                    startActivity(intent2);


            }
        });
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
