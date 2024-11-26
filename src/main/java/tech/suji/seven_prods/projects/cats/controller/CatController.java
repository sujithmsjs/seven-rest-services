package tech.suji.seven_prods.projects.cats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.suji.seven_prods.projects.cats.entity.Cat;
import tech.suji.seven_prods.projects.cats.repository.CatRepository;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatController {

	private CatRepository catRepository;

	public CatController(CatRepository catRepository) {
		this.catRepository = catRepository;
	}

	@PostMapping
	public ResponseEntity<String> createCat(Cat cat) {
		catRepository.save(cat);
		return ResponseEntity.ok("SUCCESS");
	}

}