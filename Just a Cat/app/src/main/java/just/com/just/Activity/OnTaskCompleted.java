package just.com.just.Activity;

import java.util.ArrayList;

import just.com.just.Data.Articles;

/**
 * OnTaskCompleted
 * Interface to pass the response back from the FetchTask to the ArticleListActivity
 */
public interface OnTaskCompleted
{
    void onTaskComplete(ArrayList<Articles> articles);
}