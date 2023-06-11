# AOS-GUI
# Installation:

## First time installation:
- Run the following script: [install_AOS_GUI.bash](https://github.com/vladiodes/AOS-GUI/blob/main/install_AOS_GUI.bash)
- Please make sure you follow the last instructions (configuring JDK 19 as your default JDK).
- To run the app, run the following command:
```code
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" && cd ~/AOS/AOS-GUI && mvn clean javafx:run
```

# In order to run project's tests:
```code
mvn test
```

# Folder structure:
* The program assumes AOS-WebAPI is installed on the local machine.
  * [AOS-WebAPI repo](https://github.com/orhaimwerthaim/AOS-WebAPI)
* The program assumes the following folder structure:
  * Home directory
    * AOS (web API and all its corresponding structure)
    * AOS-GUI (The folder structure of THIS repo).
