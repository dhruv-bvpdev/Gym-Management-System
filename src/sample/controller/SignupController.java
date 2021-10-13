package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DB_Handler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id_field;

    @FXML
    private TextField name_field;

    @FXML
    private TextField address_field;

    @FXML
    private TextField aadhaar_field;

    @FXML
    private TextField mobile_field;

    @FXML
    private TextField username_field;

    @FXML
    private TextField password_field;

    @FXML
    private JFXButton signup_btn;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;

    @FXML
    void initialize() {
        id_field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    System.out.println("Enter Id");
                }
                else {
                    int id = Integer.parseInt(id_field.getText());
                    DBhandler = new DB_Handler();
                    try {
                        connection = DBhandler.getDbConnection();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    String query = "SELECT * FROM gym_staff WHERE ID = ?";
                    try {
                        PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
                        st.setInt(1,id);
                        ResultSet rs = st.executeQuery();
                        if(rs.next())
                        {
                            signup_btn.getScene().getWindow().hide();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/view/staff_check.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Parent root_1 = loader.getRoot();
                            Stage stage_1 = new Stage();
                            stage_1.setScene(new Scene(root_1));
                            stage_1.showAndWait();
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        signup_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = Integer.parseInt(id_field.getText());
                String name = name_field.getText();
                String address = address_field.getText();
                int aadhaar = Integer.parseInt(aadhaar_field.getText());
                int mobile = Integer.parseInt(mobile_field.getText());
                String username = username_field.getText();
                String password = password_field.getText();
                //staffCheck(id, name, address, aadhaar, mobile, username, password);
                createuser(id, name, address, aadhaar, mobile, username, password);
                backtohome();
            }
        });
    }

    //public void staffCheck(int id, String name, String address, int aadhaar, int mobile, String username, String password){
      //  DBhandler = new DB_Handler();
        //try {
          //  connection = DBhandler.getDbConnection();
        //} catch (ClassNotFoundException e1) {
          //  e1.printStackTrace();
        //} catch (SQLException e1) {
          //  e1.printStackTrace();
        //}
        //String query = "SELECT * FROM gym_staff WHERE ID = ?";
        //try {
          //  PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
            //st.setInt(1,id);
            //ResultSet rs = st.executeQuery();
            //if(rs.next())
           // {
             //   signup_btn.getScene().getWindow().hide();
               // FXMLLoader loader = new FXMLLoader();
               // loader.setLocation(getClass().getResource("/sample/view/staff_check.fxml"));
               // try {
                 //   loader.load();
               // } catch (IOException e) {
                 //   e.printStackTrace();
                //}
                //Parent root_1 = loader.getRoot();
                //Stage stage_1 = new Stage();
                //stage_1.setScene(new Scene(root_1));
                //stage_1.showAndWait();
            //}
            //else{
              //  createuser(id, name, address, aadhaar, mobile, username, password);
            //}
        //} catch (SQLException e2) {
          //  e2.printStackTrace();
        //}
    //}

    public void createuser(int id, String name, String address, int aadhaar, int mobile, String username, String password){
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String insert  = "INSERT INTO gym_staff(ID,Name,Address,Aadhaar,Phone_Number,User_name,Password)"+ "VALUES(?,?,?,?,?,?,?)";
        try {
            preparedstatement = (PreparedStatement) connection.prepareStatement(insert);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            preparedstatement.setInt(1,id);
            preparedstatement.setString(2,name);
            preparedstatement.setString(3,address);
            preparedstatement.setInt(4,aadhaar);
            preparedstatement.setInt(5,mobile);
            preparedstatement.setString(6,username);
            preparedstatement.setString(7,password);
            preparedstatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void backtohome(){
        signup_btn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/Screen_3.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
