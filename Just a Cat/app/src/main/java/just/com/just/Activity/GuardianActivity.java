package just.com.just.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import just.com.just.Bean.Story;
import just.com.just.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import just.com.just.Adapter.ArticleListAdapter;
import just.com.just.Data.Articles;
import just.com.just.Data.FetchTask;

import static just.com.just.Activity.BaseActivity.isNight;


public class GuardianActivity extends AppCompatActivity implements OnTaskCompleted
{
    ArrayList<Articles> mArticles;
    ArticleListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (isNight) setTheme(R.style.AppThemeNight);
        setTitle("GUARDIAN");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getTitle().toString());


        OnTaskCompleted onTaskCompleted = this;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected)
        {
            FetchTask fetchTask = new FetchTask(onTaskCompleted);
            fetchTask.execute();
        }
        else
        {
            CharSequence text = getString(R.string.no_network_connection);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        mAdapter = new ArticleListAdapter(mArticles, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    public void onTaskComplete(ArrayList<Articles> articles)
    {
        mArticles = articles;
        View recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        mAdapter.notifyDataSetChanged();
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
