package just.com.just.Http;

import just.com.just.Bean.Ask;
import just.com.just.Bean.Take;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface API
{
    //发送json数据形式的post请求，把网络请求接口的后半部分openapi/api/v写在里面
    //Ask是请求数据实体类，Take接受数据实体类
    @POST("openapi/api/v2")
    Call<Take> request(@Body Ask ask);

}
