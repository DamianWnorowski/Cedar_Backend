package main.java.server;


public class Movies {
    private final int id;
    private final String title;
    private final String poster_path;
    private final String overview;
    private final String releaseDate;

    public Movies(int id, String title, String poster_path, String overview, String releaseDate){
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getPoster_path(){
        return poster_path;
    }
    public String getOverview(){
        return overview;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    
}