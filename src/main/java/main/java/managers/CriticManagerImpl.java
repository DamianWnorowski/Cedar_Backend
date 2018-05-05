package main.java.managers;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CriticManagerImpl implements CustomCriticManager {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Integer> getTopCriticIds() {
		Query idsQuery = entityManager.createNativeQuery("SELECT id, count(id) as num_reviews FROM users, user_roles, critic_review WHERE users.id = user_roles.user_id AND user_roles.roles = \"ROLE_CRITIC\" AND critic_review.author_id = users.id GROUP BY users.id ORDER BY num_reviews DESC LIMIT 10;");
		List<Integer[]> idsAndCounts = idsQuery.getResultList();
		
		List<Integer> ids = new ArrayList<>();
		for (Object[] row : idsAndCounts) {
			ids.add((Integer)row[0]);
		}
		return ids;
	}
	
}
