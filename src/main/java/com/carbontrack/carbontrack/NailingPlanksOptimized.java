package com.carbontrack.carbontrack;

import java.util.Arrays;
import java.util.Comparator;

public class NailingPlanksOptimized {

	  public static void main(String[] args) {
	        int[] A = {1, 4, 5, 8};
	        int[] B = {4, 5, 9, 10};
	        int[] C = {4, 6, 7, 10, 2};

	        System.out.println(minimumNails(A, B, C)); // Expected: 4
	    }

	    static class Nail {
	        int position;
	        int index;

	        Nail(int position, int index) {
	            this.position = position;
	            this.index = index;
	        }
	    }

	    public static int minimumNails(int[] A, int[] B, int[] C) {
	        int N = A.length;
	        int M = C.length;

	        // Pair nail positions with their original indices
	        Nail[] nails = new Nail[M];
	        for (int i = 0; i < M; i++) {
	            nails[i] = new Nail(C[i], i);
	        }

	        // Sort by nail position
	        Arrays.sort(nails, Comparator.comparingInt(n -> n.position));

	        int low = 1, high = M, result = -1;

	        while (low <= high) {
	            int mid = (low + high) / 2;

	            if (allPlanksNailed(A, B, nails, mid)) {
	                result = mid;
	                high = mid - 1;  // try to reduce number of nails
	            } else {
	                low = mid + 1;
	            }
	        }

	        return result;
	    }

	    private static boolean allPlanksNailed(int[] A, int[] B, Nail[] nails, int usedNails) {
	        for (int i = 0; i < A.length; i++) {
	            if (!isPlankNailed(A[i], B[i], nails, usedNails)) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private static boolean isPlankNailed(int start, int end, Nail[] nails, int usedNails) {
	        int left = 0, right = nails.length - 1;

	        // Binary search to find the first nail whose position >= start
	        while (left <= right) {
	            int mid = (left + right) / 2;

	            if (nails[mid].position < start) {
	                left = mid + 1;
	            } else {
	                right = mid - 1;
	            }
	        }

	        // Now left is the first nail >= start
	        while (left < nails.length && nails[left].position <= end) {
	            if (nails[left].index < usedNails) {
	                return true;  // valid nail found
	            }
	            left++;
	        }

	        return false;
	    }
}
