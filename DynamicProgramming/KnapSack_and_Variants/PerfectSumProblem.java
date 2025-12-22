package DynamicProgramming.KnapSack_and_Variants;
/*
 🔹 Problem: Perfect Sum Problem
 🔹 Platform: GeeksForGeeks
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Subset DP
 🔹 Link: https://www.geeksforgeeks.org/problems/perfect-sum-problem5633/1

 ------------------------------------------------------------
 📝 Problem Statement:

Given an array of non-negative integers and a target sum,
count the number of subsets whose sum is exactly equal to
the given target.

Return the count modulo 1e9 + 7.

 ------------------------------------------------------------
 📊 Examples:

Input: arr = [5, 2, 3, 10, 6, 8], target = 10
Output: 3

Input: arr = [2, 5, 1, 4, 3], target = 10
Output: 3

Input: arr = [35, 2, 8, 22], target = 0
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ arr.length ≤ 100
 • 0 ≤ arr[i] ≤ 100
 • 0 ≤ target ≤ 100

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Count all subsets whose sum equals the given target.

📍 Approach 1: Recursion + Memoization
- Try including or excluding each element
- Cache results to avoid recomputation

📍 Approach 2: Bottom-Up DP (2D)
- dp[i][j] = number of subsets using first i elements with sum j
- Transition via include/exclude

📍 Approach 3 (✅ Optimized): Bottom-Up DP (1D Space Optimized)
- Reduce space from O(n × target) to O(target)
- Use rolling arrays
- Final and most optimal solution

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Recursion + Memoization)
   ⏱️ Time Complexity: O(n × target)
   💾 Space Complexity: O(n × target)
 ------------------------------------------------------------

 // class Solution {
 //     public int perfectSum(int[] nums, int target) {
 //         int n = nums.length;
 //         int[][] dp = new int[n + 1][target + 1];
 //         for (int[] row : dp) Arrays.fill(row, -1);
 //         return solve(nums, target, n, dp);
 //     }
 //
 //     private int solve(int[] nums, int target, int i, int[][] dp) {
 //         if (i == 0) return target == 0 ? 1 : 0;
 //         if (dp[i][target] != -1) return dp[i][target];
 //
 //         if (nums[i - 1] <= target) {
 //             return dp[i][target] =
 //                 solve(nums, target - nums[i - 1], i - 1, dp)
 //               + solve(nums, target, i - 1, dp);
 //         }
 //         return dp[i][target] = solve(nums, target, i - 1, dp);
 //     }
 // }

 /*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Bottom-Up DP 2D)
   ⏱️ Time Complexity: O(n × target)
   💾 Space Complexity: O(n × target)
 ------------------------------------------------------------

 // class Solution {
 //     private static final int MOD = 1_000_000_007;
 //
 //     public int perfectSum(int[] nums, int target) {
 //         int n = nums.length;
 //         int[][] dp = new int[n + 1][target + 1];
 //         dp[0][0] = 1;
 //
 //         for (int i = 1; i <= n; i++) {
 //             for (int j = 0; j <= target; j++) {
 //                 dp[i][j] = dp[i - 1][j];
 //                 if (nums[i - 1] <= j) {
 //                     dp[i][j] =
 //                         (dp[i][j] + dp[i - 1][j - nums[i - 1]]) % MOD;
 //                 }
 //             }
 //         }
 //         return dp[n][target];
 //     }
 // }
*/

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Bottom-Up DP – Space Optimized)
   ⏱️ Time Complexity: O(n × target)
   💾 Space Complexity: O(target)
 ------------------------------------------------------------
*/

import java.util.*;

public class PerfectSumProblem {

    private static final int MOD = 1_000_000_007;

    public int perfectSum(int[] nums, int target) {
        int n = nums.length;

        int[] prev = new int[target + 1];
        int[] curr = new int[target + 1];

        prev[0] = 1;

        for (int i = 1; i <= n; i++) {
            Arrays.fill(curr, 0);
            for (int sum = 0; sum <= target; sum++) {
                curr[sum] = prev[sum];
                if (nums[i - 1] <= sum) {
                    curr[sum] = (curr[sum] + prev[sum - nums[i - 1]]) % MOD;
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[target];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

arr = [5, 2, 3], target = 5

Subsets:
[]           → 0
[5]          → 5 ✅
[2,3]        → 5 ✅

Answer = 2
 ------------------------------------------------------------
*/
