package Controllers;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class AddPhotoController implements Initializable{

//Fields
    private UserDetail user;
    private AlbumDetail album;

//BUTTONS
    @FXML
    private Button logOutButton;
    @FXML
    private Button backButton;
    @FXML
    private Button addPhotoButton;
    @FXML
    private Button pickComputerButton;
    @FXML
    private Button pickStockButton;
    @FXML
    private Button addTageButton;

//TEXTFIELDS
    @FXML
    private TextField tagsTextField;
    @FXML
    private TextField captionsTextField;

//ImageView
    @FXML
    private ImageView photoImageView;

    //Labels
    @FXML
    private Label newPhotoLabel;

    //ComboBox
    @FXML
    private ComboBox<String> tagTypeComboBox;

    //Image path
    String path;

    //Observable List
    ObservableList<String> tagTypeOptions = FXCollections.observableArrayList();
    ArrayList<String> tagTypesArrayList = new ArrayList<String>();

    //add tags
    //HashMap<String,String> listOfTags = new HashMap<String,String>();

    ArrayList<TagNode> listOfTags = new ArrayList<TagNode>();

    //isStock Boolean
    Boolean isStock = false;

    /**
     * Initializes the add photo screen by adding tag types to choose from and disables/enabling certain elements
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       addPhotoButton.setDisable(true);
       tagsTextField.setDisable(true);
       tagTypeComboBox.setDisable(true);
       captionsTextField.setDisable(true);

//        tagTypeOptions.add("Location");
//        tagTypeOptions.add("Person");
//        tagTypeOptions.add("Color");
//        tagTypeOptions.add("Add New Type");
//
//
//       tagTypeComboBox.setItems(tagTypeOptions);
       // tagTypeComboBox.getItems().setAll(tagTypesArrayList);


    }


    /**
     * Logs the user out from this screen to the login screen
     * @param e User clicks on log out
     * @throws IOException
     */
    @FXML
    private void logOutPressed(ActionEvent e) throws IOException {
        System.out.println("Logging out from add Photo screen");

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
     * Takes the user back to the previous screen - albumDetail
     * @param e User clicked on back button
     * @throws IOException
     */

    @FXML
    private void backPressed(ActionEvent e) throws IOException {

        //UsersList.getInstance().writeApp();

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        if(e.getSource() == backButton){
            stage = (Stage) backButton.getScene().getWindow();
            loader.setLocation(getClass().getResource("albumDetailScreen.fxml"));
            root = loader.load();
            AlbumDetailController next = loader.getController();
            next.setAlbumAndUser(user, album);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows user to choose a photo to upload from their local machine and sets the image view to that photo
     * Save the local file path for the photo
     * @param e User clicks Choose from Computer
     * @throws IOException
     */

    @FXML
    private void pickComputerPressed(ActionEvent e) throws IOException {
        System.out.println("Picking a picture from the computer");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File fileChosen = fileChooser.showOpenDialog(pickComputerButton.getScene().getWindow());
        if(fileChosen !=null){
            System.out.println("File Path :" + fileChosen.getAbsolutePath()); //THIS IS HOW TO GET THE PATH OF THE PICTURE SELECTED
            //THIS IS WHAT WE NEED TO SAVE! IN SERIALIZING

            //load photo into imageview
            path = fileChosen.getAbsolutePath();
            File myFile = new File(path);
            Image myImage = new Image(myFile.toURI().toString());

            System.out.println("About to set ImageView");

            isStock = false;
            isStock = false;
            photoImageView.setImage(myImage);
        }
        else{
            return;
        }


        addPhotoButton.setDisable(false);
        tagTypeComboBox.setDisable(false);
        tagsTextField.setDisable(false);
        captionsTextField.setDisable(false);
    }

    /**
     * Adds the photo selected to the album by converting all the tags, caption, date, and file path to a PhotoDetail object
     * Once added, takes back to album display of all photos
     * @param e User presses add photo
     * @throws IOException
     */

    @FXML
    private void addPhotoPressed(ActionEvent e) throws IOException {
        String caption = captionsTextField.getText();
        String[] tags = tagsTextField.getText().split(",");

        String photoPath = path;

        File myFile = new File(path);
        String date = new SimpleDateFormat("MM/dd/yyyy").format(myFile.lastModified());

        System.out.println("Date (USING CAL) : " + date); //hour, minute, second format

        //Create photo object
        PhotoDetail newPhoto = new PhotoDetail(caption, listOfTags, photoPath, date, isStock);
        //System.out.println("Photo added : " + photo.filePathLocal + " caption : " + photo.caption + " date: " + photo.time);
        System.out.println(Arrays.asList(listOfTags));


            //Bring Back to Album and add to album
            System.out.println("Album name: " + this.album.getName());
            album.addPhoto(newPhoto);
            System.out.println("After add photo");
//
            UsersList.getInstance().writeApp();

            Stage stage = null;
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();

            if(e.getSource() == addPhotoButton){
                stage = (Stage) addPhotoButton.getScene().getWindow();
                loader.setLocation(getClass().getResource("albumDetailScreen.fxml"));
                root = loader.load();
                AlbumDetailController next = loader.getController();
                next.setAlbumAndUser(user, album);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


    }

    /**
     * Allows user to choose a preloaded stock image to add to the album
     * Takes to a different screen which displays stock photos
     * @param e User presses "Choose Stock"
     * @throws IOException
     */
    @FXML
    private void pickStockPressed(ActionEvent e) throws IOException {

        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        stage = (Stage) pickStockButton.getScene().getWindow();

        loader.setLocation(getClass().getResource("stockPhotos.fxml"));
        root = loader.load();

        StockPhotosController next = loader.getController();
        next.setAlbumAndUser(user, album);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows user to pass the current user and album from the StockPhotoController back to this screen
     * Saves which stock photo was chosen to add to the album and sets the imageview to that photo
     * @param subject The stock photo chosen
     * @param user Current User of Application
     * @param album Current Album
     */

    public void setStockPhoto(String subject, UserDetail user, AlbumDetail album){
        this.user = user;
        this.album = album;
        System.out.println("Reset user and album of add photo controller: " + this.album);
        isStock = true;
        String tempPath;


        String[] stockImages = {"./data/cat_caviar.jpg/", "./data/puppy.jpeg/", "./data/chocolate.jpg/", "./data/family.jpeg/", "./data/happy.jpg/", "./data/flower.jpg/"};

        if(subject.equals("cat")){
            File myFile = new File(stockImages[0]);
            tempPath = myFile.toURI().toString();
        }
        else if(subject.equals("dog")){
            System.out.println("Dog selected");
            File myFile = new File(stockImages[1]);
            tempPath = myFile.toURI().toString();
        }
        else if(subject.equals("chocolate")){
            System.out.println("Chocolate selected");
            File myFile = new File(stockImages[2]);
            tempPath = myFile.toURI().toString();
        }
        else if(subject.equals("family")){
            System.out.println("Family selected");
            File myFile = new File(stockImages[3]);
            tempPath = myFile.toURI().toString();
        }
        else if(subject.equals("baby")){
            System.out.println("Baby selected");
            File myFile = new File(stockImages[4]);
            tempPath = myFile.toURI().toString();
        }
        else{
            System.out.println("Flower selected");
            File myFile = new File(stockImages[5]);
            tempPath = myFile.toURI().toString();
        }

        path = tempPath;

        Image myImage = new Image(tempPath);

        System.out.println("About to set ImageView");

        photoImageView.setImage(myImage);

        addPhotoButton.setDisable(false);
        tagTypeComboBox.setDisable(false);
        tagsTextField.setDisable(false);
        captionsTextField.setDisable(false);
    }

    /**
     * Adds the chosen tag and tag type to the photo
     * @param e User pressed add tag
     */
    @FXML
    private void addTagPressed(ActionEvent e){
        if(tagsTextField.getText()==null || tagTypeComboBox.getSelectionModel().getSelectedItem()==null || tagsTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Must enter both a tag and a tag type!", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }
//        System.out.println(tagsTextField.getText());
//        System.out.println(tagTypeComboBox.getSelectionModel().getSelectedItem());
        if(!(tagTypeComboBox.getSelectionModel().getSelectedItem().equals("Add New Type"))){
            //listOfTags.put(tagTypeComboBox.getSelectionModel().getSelectedItem(),tagsTextField.getText());
            if(listOfTags.contains(new TagNode(tagTypeComboBox.getSelectionModel().getSelectedItem().trim(), tagsTextField.getText().trim()))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Tag Already Exists! (not adding new type)");
                alert.showAndWait();
                return;
            }

            listOfTags.add(new TagNode(tagTypeComboBox.getSelectionModel().getSelectedItem().trim(),tagsTextField.getText().trim()));
            System.out.println(tagsTextField.getText());
            System.out.println(tagTypeComboBox.getSelectionModel().getSelectedItem());
        }
        else{
            return;
        }

        tagsTextField.setText("");
        tagTypeComboBox.getSelectionModel().clearSelection();

    }

    /**
     * Prompts user to add a new tag type and determines whether type already exists or not
     * @param e User chooses to add a new tag type
     *
     */

    @FXML
    private void tagTypeSelected(ActionEvent e){

        tagTypeComboBox.setItems(tagTypeOptions);
        if (tagTypeComboBox.getSelectionModel().getSelectedItem() != null) {
            if(tagTypeComboBox.getSelectionModel().getSelectedItem().equals("Add New Type")){
                TextInputDialog newTag = new TextInputDialog();
                newTag.setHeaderText("Add New Tag Type");
                newTag.showAndWait();

                if(tagTypeOptions.contains(newTag.getResult().trim())){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Tag Type Already Exists! (adding new type)");
                    alert.showAndWait();
                    return;
                }


                String newTagText = newTag.getResult().trim();
                tagTypeOptions.add(newTagText);
                user.addUserTag(newTagText);
                //user.addUserTag(tagTypeComboBox.getSelectionModel().getSelectedItem().trim());
                for(String a : user.getUserTags()){
                    System.out.println("Tag types: " + a);
                }
                tagTypeComboBox.getSelectionModel().select(newTag.getResult().trim());

            }
        }
        else{
            return;
        }
    }

    /**
     * Allows user to pass current user and album to this screen
     * @param user Current user of application
     * @param album Current Album
     */
    public void setAlbumAndUser(UserDetail user, AlbumDetail album){
        this.user = user;
        this.album = album;


        tagTypesArrayList = user.getUserTags();

//        for(String a : tagTypesArrayList){
//            System.out.println("Tag types (set): " + a);
//        }



        //tagTypeOptions.setAll(tagTypesArrayList);

//        for(String a: tagTypeOptions){
//            System.out.println("Tag type options:" + a);
//        }

        tagTypeComboBox.setItems(tagTypeOptions);
    }


    public void addTagsCombo(MouseEvent mouseEvent) {
       // System.out.println("IN TAG");
        tagTypesArrayList = user.getUserTags();

//        for(String a : tagTypesArrayList){
//            System.out.println("Tag types (prive): " + a);
//        }

        tagTypeOptions.setAll(tagTypesArrayList);

//        for(String a: tagTypeOptions){
//            System.out.println("Tag type options (prive):" + a);
//        }

        tagTypeComboBox.setItems(tagTypeOptions);
    }
}
