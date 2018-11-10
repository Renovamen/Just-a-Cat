package just.com.just.Bean;

import android.graphics.Bitmap;

public class News
{
	// News title
	private String mTitle;
	// News section
	private String mSection;
	// Date when news was published
	private String mDate;
	// Url for the news story
	private String mStoryUrl;
	// Url for thumbnail of news story
	private String mThumbnailUrl;
	// Thumbnail bitmap of news story
	private Bitmap mThumbnail;

	// Create new news object that stores title, section, story url and thumbnail url of the news
	public News(String title, String section, String storyUrl, String thumbnailUrl, String date)
	{
		this.mTitle = title;
		this.mSection = section;
		this.mStoryUrl = storyUrl;
		this.mThumbnailUrl = thumbnailUrl;
		this.mDate = date;
	}

	// Return news title information
	public String getTitle()
	{
		return this.mTitle;
	}

	// Return news section information
	public String getSection()
	{
		return this.mSection;
	}

	// Return date when news story was published
	public String getTestDate()
	{
		return mDate;
	}

	// Return news story url information
	public String getStoryUrl()
	{
		return this.mStoryUrl;
	}

	// Return news story thumbnail url information
	public String getThumbnailUrl()
	{
		return this.mThumbnailUrl;
	}

	// Return news story thumbnail bitmap information
	public Bitmap getThumbnail()
	{
		return this.mThumbnail;
	}

	// Set news story thumbnail bitmap
	public void setThumbnail(Bitmap thumbnail)
	{
		this.mThumbnail = thumbnail;
	}

}
