package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animation.Shaker;
import sample.database.DB_Handler;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class SC2_controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField username_txtfield;

    @FXML
    private PasswordField password_txtfield;

    @FXML
    private JFXButton login_btn;

    @FXML
    private Label warning_label;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;

    @FXML
    void initialize() {

        login_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (username_txtfield.getText().equals("") || password_txtfield.getText().equals(""))
                    warning_label.setText("Username,Password cannot be empty");
                else{
                    String username = username_txtfield.getText().trim();
                    String password = password_txtfield.getText().trim();
                    loginuser(username,password);
                }
            }
        });

    }

    private void loginuser(String username, String password) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String query = "SELECT * FROM gym_staff WHERE User_name =? AND Password =?";
        try {
            PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
            st.setString(1,username);
            st.setString(2,password);
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                    login_btn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/Screen_3.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root_1 = loader.getRoot();
                    Stage stage_1 = new Stage();
                    stage_1.setScene(new Scene(root_1));

                    Sc3_controller sc_3 = loader.getController();
                    sc_3.setName(username_txtfield.getText());
                    stage_1.showAndWait();
            }
            else{
                warning_label.setText("Wrong Username or Password");
                Shaker shaker = new Shaker(username_txtfield);
                Shaker shaker1 = new Shaker(password_txtfield);
                shaker.shake();
                shaker1.shake();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}

