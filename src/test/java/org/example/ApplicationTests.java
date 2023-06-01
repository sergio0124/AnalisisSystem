package org.example;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationTests {

    @Test
    public void applicationLoad(){
        assertEquals(true, true);
    }
}
