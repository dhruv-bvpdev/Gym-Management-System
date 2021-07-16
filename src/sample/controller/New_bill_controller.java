package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DB_Handler;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class New_bill_controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name_field;

    @FXML
    private JFXButton search_btn;

    @FXML
    private Label baseamt_label;

    @FXML
    private JFXCheckBox yes_box;

    @FXML
    private JFXCheckBox no_box;

    @FXML
    private JFXCheckBox locker_box;

    @FXML
    private JFXCheckBox shower_box;

    @FXML
    private JFXCheckBox shake_box;

    @FXML
    private JFXCheckBox food_box;

    @FXML
    private Label total_amt_label;

    @FXML
    private JFXButton finalize_btn;

    @FXML
    private JFXButton bill_c_btn;

    @FXML
    private JFXButton back_btn;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;

    long sum = 0;

    @FXML
    void initialize() {

        search_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = name_field.getText();
                searchuser(name);
            }
        });

        services();

        finalize_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                total();
            }
        });

        bill_c_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String cname = name_field.getText();
                int bill_total = Integer.parseInt(total_amt_label.getText());
                bill(cname,bill_total);
            }
        });

        back_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backtohome();
            }
        });
    }

    public void searchuser(String name){
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String query = "SELECT * FROM gym_customer WHERE Cust_Name =?";
        try {
            PreparedStatement st= (PreparedStatement) connection.prepareStatement(query);
            st.setString(1,name);
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                baseamt_label.setText("1500");
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        String amt = baseamt_label.getText();
        try{
            FileWriter fw = new FileWriter("./src/sample/addition.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(amt + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void services(){

        yes_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (yes_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("6000" + "\n");
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        no_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (no_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("0" + "\n");
                        bw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        locker_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (locker_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("100" + "\n");
                        bw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        shower_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (shower_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("200" + "\n");
                        bw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        shake_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (shake_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("500" + "\n");
                        bw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        food_box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (food_box.isSelected()){
                    try{
                        FileWriter fw = new FileWriter("./src/sample/addition.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("250" + "\n");
                        bw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void total(){
        String fileName = "./src/sample/addition.txt";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName));) {
            long sum = 0;
            while(true) {
                String lineRead = fileReader.readLine();
                if (lineRead == null) {
                    break;
                }
                long num = Integer.parseInt(lineRead);
                sum += num;
            }
            total_amt_label.setText(String.valueOf(sum));
        }
        catch (IOException io) {
            System.err.println("Error");
        }
    }

    public void bill(String cname, int bill_total){
        Date date = new Date();
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String insert  = "INSERT INTO gym_bills(Name,Amount,Pay_date)"+ "VALUES(?,?,?)";
        try {
            preparedstatement = (PreparedStatement) connection.prepareStatement(insert);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {
            preparedstatement.setString(1,cname);
            preparedstatement.setInt(2,bill_total);
            preparedstatement.setDate(3,sqldate);
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
