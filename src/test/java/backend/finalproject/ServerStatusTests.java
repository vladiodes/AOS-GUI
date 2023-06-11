package backend.finalproject;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import utils.Response;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerStatusTests {

    private final IAOSFacade facade = AOSFacade.getInstance();

    @Order(3)
    @Test
    void serverOnReturnsTrue() throws InterruptedException {
        Response<Boolean> response = facade.activateAOServer();
        // adding some overhead time so the process is created
        Thread.sleep(1000);
        Response<Boolean> response2 = facade.showAOServerStatus();
        // removing the created process from the p-table
        facade.deactivateAOServer();
        assertTrue(response2.getValue());
    }
    @Order(2)
    @Test
    void serverOffReturnsFalse(){
        Response<Boolean> response2 = facade.showAOServerStatus();
        assertFalse(response2.getValue());
    }

    @Order(1)
    @Test
    void serverOnThenOffReturnsFalse() throws InterruptedException {
        Response<Boolean> response1 = facade.activateAOServer();
        // adding some overhead time so the process is created
        Thread.sleep(1000);
        Response<Boolean> response2 = facade.showAOServerStatus();
        assertTrue(response2.getValue());
        Response<Boolean> response3 = facade.deactivateAOServer();
        // adding some overhead time so the process is destroyed
        Thread.sleep(1000);
        Response<Boolean> response4 = facade.showAOServerStatus();
        assertFalse(response4.getValue());
    }
}
