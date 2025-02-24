package antidimon.web.tasktrackerstatistics.services;

import org.junit.jupiter.api.Test;

public class safTest {



    @Test
    public void test(){
        Integer a = 127;
        Integer b = 127;
        Integer c = 128;
        Integer d = 128;

        System.out.println(a == b);
        System.out.println(c == d);
    }
}
