# LeafyLodge

After downloading the code, you will face an error because the libraries are missing, there's two ways to fix this problem:                                                                     
Way 1:                                                                                                                                                                                                     
  check the .vscode/settings.json and move the missing files to the destined locations:                                                                                  
  Download JavaFX from the Internet then go to "c:/ProgramFiles/Java/"
  put it there, after that you need to download the mysql connector from mysql, after downloading it do the same thing
  you did to the JavaFX file, go to "c:/ProgramFiles/Java/"
  create a new file called mysql-connector(version), and you're good to go.

  just extract both files after downloading them in the "c:/ProgramFiles/Java/".

Another way:                                                                                                                                                  
  add JavaFX lib file and mysql-connecotr.jar to the code, and you're good to go!

Links:                                                                                                                                                                     
  mysql-connector: https://dev.mysql.com/downloads/connector/j/                                                                                                         
  JavaFX: https://gluonhq.com/products/javafx/                                                                                                  
  
if you run into this Error: JavaFX runtime components are missing, and are required to run this application
you might want to comment vmArgs in ".vscode/launch.json"
