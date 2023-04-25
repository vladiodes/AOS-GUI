# Autonomous-Operating-System
# Installation:

## First time installation:
- Install maven:
```code
sudo apt install maven
```
- Install JDK 19, using the following commands from your terminal:

```code
wget https://download.oracle.com/java/19/latest/jdk-19_linux-x64_bin.deb](https://download.oracle.com/java/19/archive/jdk-19.0.2_linux-x64_bin.deb
sudo apt-get -qqy install ./jdk-19.0.2_linux-x64_bin.deb
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-19/bin/java 1
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-19/bin/javac 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-19/bin/jar 1
```

Configure your java version by selecting java 19 in all the following commands:

```code
sudo update-alternatives --config java
sudo update-alternatives --config javac
sudo update-alternatives --config jar
```

At last, run the following command:
```code
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED"
```

## Running the application:
Make sure you have Java 19 and Maven installed.
Run the following:

```code
cd FinalProject
mvn clean javafx:run
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
    * Autonomous-Operating-System (The folder structure of THIS repo).
