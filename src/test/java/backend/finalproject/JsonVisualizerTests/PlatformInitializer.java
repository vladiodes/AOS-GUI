package backend.finalproject.JsonVisualizerTests;

public class PlatformInitializer {
    private static boolean wasInitialized = false;
    public static void initPlatform() {
        if (wasInitialized) {
            return;
        }

        javafx.application.Platform.startup(() -> {
        });
        wasInitialized = true;
    }
}
