package just.com.just.util;

public interface HttpCallBackListener
{
    void onFinish(String response);
    void onError(Exception e);
}
