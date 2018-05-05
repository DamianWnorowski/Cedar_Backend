package main.java.managers;

import main.java.models.ReviewReport;
import org.springframework.data.repository.CrudRepository;

public interface ReviewReportManager extends CrudRepository<ReviewReport, Integer> {

}