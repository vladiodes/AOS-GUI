package backend.finalproject.LogFetcherTests;

import backend.finalproject.AOSFacade;
import frontend.finalproject.Utils.LogsNotificationsFetcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import utils.Response;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LogFetcherTests {
    public static final String LOGS_RESPONSE = """
            [
                {
                    "id": "6479ef3fba7d29af040ea738",
                    "logLevel": 4,
                    "logLevelDesc": "Info",
                    "event": "Solver Selected Action: ID:2,draw_in_cellAction,oCellP:2",
                    "advanced": "",
                    "component": "",
                    "time": "06/02/2023 16:31"
                },
                {
                    "id": "6479ef3fc7abe08aa6000711",
                    "logLevel": 4,
                    "logLevelDesc": "Info",
                    "event": "Solver Selected Action: ID:2,draw_in_cellAction,oCellP:2",
                    "advanced": "",
                    "component": "",
                    "time": "06/02/2023 16:31"
                }
            ]
            """;
    private LogsNotificationsFetcher fetcher;
    private Thread logsThread;
    private AtomicBoolean hasReached;
    private AtomicInteger counter;
    @Mock private AOSFacade mockFacade;

    @BeforeEach
    void setUp(){
        counter = new AtomicInteger(0);
        mockFacade = mock(AOSFacade.class);
        when(mockFacade.sendRequest(any())).thenAnswer(invocationOnMock -> {
            counter.incrementAndGet();
            return Response.OK(LOGS_RESPONSE);
        });
        fetcher = new LogsNotificationsFetcher(mockFacade);
        hasReached = new AtomicBoolean(false);
        logsThread = new Thread(() -> {
            fetcher.run();
            hasReached.set(true);
        });
    }

    /**
     * Pausing the fetcher, checking that it was indeed paused
     * Afterward resuming the fetcher, checking that it was indeed resumed
     */
    @Test
    void testFetcherIsCorrectlyPaused() throws InterruptedException {
        // pausing fetcher
        fetcher.pauseFetcher();
        logsThread.start();
        sleep(LogsNotificationsFetcher.INTERVAL_TIME * 2);
        // verifying that no request was sent and that the thread hasn't terminated
        verify(mockFacade, never()).sendRequest(any());
        Assertions.assertFalse(hasReached.get());

        // resuming fetcher
        fetcher.resumeFetcher();
        sleep(LogsNotificationsFetcher.INTERVAL_TIME * 2);
        // verifying that a request was sent
        verify(mockFacade, atLeastOnce()).sendRequest(any());
    }

    /**
     * Testing that the fetcher is terminated while paused
     * @throws InterruptedException
     */
    @Test
    void testFetcherIsTerminatedWhilePaused() throws InterruptedException {
        fetcher.pauseFetcher();
        logsThread.start();
        fetcher.terminate();
        logsThread.join(LogsNotificationsFetcher.INTERVAL_TIME * 2);
        Assertions.assertTrue(hasReached.get());
    }

    @Test
    void fetcherIsPausedAndThenResumed(){
        logsThread.start();
        sleep(LogsNotificationsFetcher.INTERVAL_TIME * 2);
        fetcher.pauseFetcher();
        int prevCount = counter.get();
        fetcher.resumeFetcher();
        sleep(LogsNotificationsFetcher.INTERVAL_TIME * 2);
        int newCount = counter.get();

        Assertions.assertTrue(newCount > prevCount);
    }


    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        fetcher.terminate();
        logsThread.join(LogsNotificationsFetcher.INTERVAL_TIME * 2);
    }
}
