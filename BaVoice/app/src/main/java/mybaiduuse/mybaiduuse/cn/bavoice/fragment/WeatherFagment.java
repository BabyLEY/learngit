package mybaiduuse.mybaiduuse.cn.bavoice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import mybaiduuse.mybaiduuse.cn.bavoice.R;
import mybaiduuse.mybaiduuse.cn.bavoice.domain.WeatherBean;


/**
 * Created by smyh on 2015/4/28.
 */
public class WeatherFagment extends Fragment {
    private EditText et_weather;
    private WeatherBean weatherBean;
      private ListNewsAdapter mListNewsAdapter;
    private ListView lv_weathers;
    private List<WeatherBean.Results.Weather_data> mListWeathers;
    private String city = "桂林";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_weather, null);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

           initView();
          initEvent();
         setListAdapter();

        initButton();

    }

    private void initButton() {
        Button button = (Button) getActivity().findViewById(R.id.bt_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = et_weather.getText().toString().trim();
                //et_weather.setInputType(InputType.TYPE_NULL);//查询后关闭软键盘
                if(TextUtils.isEmpty(city)){
                    Toast.makeText(getActivity(),"请输入查询城市",Toast.LENGTH_LONG).show();
                    return;
                }
                initEvent();
                setListAdapter();
                // mListNewsAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 访问网络获取Json文件使用Gson解析
     */
    private void initEvent() {

        String dataUrl = "http://api.map.baidu.com/telematics/v3/weather?location=" +
                city +
                "&output=json&ak=6tYzTvGZSOpYB5Oc2YGGOKt8";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, dataUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {


                String jsonStr = responseInfo.result;
                int i = jsonStr.indexOf(":0");//判断是否输入了正确的城市

                if (i >= 0) {

                    getGson(jsonStr);//使用Gson解析
                    Toast.makeText(getActivity(), "正在查询!", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(jsonStr);
                    Toast.makeText(getActivity(), "请输入正确的城市!!!!!!", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                //   System.out.println("出错");
                Toast.makeText(getActivity(), "访问超时", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 使用Gson解析
     */

    private void getGson(String jsonStr) {
        Gson gson = new Gson();
        weatherBean = gson.fromJson(jsonStr, WeatherBean.class);

//        String city = weatherBean.results.get(0).currentCity;//获取当前城市
        // String date = weatherBean.date;//获取当前日期
        mListWeathers = weatherBean.results.get(0).weather_data;
    }

    /**
     * 获取界面以及id
     */
    private void initView() {
        et_weather = (EditText)getActivity(). findViewById(R.id.et_activity_weather_city);
        lv_weathers = (ListView)getActivity(). findViewById(R.id.lv__activity_weather_meassage);

    }



    /**
     * 设置列表新闻的数据
     */
    private void setListAdapter() {
        //适配器
        if (mListNewsAdapter == null) {
            mListNewsAdapter = new ListNewsAdapter();
            //设置给ListView
            lv_weathers.setAdapter(mListNewsAdapter);
        } else {
            mListNewsAdapter.notifyDataSetChanged();
        }

    }

    private class ListNewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mListWeathers != null) {
                return mListWeathers.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //界面
            ViewHolder viewHolder = null;
            if (convertView == null) {//如果没有缓存
                convertView = View.inflate(getActivity(), R.layout.item_weather_lv, null);
                viewHolder = new ViewHolder();

                viewHolder.tv_item_weather_date = (TextView) convertView.findViewById(R.id.tv_item_weather_date);
                viewHolder.tv_item_weather_temperature = (TextView) convertView.findViewById(R.id.tv_item_weather_temperature);
                viewHolder.tv_item_weather_tianqi = (TextView) convertView.findViewById(R.id.tv_item_weather_tianqi);
                viewHolder.tv_item_weather_wind = (TextView) convertView.findViewById(R.id.tv_item_weather_wind);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String currentCity = weatherBean.results.get(0).currentCity;
            //获取数据

            viewHolder.tv_item_weather_date.setText(mListWeathers.get(position).date);
            viewHolder.tv_item_weather_temperature.setText(currentCity+":"+mListWeathers.get(position).temperature);
            viewHolder.tv_item_weather_tianqi.setText(mListWeathers.get(position).weather);
            viewHolder.tv_item_weather_wind.setText(mListWeathers.get(position).wind);

            return convertView;
        }
    }

    /**
     * listView的缓存类
     */
    private class ViewHolder {

        TextView tv_item_weather_date;
        TextView tv_item_weather_temperature;
        TextView tv_item_weather_tianqi;
        TextView tv_item_weather_wind;
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


