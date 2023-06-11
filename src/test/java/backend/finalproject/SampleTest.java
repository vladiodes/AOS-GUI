package backend.finalproject;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SampleTest {

    @Test
    void test1(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println(s);
    }
}
