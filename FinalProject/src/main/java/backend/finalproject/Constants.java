package backend.finalproject;

import java.io.File;

public class Constants {
    public static String AOS_SERVER_HOST = "localhost";
    public static final int AOS_SERVER_PORT = 5000;
    public static final int DEFAULT_TIMEOUT = 10000;

    public static final String AOS_API_ACTIVATION_DIR = "/AOS/AOS-WebAPI/bin/Debug/net6.0";
    public static final String AOS_API_ACTIVATION_COMMAND = "./WebApiCSharp";
    public static final String PROJECTS_FOLDER_PATH = "." + File.separator + "Projects";
    public static final String ENVIRONMENT_FILE_SUFFIX = ".environment.json";

}
