package just.com.just.Provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import just.com.just.R;
import just.com.just.Activity.MainActivity;

// Implementation of App Widget functionality
public class NewsWidgetProvider extends AppWidgetProvider
{

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {

        // Get sample news story strings
        CharSequence sampleNewStoryOne = context.getString(R.string.sample_news_story_1);
        CharSequence sampleNewStoryTwo = context.getString(R.string.sample_news_story_2);
        CharSequence sampleNewStoryThree = context.getString(R.string.sample_news_story_3);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
        views.setTextViewText(R.id.appwidget_tv_1, sampleNewStoryOne);
        views.setTextViewText(R.id.appwidget_tv_2, sampleNewStoryTwo);
        views.setTextViewText(R.id.appwidget_tv_3, sampleNewStoryThree);

        // Widget click open app
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.news_widget_container, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}

