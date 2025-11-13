package com.carbontrack.carbontrack;

import java.security.cert.CRLReason;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.angus.mail.util.ASCIIUtility;

public class Trial {

	public static void staircase(int n)
	{
		 for(int i=1; i<=n; i++)
		    {
		        for(int j=i; j<n; j++)
		        {
		            System.out.print(" ");
		        }
		        for(int j=i; j>0; j--)
		            System.out.print("#");
		        
		        System.out.println();
		    }
	}
	
	public static int lonegstSubString(String s)
	{
		/*
		 * int length = str.length(); int maxCount=0, currentCount=0;
		 * 
		 * for(int i=0; i<= length-1; i++) { StringBuffer sb = new StringBuffer();
		 * sb.append(str.charAt(i)); currentCount++;
		 * 
		 * for(int j=i+1; j<= length-1; j++) {
		 * if(sb.toString().contains(String.valueOf(str.charAt(j)))) break; else {
		 * sb.append(str.charAt(j)); currentCount++; } } if(currentCount > maxCount)
		 * maxCount = currentCount; currentCount = 0; } return maxCount;
		 */
		
		 // declare maxLen
        // declare start
        int maxLen = 0;
        int start = 0;
        int[] lastIndex = new int[128]; // For ASCII characters
        for (int i = 0; i < 128; i++) lastIndex[i] = -1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.println("ASCII value of "+ c +" : "+ (int)c);
            if (lastIndex[c] >= start) {
                start = lastIndex[c] + 1;
            }
            lastIndex[c] = i;
            maxLen = Math.max(maxLen, i - start + 1);
        }
        return maxLen;
	}
	
	public static void main(String args[])
	{
		/*
		 * int[] nums = {5, 3, 8, 1}; Arrays.sort(nums);
		 * System.out.println("Sorted array: " + Arrays.toString(nums));
		 * 
		 * // List example List<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 8,
		 * 1)); list.sort(Collections.reverseOrder()); // list.sort(null);
		 * System.out.println("Sorted list: " + list);
		 * 
		 * staircase(5);
		 */
		String str = "geeksforgeeks";
		System.out.println("Longest substring in " + str + " is : "+ lonegstSubString(str.toLowerCase()) );
		
		 HashMap<String, Integer> capitalCities = new HashMap<String, Integer>();
		    capitalCities.put("timetoprac", 0);
		    capitalCities.put("imetoprac", 1);
		    capitalCities.put("metoprac", 2);
		    capitalCities.put("etoprac", 3);
		    capitalCities.put("toprac", 4); // Duplicate
		    capitalCities.put("opract", 5);
		    int index = -1;
		    String key = null;
		    for(Entry<String, Integer> i : capitalCities.entrySet())
		    {
		    	if(i.getKey().length() == 6 && i.getValue() < index)
		    	{
		    		index = i.getValue();
		    		key = i.getKey();		    	
		    	}
		    }

		    System.out.println("Key with smallest index : "+ key); 
		
	}
}
