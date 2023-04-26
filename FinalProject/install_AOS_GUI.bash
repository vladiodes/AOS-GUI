#!/bin/bash

echo 'Install maven'
sudo apt install maven

cd ~
echo 'Install JDK 19'
wget https://download.oracle.com/java/19/archive/jdk-19.0.2_linux-x64_bin.deb
sudo apt-get -qqy install ./jdk-19.0.2_linux-x64_bin.deb
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-19/bin/java 1
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-19/bin/javac 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-19/bin/jar 1

echo 'AOS GUI: Downloading code'
cd ~
git clone https://github.com/vladiodes/Autonomous-Operating-System.git

echo 'Done!'
echo '============= Running Instructions ============='
echo 'Please make sure that JDK 19 is set as your default JDK by using the following commands:'
echo 'sudo update-alternatives --config java'
echo 'sudo update-alternatives --config javac'
echo 'sudo update-alternatives --config jar'
echo 'In order to run the GUI, run the following command: "export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" && cd ~/Autonomous-Operating-System/FinalProject && mvn clean javafx:run"'

