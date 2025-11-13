package com.carbontrack.carbontrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissingIntegers {

	public static int solution(int[] A) {
		System.out.println("A = " + Arrays.toString(A));
		int smallestPositiveInteger = 1;
		int maxValue = 0;
		boolean allValuesAreNegative = true;
		Arrays.sort(A);
		Set<Integer> set = new HashSet<>();
		for (int value : A) {
			if (value > 0) {
				set.add(value);
				allValuesAreNegative = false;
				if (value > maxValue) {
					maxValue = value;
				}
			}
		}
		if (!allValuesAreNegative) {
			boolean smallestPositiveIntegerFinded = false;
			int i = 1;
			while(!smallestPositiveIntegerFinded && i < maxValue) {
				if (!set.contains(i)) {
					smallestPositiveIntegerFinded = true;
					smallestPositiveInteger = i;
				}
				i++;
			}
			if (!smallestPositiveIntegerFinded) {
				smallestPositiveInteger = maxValue + 1;
			}
		}
		System.out.println("Missing integer is: " + smallestPositiveInteger);
		return smallestPositiveInteger;
	}
	
	public static boolean onlyOddNumbers(List<Integer> list) {
		 return !(list
		   .parallelStream() // parallel stream for faster processing
		   .anyMatch(x -> x % 2 == 0)); // return as soon as any elements match the condition
		}
	
	public static void main(String args[])
	{
		int k = 1;
		List<Integer> arr = new ArrayList<Integer>();
		arr = Arrays.asList(9,4,6,5,7,1,8);
		 boolean containsInt = arr.parallelStream().anyMatch(i -> i.equals(Integer.valueOf(k)));
		 String result = "";
	        if(containsInt)
	        	result = "YES";
	        else result = "NO";
	        System.out.println(result);
		System.out.println("Missing smallest possible integer is : "+ solution(new int[] {1,3,6,4,1,2}));
		System.out.println("Only odd numbers? : "+ onlyOddNumbers(Arrays.asList(1,3,7,5,1)));
	}
}
