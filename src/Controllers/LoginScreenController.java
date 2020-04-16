package Controllers;

import Model.AlbumDetail;
import Model.PhotoDetail;
import Model.UserDetail;
import Model.UsersList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class LoginScreenController implements Initializable {

    //private UsersList usersList = UsersList.getInstance();
    static final long serialVersionUID = 1L;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button closeButton;


    /**
     * Sets up login screen - user must log in with a valid username or admin
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            UsersList.getInstance().readApp();
        } catch (EOFException e){
            System.out.println("No users");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(UsersList.getInstance().getUsers().size() == 0){
            try {
                setUpStock();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                UsersList.getInstance().writeApp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**Sets up permanent stock user with a stock album of stock photos
     * Must be in every run of application
     */
    private void setUpStock() throws IOException, ClassNotFoundException {

        String str = "stock";
        String album = "Stock Album";

        UsersList.getInstance().addUser(new UserDetail(str));
        UsersList.getInstance().getUser(str).addAlbum(new AlbumDetail(album));

        String[] stockImages = {"./data/cat_caviar.jpg/", "./data/chocolate.jpg/", "./data/family.jpeg/", "./data/flower.jpg/", "./data/happy.jpg/", "./data/puppy.jpeg/"};

        for(int i = 0; i<stockImages.length; i++) {
            File myFile = new File(stockImages[i]);
            String text = myFile.toURI().toString();
            UsersList.getInstance().getUser(str).getAlbum(album).addPhoto(new PhotoDetail("StockImage "+(i+1), text, true));
        }

    }

    /**
     * Validates user's username and allows them to login to personalized account
     * @param e
     * @throws IOException
     */
    @FXML
    private void loginPressed(ActionEvent e) throws IOException {
        System.out.println("Login Button Pressed");
        
        Stage stage = null;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        String usernameText = usernameTextField.getText().trim();

        if(usernameText.length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
        if(e.getSource() == loginButton){
            stage = (Stage) loginButton.getScene().getWindow();
            if(usernameText.compareTo("admin") == 0){
                loader.setLocation(getClass().getResource("adminScreen.fxml"));
                root = loader.load();
            }else if(UsersList.getInstance().checkName(usernameText)){
                loader.setLocation(getClass().getResource("albumsMainScreen.fxml"));
                root = loader.load();
                AlbumsMainController next = loader.getController();
                next.setUser(UsersList.getInstance().getUser(usernameText));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void closeButtonPressed(ActionEvent e){
        Main.onCloseRequest();
    }


}