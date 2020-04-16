package Model;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */

public class PhotoDetail implements Serializable {
    //private ImageView photo;
    /**
     * Every PhotoDetail has a caption, file path, date, and whether it is a stock photo or not
     * It also has a list of all tags and tag values
     */

    static final long serialVersionUID = 1L;

    //private HashMap tags;

    private ArrayList<TagNode> tags;

    private String caption;
    private String filePathLocal;
    private String date;
    private boolean isStock;

    /**
     * 2 Contructors for PhotoDetail
     * @param caption
     * @param tags
     * @param filePath
     * @param date
     * @param isStock
     */

    public PhotoDetail(String caption, ArrayList<TagNode> tags, String filePath, String date, boolean isStock){
        this.caption = caption;
        this.tags = tags;
        this.filePathLocal = filePath;
        this.date = date;
        this.isStock = isStock;
    }

    public PhotoDetail(String caption, String filePathLocal, boolean isStock){
        this.caption = caption;
        this.filePathLocal = filePathLocal;
        this.isStock = isStock;
        this.tags = new ArrayList<TagNode>();
        this.date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

    }

    /**
     *
     * @return caption of photo
     */
    public String getCaption(){

        return caption;
    }

    /**
     *
     * @return filepath of photo
     */

    public String getFilePathLocal(){

        return filePathLocal;
    }

    /**
     *
     * @return last modified date of photo
     */

    public String getDate(){
        return date;
    }

    /**
     *
     * @return whether the photo is a stock photo or a photo from the local machine
     */

    public boolean getIsStock(){

        return isStock;
    }

    /**
     * Allows user to change caption of photo
     * @param caption
     */
    public void setCaption(String caption){
        this.caption = caption;
    }


    /**
     *
     * @return prints out caption
     */
    public String toString(){
        return caption;
    }

    /**
     *
     * @return list of tags and tag values of photo
     */
    public ArrayList<TagNode>  getTags(){
        return tags;
    }

    /**
     * Allows user to remove a certain tag
     * @param tag
     */
    public void removeTag(TagNode tag){
        tags.remove(tag);
    }

    /**
     * Allows user to add a tag
     * @param tag
     */
    public void addTag(TagNode tag){
        tags.add(tag);
    }



}
