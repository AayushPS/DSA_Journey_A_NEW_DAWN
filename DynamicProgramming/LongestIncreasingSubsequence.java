/*
 🔹 Problem: 300. Longest Increasing Subsequence
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Binary Search
 🔹 Link: https://leetcode.com/problems/longest-increasing-subsequence/

 ------------------------------------------------------------
 🔸 Problem Statement:

Given an integer array nums, return the length of the longest strictly increasing subsequence.

A subsequence is a sequence that can be derived from an array by deleting some or no elements 
without changing the order of the remaining elements.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101].

Example 2:
Input: nums = [0,1,0,3,2,3]
Output: 4

Example 3:
Input: nums = [7,7,7,7,7,7,7]
Output: 1

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= nums.length <= 2500
 • -10^4 <= nums[i] <= 10^4

 ------------------------------------------------------------
 🔹 Approach Summary:

✅ **Goal:** Find the maximum length of a strictly increasing subsequence.

✅ **Approach 1 (Recursive + Memoization):**
   - Try taking or not taking each element while maintaining previous index.
   - Store results in a 2D DP table to avoid recomputation.
   - Top-down recursive exploration with memoization.

✅ **Approach 2 (Iterative DP - Tabulation):**
   - Bottom-up DP where `dp[i]` represents LIS ending at index `i`.
   - For every pair (j, i), if nums[j] < nums[i], extend sequence.
   - Track global maximum LIS length.
   - More efficient and preferred for practical use.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Recursive + Memoization)
   - Time Complexity: O(N²)
   - Space Complexity: O(N²)
 ------------------------------------------------------------

// class Solution {
//     public int lengthOfLIS(int[] nums) {
//         int[][] dp = new int[nums.length][nums.length + 1];
//         return lis(nums, 0, dp, -1);
//     }
//
//     private int lis(int[] nums, int i, int[][] dp, int prev) {
//         if (i == nums.length) return 0;
//         if (dp[i][prev + 1] != 0) return dp[i][prev + 1];
//
//         int take = 0;
//         if (prev == -1 || nums[i] > nums[prev])
//             take = 1 + lis(nums, i + 1, dp, i);
//
//         int dontTake = lis(nums, i + 1, dp, prev);
//         return dp[i][prev + 1] = Math.max(take, dontTake);
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (✅ Iterative DP - Optimized & Clean)
   - Time Complexity: O(N²)
   - Space Complexity: O(N)
 ------------------------------------------------------------
*/

import java.util.Arrays;

class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        int max = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

nums = [10,9,2,5,3,7,101,18]

Step-by-step:
dp = [1,1,1,1,1,1,1,1]
i=2, j=0,1 → no increase → dp[2]=1
i=3, compare to smaller:
   j=2 → 2<5 → dp[3]=2
i=4, j=2 → 2<3 → dp[4]=2
i=5, j=4 → 3<7 → dp[5]=3
i=6, j=5 → 7<101 → dp[6]=4
Result = 4 ✅
 ------------------------------------------------------------
*/
