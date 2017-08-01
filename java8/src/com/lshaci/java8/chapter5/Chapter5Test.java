package com.lshaci.java8.chapter5;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.lshaci.java8.chapter2.Chapter2Test;
import com.lshaci.java8.chapter2.Dish;

public class Chapter5Test extends Chapter2Test {

	/**
	 * flatMap: 使流扁平化(把一个流里面的多个流搞成多个流)
	 */
	@Test
	public void testFlatMap() throws Exception {
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<int[]> pairs = numbers1.stream()
				// 如果这一步使用map: 则返回的是Stream<Stream<Integer[]>>
				// 使用flatMap可以让流扁平化, 则返回的是Stream<Integer[]>
				.flatMap(i -> numbers2
									.stream()
									.map(j -> new int[] { i, j }))
				.collect(toList());
		pairs.stream().forEach(e -> System.out.println(Arrays.toString(e)));
	}
	
	/**
	 * anyMatch: 有一个匹配就返回true
	 * allMatch: 全部匹配才返回true
	 * noneMatch: 全部不匹配才返回true
	 */
	@Test
	public void testMatch() throws Exception {
		boolean hasVegetarian = menu.stream()
				.anyMatch(Dish::isVegetarian);
		System.out.println("anyMatch --> The menus has a vegetarian dish: " + hasVegetarian);
		
		boolean isHealthy1 = menu.stream() 
				 .allMatch(d -> d.getCalories() < 1000);
		System.out.println("allMatch --> The menus all dish calories less than 1000: " + isHealthy1);
		
		boolean isHealthy2 = menu.stream() 
				 .noneMatch(d -> d.getCalories() >= 1000);
		System.out.println("noneMatch --> The menus all dish calories less than 1000: " + isHealthy2);
	}
	
	/**
	 * findAny: 返回当前流中的任意元素
	 * findFirst: 从流程找出第一个
	 */
	@Test
	public void testFind() throws Exception {
		Optional<Dish> dish = menu
				.stream()
				.filter(Dish::isVegetarian)
				.findAny();
		System.out.println(dish);
		
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		Optional<Integer> firstSquareDivisibleByThree = someNumbers
				.stream()
				.map(x -> x * x)
				.filter(x -> x % 3 == 0)
				.findFirst(); // 9
		System.out.println(firstSquareDivisibleByThree);
	}
	
	@Test
	public void testReduce() throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		int sum = numbers
				.stream()
//				.reduce(0, (a, b) -> a + b);
				.reduce(0, Integer::sum);
		
		System.out.println(sum);
	}
	
	Trader raoul = new Trader("Raoul", "Cambridge");
	Trader mario = new Trader("Mario", "Milan");
	Trader alan = new Trader("Alan", "Cambridge");
	Trader brian = new Trader("Brian", "Cambridge");
	
	List<Transaction> transactions = Arrays.asList(
			new Transaction(brian, 2011, 300),
			new Transaction(raoul, 2012, 1000), 
			new Transaction(raoul, 2011, 400), 
			new Transaction(mario, 2012, 710),
			new Transaction(mario, 2012, 700), 
			new Transaction(alan, 2012, 950)
	);
	
	
//	(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
	@Test
	public void testName1() throws Exception {
		List<Transaction> tr2011 = transactions.stream()
				.filter(t -> t.getYear() == 2011)
				.sorted(comparing(Transaction::getValue))
				.collect(toList());
		System.out.println(tr2011);
	}
//	(2) 交易员都在哪些不同的城市工作过？
	@Test
	public void testName2() throws Exception {
		List<String> workCityList = transactions.stream()
				.map(t -> t.getTrader().getCity())
				.distinct()
				.collect(toList());
		
		Set<String> workCitySet = transactions.stream()
				.map(t -> t.getTrader().getCity())
				.collect(toSet());
		System.out.println("List --> " + workCityList);
		System.out.println("Set --> " + workCitySet);
	}
//	(3) 查找所有来自于剑桥的交易员，并按姓名排序。
	@Test
	public void testName3() throws Exception {
		List<Trader> traders = transactions.stream()
				.map(t -> t.getTrader())
				.filter(t -> "Cambridge".equals(t.getCity()))
				.distinct()
				.sorted(comparing(Trader::getName))
				.collect(toList());
		System.out.println(traders);
	}
//	(4) 返回所有交易员的姓名字符串，按字母顺序排序。
	@Test
	public void testName4() throws Exception {
		List<String> traderNames = transactions.stream()
				.map(t -> t.getTrader().getName())
				.distinct()
				.sorted()
				.collect(toList());
		System.out.println(traderNames);
		
		String traderName = transactions.stream()
				.map(t -> t.getTrader().getName())
				.distinct()
				.sorted()
//				.reduce("", (n1, n2) -> n1 + n2);
				.collect(joining());
		System.out.println(traderName);
	}
//	(5) 有没有交易员是在米兰工作的？
	@Test
	public void testName5() throws Exception {
		boolean isInMilan = transactions.stream()
				.anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
		System.out.println("有没有交易员是在米兰工作的？ " + isInMilan);
	}
//	(6) 打印生活在剑桥的交易员的所有交易额。
	@Test
	public void testName6() throws Exception {
		transactions.stream()
				.filter(t -> "Cambridge".equals(t.getTrader().getCity()))
				.map(Transaction::getValue)
				.forEach(System.out::println);
		
		Integer reduce = transactions.stream()
				.filter(t -> "Cambridge".equals(t.getTrader().getCity()))
				.map(Transaction::getValue)
//				.reduce(0, (a, b) -> a + b);
				.reduce(0, Integer::sum);	// 有装箱
		
		int reduceInt = transactions.stream()
				.filter(t -> "Cambridge".equals(t.getTrader().getCity()))
				.mapToInt(Transaction::getValue)
				.sum();
		System.out.println("生活在剑桥的交易员的所有交易额 = " + reduce);
		System.out.println("生活在剑桥的交易员的所有交易额 = " + reduceInt);
	}
//	(7) 所有交易中，最高的交易额是多少？
	@Test
	public void testName7() throws Exception {
		Optional<Integer> reduce = transactions.stream()
				.map(Transaction::getValue)
				.reduce(Integer::max);
		System.out.println("所有交易中，最高的交易额是多少？ " + reduce);
	}
//	(8) 找到交易额最小的交易。
	@Test
	public void testName8() throws Exception {
		Optional<Transaction> reduce = transactions.stream()
				.min(comparing(Transaction::getValue));
		System.out.println("交易额最小的交易 --> " + reduce);
	}

	@Test
	public void testName() throws Exception {
		Stream<int[]> pythagoreanTriples = IntStream
				.rangeClosed(1, 100)
				.boxed()
				.flatMap(a -> 
						IntStream
							.rangeClosed(a, 100)
							.filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
							.mapToObj(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b)})
		);
		
		Stream<double[]> pythagoreanTriples2 = IntStream
				.rangeClosed(1, 100)
				.boxed()
				.flatMap(a -> 
						IntStream
							.rangeClosed(a, 100)
							.mapToObj(b -> new double[] { a, b, Math.sqrt(a * a + b * b)})
							.filter(arr -> arr[2] % 1 == 0)
		);
		pythagoreanTriples.limit(100)
				.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
		
		pythagoreanTriples2.limit(100)
				.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
		
		
	}
	
}
