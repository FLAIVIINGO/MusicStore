package sample;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
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

    @FXML
    private TextField searchField;

    private ObservableList<Teachers> list;
    private ObservableList<Teachers> dataList;

    private int index = -1;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void addUsers() {
        if(txt_id.getText().isBlank() || txt_username.getText().isBlank() || txt_password.getText().isBlank()
        || txt_email.getText().isBlank() || txt_start_date.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all text fields");
        }
        else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
                preparedStatement = connection.prepareStatement("INSERT INTO employees VALUES(?,?,?,?,?)");
                preparedStatement.setString(1, txt_id.getText());
                preparedStatement.setString(2, txt_username.getText());
                preparedStatement.setString(3, txt_password.getText());
                preparedStatement.setString(4, txt_email.getText());
                preparedStatement.setString(5, txt_start_date.getText());
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "Users added");
                updateTable();
            } catch (SQLException e) {
                //e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);
            }
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

    public void edit() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
            String value1 = txt_id.getText();
            String value2 = txt_username.getText();
            String value3 = txt_password.getText();
            String value4 = txt_email.getText();
            String value5 = txt_start_date.getText();

            if(value1.isBlank() || value2.isBlank() || value3.isBlank() || value4.isBlank() || value5.isBlank()) {
                JOptionPane.showMessageDialog(null, "Please fill in all text fields");
            }

            else {
                String sql = "UPDATE employees SET user_id = '" + value1 + "', user_name = '" + value2 + "', password = '" + value3 + "'," +
                        "email = '" + value4 + "', start_date = '" + value5 + "' WHERE user_id = '" + value1 + "'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "Update successful");
                updateTable();
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void delete() {
        if(txt_id.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Please enter a user ID to delete an employee");
        }
        else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
                String value1 = txt_id.getText();
                String sql = "DELETE FROM employees WHERE user_id = '" + value1 + "'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "Delete successful");
                updateTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTable() {
        userIDCol.setCellValueFactory(new PropertyValueFactory<Teachers,Integer>("user_id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("user_name"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("email"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("start_date"));

        list = Utilities.getTeacherInformation();
        userTable.setItems(list);
    }

    public void searchUser() {
        userIDCol.setCellValueFactory(new PropertyValueFactory<Teachers, Integer>("user_id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("user_name"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("email"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<Teachers,String>("start_date"));

        dataList = Utilities.getTeacherInformation();
        userTable.setItems(dataList);
        FilteredList<Teachers> filteredList = new FilteredList<>(dataList, b -> true);
        searchField.textProperty().addListener((obeservable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(person.getUser_name().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if(person.getPassword().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if(person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if(person.getStart_date().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<Teachers> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedList);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        searchUser();
    }
}
