package just.com.just.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil
{
    public static void sendHttpRequest(final String address,final HttpCallBackListener httpCallBackListener){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                HttpURLConnection connection = null;
                try
                {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) response.append(line).append("\n");
                    if (httpCallBackListener != null) httpCallBackListener.onFinish(response.toString());
                }
                catch (Exception e)
                {
                    if (httpCallBackListener != null) httpCallBackListener.onError(e);
                }
                finally
                {
                    if (connection != null) connection.disconnect();
                }
            }
        }).start();
    }
}
