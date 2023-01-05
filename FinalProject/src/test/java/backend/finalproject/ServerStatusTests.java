package backend.finalproject;

import org.junit.jupiter.api.Test;
import utils.Response;

import static org.junit.jupiter.api.Assertions.*;

public class ServerStatusTests {

    private final IAOSFacade facade = AOSFacade.getInstance();

    @Test
    void serverOnReturnsTrue(){
        Response<Boolean> response = facade.activateAOServer();
        Response<Boolean> response2 = facade.showAOServerStatus();
        assertTrue(response2.getValue());
    }

    @Test
    void serverOffReturnsFalse(){
        Response<Boolean> response = facade.deactivateAOServer();
        Response<Boolean> response2 = facade.showAOServerStatus();
        assertFalse(response2.getValue());
    }

    @Test
    void serverOnThenOffReturnsFalse(){
        Response<Boolean> response1 = facade.activateAOServer();
        Response<Boolean> response2 = facade.showAOServerStatus();
        assertTrue(response2.getValue());
        Response<Boolean> response3 = facade.deactivateAOServer();
        Response<Boolean> response4 = facade.showAOServerStatus();
        assertFalse(response4.getValue());
    }
}
