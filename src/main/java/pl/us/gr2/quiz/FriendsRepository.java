package pl.us.gr2.quiz;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FriendsRepository {
    private Map<String, List<String>> friends = new HashMap<>();

    public FriendsRepository() {
        friends.put("Karol", List.of("Ewa", "Robert"));
        friends.put("Ewa", List.of("Karol", "Tomek","Bożena"));
        friends.put("Tomek", List.of("Ewa"));
    }

    public List<String> friends(String person){
        var f = friends.get(person);
        return f == null ? Collections.emptyList() : f;
    }
}
