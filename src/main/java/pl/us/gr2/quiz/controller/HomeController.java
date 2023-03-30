package pl.us.gr2.quiz.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import pl.us.gr2.quiz.FriendsRepository;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@RestController
public class HomeController {

    private final FriendsRepository repository;

    public HomeController(FriendsRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(){
        return "Hello from Spring Boot!";
    }

    @GetMapping("/age/{date}")
    public String age(@PathVariable LocalDate date){
        return "Age = " + (LocalDate.now().getYear() - date.getYear());
    }

    @GetMapping("/friends")
    public String friends(@RequestParam String name, HttpServletResponse response){
        var f = repository.friends(name);
        if (f.isEmpty()){
            response.setStatus(404);
            return "Nie znaleziono takiej osoby!";
        } else {
            return name + " friend's: " + f;
        }
    }
    //Zdefiniuj metodę dodającą dwa parametry: a i b pod ścieżką add
    //np. /add?a=5&b=6 to odpowiedź będzie "11", lub "5 + 6 = 11"

    @GetMapping("/add")
        public String add(@RequestParam(defaultValue = "1",required = false, name="factor-a") double a, @RequestParam double b){
            return String.format("%.3f",a + b);
        }
}
