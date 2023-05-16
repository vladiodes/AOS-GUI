# Autonomous-Operating-System
# Installation:

## First time installation:
- Run the following file [Link](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/install_AOS_GUI.bash)
- Make sure you execute the commands prompted at the end of the script execution (to configure java 19 correctly).

## Running the application:
Make sure you have Java 19 and Maven installed 
Run the following:

```code
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" && cd ~/Autonomous-Operating-System/FinalProject && mvn clean javafx:run
```

# Folder structure:
* The program assumes AOS-WebAPI is installed on the local machine.
  * [AOS-WebAPI repo](https://github.com/orhaimwerthaim/AOS-WebAPI)
* The program assumes the following folder structure:
  * Home directory
    * AOS (web API and all its corresponding structure)
    * Autonomous-Operating-System (The folder structure of THIS repo).
* The program saves and loads all of its projects in the following path:
```code
~/Autonomous-Operating-System/FinalProject/Projects
```
