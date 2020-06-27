package com.vikas.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		Date date1 = new Date();
		Thread.sleep(3000);
		Date date2 = new Date();
		
		LocalDateTime newDate1 = LocalDateTime.now();
		
		System.out.println(newDate1);
	}
	
}
