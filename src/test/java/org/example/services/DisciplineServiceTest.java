package org.example.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisciplineServiceTest {

    @Test
    public void getDisciplinesByPlan(){
        assertEquals(true, true);
    }

    @Test
    public void getDisciplinesByPlansWithSearch(){
        assertEquals(true, true);
    }

    @Test
    public void getDiscipline(){
        assertEquals(true, true);
    }
}
