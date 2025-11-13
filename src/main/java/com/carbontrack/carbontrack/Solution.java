package com.carbontrack.carbontrack;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;

public class Solution {

	//	public static int solution(String E, String L)
	//	{
	////		  int entryCost = 2;
	////			int firstHourCost = 3;
	////			int subsequentHoursCost = 4;
	////			String[] start = E.split(":");
	////	        String[] end = L.split(":");
	////
	////	        int startHour = Integer.parseInt(start[0]);
	////	        int startMin  = Integer.parseInt(start[1]);
	////
	////	        int endHour = Integer.parseInt(end[0]);
	////	        int endMin  = Integer.parseInt(end[1]);
	////
	////	        // Total parked minutes
	////	        int totalMinutes = (endHour * 60 + endMin) - (startHour * 60 + startMin);
	////
	////	        // Convert minutes to hours (round up partial hours)
	////	        int totalHours = (totalMinutes + 59) / 60;  // ceiling
	////
	////	        // Billing:
	////	        // Entrance fee = 2
	////	        // First hour = 3
	////	        // Each next hour = 4
	////
	////	        if (totalHours == 0) {
	////	            return 2; // if somehow less than an hour, but by rules totalHours >= 1
	////	        }
	////
	////	        return entryCost + firstHourCost + (totalHours - 1) * subsequentHoursCost;
	////			
	//		
	//			}

	public static String solution(String S)
	{
		StringBuilder sb = new StringBuilder();

		for(char c : S.toUpperCase().toCharArray())
		{
			sb.append(c);
			int length = sb.length();
			if(length >= 2)
			{
				String lastTwoChars = sb.substring(length -2);
				if(lastTwoChars.equals("AB") || lastTwoChars.equals("BA") || lastTwoChars.equals("CD") || lastTwoChars.equals("DC"))
					sb.delete(length-2, length);
			}
		}	
		return sb.toString();
	}

	public static int mySolution(String S)
	{

		S = S.replaceFirst("^0+(?!$)", "");
		int steps = 0;
		int n = S.length();
		
		if(n == 1 && S.charAt(0) == '0')
			return steps;

		// Traverse from rightmost bit to left (LSB to MSB)
		for (int i = n - 1; i > 0; i--) {
			if (S.charAt(i) == '0') {
				// even -> only divide by 2
				steps += 1;
			} else {
				// odd -> subtract (makes it even), then divide

				steps += 2;
			}
		}

		// For the most significant bit (leftmost), only one subtract step remains ('1' -> 0)
		steps += 1;

		return steps;
	}

	public static String truncateString(String S)
	{
		char[] ch = S.toUpperCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(char c : ch)
		{
			if(sb.isEmpty())
				sb.append(c);
			else if(((c == 'C' && sb.charAt(index)=='D') || (c == 'D' && sb.charAt(index)=='C')) ||
					((c == 'A' && sb.charAt(index)=='B') || (c == 'B' && sb.charAt(index)=='A')) ){
				sb.deleteCharAt(index);
				if(index > 0)
					index--;
			}else
			{
				sb.append(c);
				index++;
			}
		}
		return sb.toString();

	}

	public static void main (String []args)
	{
		//		System.out.println(solution("18:21", "18:29"));
		//		String s= solution("CABABD");
		//		System.out.println( s == null ? "Empty String" : "The returned string : "+s);
		//		

		String st = "0"; System.out.println(mySolution(st));

		//		String str= truncateString("CABABDCAD");
		//		String s = solution("CABABDCAD");

		//		String str= truncateString("CABABD");
		//		String s = solution("CABABD");

		//		System.out.println( str == null ? "Empty String" : "The returned string : "+str);
		//		System.out.println( s == null ? "Empty String" : "The returned string : "+s);

	}

}
