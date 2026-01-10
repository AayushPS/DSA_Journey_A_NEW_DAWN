/*
 🔹 Problem: 416. Partition Equal Subset Sum
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Knapsack
 🔹 Link: https://leetcode.com/problems/partition-equal-subset-sum/

 ------------------------------------------------------------
 📝 Problem Statement:

Given an integer array nums, determine whether it can be partitioned
into two subsets such that the sum of elements in both subsets is equal.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: Subsets [1,5,5] and [11] have equal sum.

Example 2:
Input: nums = [1,2,3,5]
Output: false

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ nums.length ≤ 200
 • 1 ≤ nums[i] ≤ 100

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Check if there exists a subset with sum = totalSum / 2.

Observation:
- If total sum is odd → impossible.
- Otherwise, this reduces to a **Subset Sum** problem.

📍 **Approach 1 (Top-Down Recursion + Memoization)**
   - Try include/exclude for each index.
   - Uses Boolean[][] memo table.
   - Clear but recursion overhead.

📍 **Approach 2 (Bottom-Up DP with 2 Arrays)**
   - Iterative knapsack.
   - Uses O(sum) space but extra array swaps.

📍 **Approach 3 (Bottom-Up DP with Single Array ✅ Most Optimal)**
   - Classic 0/1 knapsack.
   - Iterate sums backwards to avoid overwriting.
   - Minimal space and clean logic.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented – Recursion + Memoization)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(n × sum)
 ------------------------------------------------------------
*/

/*
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = IntStream.of(nums).sum();
        if (sum % 2 != 0) return false;
        Boolean[][] dp = new Boolean[nums.length][sum / 2 + 1];
        return solve(sum / 2, 0, nums, dp);
    }

    private boolean solve(int target, int i, int[] nums, Boolean[][] dp) {
        if (i == nums.length) return target == 0;
        if (target < 0) return false;
        if (dp[i][target] != null) return dp[i][target];
        return dp[i][target] =
            solve(target, i + 1, nums, dp) ||
            solve(target - nums[i], i + 1, nums, dp);
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented – Bottom-Up DP with Two Arrays)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(sum)
 ------------------------------------------------------------

class Solution {
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;

        int target = sum / 2;
        boolean[] prev = new boolean[target + 1];
        boolean[] curr = new boolean[target + 1];

        prev[0] = true;

        for (int num : nums) {
            Arrays.fill(curr, false);
            curr[0] = true;
            for (int j = 1; j <= target; j++) {
                if (num <= j) {
                    curr[j] = prev[j] || prev[j - num];
                } else {
                    curr[j] = prev[j];
                }
            }
            boolean[] tmp = prev;
            prev = curr;
            curr = tmp;
        }
        return prev[target];
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Bottom-Up DP with Single Array – Most Optimal)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(sum)

   🧠 Key Insight:
   Reverse iteration ensures each number is used only once.

   💡 Why it works:
   This is equivalent to the 0/1 Knapsack subset-sum formulation.

 ------------------------------------------------------------
*/

import java.util.*;

public class PartitionEqualSubsetSum {

    public boolean canPartition(int[] nums) {

        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[target];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

nums = [1,5,11,5]
totalSum = 22 → target = 11

Subset found:
11 OR 1 + 5 + 5 = 11

Return true ✅

 ------------------------------------------------------------
*/

