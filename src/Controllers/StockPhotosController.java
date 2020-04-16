package Controllers;

import Model.AlbumDetail;
import Model.UserDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class StockPhotosController implements Initializable {
    //Fields
    private UserDetail user;
    private AlbumDetail album;

    //Buttons
    @FXML
    private Button backButton;

    //ImageViews
    @FXML
    private ImageView dogImageView;

    @FXML
    private ImageView catImageView;

    @FXML
    private ImageView chocolateImageView;

    @FXML
    private ImageView happyBabyImageView;

    @FXML
    private ImageView flowerImageView;

    @FXML
    private ImageView familyImageView;

    /**
     * Initializes image views in stock photo screen with images of stock photos
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] stockImages = {"./data/cat_caviar.jpg/", "./data/chocolate.jpg/", "./data/family.jpeg/", "./data/flower.jpg/", "./data/happy.jpg/", "./data/puppy.jpeg/"};

        File myFile = new File(stockImages[0]);
        String text = myFile.toURI().toString();
        Image image = new Image(text);
        catImageView.setImage(image);

        myFile = new File(stockImages[1]);
        text = myFile.toURI().toString();
        image = new Image(text);
        chocolateImageView.setImage(image);

        myFile = new File(stockImages[2]);
        text = myFile.toURI().toString();
        image = new Image(text);
        familyImageView.setImage(image);

        myFile = new File(stockImages[3]);
        text = myFile.toURI().toString();
        image = new Image(text);
        flowerImageView.setImage(image);

        myFile = new File(stockImages[4]);
        text = myFile.toURI().toString();
        image = new Image(text);
        happyBabyImageView.setImage(image);

        myFile = new File(stockImages[5]);
        text = myFile.toURI().toString();
        image = new Image(text);
        dogImageView.setImage(image);


//        myFile = new File(stockImages[4]);
//        text = myFile.toURI().toString();
//        image = new Image(text);
//        happyBabyImageView.setImage(image);
//
//        Image dog = new Image(text);
//        Image happyBaby = new Image("./data/happy.jpg");
//        Image flower = new Image("./data/flower.jpg");
//        Image cat = new Image(cattext);
//        Image chocolate = new Image("./data/chocolate.jpg");
//        Image family = new Image("./data/family.jpeg");
//
//
//        dogImageView.setImage(dog);
//
//        happyBabyImageView.setImage(happyBaby);
//
//        flowerImageView.setImage(flower);
//
//        catImageView.setImage(cat);
//
//        chocolateImageView.setImage(chocolate);
//
//        familyImageView.setImage(family);

    }

    /**
     * User picked a dog stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void dogClicked(MouseEvent e) throws IOException {
//        FXMLLoader loader = new FXMLLoader((getClass().getResource("addPhotoScreen.fxml")));
//        Parent root = (Parent) loader.load();
//        AddPhotoController alb = loader.getController();
//        alb.setStockPhoto("dog",user,album);
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) dogImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("dog",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * User picked a cat stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */

    @FXML
    private void catClicked(MouseEvent e) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) catImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("cat",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * User picked a family stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void familyClicked(MouseEvent e) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) familyImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("family",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * User picked a baby stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void babyClicked(MouseEvent e) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) happyBabyImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("baby",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * User picked a chocolate stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void chocolateClicked(MouseEvent e) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) chocolateImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("chocolate",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * User picked a flower stock photo - saves info and takes back to add photo screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void flowerClicked(MouseEvent e) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) flowerImageView.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setStockPhoto("flower",user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows user to send in current album and user to this screen
     * @param user
     * @param album
     */

    public void setAlbumAndUser(UserDetail user, AlbumDetail album){
        this.user = user;
        this.album = album;
        System.out.println("Setting user and album of stock photo controller: " + this.album);
       // System.out.println(this.album);
    }

    /**
     * Allows user to go back to add photo screen
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) backButton.getScene().getWindow();

        loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
        root = loader.load();

        AddPhotoController next = loader.getController();
        next.setAlbumAndUser(user,album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
