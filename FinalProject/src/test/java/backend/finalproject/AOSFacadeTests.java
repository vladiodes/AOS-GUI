package backend.finalproject;

import org.junit.jupiter.api.Test;
import utils.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AOSFacadeTests {
    private final IAOSFacade facade = AOSFacade.getInstance();
    @Test
    void getAllProjectsTest(){
       Response<List<String>> response = facade.getAllProjects();
       if (response.getValue() != null){
           assertTrue(response.getValue().contains("aosPaper"));
       }
    }

    @Test
    void setCurrentProjectTest(){
        Response<Boolean> response = facade.setCurrentWorkingProject("aosPaper");
        if (response.getValue() != null){
            assertTrue(response.getValue());
        }
    }


}
