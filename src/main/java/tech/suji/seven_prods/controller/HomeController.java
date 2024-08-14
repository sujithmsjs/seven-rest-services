package tech.suji.seven_prods.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;



@RestController
@RequestMapping(value = "/api/query", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

	@PersistenceContext
	private EntityManager entityManager;
	
	
    @PostMapping("/runQuery")
	@Transactional
	public Map runQuery(@RequestBody String query) {

		Map m = new HashMap<>();

		try {
			 Query createQuery = entityManager.createQuery(query);
			
			int index =  query.trim().indexOf(" ");
			String command = query.trim().substring(0, index);
			
			switch(command) {
			
			case "SELECT" :
				List resultList = createQuery.getResultList();
				m.put("data", resultList);
				break;
			case "UPDATE" :
			case "DELETE":
				int executeUpdate = createQuery.executeUpdate();
				m.put("executeUpdate", executeUpdate);
				break;
			default:
				throw new RuntimeException("Query should start withs SELECT/UPDATE/DELETE(Must be in Uppercase)");
			}
		} catch (Exception e) {
			m.put("errMsg", e.getMessage());
		}
		return m;
	}

}
