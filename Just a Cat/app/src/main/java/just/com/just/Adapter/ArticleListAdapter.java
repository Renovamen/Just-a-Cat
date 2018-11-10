package just.com.just.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import just.com.just.R;
import just.com.just.Data.Articles;
import just.com.just.util.Utilities;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>
{
    private final static String DATE_DISPLAY = "MMM dd, yyyy";
    private final static String DATE_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final static String NO_IMAGE = "No Image";
    private final static String NO_AUTHOR = "No Author";
    private final Context mContext;
    private final ArrayList<Articles> mValues;

    public ArticleListAdapter(ArrayList<Articles> items, Context context)
    {
        this.mValues = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        // Get the article variables
        String imageUrl = holder.mItem.getImageUrl();
        String title = holder.mItem.getWebTitle();
        String date = holder.mItem.getWebPublicationDate();
        String section = holder.mItem.getSectionName().toLowerCase();
        String type = holder.mItem.getType().toLowerCase();
        String author = holder.mItem.getAuthor();
        final String url = holder.mItem.getWebUrl();
        // Convert the date to readable format
        Date dateObj = Utilities.getStringAsDate(date,DATE_UTC,null);
        String dateSting = Utilities.getDateAsString(dateObj, DATE_DISPLAY, null);
        // Set the views
        if ( imageUrl.equals(NO_IMAGE)) holder.mArticleImage.setVisibility(View.GONE);
        else
        {
            holder.mArticleImage.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(imageUrl).into(holder.mArticleImage);
        }
        holder.mArticleTitleView.setText(title);
        if ( author.equals(NO_AUTHOR) )
        {
            holder.mArticleDotView.setVisibility(View.GONE);
            holder.mArticleByView.setVisibility(View.GONE);
            holder.mArticleAuthorView.setVisibility(View.GONE);
        }
        else holder.mArticleAuthorView.setText(author);
        holder.mArticleDateView.setText(dateSting);
        holder.mArticleSectionView.setText(section);
        holder.mArticleTypeView.setText(type);
        holder.mArticleReadStory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(mContext.getPackageManager()) != null)
                    mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (mValues != null) return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final ImageView mArticleImage;
        public final TextView mArticleTitleView;
        public final TextView mArticleByView;
        public final TextView mArticleAuthorView;
        public final TextView mArticleDotView;
        public final TextView mArticlePublishedOnView;
        public final TextView mArticleDateView;
        public final TextView mArticleSectionView;
        public final TextView mArticleTypeView;
        public final Button mArticleReadStory;
        public Articles mItem;
        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mArticleImage = (ImageView) view.findViewById(R.id.article_image);
            mArticleTitleView = (TextView) view.findViewById(R.id.article_title);
            mArticleByView = (TextView) view.findViewById(R.id.article_by);
            mArticleAuthorView = (TextView) view.findViewById(R.id.article_author);
            mArticleDotView = (TextView) view.findViewById(R.id.article_dot);
            mArticlePublishedOnView = (TextView) view.findViewById(R.id.article_published_on);
            mArticleDateView = (TextView) view.findViewById(R.id.article_date);
            mArticleSectionView = (TextView) view.findViewById(R.id.article_section);
            mArticleTypeView = (TextView) view.findViewById(R.id.article_type);
            mArticleReadStory = (Button) view.findViewById(R.id.article_read_story);
        }
    }
}