package backend.finalproject;

import org.junit.jupiter.api.Test;
import utils.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AOSFacadeTests {
    private final IAOSFacade facade = AOSFacade.getInstance();
    @Test
    void getAllProjectsTest(){
       Response<List<String>> response = facade.getAllProjects();
       if (response.getValue() != null){
           assertTrue(response.getValue().contains("aosPaper"));
       }
       else {
           fail(response.getMessage());
       }
    }

    @Test
    void setCurrentProjectTestToys(){
        Response<Boolean> response = facade.setCurrentWorkingProject("collectValuableToys");
        if (response.getValue() != null){
            assertTrue(response.getValue());
        }
        else{
            fail(response.getMessage());
        }
    }

    @Test
    void setCurrentProjectTestAosPaper(){
        Response<Boolean> response = facade.setCurrentWorkingProject("aosPaper");
        if (response.getValue() != null){
            assertTrue(response.getValue());
        }
        else{
            fail(response.getMessage());
        }
    }

    @Test
    void setCurrentProjectTestTicTacToe() {
        Response<Boolean> response = facade.setCurrentWorkingProject("panda_tic_tac_toe_AAAI");
        if (response.getValue() != null) {
            assertTrue(response.getValue());
        } else {
            fail(response.getMessage());
        }
    }
}
