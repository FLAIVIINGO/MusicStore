package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.collections.ObservableArray;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class Utilities {

    // Change Scene method
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if(username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlFile));
                root = loader.load();
                if(fxmlFile.equals("adminPage.fxml")) {
                    AdminPageController adminPageController = loader.getController();
                }
                else if(fxmlFile.equals("userPage.fxml")) {
                    UserPageController userPageController = loader.getController();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                root = FXMLLoader.load(Utilities.class.getResource(fxmlFile));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        if(fxmlFile.equals("adminPage.fxml")) {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 700, 550));
            stage.show();
        }
        else if(fxmlFile.equals("userPage.fxml")) {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 610, 500));
            stage.show();
        }

    }

    public static ObservableList<Teachers> getTeacherInformation() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Teachers> list = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
            preparedStatement = connection.prepareStatement("SELECT * FROM employees");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(new Teachers(resultSet.getInt("user_id"), resultSet.getString("user_name"),
                        resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("start_date")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null) {
                try{
                    preparedStatement.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void loginAction (ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String retrievedPassword = null;

        if(username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        }
        else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teacher_manager", "root", "sailtheoceanblue");
                preparedStatement = connection.prepareStatement("SELECT password FROM employees WHERE user_name = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Username and or password is incorrect");
                    alert.show();
                }

                else {
                    while (resultSet.next()) {
                        retrievedPassword = resultSet.getString("password");
                        if (retrievedPassword.equals(password) && username.equals("admin")) {
                            System.out.println("admin");
                            changeScene(event, "adminPage.fxml", "User Page", username);
                        } else if (retrievedPassword.equals(password)) {
                            System.out.println("user");
                            changeScene(event, "userPage.fxml", "User Page", username);
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Username and or password is incorrect");
                            alert.show();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
