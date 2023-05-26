package frontend.finalproject.Utils;

import DTO.HttpRequests.GetLogsRequestDTO;
import backend.finalproject.AOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javafx.application.Platform;
import utils.Response;

import java.util.concurrent.Semaphore;

public class LogsNotificationsFetcher {
    public static final String ID_JSON_KEY = "id";
    public static final String EVENT_JSON_KEY = "event";
    private static LogsNotificationsFetcher instance = null;
    public static LogsNotificationsFetcher getInstance(){
        if(instance == null)
            instance = new LogsNotificationsFetcher();
        return instance;
    }

    private boolean shouldTerminate = false;
    private String recentLogId = null;
    private Semaphore semaphore;
    private boolean isPaused = false;

    private LogsNotificationsFetcher(){
        semaphore = new Semaphore(1);

    }

    public void run() {
        while (!shouldTerminate) {
            try {
                Thread.sleep(1000);
                semaphore.acquire();
            } catch (InterruptedException ignored) {
            }

            Response<String> response = AOSFacade.getInstance().sendRequest(new GetLogsRequestDTO());
            if (response.hasErrorOccurred()) {
                semaphore.release();
                continue;
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = gson.fromJson(response.getValue(), JsonElement.class);
            JsonArray logs = jsonElement.getAsJsonArray();

            if (logs.size() == 0 || logs.get(logs.size() - 1).getAsJsonObject().get(ID_JSON_KEY).getAsString().equals(recentLogId)) {
                semaphore.release();
                continue; // No new logs
            }

            if(recentLogId == null){ // first run of the thread. set recentLogId to the last log id
                recentLogId = logs.get(logs.size() - 1).getAsJsonObject().get(ID_JSON_KEY).getAsString();
                semaphore.release();
                continue;
            }

            for(int i=logs.size() - 1; i>=0; i--){
                JsonElement log = logs.get(i);
                String id = log.getAsJsonObject().get(ID_JSON_KEY).getAsString();
                if(id.equals(recentLogId)){
                    break;
                }
                String logNotifContent = String.format("A new log has arrived:\n Event: %s\n", log.getAsJsonObject().get(EVENT_JSON_KEY).getAsString());
                Platform.runLater(() -> {
                    UtilsFXML.showNotification(NotificationUtils.NEW_LOG_TITLE,logNotifContent,null);
                });
            }
            recentLogId = logs.get(logs.size() - 1).getAsJsonObject().get(ID_JSON_KEY).getAsString();
            semaphore.release();
        }
    }

    public void pauseFetcher() {
        try {
            semaphore.acquire();
            isPaused = true;
        } catch (InterruptedException ignored) {
        }
    }

    public void resumeFetcher(){
        semaphore.release();
        isPaused = false;
    }


    public void terminate(){
        semaphore.release();
        shouldTerminate = true;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
