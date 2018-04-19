package main.java.managers;

import java.util.List;
import main.java.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleManager extends CrudRepository<Article, Integer> {

	/*	public List<Article> findTop10ArticlesOrderByPostDate();
	Is this descending or ascending? date doesn't have desc
	 not working
	*/
}