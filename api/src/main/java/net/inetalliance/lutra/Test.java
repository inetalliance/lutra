package net.inetalliance.lutra;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		System.out.println(new DocumentBuilder(new File("test.html")).buildGeneric().toString(true));
	}
}
