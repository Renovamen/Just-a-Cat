package just.com.just.Data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TranslateParsing
{
    public  String quertString="";
    public  String transResultString="";

    public void resultAnalyze(String string)
    {
        try
        {
            JSONObject object = new JSONObject(string);
            //翻译成功
            if (object.getInt("errorCode") == 0)
            {
                //要翻译的文本
                if (object.has("query"))
                {
                    String queryString = object.getString("query");
                    quertString = queryString;
                    Log.i("2333---quertString", quertString);
                }
                //翻译结果值
                if (object.has("translation"))
                {
                    String sumResult = "";
                    JSONArray transResult = object.getJSONArray("translation");
                    for (int i = 0; i < transResult.length(); i++) sumResult = sumResult + "  " + transResult.getString(i);
                    transResultString = sumResult;
                    Log.i("2333--transResultString", transResultString);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
