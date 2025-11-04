/*
 🔹 Problem: 300. Longest Increasing Subsequence
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Binary Search, Patience Sorting
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

✅ **Approach 3 (Patience Sorting with TreeSet):**
   - Maintain a TreeSet that represents the "piles" in patience sorting.
   - For each element, find the smallest element >= current (ceiling).
   - If found, replace it (optimizing the pile); otherwise, add a new pile.
   - Final size of TreeSet = length of LIS.
   - Elegant O(N log N) solution using greedy approach.

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
 🔹 Approach 2 (Commented - Iterative DP - Tabulation)
   - Time Complexity: O(N²)
   - Space Complexity: O(N)
 ------------------------------------------------------------

// import java.util.Arrays;
//
// class Solution {
//     public int lengthOfLIS(int[] nums) {
//         int n = nums.length;
//         int[] dp = new int[n];
//         Arrays.fill(dp, 1);
//         
//         int max = 1;
//         for (int i = 1; i < n; i++) {
//             for (int j = 0; j < i; j++) {
//                 if (nums[j] < nums[i]) {
//                     dp[i] = Math.max(dp[i], dp[j] + 1);
//                 }
//             }
//             max = Math.max(max, dp[i]);
//         }
//         return max;
//     }
// }
/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Patience Sorting with TreeSet - Optimized)
   - Time Complexity: O(N log N) - each operation on TreeSet is O(log N)
   - Space Complexity: O(N) - TreeSet can grow up to N elements
   
   🧠 **Key Insight:**
   This approach uses the "Patience Sorting" algorithm concept:
   - Think of building piles of cards where you can only place smaller cards on larger ones
   - For each number, find the leftmost pile where it can be placed (smallest pile top >= current)
   - If such pile exists, replace its top (optimization); else create a new pile
   - The number of piles at the end = length of LIS
   
   💡 **Why it works:**
   - TreeSet maintains sorted order and allows O(log N) ceiling operation
   - Replacing ceiling element optimizes the pile structure without affecting LIS length
   - This greedy approach ensures we always have the best "tail" for each pile size
 ------------------------------------------------------------
*/

import java.util.TreeSet;

class Solution {
    public int lengthOfLIS(int[] nums) {
        TreeSet<Integer> arr = new TreeSet<>();
        
        for (int i : nums) {
            Integer el = arr.ceiling(i);
            if (el != null) {
                arr.remove(el);
            }
            arr.add(i);
        }
        
        return arr.size();
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (Patience Sorting):

nums = [10,9,2,5,3,7,101,18]

TreeSet initially: []
i=10: ceiling(10)=null → add 10 → TreeSet: [10]
i=9: ceiling(9)=10 → remove 10, add 9 → TreeSet: [9]
i=2: ceiling(2)=9 → remove 9, add 2 → TreeSet: [2]
i=5: ceiling(5)=null → add 5 → TreeSet: [2, 5]
i=3: ceiling(3)=5 → remove 5, add 3 → TreeSet: [2, 3]
i=7: ceiling(7)=null → add 7 → TreeSet: [2, 3, 7]
i=101: ceiling(101)=null → add 101 → TreeSet: [2, 3, 7, 101]
i=18: ceiling(18)=101 → remove 101, add 18 → TreeSet: [2, 3, 7, 18]

Final TreeSet size = 4 ✅ (Matches LIS length)

💡 Note: The TreeSet doesn't store the actual LIS, but its size equals the LIS length.
 ------------------------------------------------------------
*/
