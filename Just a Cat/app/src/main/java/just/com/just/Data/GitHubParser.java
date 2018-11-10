package just.com.just.Data;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "projects",strict = false)
public class GitHubParser
{

    @ElementList(inline = true,entry = "project",required = false)
    private ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }


    @Root(name = "project",strict = false)
    public static class Project
    {

        @Element(name = "owner",required = false)
        private String owner;

        @Element(name = "repository_name",required = false)
        private String repository_name;

        @Element(name = "name",required = false)
        private String name;

        @Element(name = "descriptions",required = false)
        private String descriptions;


        @Element(name = "language",required = false)
        private String language;

        @Element(name = "stars",required = false)
        private String stars;

        @Element(name = "url",required = false)
        private String url;



        @ElementList(entry = "contributor",inline = true,required = false)
        private ArrayList<Contributor> contributors;


        public String getDescriptions()
        {
            if (descriptions == null) return "This article does not have a title, please click to view details";
            return descriptions;
        }

        public void setDescriptions(String descriptions)
        {
            this.descriptions = descriptions;
        }

        public String getTitle()
        {
            if (descriptions == null) return "This article does not have a title, please click to view details";
            return descriptions;
        }

        public String getUrl()
        {

            if (url == null) return "https://github.com/";
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ArrayList<Contributor> getContributors() {
            return contributors;
        }

        public void setContributors(ArrayList<Contributor> contributors) {
            this.contributors = contributors;
        }

        public String getName()
        {
            if(name == null) return "None";
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }

        public String getLanguage()
        {
            if(language == null) return "None";
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getRepository_name() {
            return repository_name;
        }

        public void setRepository_name(String repository_name) {
            this.repository_name = repository_name;
        }

        @Root(name = "contributor", strict = false)
        public static class Contributor{

            @Element(name = "avatar",required = false)
            private String avatar;


            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

        }
    }

    public static class Repository
    {

        @SerializedName("subscribers_count")
        private String watchers_count;

        @SerializedName("forks_count")
        private String forks_count;

        public String getWatchers_count()
        {
            return watchers_count;
        }

        public void setWatchers_count(String watchers_count)
        {
            this.watchers_count = watchers_count;
        }

        public String getForks_count()
        {
            return forks_count;
        }

        public void setForks_count(String forks_count)
        {
            this.forks_count = forks_count;
        }
    }


}
