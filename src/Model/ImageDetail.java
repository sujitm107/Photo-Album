package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */

public class ImageDetail {
    /**
    Each ImageDetail contains a PhotoDetail, an ImageView to put the photo into, the file path of the photo, and the caption
    **/

    private PhotoDetail p;
    private ImageView photo;
    private String filePathLocal;
    private String caption;

    /**
     * Constructor for ImageDetail
     * @param p
     */

    public ImageDetail(PhotoDetail p){
        this.p = p;
        this.filePathLocal = p.getFilePathLocal();
        this.caption = p.getCaption();
    }

    /**
     * Allows user to set the photo to the imageview
     * @return
     */

    public ImageView getPhoto(){
        if(p.getIsStock()){ //if Stock Photo
            photo = new ImageView();
            //File myFile = new File(filePathLocal);
            Image myImage = new Image(filePathLocal);
            photo.setImage(myImage);
        }

        else{
            photo = new ImageView();
            File myFile = new File(filePathLocal);
            Image myImage = new Image(myFile.toURI().toString());
            photo.setImage(myImage);
        }

        photo.setFitHeight(150);
        photo.setFitWidth(150);

        return photo;

    }

    /**
     * Returns the photo
     * @return
     */

    public PhotoDetail retrievePhoto(){
        return p;
    }

    /**
     * Returns the caption
     * @return
     */

    public String getCaption(){
        return this.caption;
    }

}
