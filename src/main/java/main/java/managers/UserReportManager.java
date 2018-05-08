package main.java.managers;

import main.java.models.UserReport;
import org.springframework.data.repository.CrudRepository;

public interface UserReportManager extends CrudRepository<UserReport, Integer> {

}