package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DB_Handler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Attendance_controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton check_btn;

    @FXML
    private JFXButton back_btn;

    @FXML
    private TextField name_txtField;

    @FXML
    private JFXButton search_button;

    @FXML
    private DatePicker date_picker;

    @FXML
    private JFXButton absent_btn;

    @FXML
    private JFXButton present_btn;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;

    @FXML
    void initialize() {
        search_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = name_txtField.getText();
                search_name(name);
            }
        });

        present_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = name_txtField.getText();
                String date = date_picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                present(name,date);
            }
        });

        absent_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = name_txtField.getText();
                String date = date_picker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                absent(name,date);
            }
        });

        check_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                check_btn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/Attendance1.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.show();
                stage.setScene(new Scene(root));
            }
        });

        back_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backtohome();
            }
        });
    }

    public void search_name(String name) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String query = "SELECT * FROM gym_customer WHERE Cust_Name = ?";
        try {
            PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
            st.setString(1,name);
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                date_picker.setVisible(true);
                absent_btn.setVisible(true);
                present_btn.setVisible(true);
            }
        } catch (SQLException e2) {

            e2.printStackTrace();
        }
    }

    public void present(String Name, String date) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String insert  = "INSERT INTO gym_attendance(Name,Date,Attendance)"+ "VALUES(?,?,?)";
        try {
            preparedstatement = (PreparedStatement) connection.prepareStatement(insert);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            preparedstatement.setString(1,Name);
            preparedstatement.setString(2,date);
            preparedstatement.setInt(3,1);
            preparedstatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void absent(String Name, String date) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String insert  = "INSERT INTO gym_attendance(Name,Date,Attendance)"+ "VALUES(?,?,?)";
        try {
            preparedstatement = (PreparedStatement) connection.prepareStatement(insert);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            preparedstatement.setString(1,Name);
            preparedstatement.setString(2,date);
            preparedstatement.setInt(3,0);
            preparedstatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void backtohome(){
        back_btn.getScene().getWindow().hide();
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
