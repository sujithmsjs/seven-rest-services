package tech.suji.seven_prods.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javafaker.Faker;

public class RandomUtil {

	public static final Faker FAKER = new Faker(new Locale("en-IND"));
	public static final Random RANDOM = new Random();
	
	
	public static final Supplier<String> UPPER = () -> getRandomChar('A', 'Z');
	public static final Supplier<String> LOWER = () -> getRandomChar('a', 'z');
	public static final Supplier<String> NUMBER = () -> getRandomChar('0', '9');

	public static final Supplier<String> ALPHA = () -> {
		String ch = "";
		switch (new Random().nextInt(2)) {
		case 0:
			ch = getRandomChar('a', 'z');
			break;
		case 1:
			ch = getRandomChar('A', 'Z');
			break;
		}
		return ch;
	};

	public static final Supplier<String> ALPHA_NUMERIC = () -> {
		String ch = "";
		switch (new Random().nextInt(3)) {
		case 0:
			ch = getRandomChar('a', 'z');
			break;
		case 1:
			ch = getRandomChar('A', 'Z');
			break;
		case 2:
			ch = getRandomChar('0', '9');
			break;
		}
		return ch;
	};

	/**
	 * Get multiple values from the given list.
	 */
	public static <T> Set<T> getRandomSubListOf(T... t) {
		Random r = new Random();
		Set<T> set = Arrays.stream(t).filter(p -> r.nextBoolean()).collect(Collectors.toSet());
		return set;
	}
	
	/**
	 * Get multiple values from the given list.
	 */
	public static <T> Set<T> getRandomSubListOf(List<T> list) {
		Random r = new Random();
		Set<T> set = list.stream().filter(p -> r.nextBoolean()).collect(Collectors.toSet());
		return set;
	}
	
	

	/**
	 * Get only one object from the given list.
	 */
	public static <T> T options(T... t) {
		Random r = new Random();
		return t[r.nextInt(t.length)];
	}

	/**
	 * Get the random data of birth age between minDate and maxDate.
	 */
	public static LocalDate getDateBetween(LocalDate minDate, LocalDate maxDate) {
		long minDay = minDate.toEpochDay();
		long maxDay = maxDate.toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay);
	}
	
	

	/**
	 * Get the random data of birth age between MIN and MAX.
	 */
	public static LocalDate getBirthDayByAge(int min, int max) {
		long minDate = LocalDate.now().minusYears(min).toEpochDay(); // 18 ->
		long maxDate = LocalDate.now().minusYears(max).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(maxDate, minDate);
		return LocalDate.ofEpochDay(randomDay);
	}

	/**
	 * Get the random data of birth default age between 18 and 35.
	 */
	public static LocalDate getBirthDay() {
		return getBirthDayByAge(18, 35);
	}

	/**
	 * Returns a number between min and max, return values also includes min and max
	 * values.
	 */
	public static int getNumberBetween(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	/**
	 * Returns salary between 10,000 and 1,20,000.
	 */
	public static BigDecimal getSalary() {
		return new BigDecimal(getNumberBetween(1, 12) * 10_000);
	}
	
	/**
	 * Returns salary between 10,000 and 1,20,000.
	 */
	public static int getCost() {
		return getNumberBetween(1, 12) * 5_000;
	}

	/**
	 * Returns a string according to the pattern
	 * <li>? means any LOWERCASE letter</li>
	 * <li># any DIGIT</li>
	 * <li>^ any UPPERCASE letter</li>
	 * <li>% any ALPHA</li>
	 * <li>* any ALPHA_NUMERIC letter</li>
	 */
	public static String getPatternText(String pattern) {
		StringBuilder sb = new StringBuilder(pattern);
		for (int i = 0; i < pattern.length(); i++) {

			switch (pattern.charAt(i)) {
			case '?':
				sb.replace(i, i + 1, LOWER.get());
				break;
			case '^':
				sb.replace(i, i + 1, UPPER.get());
				break;
			case '*':
				sb.replace(i, i + 1, ALPHA_NUMERIC.get());
				break;
			case '#':
				sb.replace(i, i + 1, NUMBER.get());
				break;
			case '%':
				sb.replace(i, i + 1, ALPHA.get());
				break;
			}
		}
		return sb.toString();
	}

	private static String getRandomChar(char min, char max) {
		return (char) getNumberBetween(min, max) + ""; 
	}

	/**
	 * Generates random test based on the given supplier. RandomUtil.LOWER,
	 * RandomUtil.UPPER, RandomUtil.ALPHA_NUMERIC, RandomUtil.NUMBER,
	 * RandomUtil.ALPHA can be used as supplier.
	 */
	public static String getRandomText(int min, int max, Supplier<String> supplier) {
		StringBuilder sb = new StringBuilder();
		int size = getNumberBetween(min, max);
		for (int i = 0; i < size; i++) {
			sb.append(supplier.get());
		}

		return sb.toString();
	}
	
	
	public static Faker faker() {
		return FAKER;
	}
	
	public static Random random() {
		return RANDOM;
	}

	private static String getRandomText(int origin, int bound, int size) {
		IntStream ints = new Random().ints(origin, bound).limit(size);
		StringBuilder builder = ints.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
		return builder.toString();
	}
	
	
}
