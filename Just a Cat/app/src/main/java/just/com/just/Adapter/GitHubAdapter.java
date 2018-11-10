package just.com.just.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import just.com.just.Data.GitHubParser;
import just.com.just.R;
import just.com.just.Service.GitHubService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubAdapter extends BaseAdapter
{

    private static HashMap<String,String> forks = new HashMap<>();
    private static HashMap<String,String> watchers = new HashMap<>();

    private List<GitHubParser.Project> newsList;

    private LayoutInflater mInflater;

    private Context context;

    public GitHubAdapter(ArrayList<GitHubParser.Project> newsList, Context context){
        this.newsList=newsList;
        this.context=context;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 创建产品
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        GitHubAdapter.Holder holder=null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.github_item,null);
            holder=new GitHubAdapter.Holder(convertView);
            convertView.setTag(holder);
        }
        holder = (GitHubAdapter.Holder) convertView.getTag();

        final GitHubParser.Project news = newsList.get(position);

        ImageView iv_owner=holder.iv_owner;
        TextView tv_repo=holder.tv_repo;
        TextView tv_desc=holder.tv_desc;

        TextView tv_language=holder.tv_language;

        ImageView iv_star=holder.iv_star;
        TextView tv_star_count=holder.tv_star_count;

        ImageView iv_fork=holder.iv_fork;
        final TextView tv_fork_count=holder.tv_fork_count;

        ImageView iv_watch=holder.iv_watch;
        final TextView tv_watch_count=holder.tv_watch_count;

        tv_repo.setText(news.getName());
        tv_desc.setText(news.getDescriptions());

        if (!forks.containsKey(news.getName()))
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            GitHubService gitHubService = retrofit.create(GitHubService.class);

            rx.Observable<GitHubParser.Repository> repositoryObservable = gitHubService.getRepositoryData(news.getOwner(),news.getRepository_name(),"186a796f01aa9998fc0e5d2efe641f8427cf31f0");


            //System.out.println(news.getName().replace(" ",""));

            repositoryObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<GitHubParser.Repository>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onNext(final GitHubParser.Repository storiesEntities)
                    {

                        System.out.println("yes");
                        if(storiesEntities == null)
                        {
                            tv_fork_count.setText("404");
                            tv_watch_count.setText("200");
                            forks.put(news.getName(),"404");
                            watchers.put(news.getName(),"200");
                        }
                        else if (storiesEntities.getForks_count()==null&&storiesEntities.getForks_count()=="")
                        {
                            tv_fork_count.setText("404");
                            forks.put(news.getName(),"404");
                        }
                        else if (storiesEntities.getWatchers_count()==null&&storiesEntities.getWatchers_count()=="")
                        {
                            tv_watch_count.setText("200");
                            watchers.put(news.getName(),"200");
                        }
                        else
                        {
                            forks.put(news.getName(),storiesEntities.getForks_count());
                            watchers.put(news.getName(),storiesEntities.getWatchers_count());
                            tv_fork_count.setText(storiesEntities.getForks_count());
                            tv_watch_count.setText(storiesEntities.getWatchers_count());

                        }
                    }
                });
        }
        else
        {
            tv_fork_count.setText(forks.get(news.getName()));
            tv_watch_count.setText(watchers.get(news.getName()));
        }

        tv_star_count.setText(news.getStars());
        tv_language.setText(news.getLanguage());


        // 记得判空，
        // 设置图片
        if (news.getContributors()==null||news.getContributors()==null ||news.getContributors().get(0).getAvatar()==null)
            iv_owner.setImageResource(R.mipmap.ic_launcher);
        else
        {
            iv_owner.setVisibility(View.VISIBLE);
            //用Glide根据URL加载图片
            Glide.with(context).load(news.getContributors().get(0).getAvatar()).into(iv_owner);
        }

        return convertView;
    }

    // 产品
    private class Holder
    {

        ImageView iv_owner;
        TextView tv_repo;
        TextView tv_desc;
        TextView tv_language;
        ImageView iv_star;
        TextView tv_star_count;
        ImageView iv_fork;
        TextView tv_fork_count;
        ImageView iv_watch;
        TextView tv_watch_count;


        public  Holder(View view)
        {

            iv_owner= (ImageView) view.findViewById(R.id.iv_owner);

            tv_repo= (TextView) view.findViewById(R.id.tv_repo);

            tv_desc= (TextView) view.findViewById(R.id.tv_desc);

            tv_language= (TextView) view.findViewById(R.id.tv_language);

            iv_star= (ImageView) view.findViewById(R.id.iv_star);
            tv_star_count= (TextView) view.findViewById(R.id.tv_star_count);

            iv_fork= (ImageView) view.findViewById(R.id.iv_fork);
            tv_fork_count= (TextView) view.findViewById(R.id.tv_fork_count);

            iv_watch= (ImageView) view.findViewById(R.id.iv_watch);
            tv_watch_count= (TextView) view.findViewById(R.id.tv_watch_count);

        }
    }

}