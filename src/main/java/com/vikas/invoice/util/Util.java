package com.vikas.invoice.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Util {

	// compare date excluding time
	public static boolean isDateInRange(LocalDate datedateToBeChecked, LocalDate startDate, LocalDate endDate) {

		boolean isYearInRange = datedateToBeChecked.getYear() >= startDate.getYear() && datedateToBeChecked.getYear() <= endDate.getYear();
		boolean isDayInRange = datedateToBeChecked.getDayOfYear() >= startDate.getDayOfYear() && datedateToBeChecked.getDayOfYear() <= endDate.getDayOfYear();

		if (isYearInRange && isDayInRange)
			return true;

		return false;
	}

	public static String convertToWordsIndianCurrency(Double amount) {
		return convertToWordsIndianCurrency("" + amount);
	}

	private static String convertToWordsIndianCurrency(String num) {
		BigDecimal bd = new BigDecimal(num);
		long number = bd.longValue();
		long no = bd.longValue();
		int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
		int digits_length = String.valueOf(no).length();
		int i = 0;
		ArrayList<String> str = new ArrayList<>();
		HashMap<Integer, String> words = new HashMap<>();
		words.put(0, "");
		words.put(1, "One");
		words.put(2, "Two");
		words.put(3, "Three");
		words.put(4, "Four");
		words.put(5, "Five");
		words.put(6, "Six");
		words.put(7, "Seven");
		words.put(8, "Eight");
		words.put(9, "Nine");
		words.put(10, "Ten");
		words.put(11, "Eleven");
		words.put(12, "Twelve");
		words.put(13, "Thirteen");
		words.put(14, "Fourteen");
		words.put(15, "Fifteen");
		words.put(16, "Sixteen");
		words.put(17, "Seventeen");
		words.put(18, "Eighteen");
		words.put(19, "Nineteen");
		words.put(20, "Twenty");
		words.put(30, "Thirty");
		words.put(40, "Forty");
		words.put(50, "Fifty");
		words.put(60, "Sixty");
		words.put(70, "Seventy");
		words.put(80, "Eighty");
		words.put(90, "Ninety");
		String digits[] = { "", "Hundred", "Thousand", "Lakh", "Crore" };
		while (i < digits_length) {
			int divider = (i == 2) ? 10 : 100;
			number = no % divider;
			no = no / divider;
			i += divider == 10 ? 1 : 2;
			if (number > 0) {
				int counter = str.size();
				String plural = (counter > 0 && number > 9) ? "s" : "";
				String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural
						: words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " " + words.get(Integer.valueOf((int) (number % 10))) + " "
								+ digits[counter] + plural;
				str.add(tmp);
			} else {
				str.add("");
			}
		}

		Collections.reverse(str);
		String Rupees = String.join(" ", str).trim();

		String paise = (decimal) > 0
				? " And Paise " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " " + words.get(Integer.valueOf((int) (decimal % 10)))
				: "";
		return "Rupees " + Rupees + paise + " Only";
	}

	public static List<String> getItemUnits() {
		return Arrays.asList("PCS", "GMS", "KGS", "NOS", "MTR", "BOX", "ROL", "SET", "PAC", "OTH");
	}

	public static double roundToTwoDigit(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return Double.parseDouble(decimalFormat.format(value));
	}

	public static String roundToTwoDigitStr(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return decimalFormat.format(value);
	}

}
