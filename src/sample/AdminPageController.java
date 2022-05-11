package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Teachers, String> emailCol;

    @FXML
    private TableColumn<Teachers, String> passwordCol;

    @FXML
    private TableColumn<Teachers, String> startDateCol;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Teachers, Integer> userIDCol;

    @FXML
    private TableColumn<Teachers, String> usernameCol;

    @FXML
    private TableView<Teachers> userTable;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_start_date;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_id;

    private ObservableList<Teachers> list;

    private int index = -1;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void addUsers() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
            preparedStatement = connection.prepareStatement("INSERT INTO employees VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, txt_id.getText());
            preparedStatement.setString(2, txt_username.getText());
            preparedStatement.setString(3, txt_password.getText());
            preparedStatement.setString(4, txt_email.getText());
            preparedStatement.setString(5, txt_start_date.getText());
            preparedStatement.execute();
            //JOptionPane.showMessageDialog(null, "Users added");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectUser(MouseEvent event) {
        this.index = userTable.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
            return;
        }
        txt_id.setText(userIDCol.getCellData(index).toString());
        txt_username.setText(usernameCol.getCellData(index));
        txt_password.setText(passwordCol.getCellData(index));
        txt_email.setText(emailCol.getCellData(index));
        txt_start_date.setText(startDateCol.getCellData(index));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIDCol.setCellValueFactory(new PropertyValueFactory<Teachers,Integer>("user_id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("user_name"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("email"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("start_date"));

        list = Utilities.getTeacherInformation();
        userTable.setItems(list);
    }
}
