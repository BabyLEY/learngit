package mybaiduuse.mybaiduuse.cn.bavoice.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import mybaiduuse.mybaiduuse.cn.bavoice.R;
import mybaiduuse.mybaiduuse.cn.bavoice.utils.Md5Utils;


/**
 * Created by smyh on 2015/4/28.
 */
public class TranslateFagment extends Fragment {
    private String BaiduTrans = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String APPID = "20160224000013391";
    private String MI = "t2QEBNtO3mwZtLoXsN7F";
    private TextView tv_trans;
    private Button btn_trans;
    private Button bt_entoch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_translate,null);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        initButton();

    }

    private void initButton() {
        btn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//英译中
                int   en = 1;
                tranThread(en);
            }
        });
        bt_entoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//中译英文
                int  en = 2;
                //  tranThread(en);

                transChToEn();

            }
        });
    }

    public void initView() {

        tv_trans = (TextView) getActivity().findViewById(R.id.tv_entoch_translate_trans);

        btn_trans = (Button) getActivity().findViewById(R.id.btn_trans);
        bt_entoch = (Button) getActivity().findViewById(R.id.bt_entoch);
    }


    private Handler insHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    //System.out.println("打印一下!!!!!!!!!!!");
                    String word = msg.getData().getString("word");
                    //  System.out.println("打印一下"+word+"3333");
                    ((TextView) getActivity().findViewById(R.id.tv_entoch_translate_trans)).setText(word);
                    break;
                case 1:
                    String english = msg.getData().getString("word");
                    ((TextView) getActivity().findViewById(R.id.tv_chtoen_translate_trans)).setText(english);
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 中文转英文
     */
    private void transChToEn() {
        String putword =
                ((EditText) getActivity().findViewById(R.id.et_zntoen)).getText().toString();

        //  System.out.println("transChToEn!!!!!!!!!!" + putword);

        // 对中文字符进行编码,否则传递乱码
        //    putword = URLEncoder.encode(putword, "utf-8");
        String source = APPID + putword + "1435660288" + MI;
        String encode = Md5Utils.encode(source);//使用MD5进行加密
        HttpUtils httpUtils = new HttpUtils();
        String dataUrl = BaiduTrans +
                "?q=" +
                putword +
                "&from=zh&to=en&" +
                "appid=" +
                APPID +
                "&salt=1435660288&sign=" +
                encode;
        //    String dataUrl="http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4";
        //   String url="http://localhost:8080/zhbj/categories.json";
        //System.out.println(dataUrl);
        httpUtils.send(HttpRequest.HttpMethod.GET, dataUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {



                String ss = responseInfo.result;
                // 对字符进行解码
                String back = new String(ss);
                String str = JsonToString(back);
                Message msg = new Message();
                msg.what = 1;
                Bundle bun = new Bundle();
                bun.putString("word", str);
                msg.setData(bun);
                insHandler.sendMessage(msg);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                //   System.out.println("出错");
                Toast.makeText(getActivity(), "网络访问出错", Toast.LENGTH_LONG).show();
            }
        });
    }

        /**
         * 英转中翻译
         */
    private void transEnTo() {

        String putword = ((EditText) getActivity().findViewById(R.id.et_first)).getText()
                .toString();
        try {

            // 对中文字符进行编码,否则传递乱码
            putword = URLEncoder.encode(putword, "utf-8");
            String source = APPID + putword + "1435660288" + MI;
            String encode = Md5Utils.encode(source);//使用MD5进行加密
            URL url = new URL(BaiduTrans +
                    "?q=" +
                    putword +
                    "&from=en&to=zh&" +
                    "appid=" +
                    APPID +
                    "&salt=1435660288&sign=" +
                    encode);
            connect(url);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    /**
     * 链接网络
     *
     * @param url
     */
    private void connect(URL url) {
        try {
            // System.out.println("connect的url"+url);//进行了
            URLConnection con = url.openConnection();
            con.connect();
            InputStreamReader reader = new InputStreamReader(
                    con.getInputStream());
            BufferedReader bufread = new BufferedReader(reader);
            StringBuffer buff = new StringBuffer();
            //   System.out.println("connect的buff"+buff.toString());
            String line;
            while ((line = bufread.readLine()) != null) {
                buff.append(line);
                //     System.out.println("connect"+line);
            }
            // 对字符进行解码
            String back = new String(buff.toString().getBytes("ISO-8859-1"),
                    "UTF-8");
            String str = JsonToString(back);
            Message msg = new Message();
            msg.what = 0;
            Bundle bun = new Bundle();
            bun.putString("word", str);
            msg.setData(bun);
            insHandler.sendMessage(msg);

            reader.close();
            bufread.close();
        } catch (Exception e) {
            //  System.out.println("connect的urlException"+url);
            e.printStackTrace();
        }

    }


    /**
     * 获取jsoon中翻译的内容
     *
     * @param jstring
     * @return
     */
    private String JsonToString(String jstring) {
        try {
            //   System.out.println("在访问网络" + jstring);
            JSONObject obj = new JSONObject(jstring);
            JSONArray array = obj.getJSONArray("trans_result");
            obj = array.getJSONObject(0);
            String word = obj.getString("dst");
            return word;
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 访问网络线程
     */
    private void tranThread(final int en) {
        new Thread() {
            public void run() {

                if(en==1){
                    transEnTo();
                }else if(en==2){
                    //System.out.println("英译中"+en+"!!!!!!!!!!!!!!!!!!!");
                    transChToEn();
                }
            }

            ;
        }.start();
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
