package main.java.managers;

import main.java.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserManager extends CrudRepository<User, Integer> {

    public User findByEmail(String email);
}
