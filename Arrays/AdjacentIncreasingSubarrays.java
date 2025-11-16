/*
 🔹 Problem: 3349. Adjacent Increasing Subarrays Detection I
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Approach: Iterate over the array, check every subarray of length k for strictly increasing, return true if two adjacent increasing subarrays exist.
 🔹 Time Complexity: O(n*k)
 🔹 Space Complexity: O(1)
*/

import java.util.List;

public class AdjacentIncreasingSubarrays {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        if (n < 2 * k) return false;
        for(int i = 0;i <= n - 2 * k;i++) {
            if (isIncreasing(nums, i, k)) {
                if (isIncreasing(nums, i + k, k)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isIncreasing(List<Integer> nums, int start, int k) {
        for (int i = 0; i < k - 1; i++) {
            if (nums.get(start + i) >= nums.get(start + i + 1)) {
                return false;
            }
        }
        return true;
    }
}