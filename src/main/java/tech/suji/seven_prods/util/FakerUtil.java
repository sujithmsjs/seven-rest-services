package tech.suji.seven_prods.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import tech.suji.seven_prods.model.Gender;
import tech.suji.seven_prods.projects.empmgnt.domain.Employee;
import tech.suji.seven_prods.projects.empmgnt.model.Branch;
import tech.suji.seven_prods.projects.mycart.entity.Product;

public class FakerUtil {

	public static final Faker faker = new Faker();

	private static int num = 2000;
	
	public static Employee getFakeEmployee() {

		Integer id = 100 ;
		num++;
		Name fakeName = faker.name();
		String email = fakeName.username() + "@"
				+ RandomUtil.options("gmail.com", "yahoo.com", "outlook.com", "proton.com", "hotmail.com");
		String password = "password";
		String firstName = fakeName.firstName();
		String lastName = fakeName.lastName();

		Gender gender = StringUtils.endsWithAny(firstName, "e", "a", "i") ? Gender.FEMALE : Gender.MALE;

		String job = RandomUtil.options( "Architect", "JavaSE", "DBA", "FrontEnd", "Intern", "HR");
	//	Integer manager = faker.random().nextInt(100, 110);
		LocalDate hiredate = RandomUtil.getBirthDayByAge(1, 10);
		double salary = RandomUtil.getNumberBetween(1, 100) * 1000;
		int commission = RandomUtil.getNumberBetween(10, 50) * 10;
		Branch branch =  getFakeBranch();

		Set<String> languages = RandomUtil.getRandomSubListOf("Java", "C", "Python", "Ruby", "Kotlin", "JavaScript",
				"Swift", "PHP");

		//Pet pet = new Pet(faker.animal().name(),RandomUtil.options(faker.cat().name(), faker.dog().name()) , faker.random().nextInt(1, 5));
		
		//Employee e = new Employee(id, firstName, lastName, email, password, job, pet, gender, null, hiredate, salary, commission, languages, branch, faker.random().nextBoolean());
		return null;
	}
	
	public static Product getMockProduct() {
		Product p = new Product();
		Commerce commerce = faker.commerce();
		p.setName(commerce.productName());
		p.setPrice(RandomUtil.getCost());
		String cat = RandomUtil.options( "Electronics", "Clothing", "Home and Kitchen", "Toys", "Beauty");
		p.setCategory(cat);
		LocalDate hiredate = RandomUtil.getBirthDayByAge(1, 2);
		p.setReleaseDate(hiredate);
		return p;
	}

	public static Branch getFakeBranch() {

//		Branch b1 = new Branch(100, 1234, "Hyderabad");
//		Branch b2 = new Branch(200, 1234, "Bangalore");
//		Branch b3 = new Branch(300, 1234, "Delhi");
//		Branch b4 = new Branch(400, 1234, "Mumbai");
//		Branch b5 = new Branch(500, 1234, "Chennai");
//
//		return RandomUtil.options(b1, b2, b3, b4, b5);
		return null;
	}

	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
