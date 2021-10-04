package sample.controller;

import com.jfoenix.controls.JFXButton;
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

public class Newcustomer_controller {

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
    private TextField phone_field;

    @FXML
    private JFXButton add_button;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;


    @FXML
    void initialize() {
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = Integer.parseInt(id_field.getText());
                String name = name_field.getText();
                String address = address_field.getText();
                int phone = Integer.parseInt(phone_field.getText());
                customerCheck(id, name, address, phone);
                //addnewcustomer(id,name,address,phone);
                //backtohome();
            }
        });
    }

    public void customerCheck(int id, String name, String address, int phone) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String query = "SELECT * FROM gym_customer WHERE Cust_ID = ?";
        try {
            PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                add_button.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/customer_check.fxml"));
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
            else{
                //String name = name_field.getText();
                //String address = address_field.getText();
                //int phone = Integer.parseInt(phone_field.getText());
                addnewcustomer(id, name, address, phone);
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public void addnewcustomer(int id, String name, String address, int phone){
        String insert  = "INSERT INTO gym_customer(Cust_ID,Cust_Name,Cust_Address,Cust_Phone)"+ "VALUES(?,?,?,?)";
        try {
            preparedstatement = (PreparedStatement) connection.prepareStatement(insert);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            preparedstatement.setInt(1,id);
            preparedstatement.setString(2,name);
            preparedstatement.setString(3,address);
            preparedstatement.setInt(4,phone);
            preparedstatement.executeUpdate();
            backtohome();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void backtohome(){
        add_button.getScene().getWindow().hide();
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
