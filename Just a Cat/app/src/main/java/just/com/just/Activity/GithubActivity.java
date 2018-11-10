package just.com.just.Activity;



import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.astuetz.PagerSlidingTabStrip;
import just.com.just.Adapter.GithubPagerAdapter;
import just.com.just.R;

import static just.com.just.Activity.BaseActivity.isNight;

public class GithubActivity extends AppCompatActivity
{
    private ViewPager pager;
    private PagerSlidingTabStrip tab;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (isNight) setTheme(R.style.AppThemeNight);
        setTitle("Github");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        pager= (ViewPager) findViewById(R.id.pager);
        //tab= (PagerSlidingTabStrip) findViewById(R.id.tab);
        pager.setAdapter(new GithubPagerAdapter(getSupportFragmentManager()));
        //tab.setViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
