package main.java.managers;


import java.time.LocalDate;
import java.util.List;
import main.java.models.TVShow;
import org.springframework.data.repository.CrudRepository;

public interface TVManager extends CrudRepository<TVShow, Integer> {


	public List<TVShow> findByTitleContainingIgnoreCase(String token);

	public List<TVShow> findTop10ByNextAirDate(LocalDate date);

}