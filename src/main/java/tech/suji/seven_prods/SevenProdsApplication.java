package tech.suji.seven_prods;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.suji.seven_prods.projects.movieflex.entity.Movie;
import tech.suji.seven_prods.projects.movieflex.repo.MovieRepository;
import tech.suji.seven_prods.projects.mycart.entity.Product;
import tech.suji.seven_prods.projects.mycart.repo.ProductRepository;
import tech.suji.seven_prods.projects.quotes.domain.Quote;
import tech.suji.seven_prods.projects.quotes.domain.Tag;
import tech.suji.seven_prods.projects.quotes.repos.QuoteRepository;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository;
import tech.suji.seven_prods.projects.usermgnt.entity.User;
import tech.suji.seven_prods.projects.usermgnt.repo.UserRepository;
import tech.suji.seven_prods.util.FakerUtil;
import tech.suji.seven_prods.util.RandomUtil;

@SpringBootApplication
public class SevenProdsApplication implements ApplicationRunner {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	QuoteRepository quoteRepository;
	
	
	
	public static void main(final String[] args) {
		SpringApplication.run(SevenProdsApplication.class, args);
	}

	
	public void run(ApplicationArguments args) throws Exception {
		
		if(true) {
			return;
		}
		for(long i=1;i<=10;i++) {
			Tag t = new Tag(i, "Tag" + i);
			tagRepository.save(t);
		}
		List<Long> availableTags = tagRepository.findAll().stream().map(tag -> tag.getId()).collect(Collectors.toList());
		
		
		
		for(long i=1;i<=20;i++) {
			Quote q = new Quote();
			q.setAuthor("Author "+i);
			q.setQuote("This is quote "+ i);
			Set<Tag> set = RandomUtil.getRandomSubListOf(availableTags).stream().map(t -> new Tag(t)).collect(Collectors.toSet());
			q.setTags(set);
			quoteRepository.save(q);
		}
		
		
		
		
		// Loading User Data from the JSON
		ClassPathResource userResource = new ClassPathResource("data/users.json");
		String userContent = IOUtils.toString(userResource.getInputStream(), StandardCharsets.UTF_8);
		List<User> userList = objectMapper.readValue(userContent, new TypeReference<List<User>>() {
		});
		userList.stream().distinct().forEach(m -> userRepository.save(m));

		// Loading User Data from the JSON
		ClassPathResource movieResource = new ClassPathResource("data/movies.json");
		String movieContent = IOUtils.toString(movieResource.getInputStream(), StandardCharsets.UTF_8);
		List<Movie> movieList = objectMapper.readValue(movieContent, new TypeReference<List<Movie>>() {
		});
		movieList.stream().distinct().forEach(m -> movieRepository.save(m));

		for(int i=0;i<75;i++) {
			Product product = FakerUtil.getMockProduct();
			productRepository.save(product);
		}
		 
		
	}




}
