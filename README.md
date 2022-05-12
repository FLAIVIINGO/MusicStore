---
# MusicStore
---
This project displays a working database in MySQL 5.7 that is being controlled by JAVAFX in IntelliJ in Windows.
---
## Requirements
JavaFX Download
https://openjfx.io/

MySQL Download
https://dev.mysql.com/downloads/mysql/5.7.html?tpl=version&amp;os=src

IntelliJ IDEA
https://www.jetbrains.com/idea/download/?fromIDE=#section=windows

## Quickstart For Windows Only
### After creating you SQL connection:
+ Open MySQL 5.7 Command Line
+ `CREATE DATABASE data_base_name;` Replace data_base_name with your own naming convention
+ `CREATE TABLE table_name (` Replace table_name with your own naming convention
+ `user_id INT PRIMARY KEY`
+ `user_name VARCHAR(45)`
+ `password VARCHAR(45)`
+ `email VARCHAR(45)`
+ `start_date VARCHAR(45));`
+ Create an admin user to control the admin page
+ `INSERT INTO table_name VALUES (1, 'admin', 'password', 'email_name', 'start_date');`
+ Now we can connect the database into the JAVAFX program with just a couple of steps
+ In the Utilities class on line 20 - 22 and in AdminPageController class on line 78 - 80
+ `private String jdbcURL = "hdbc:mysql://localhost:3306/data_base_name"` Replace data_base_name with your own naming convention
+ `private String sqlConnectionName = "connection_name"` Replace connection_name with your own naming convention
+ `private String sqlPassword = "pasword"` Replace password with your own naming convention
### JavaFX Setup
+ In IntelliJ, go to File > Project Structure
+ Select Libraries on the left
+ Select the plus sign and browse your system to locate the installed JavaFX folder where you can find the lib folder
+ Hit Apply and OK

![Alt text](/src/images/tutorial_img1.PNG)

+ Select the Run tab and go down to Edit Configurations
+ Click Modify options
+ Click Add VM options
+ next add the following command `--module-path "%path to JAVAFX lib%" --add-modules javafx.controls,javafx.fxml`
+ Click Apply and OK

![Alt text](/src/images/tutorial_img2.PNG)
![Alt text](/src/images/tutorial_img3.PNG)

### MySQL JavaFX Setup
+ Select File > Project Structure
+ Click Modules and Click on the plus sign
+ Locate the jar file location on your computer
+ Click Apply and OK

![Alt text](/src/images/tutorial_img4.PNG)
