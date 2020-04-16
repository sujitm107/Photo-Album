package Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Album object
 * By Sujit Molleti and Rachana Kotamraju
 *
 */
public class AlbumDetail implements Serializable {
    /**
     * Each album has a name and a list containing all of the photos in the album
     */
    String name;
    private ArrayList<PhotoDetail> photos;
    static final long serialVersionUID = 1L;

    /**
     * Constructors for album detail
     * @param name
     */
    public AlbumDetail(String name){
        this.name = name;
        this.photos = new ArrayList<PhotoDetail>();
    }

    public AlbumDetail(String searchedText, ArrayList<PhotoDetail> searchedPhotos){
        this.name = searchedText;
        this.photos = searchedPhotos;
    }

    /**
     * Returns name of the album
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Allows user to set a new name to the album
     * @param newName
     */

    public void setName(String newName){
        this.name = newName;
    }

    /**
     * Prints out album details such as getting the date of the first and last photo to provide a date range as well as the number of photos in an album
     * Used to set the details of each album in the AlbumMain screen which displays all the albums of a user
     * @return
     */
    public String toString(){

        if(photos.size() == 0){
            return name+" • "+photos.size();
        }

        String startDate = null;
        try {
            startDate = getFirstPhoto();
        } catch (ParseException e) {
            startDate = "";
        } catch (NullPointerException e){
            return name+" • "+photos.size();
        }
        String endDate = null;
        try {
            endDate = getLastPhoto();
        } catch (ParseException e) {
            endDate = "";
        } catch (NullPointerException e){
            return name+" • "+photos.size();
        }

        return name+" • "+photos.size()+ " • "+startDate+" to "+endDate;
    }

    /**
     * Allows user to get date of first photo in the album
     * @return
     * @throws ParseException
     */

    private String getFirstPhoto() throws ParseException {

        Date min = new SimpleDateFormat("MM/dd/yyyy").parse(photos.get(0).getDate());
        Date temp;
        for(PhotoDetail p : photos){
            temp = new SimpleDateFormat("MM/dd/yyyy").parse(p.getDate());
            if(temp.before(min)){
                min = temp;
            }
        }

        String date = new SimpleDateFormat("MM/dd/yyyy").format(min);

        return date;
    }

    /**
     * Allows user to get date of last photo in the album
     * @return
     * @throws ParseException
     */

    private String getLastPhoto() throws ParseException {
        Date max = new SimpleDateFormat("MM/dd/yyyy").parse(photos.get(0).getDate());
        Date temp;
        for(PhotoDetail p : photos){
            temp = new SimpleDateFormat("MM/dd/yyyy").parse(p.getDate());
            if(temp.after(max)){
                max = temp;
            }
        }

        String date = new SimpleDateFormat("MM/dd/yyyy").format(max);

        return date;
    }

    /**
     * Allows user to add a new photo to the album
     * @param photo
     */
    public void addPhoto(PhotoDetail photo){
        photos.add(photo);
    }


    /**
     * Return the lists of photos in the album
     * @return
     */
    public ArrayList<PhotoDetail> getPhotos(){

        return this.photos;
    }


    /**
     * Allows user to delete a photo from the album
     * @param removedPhoto
     */
    public void removePhoto(PhotoDetail removedPhoto){

        photos.remove(removedPhoto);
    }
}
