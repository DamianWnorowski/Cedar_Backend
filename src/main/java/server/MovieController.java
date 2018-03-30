package main.java.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @RequestMapping("/movie")
    public Movies movies(@RequestParam(value="id") int id) {
        //search db for movie id and return the movie details
        return new Movies(id, "test1", "test2", "test3", "test4");
    }
}