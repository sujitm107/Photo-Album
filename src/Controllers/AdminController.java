package Controllers;

import Model.UserDetail;
import Model.UsersList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;
/**
 * By Sujit Molleti and Rachana Kotamraju
 */
public class AdminController implements Initializable {

    static final long serialVersionUID = 1L;

    //private UsersList usersList = UsersList.getInstance();

    //Buttons
     @FXML
     private Button logOutButton;
     @FXML
     private Button deleteUserButton;
     @FXML
     private Button createNewUserButton;

     //ListView
    @FXML
    private ListView<UserDetail> usersListView;

    ObservableList<UserDetail> usersObservableList = observableArrayList();

    /**
     * Initializes this screen by reading in all the current users of the application and setting it to the listview
     * This is the admin screen
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("Intitializing");

        try {
            UsersList.getInstance().readApp();
        } catch (EOFException e){
            //usersList = new UsersList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        usersObservableList.addAll(UsersList.getInstance().getUsers());
        usersListView.setItems(usersObservableList);

    }

    /**
     * Logs out admin to login screen
     * @param e User presses log out button
     * @throws IOException
     */

     @FXML
        private void logOutPressed(ActionEvent e) throws IOException {

         System.out.println("Logging out from Admin Main Screen");

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
     * Allows admin to create a new user
     * @param e User presses create user
     */
     @FXML
    private void createUserPressed(ActionEvent e){
         TextInputDialog td = new TextInputDialog();
         td.setHeaderText("Enter a UserName");
         td.showAndWait();

         if(td.getResult() == null){
             return;
         }

         String username = td.getResult().trim();
         if(username.length() == 0){
             Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Username!", ButtonType.OK);
             alert.showAndWait();
             return;
         }

         if(invalidUsername(username)){
             Alert alert = new Alert(Alert.AlertType.ERROR, "Username Already Exists!", ButtonType.OK);
             alert.showAndWait();
             return;
         }

         UserDetail newUser = new UserDetail(username);
         usersObservableList.add(newUser);
         UsersList.getInstance().addUser(newUser);
         deleteUserButton.setDisable(false);
     }

    /**
     * Checks if username added by the admin to create new user already exists
     * @param username New UserName
     * @return true if already exists, false otherwise
     */
     private boolean invalidUsername(String username){

        for(UserDetail u : UsersList.getInstance().getUsers()){
            String existingUsername = u.getUsername().toLowerCase();
            if(existingUsername.equals(username.toLowerCase())){
                return true;
            }
        }
        return false;
     }

    /**
     * Allows admin to delete any user except for the stock user. Reloads user list view to updated list
     * @param e Pressed Delete user
     */
     @FXML
    private void deleteUserPressed(ActionEvent e) {
         if(usersListView.getSelectionModel().getSelectedItem() == null){
             return;
         }

         if(usersListView.getSelectionModel().getSelectedItem().getUsername().equals("stock")){

             Alert alert = new Alert(Alert.AlertType.WARNING,"YOU CANNOT DELETE THE STOCK USER!", ButtonType.OK);
             alert.showAndWait();

             return;
         }

         ButtonType userChoice = ButtonType.NO;
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this user?", ButtonType.CANCEL, ButtonType.YES);
         alert.showAndWait();
         userChoice = alert.getResult();
         if(userChoice == ButtonType.CANCEL){
             return;
         }

         UserDetail removedUser = usersListView.getSelectionModel().getSelectedItem();
         usersObservableList.remove(removedUser);
         UsersList.getInstance().removeUser(removedUser);

         System.out.println("Deleted");
     }
}
