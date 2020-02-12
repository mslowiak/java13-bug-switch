package com.slowiak.java13bugexample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
public class ExampleController {
    @RequestMapping("/test1")
    public Mono<Integer> example1() {
        return Mono.just(getRandomNumberInRange(1, 3))
                .flatMap(this::handleWithIf);
    }

    @RequestMapping("/test2")
    public Mono<Integer> example2() {
        return Mono.just(getRandomNumberInRange(2, 4))
                .flatMap(this::handleWithSwitch);
    }

    private Mono<Integer> handleWithSwitch(Integer integer) {
        return switch (integer) {
            case 1 -> Mono.error(new Exception("1"));
            case 2 -> Mono.error(new Exception("2"));
            default -> Mono.just(100);
        };
    }

    private Mono<Integer> handleWithIf(Integer number) {
        if (number == 1) {
            return Mono.error(new Exception("1"));
        } else if (number == 2) {
            return Mono.error(new Exception("2"));
        } else {
            return Mono.just(100);
        }
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("MIN > MAX");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }
}
