package main.java.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String password;
    private String email;

    private boolean verified;
    private boolean visible;

    @OneToMany(targetEntity = Content.class, mappedBy = "id")
    private List<Content> movieWatchlist;
    @OneToMany(targetEntity = TVShowSeason.class, mappedBy = "season_id")
    private List<TVShowSeason> televisionWatchlist;
    @ElementCollection
    private List<Content> blacklist;
    @ElementCollection
    private List<String> roles;
    @ElementCollection
    private List<User> following;

    public User() {
        roles = new ArrayList();
        following = new ArrayList();
        blacklist = new ArrayList();
    }

    public User(int id) {
        this.id = id;
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role.name());
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public int addToWatchlist(int contentID) {
        return 0;
    }

    public int addToBlacklist(int contentID) {
        return 0;
    }

    public List<Content> getBlackList() {
        return blacklist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Content> getMovieWatchlist() {
        return movieWatchlist;
    }

    public void setMovieWatchlist(List<Content> movieWatchlist) {
        this.movieWatchlist = movieWatchlist;
    }

    public List<TVShowSeason> getTelevisionWatchlist() {
        return televisionWatchlist;
    }

    public void setTelevisionWatchlist(List<TVShowSeason> televisionWatchlist) {
        this.televisionWatchlist = televisionWatchlist;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
