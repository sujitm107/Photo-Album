package Controllers;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class AlbumDetailController implements Initializable {

    //Fields
    private UserDetail user;
    private AlbumDetail album;
    private boolean search;

    //BUTTONS
    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button addPhotoButton;

    @FXML
    private Label albumNameLabel;
    @FXML
    private Button createAlbumButton;

    @FXML
    private TableView<ImageDetail> albumTableView;

    @FXML
    private TableColumn<ImageDetail, String> column1;

    @FXML
    private TableColumn<ImageDetail, ImageView> column2;

    private ArrayList<ImageDetail> images;


    final ObservableList<ImageDetail> albumsObservableList = observableArrayList();

    /**
     * Initializes the Table View with the photos and captions currently in the album
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        addPhotoButton.setDisable(false);
        addPhotoButton.setVisible(true);
        createAlbumButton.setDisable(true);
        createAlbumButton.setVisible(false);


        images = new ArrayList<ImageDetail>();

        column2.setCellValueFactory(new PropertyValueFactory<>("caption"));
        column1.setCellValueFactory(new PropertyValueFactory<>("photo"));

        albumTableView.setItems(albumsObservableList);
        //albumTableView.getColumns().addAll(column1, column2);

    }

    /**
     * Allows user to log out to the login screen and saves current state
     * @param e User clicked log out
     * @throws IOException
     */

    //On Click Methods
    @FXML
    private void logOutPressed(ActionEvent e) throws IOException {
        System.out.println("Logging out from albums main screen");

        UsersList.getInstance().writeApp();

        Stage stage = null;
        Parent root = null;

        if(e.getSource() == logoutButton){
            stage = (Stage) logoutButton.getScene().getWindow();

            root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    /**
     * Allows user to go back to the previous screen which displays all the albums of the user
     * @param e
     * @throws IOException
     */
    @FXML
    private void backPressed(ActionEvent e) throws IOException {
        System.out.println("Going back to albums main screen");

        UsersList.getInstance().writeApp();

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        if(e.getSource() == backButton){
            stage = (Stage) backButton.getScene().getWindow();
            loader.setLocation(getClass().getResource("albumsMainScreen.fxml"));
            root = loader.load();
            AlbumsMainController next = loader.getController();
            next.setUser(UsersList.getInstance().getUser(user.getUsername()));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to a screen which allows them to add a new photo to the album
     * @param e User clicked add photo
     * @throws IOException
     */
    @FXML
    private void addPhotoPressed(ActionEvent e) throws IOException{

       Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        if(e.getSource() == addPhotoButton){
            stage = (Stage) addPhotoButton.getScene().getWindow();
            loader.setLocation(getClass().getResource("addPhotoScreen.fxml"));
            root = loader.load();
            AddPhotoController next = loader.getController();
            next.setAlbumAndUser(user, album);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Allows user to create a new album and takes back to albumMain screen where all the albums (including new) are displayed
     * @throws IOException
     */
    @FXML
    private void createAlbumPressed() throws IOException {
        //maybe change name because we have the same method in the AlbumsMainController
        System.out.println("Create Album");

        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter an Album Name.");
        td.showAndWait();

        //if the user presses cancel
        if(td.getResult() == null){
            return;
        }
        String albumName = td.getResult().trim();

        if(user.getAlbum(albumName)!=null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "An album already exists with this name!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //if User tries to add an empty album
        if(albumName.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter an album name!", ButtonType.OK);
            alert.showAndWait();
            createAlbumPressed();
        }
        album.setName(albumName);
        user.addAlbum(album);

        //Save and goBack
        UsersList.getInstance().writeApp();

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();


        stage = (Stage) backButton.getScene().getWindow();
        loader.setLocation(getClass().getResource("albumsMainScreen.fxml"));
        root = loader.load();
        AlbumsMainController next = loader.getController();
        next.setUser(UsersList.getInstance().getUser(user.getUsername()));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Takes to the display photo screen which displays the elements of the photo selected and allows editing
     * @param mouseEvent Picked a picture
     * @throws IOException
     */
    public void pictureSelected(MouseEvent mouseEvent) throws IOException {
        System.out.println("picture selected");

        ImageDetail photoPicked = albumTableView.getSelectionModel().getSelectedItem();

        System.out.println(photoPicked.retrievePhoto().getFilePathLocal());

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) albumTableView.getScene().getWindow();

        loader.setLocation(getClass().getResource("displayPhoto.fxml"));
        root = loader.load();

        DisplayPhotoController next = loader.getController();
        next.setAlbumAndUserandPhoto(user,album,photoPicked.retrievePhoto());
        if(search){
            next.setFromSearch();
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    //public void addPhotoToAlbum(PhotoDetail photo){
        //System.out.println("Photo passed to Album screen: " + photo.filePathLocal + " caption : " + photo.caption + " date: " + photo.time);

        //NOW MUST ADD PHOTO TO ALBUM
        //System.out.println("Current Album : " + currentAlbum.name);
//      currentAlbum.addPhoto(photo);
//        System.out.println("added photo");
//
//        for(int i = 0; i < currentAlbum.photos.size(); i++){
//            System.out.println(currentAlbum.photos.get(i).caption);
//        }
   // }

//    @FXML
//    private void photoSelected(MouseEvent e){
//        System.out.println("Picture clicked in table view");
//    }

    /**
     * Allows user to pass the current User and Album into this screen
     * @param user
     * @param album
     */

    public void setAlbumAndUser(UserDetail user, AlbumDetail album){
        this.user = user;
        this.album = album;
        albumNameLabel.setText(album.getName());

        for( PhotoDetail p : album.getPhotos() ){
            this.images.add(new ImageDetail(p));
        }

        albumsObservableList.addAll(images);

    }

    /**
     * Allows user to set whether the page is in search mode or not
     */
    public void setSearch(){
        if(this.search){
            this.search = false;
            return;
        }
        this.search = true;
        addPhotoButton.setDisable(true);
        addPhotoButton.setVisible(false);
        createAlbumButton.setDisable(false);
        createAlbumButton.setVisible(true);
    }



}
