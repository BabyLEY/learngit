package mybaiduuse.mybaiduuse.cn.bavoice.domain;

import java.util.List;

/**
 * 天气的Gson数据封装类
 * Created by ba on 2016/2/29.
 */
public class WeatherBean {
    public String date ;//日期
    public String error ;//错误代码
    public List<Results> results;
    public class Results{
        public String currentCity ;//日期
        public List<Index>   index;
        public class Index{
            public String  des	;//天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。
            public String   tipt;//	穿衣指数
            public String  title;//	穿衣
            public String   zs	;//冷
        }
        public String pm25;
        public List<Weather_data>   weather_data;
        public class Weather_data{
            public String date;//	周一 02月29日 (实时：1℃)
            public String  dayPictureUrl;//	http://api.map.baidu.com/images/weather/day/duoyun.png
            public String   nightPictureUrl;//	http://api.map.baidu.com/images/weather/night/qing.png
            public String    temperature;//	5 ~ -4℃
            public String   weather;//	多云转晴
            public String    wind	;//微风
        }


    }


    public String status;//获取成功或者失败


}
