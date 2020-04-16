package Controllers;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.net.URL;

import static javafx.collections.FXCollections.observableArrayList;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class AlbumsMainController implements Initializable{

//Fields
    private UserDetail user;
    //private UsersList usersList = UsersList.getInstance();
    static final long serialVersionUID = 1L;

//BUTTONS
    @FXML
    private Button logOutButton;

    @FXML
    private Button renameAlbumButton;
    @FXML
    private Button deleteAlbumButton;
    @FXML
    private Button createAlbumButton;
    @FXML
    private Button selectAlbumButton;
    @FXML
    private Button searchButton;

    @FXML
    private Button cancelButton;

    //Editing mode
    boolean editMode;

//TEXTFIELDS
    @FXML
    private TextField searchAlbumsTextField;

//LISTVIEW
    @FXML
    private ListView<AlbumDetail> AlbumsListView;

    final ObservableList<AlbumDetail> albumsObservableList = observableArrayList();

//ComboBox
    @FXML
    private ComboBox searchComboBox;

    /**
     * Initializes this screen by loading all the current albums of the user into a listview
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        editMode = false;
        disableEditingMode();
        AlbumsListView.setItems(albumsObservableList);

        searchComboBox.getItems().add("Tag");
        searchComboBox.getItems().add("Date");
        searchComboBox.getSelectionModel().selectFirst();

        AlbumsListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(AlbumsListView.isFocused()){
                    System.out.println("No Error");

                }
            }
        });

    }

//ONCLICK METHODS

    /**
     * Allows user to search by date or tags - calls respective methods
     * @param e
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    private void searchButtonPressed(ActionEvent e) throws IOException, ParseException {

        ArrayList<PhotoDetail> temp = new ArrayList<PhotoDetail>();
        String searchedText = searchAlbumsTextField.getText().trim();
        //not allowing user to search for nothing, no need for alert because google does not alert
        if(searchedText.length() == 0){ return; }

        if(searchComboBox.getSelectionModel().isSelected(0)){
            searchByTag(searchedText);
        }else{
            searchByDate(searchedText);
        }



    }

    /**
     * Allows user to search by a date range specified. Returns all photos in all albums of the current user that meet the date range (inclusive)
     * @param searchedText
     * @throws IOException
     * @throws ParseException
     */
    private void searchByDate(String searchedText) throws IOException, ParseException {

        //Make sure they are entering the date in the correct format
        //mm/dd/year to mm/dd/year
        String[] dates = searchedText.split("[ to]+");

        Date startDate;
        Date endDate;

        if(dates.length < 2 || searchedText.indexOf("to") == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Search date in format: mm/dd/yyyy to mm/dd/yyyy", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }

        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dates[0]);
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dates[1]);
        }catch (ParseException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Search date in format: mm/dd/yyyy to mm/dd/yyyy", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }

        ArrayList<PhotoDetail> temp = new ArrayList<PhotoDetail>();

        for(AlbumDetail a : user.getAlbums()){
            for(PhotoDetail p : a.getPhotos()){
                Date photoDate = new SimpleDateFormat("MM/dd/yyyy").parse(p.getDate());
                if(photoDate.equals(startDate) || photoDate.equals(endDate)){
                    temp.add(p);
                } else if(photoDate.after(startDate) && photoDate.before(endDate)) {
                    temp.add(p);
                }
            }
        }


        AlbumDetail tempAlbum = new AlbumDetail(searchedText, temp);

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) AlbumsListView.getScene().getWindow();
        loader.setLocation(getClass().getResource("albumDetailScreen.fxml"));
        root = loader.load();
        AlbumDetailController next = loader.getController();
        next.setAlbumAndUser(user, tempAlbum);
        next.setSearch();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Searching By date");
    }

    /**
     * Allows user to search all albums of the current user for photos that match the tag value pairs
     * @param searchedText
     * @throws IOException
     */
    private void searchByTag(String searchedText) throws IOException{


        ArrayList<PhotoDetail> temp = new ArrayList<PhotoDetail>();

        String[] part = searchedText.split("[ =]+");

        if(part.length < 2 || searchedText.indexOf('=') == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please Enter a search in the format: Tag=Value", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }

        TagNode searchTagNode = new TagNode(part[0], part[1]);


        if(part.length > 2){
            String logical = part[2].toLowerCase();
            String tag2 = part[3];
            String value2 = part[4];

            TagNode searchTagNode2 = new TagNode(part[3], part[4]);

            for(AlbumDetail a : user.getAlbums()){
                for(PhotoDetail p : a.getPhotos()){
                    if(logical.equals("and")) {
                        if (p.getTags().contains(searchTagNode) && p.getTags().contains(searchTagNode2)) {
                            temp.add(p);
                        }
                    }
                    else if(logical.equals("or")){
                        if(p.getTags().contains(searchTagNode) || p.getTags().contains(searchTagNode2)){
                            temp.add(p);
                        }
                    }
                }
            }


        }else{
            for(AlbumDetail a : user.getAlbums()){
                for(PhotoDetail p : a.getPhotos()){
                    if(p.getTags().contains(searchTagNode)) {
                        temp.add(p);
                    }
                }
            }
        }



        if(temp.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There are no search results", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }

        AlbumDetail tempAlbum = new AlbumDetail(searchedText, temp);

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) AlbumsListView.getScene().getWindow();
        loader.setLocation(getClass().getResource("albumDetailScreen.fxml"));
        root = loader.load();
        AlbumDetailController next = loader.getController();
        next.setAlbumAndUser(user, tempAlbum);
        next.setSearch();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to the screen which displays contents of the album selected
     * @throws IOException
     */
    @FXML
    private void goToAlbumDetailScreen() throws IOException{
        //ERROR: For some reason cannot click on listview with ActionEvent e as parameter

        if(editMode == false){
            AlbumDetail selectedAlbum = AlbumsListView.getSelectionModel().getSelectedItem();

            if(selectedAlbum == null){
                return;
            }

            Stage stage = null;
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();

            stage = (Stage) AlbumsListView.getScene().getWindow();

            loader.setLocation(getClass().getResource("albumDetailScreen.fxml"));
            root = loader.load();

            AlbumDetailController next = loader.getController();
            next.setAlbumAndUser(user, selectedAlbum);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }

    /**
     * Checks if there are any albums
     */

    public void isListViewEmpty(){
        if(albumsObservableList.size() == 0){
            AlbumsListView.setDisable(true);
        }
    }

    /**
     * Allows user to log out to the login screen
     * @param e
     * @throws IOException
     */
    @FXML
    private void logOutPressed(ActionEvent e) throws IOException {
        System.out.println("Logging out from albums main screen");

        //saving data on albums
        UsersList.getInstance().writeApp();

        Stage stage = null;
        Parent root = null;

        if(e.getSource() == logOutButton){
            stage = (Stage) logOutButton.getScene().getWindow();

            root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows user to select an album to delete or rename. Turns off display for album details
     * @param e
     */
    @FXML
    private void selectPressed(ActionEvent e){
        editMode = true;

        renameAlbumButton.setDisable(false);
        renameAlbumButton.setVisible(true);
        deleteAlbumButton.setDisable(false);
        deleteAlbumButton.setVisible(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);

        if(user.getAlbums().size() == 0){
            selectAlbumButton.setDisable(true);
        }


    }

    /**
     * Allows user to exit select mode without selecting anything
     * @param e
     */
    @FXML
    private void cancelPressed(ActionEvent e){
        disableEditingMode();
        editMode = false;
    }


    /**
     * Allows user to create a new album and checks whether the album name already exists
     */
    @FXML
    private void createAlbumPressed(){

        editMode = true;
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter an Album Name.");
        td.showAndWait();

        //if the user presses cancel
        if(td.getResult() == null){
            editMode = false;
            return;
        }

        String albumName = td.getResult().trim();

        //if User tries to add an empty album
        if(albumName.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter an album name!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        for( AlbumDetail a : user.getAlbums()){
            if(a.getName().equals(albumName)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "This album name already exists!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }

        AlbumDetail newAlbum = new AlbumDetail(albumName);
        albumsObservableList.add(newAlbum);
        user.addAlbum(newAlbum);

        selectAlbumButton.setDisable(false);
        editMode = false;
        AlbumsListView.setDisable(false);

    }

    /**
     * Allows user to rename a album
     * @param e
     */
    @FXML
    private void renameAlbumPressed(ActionEvent e){
        editMode = true;
        if(AlbumsListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        TextInputDialog inputName = new TextInputDialog();
        inputName.setHeaderText("Enter a new album name.");
        inputName.showAndWait();

        if(inputName.getResult() == null){
            return;
        }

        String newName = inputName.getResult().trim();

        if(user.getAlbum(newName)!=null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "An album with the same name already exists!!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        AlbumDetail selectedAlbum =AlbumsListView.getSelectionModel().getSelectedItem();
        System.out.println("Current Name: " +selectedAlbum);
        System.out.println("New Name: "+newName);
        AlbumsListView.getSelectionModel().getSelectedItem().setName(newName); //changes name behind the scenes but doesn't show up
        AlbumsListView.getItems().set(AlbumsListView.getSelectionModel().getSelectedIndex(), selectedAlbum); //shows up
        AlbumsListView.setItems(albumsObservableList); //Added to fix rename problem on April 14th

        editMode = false;

        disableEditingMode();
        selectAlbumButton.setDisable(false);
    }

    /**
     * Allows user to delete an album
     * @param e
     */
    @FXML
    private void deleteAlbumPressed(ActionEvent e){

            AlbumDetail toBeDeleted = AlbumsListView.getSelectionModel().getSelectedItem();

            editMode = true;
            if(toBeDeleted == null){
                return;
            }

            ButtonType userChoice = ButtonType.NO;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this album?", ButtonType.CANCEL, ButtonType.YES);
            alert.showAndWait();
            userChoice = alert.getResult();
            if(userChoice == ButtonType.CANCEL) {
                return;
            }


            albumsObservableList.remove(toBeDeleted);
            user.removeAlbum(toBeDeleted);
            isListViewEmpty();

            System.out.println("Deleted");
            editMode = false;

            disableEditingMode();
            selectAlbumButton.setDisable(false);
    }

    /**
     * Allows user to pass current user to this screen and set the observable list to the albums of that user
     * @param user
     */
    public void setUser(UserDetail user){
        this.user = user;
        albumsObservableList.addAll(user.getAlbums());

        if(user.getAlbums().size() == 0) {
            selectAlbumButton.setDisable(true);
            isListViewEmpty();
        }
    }

    /**
     * Disables editing mode - when user presses an album, takes to album display screen
     */
    private void disableEditingMode(){
        renameAlbumButton.setDisable(true);
        renameAlbumButton.setVisible(false);
        deleteAlbumButton.setDisable(true);
        deleteAlbumButton.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setVisible(false);

    }
}
