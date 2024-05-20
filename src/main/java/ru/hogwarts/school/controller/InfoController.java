package ru.hogwarts.school.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("GET")
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String port() {
        return port;
    }
    @GetMapping("/getSum")
    public long getSum() {
        long time = System.currentTimeMillis();

        Integer result = Stream.iterate(1, a -> a +1)
                .parallel()                    // 42-50ms c .reduce(0, (a, b) -> a + b);
                .limit(1_000_000)
               // .reduce(0, (a, b) -> a + b);    35-40ms без .parallel()
               .reduce(0, Integer::sum); //34-38ms без .parallel()

// самая большая производительность при сочетании .parallel() и .reduce(0, Integer::sum) 25-40ms

        time= System.currentTimeMillis()-time;

        return time;
    }
}
