package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.database.DB_Handler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Attendance1_controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField search_textField;

    @FXML
    private JFXButton search_btn;

    @FXML
    private JFXButton back_btn;

    @FXML
    private TableView<ObservableList> attendance_table;

    private ObservableList<ObservableList> data;

    static private DB_Handler DBhandler;
    static private Connection connection;
    static private PreparedStatement preparedstatement;

    @FXML
    void initialize() {
        search_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = Integer.parseInt(search_textField.getText());
                search(id);
            }
        });

        back_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backtohome();
            }
        });
    }

    public void search(int id) {
        DBhandler = new DB_Handler();
        try {
            connection = DBhandler.getDbConnection();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        data = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM gym_attendance WHERE ID = ?";
        try{
            PreparedStatement st= (PreparedStatement) connection.prepareStatement(SQL);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                attendance_table.getColumns().addAll(col);
            }
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            attendance_table.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
