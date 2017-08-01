package com.lshaci.java8.chapter2;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Chapter2Test {
	
	protected List<Dish> menu;
	
	@Before
	public void beforeInitMenu() {
		menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), 
				new Dish("chicken", false, 900, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), 
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), 
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), 
				new Dish("salmon", false, 450, Dish.Type.FISH)
			);
	}

	/**
	 * 获取热量最高的3个菜名
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetThreeHighCaloricDishNames() throws Exception {
		List<String> threeHighCaloricDishNames = menu
				.stream()								// 从menu获得流（菜肴列表）    -->  建立操作流水线
				.filter(d -> d.getCalories() > 300)		// 首先选出高热量的菜肴
				.sorted(comparing(Dish::getCalories).reversed())	// 按热量排序(降序)
				.map(Dish::getName)						// 获取菜名
				.limit(3)								// 只选择头三个
				.collect(toList());						// 将结果保存在另一个List中

		System.out.println(threeHighCaloricDishNames);	// [chicken, pork, beef]
	}
}
