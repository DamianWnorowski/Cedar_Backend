package main.java.managers;

import main.java.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriticManager extends JpaRepository<User, Integer>, CustomCriticManager {
	
}
