package just.com.just.Data;

public class Articles
{
    private String id;
    private String type;
    private String sectionId;
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;
    private String apiUrl;
    private Boolean isHosted;
    private String author;
    private String imageUrl;

    public Articles(String id, String type, String sectionId, String sectionName,
                    String webPublicationDate, String webTitle, String webUrl,
                    String apiUrl, Boolean isHosted, String author, String imageUrl) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.isHosted = isHosted;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Boolean getHosted() {
        return isHosted;
    }

    public String getAuthor(){ return author; }

    public String getImageUrl(){ return imageUrl; }
}
