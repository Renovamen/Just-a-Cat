package just.com.just.Service;

import just.com.just.Data.GitHubParser;

import io.reactivex.Observable;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit2.http.GET;

public interface GitHubService
{

    @GET("/github")
    Observable<GitHubParser> getData();

    @retrofit.http.GET("/repos/{str1}/{str2}")
    rx.Observable<GitHubParser.Repository> getRepositoryData(@Path("str1") String st1, @Path("str2") String str2, @Query("access_token") String token);
}
