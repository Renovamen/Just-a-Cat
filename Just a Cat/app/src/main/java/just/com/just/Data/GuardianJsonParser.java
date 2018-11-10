package just.com.just.Data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuardianJsonParser
{
    // Field constants
    private static final String JSON_RESPONSE = "response";
    private static final String JSON_RESULTS = "results";
    private static final String JSON_TAGS = "tags";
    private static final String JSON_BLOCKS = "blocks";
    private static final String JSON_REQUESTED_BODY_BLOCKS = "requestedBodyBlocks";
    private static final String JSON_BODY_LATEST = "body:latest";
    private static final String JSON_ELEMENTS = "elements";
    private static final String JSON_ASSETS = "assets";
    private static final String JSON_FILE = "file";
    private static final String VALUE_NO_IMAGE = "No Image";
    private static final String VALUE_NO_AUTHOR = "No Author";
    private static final String ARTICLE_ID = "id";
    private static final String ARTICLE_TYPE = "type";
    private static final String ARTICLE_SECTION_ID = "sectionId";
    private static final String ARTICLE_SECTION_NAME = "sectionName";
    private static final String ARTICLE_WEB_PLUBLICATION_DATE = "webPublicationDate";
    private static final String ARTICLE_WEB_TITLE = "webTitle";
    private static final String ARTICLE_WEB_URL = "webUrl";
    private static final String ARTICLE_API_URL = "apiUrl";
    private static final String ARTICLE_IS_HOSTED = "isHosted";
    private static ArrayList<Articles> mArticlesArrayList;
    /**
     * GuardianJsonParser Constructor
     */
    public GuardianJsonParser()
    {
    }

    public static ArrayList<Articles> getArticlesFromJSON(String articlesJSONString)
    {
        try
        {
            // Create the articlesJSON object with the articlesJSONString parameter
            JSONObject jsonObject = new JSONObject(articlesJSONString);
            JSONObject responseObject = jsonObject.getJSONObject(JSON_RESPONSE);
            JSONArray resultsArray = responseObject.getJSONArray(JSON_RESULTS);
            int articlesQty = resultsArray.length();
            mArticlesArrayList = new ArrayList<>();
            // Loop through the articlesArray to parse each Json object needed
            for (int i = 0; i < articlesQty; i++)
            {
                JSONObject articleRecord = resultsArray.getJSONObject(i);
                // Parse the individual data elements needed
                String articleId = articleRecord.getString(ARTICLE_ID);
                String articleType = articleRecord.getString(ARTICLE_TYPE);
                String articleSectionId = articleRecord.getString(ARTICLE_SECTION_ID);
                String articleSectionName = articleRecord.getString(ARTICLE_SECTION_NAME);
                String articleWebPublicationDate = articleRecord.getString(ARTICLE_WEB_PLUBLICATION_DATE);
                String articleWebTitle = articleRecord.getString(ARTICLE_WEB_TITLE);
                String articleWebUrl = articleRecord.getString(ARTICLE_WEB_URL);
                String articleApiUrl = articleRecord.getString(ARTICLE_API_URL);
                Boolean articleIsHosted = articleRecord.getBoolean(ARTICLE_IS_HOSTED);
                // Try to get the author from the tags
                JSONArray articleTagsArray = articleRecord.getJSONArray(JSON_TAGS);
                String articleAuthor = VALUE_NO_AUTHOR;
                try
                {
                    JSONObject articleContributor = articleTagsArray.getJSONObject(0);
                    articleAuthor = articleContributor.getString(ARTICLE_WEB_TITLE);
                } catch (JSONException ignored)
                {
                }
                // Try to get the image
                String articleFile = VALUE_NO_IMAGE;
                try {
                    JSONObject articleBlocks = articleRecord.getJSONObject(JSON_BLOCKS);
                    JSONObject articleRequestedBlocks = articleBlocks.getJSONObject(JSON_REQUESTED_BODY_BLOCKS);
                    JSONArray articleBodyLatestArray = articleRequestedBlocks.getJSONArray(JSON_BODY_LATEST);
                    JSONObject articleBodyLatest = articleBodyLatestArray.getJSONObject(0);
                    JSONArray articleElements = articleBodyLatest.getJSONArray(JSON_ELEMENTS);
                    JSONObject articleAssets = articleElements.getJSONObject(1);
                    JSONArray articleImageArray = articleAssets.getJSONArray(JSON_ASSETS);
                    JSONObject articleImage = articleImageArray.getJSONObject(3);
                    articleFile = articleImage.getString(JSON_FILE);
                }
                catch (JSONException ignored)
                {
                }
                // Create an Article Object
                Articles article = new Articles(
                        articleId,
                        articleType,
                        articleSectionId,
                        articleSectionName,
                        articleWebPublicationDate,
                        articleWebTitle,
                        articleWebUrl,
                        articleApiUrl,
                        articleIsHosted,
                        articleAuthor,
                        articleFile
                );
                // Add the Article Object to the ArrayList
                mArticlesArrayList.add(article);
            }
        }
        catch (JSONException e)
        {
            Log.e("Exception: ", "" + e);
            e.printStackTrace();
        }
        return mArticlesArrayList;
    }

}
