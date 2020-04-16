package Model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class UserDetail implements Serializable{

    /**
     * Each UserDetail has a username and a list of all the albums
     */
    private String username;
    static final long serialVersionUID = 1L;

    private ArrayList<AlbumDetail> albums;

    private ArrayList<String> tags;

    /**
     * Constructor for UserDetail
     * @param name
     */

    public UserDetail(String name){
        this.username = name;
        this.albums = new ArrayList<AlbumDetail>();
        this.tags = new ArrayList<String>();
        tags.add("Location");
        tags.add("Person");
        tags.add("Color");
        tags.add("Add New Type");
    }

    /**
     * Prints out username
     * @return
     */
    public String toString(){
        return username;
    }

    /**
     *
     * @return username of user
     */
    public String getUsername(){
        return username;
    }

    /**
     * Functionality to add an album to the user
     * @param newAlbum
     */
    public void addAlbum(AlbumDetail newAlbum){
        albums.add(newAlbum);
    }

    /**
     * Functionality to remove an album from the user
     * @param removedAlbum
     */

    public void removeAlbum(AlbumDetail removedAlbum){
        albums.remove(removedAlbum);
    }

    /**
     *
     * @return all albums of the user
     */

    public ArrayList<AlbumDetail> getAlbums(){
        return this.albums;
    }

    /**
     * Returns album you are looking for
     * @param name
     * @return
     */

    public AlbumDetail getAlbum(String name){
        for(AlbumDetail a : albums){
            if(a.name.equals(name)){
                return a;
            }
        }
        return null;
    }

    public void addUserTag(String newTag){
        tags.add(newTag);
    }

    public ArrayList<String> getUserTags(){
        return this.tags;
    }
    /*public void addAlbum(AlbumDetail album){
        albums.add(album);
        System.out.println("Added Album");
    }*/
}
