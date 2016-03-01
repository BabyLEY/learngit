package mybaiduuse.mybaiduuse.cn.bavoice.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**

 *     屏蔽掉ViewPager的toch事件;

 */
public class NoScrollViewPager extends ViewPager {
    /**
     * 必须要导入此构造方法不然会报错 因为会调用它
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public NoScrollViewPager(Context context) {
        super(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 屏蔽掉touch事件
        return false;
    }

}
