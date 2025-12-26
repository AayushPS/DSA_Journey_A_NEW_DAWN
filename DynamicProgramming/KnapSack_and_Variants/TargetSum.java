/*
 🔹 Problem: 494. Target Sum
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Subset Sum
 🔹 Link: https://leetcode.com/problems/target-sum/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an integer array nums and an integer target.

You must assign either '+' or '-' in front of each number and
evaluate the resulting expression.

Return the number of different expressions that evaluate to target.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [1,1,1,1,1], target = 3
Output: 5

Example 2:
Input: nums = [1], target = 1
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ nums.length ≤ 20
 • 0 ≤ nums[i] ≤ 1000
 • sum(nums) ≤ 1000
 • −1000 ≤ target ≤ 1000

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Count the number of ways to assign + / − signs to reach the target.

📍 Key Transformation:
Let:
S1 − S2 = target  
S1 + S2 = totalSum  

⇒ S1 = (totalSum − target) / 2

So the problem reduces to:
👉 Count subsets with sum = (totalSum − target) / 2

📍 Approaches:
1. Recursion + Memoization (exponential → optimized)
2. Bottom-Up DP (2D)
3. Bottom-Up DP (1D Space Optimized) ✅

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Recursion + Memoization)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(n × sum)
 ------------------------------------------------------------

 // class Solution {
 //     public int findTargetSumWays(int[] nums, int target) {
 //         int total = Arrays.stream(nums).sum();
 //         if (Math.abs(target) > total) return 0;
 //
 //         int[][] dp = new int[nums.length + 1][2 * total + 1];
 //         for (int[] row : dp) Arrays.fill(row, -1);
 //
 //         return dfs(0, nums, target, dp, total);
 //     }
 //
 //     private int dfs(int i, int[] nums, int target, int[][] dp, int offset) {
 //         if (i == nums.length) return target == 0 ? 1 : 0;
 //         if (dp[i][target + offset] != -1) return dp[i][target + offset];
 //
 //         int add = dfs(i + 1, nums, target + nums[i], dp, offset);
 //         int sub = dfs(i + 1, nums, target - nums[i], dp, offset);
 //
 //         return dp[i][target + offset] = add + sub;
 //     }
 // }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Bottom-Up DP 2D)
   ⏱️ Time Complexity: O(n × target)
   💾 Space Complexity: O(n × target)
 ------------------------------------------------------------

 // class Solution {
 //     public int findTargetSumWays(int[] nums, int target) {
 //         int sum = Arrays.stream(nums).sum();
 //         if (sum - target < 0 || (sum - target) % 2 != 0) return 0;
 //
 //         int t = (sum - target) / 2;
 //         int[][] dp = new int[nums.length + 1][t + 1];
 //         dp[0][0] = 1;
 //
 //         for (int i = 1; i <= nums.length; i++) {
 //             for (int j = 0; j <= t; j++) {
 //                 dp[i][j] = dp[i - 1][j];
 //                 if (nums[i - 1] <= j)
 //                     dp[i][j] += dp[i - 1][j - nums[i - 1]];
 //             }
 //         }
 //         return dp[nums.length][t];
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

public class TargetSum {

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int v : nums) sum += v;

        if (sum - target < 0 || (sum - target) % 2 != 0) return 0;

        int required = (sum - target) / 2;

        int[] prev = new int[required + 1];
        int[] curr = new int[required + 1];
        prev[0] = 1;

        for (int num : nums) {
            Arrays.fill(curr, 0);
            for (int s = 0; s <= required; s++) {
                curr[s] = prev[s];
                if (num <= s) {
                    curr[s] += prev[s - num];
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[required];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

nums = [1,1,1,1,1], target = 3
Total sum = 5

Required subset sum = (5 − 3) / 2 = 1

Number of subsets with sum = 1 → 5

Answer: 5 ✅
 ------------------------------------------------------------
*/
