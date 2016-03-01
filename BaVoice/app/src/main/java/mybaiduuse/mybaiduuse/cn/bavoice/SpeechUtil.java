package mybaiduuse.mybaiduuse.cn.bavoice;

/**
 * Created by ba on 2016/2/26.
 */

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;


/**
 * 语音合成工具类
 *
 * @author JPH
 * @date 2015-4-14 下午1:33:37
 */
public class SpeechUtil implements SpeechSynthesizerListener {

    protected static final int UI_LOG_TO_VIEW = 0;
    public SpeechSynthesizer speechSynthesizer;
    private Context context;

    public SpeechUtil(Context activity) {
        this.context = activity;
        init();
    }
    /**
     * 初始化合成相关组件
     *
     * @author JPH
     * @date 2015-4-14 下午1:36:53
     */
    private void init() {

        speechSynthesizer = new SpeechSynthesizer(context, "holder", this);
        // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
        speechSynthesizer.setApiKey("jv4niBhBrajgCGpr49hdBD0w",
                "5f64ce39d5b3cf3e506b076db24cf742");
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setParams();
    }
    /**
     * 开始文本合成并朗读
     * @author JPH
     * @date 2015-4-14 下午1:47:05
     * @param content
     */
    public void speak(final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    // setParams();
                int ret = speechSynthesizer.speak(content.toString());
                if (ret != 0) {
                    Log.e("inf","开始合成器失败："+ret);
                }
            }
        }).start();
    }
    /**
     * 取消本次合成并停止朗读
     * @author JPH
     * @date 2015-4-14 下午2:20:33
     */
    public void cancle() {
      //  speechSynthesizer.
    }
    /**
     * 暂停文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
     * @author JPH
     * @date 2015-4-14 下午2:21:07
     */
    public void pause() {
        speechSynthesizer.pause();
    }
    /**
     * Error:Execution failed for task ':app:dexDebug'.
     > com.android.ide.common.process.ProcessException: org.gradle.process.internal.ExecException: Process 'command 'C:\Program Files\Java\jdk1.7.0_07\bin\java.exe'' finished with non-zero exit value 2
     * 继续文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
     * @author JPH
     * @date 2015-4-14 下午2:21:29
     */
    public void resume() {
        speechSynthesizer.resume();
    }
    /**
     * 为语音合成器设置相关参数
     * @author JPH
     * @date 2015-4-14 下午1:45:11
     */
    private void setParams() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");//发音人，目前支持女声(0)和男声(1)
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");//音量，取值范围[0, 9]，数值越大，音量越大
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");//朗读语速，取值范围[0, 9]，数值越大，语速越快
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");//音调，取值范围[0, 9]，数值越大，音量越高
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE,
                SpeechSynthesizer.AUDIO_ENCODE_AMR);//音频格式，支持bv/amr/opus/mp3，取值详见随后常量声明
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE,
                SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);//音频比特率，各音频格式支持的比特率详见随后常量声明
    }


    @Override
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {
        System.out.println("开始");
    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {
        System.out.println("开始");
    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {

    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onCancel(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {

    }
}