package main.java.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movies {
    @Id
    private final int id;
    private final String title;
    private final String poster_path;
    private final String overview;
    private final String releaseDate;
    private final String written;
    private final String runtime;
    private final String boxoffice;
    private final String studio;
    private final String genre;
    private final String directed;

    public Movies() {
        this.id = -1;
        this.title = "title";
        this.poster_path = "poster_path";
        this.overview = "overview";
        this.releaseDate = "releaseDate";
        this.written = "written";
        this.runtime = "runtime"; 
        this.boxoffice = "boxoffice";
        this.studio = "studio";
        this.genre = "genre";
        this.directed = "directed";
    }

    public Movies(int id, String title, String poster_path, String overview, String releaseDate, String written, String runtime, String boxoffice, String studio, String genre, String directed){
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.written = written;
        this.runtime = runtime; 
        this.boxoffice = boxoffice;
        this.studio = studio;
        this.genre = genre;
        this.directed = directed;
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
    public String getWritten(){
        return written;
    }
    public String getRuntime() {
        return runtime;
    }
    public String getBoxoffice() {
        return boxoffice;
    }
    public String getStudio() {
        return studio;
    }
    public String getGenre() {
        return genre;
    }
    public String getDirected() {
        return directed;
    }

    
}