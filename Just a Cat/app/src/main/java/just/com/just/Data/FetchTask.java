package just.com.just.Data;;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import just.com.just.Activity.OnTaskCompleted;

public class FetchTask extends AsyncTask<String, Void, ArrayList<Articles>>
{
    public OnTaskCompleted listener = null;

    public FetchTask(OnTaskCompleted listener)
    {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Articles> doInBackground(String... String)
    {
        String jsonResults = API.callAPI();
        Log.e("Json ", jsonResults);
        return GuardianJsonParser.getArticlesFromJSON(jsonResults);
    }

    @Override
    protected void onPostExecute(ArrayList<Articles> articlesArrayList)
    {
        super.onPostExecute(articlesArrayList);
        Log.e("Array ", "" + articlesArrayList.size());
        listener.onTaskComplete(articlesArrayList);
    }

}
