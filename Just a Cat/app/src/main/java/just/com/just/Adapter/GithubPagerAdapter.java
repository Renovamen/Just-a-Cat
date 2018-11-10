package just.com.just.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import just.com.just.Fragment.GithubFragment;


public class GithubPagerAdapter extends FragmentPagerAdapter
{
    private final String[] title={"GitHub"};
    private List<Fragment>fragments=new ArrayList<Fragment>();
    public GithubPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragments.add(new GithubFragment());
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return title[position];
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return 1;
    }
}
