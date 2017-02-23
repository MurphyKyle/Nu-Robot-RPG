package application;

import models.Map;

public class Test {
	public static void run() {
		Map map = new Map(10);
		System.out.println(map);
		map = new Map(20);
		System.out.println(map);
	}
}
