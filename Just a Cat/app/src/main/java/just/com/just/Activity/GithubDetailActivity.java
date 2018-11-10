package just.com.just.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import just.com.just.R;

public class GithubDetailActivity extends AppCompatActivity
{
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_detail);

        //左上角出现小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wv= (WebView) findViewById(R.id.webView);

        wv.getSettings().setJavaScriptEnabled(true);

        Intent intent=getIntent();


        String url = intent.getStringExtra("url");

        wv.loadUrl(url);


        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        wv.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack())
                    {  //表示按返回键
                        wv.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //点击小箭头返回
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
