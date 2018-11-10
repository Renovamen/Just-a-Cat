package just.com.just.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import just.com.just.util.HttpCallBackListener;
import just.com.just.util.HttpUtil;
import just.com.just.util.MD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.Random;

import just.com.just.R;

import static just.com.just.Activity.BaseActivity.isNight;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import just.com.just.Gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

import just.com.just.Data.TranslateParsing;

// 利用有道翻译API实现翻译
// 文档地址：http://ai.youdao.com/docs/api.s#java-demo

public class TranslateActivity extends AppCompatActivity
{

    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;
    private Button mVoice;
    private HashMap<String,String> mIatResults = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (isNight) setTheme(R.style.AppThemeNight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        mEditText = (EditText) findViewById(R.id.editText1);
        mButton = (Button) findViewById(R.id.button1);
        mTextView = (TextView) findViewById(R.id.textView1);

        mVoice = findViewById(R.id.yuyinshuru);
        mVoice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startSpeechDialog();
            }
        });
        initSpeech();

    }

    //点击按钮方法
    public void translateString(View view)
    {
        String appId = "put in your youdao_appid";
        String appKey = "put in your youdao_api_key";
        String salt = "2";
        String textURLEncode = mEditText.getText().toString().trim();
        try
        {
            //进行URL编码，处理中文拼接URL导致网络访问失败
            String text = URLEncoder.encode(textURLEncode, "utf-8");
            Log.i("2333---text", text);
            //拼成需要加密的字符串
            String startMD5 = appId + textURLEncode+salt+appKey;
            //调用getMD5Str()方法获取签名sign
            String resultMD5 = getMD5Str(startMD5);
            Log.i("2333---resultMD5", resultMD5);
            //生成URL
            String URL = "http://openapi.youdao.com/api?q=" + text + "&from=auto&to=auto&appKey=" + appId + "&salt=" + salt + "&sign=" + resultMD5;
            Log.i("2333---URL", URL);

            //执行异步任务
            new MyAsync().execute(URL);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private String getMD5Str(String str)
    {
        MessageDigest messageDigest = null;

        try
        {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        //16位加密取32位加密的第9位到25位
        return md5StrBuff.toString().toUpperCase();
    }

    //异步网络请求
    class MyAsync extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Log.i("2333---s", s);
            TranslateParsing ParsingString = new TranslateParsing();
            ParsingString.resultAnalyze(s);
            Log.i("2333--transResultString",ParsingString.transResultString);
            mTextView.setText(ParsingString.transResultString);
            if(ParsingString.transResultString == "")
                Toast.makeText(TranslateActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(TranslateActivity.this, "翻译成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String url = strings[0];
            Log.i("2333---url", url);
            URLConnection connection;
            InputStream inputStream;
            String transResult = "";
            try
            {
                connection = new URL(url).openConnection();
                inputStream = connection.getInputStream();
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputReader);
                String line;
                while ((line = reader.readLine()) !=null) transResult += line;
                Log.i("2333---transResult", transResult);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return transResult;
        }
    }

    private void startSpeechDialog()
    {
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener()) ;
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mDialog.setParameter(SpeechConstant. ACCENT, "mandarin" );
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener( new MyRecognizerDialogListener()) ;
        //4. 显示dialog，接收语音输入
        mDialog.show() ;
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener
    {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast)
        {
            String result = results.getResultString(); //为解析的

            String text = JsonParser.parseIatResult(result) ;//解析过后的

            String sn = null;
            // 读取json结果中的 sn字段
            try
            {
                JSONObject resultJson = new JSONObject(results.getResultString()) ;
                sn = resultJson.optString("sn" );
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            mIatResults .put(sn, text) ;//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet())
            {
                resultBuffer.append(mIatResults .get(key));
            }
            String result_text = resultBuffer.toString().substring(0,resultBuffer.toString().length()-1);
            mEditText.setText(result_text);// 设置输入框的文本
            mEditText.setSelection(mEditText.length()) ;//把光标定位末尾
        }

        @Override
        public void onError(SpeechError speechError)
        {

        }
    }
    class MyInitListener implements InitListener
    {

        @Override
        public void onInit(int code)
        {
            if (code != ErrorCode.SUCCESS) Log.d("init","初始化失败 ");
        }
    }

    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener()
    {
        // 听写结果回调接口，返回Json 格式结果
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加
        //关于解析Json的代码可参见JsonParser类
        //isLast等于true 时会话结束。
        public void onResult(RecognizerResult results, boolean isLast)
        {

        }

        // 会话发生错误回调接口
        public void onError(SpeechError error)
        {

        }

        // 开始录音
        public void onBeginOfSpeech()
        {

        }

        //volume 音量值0~30， data音频数据
        public void onVolumeChanged(int volume, byte[] data)
        {

        }

        // 结束录音
        public void onEndOfSpeech()
        {

        }

        // 扩展用接口
        public void onEvent(int eventType, int arg1 , int arg2, Bundle obj)
        {
        }
    };

    // 语音识别
    private void startSpeech()
    {
        //1. 创建SpeechRecognizer对象，第二个参数： 本地识别时传 InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer( this, null); //语音识别器
        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant. DOMAIN, "iat" );// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mIat.setParameter(SpeechConstant. ACCENT, "mandarin" );// 设置普通话
        //3. 开始听写
        mIat.startListening( mRecoListener);
    }

    private void initSpeech()
    {
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=put in your ifly_app_id");
    }

}
