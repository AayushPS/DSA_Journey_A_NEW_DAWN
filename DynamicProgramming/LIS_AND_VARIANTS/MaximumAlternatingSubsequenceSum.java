/*
 🔹 Problem: 1911. Maximum Alternating Subsequence Sum
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Greedy, Subsequence
 🔹 Link: https://leetcode.com/problems/maximum-alternating-subsequence-sum/

 ------------------------------------------------------------

 🔸 Problem Statement:
 The alternating sum of a 0-indexed array is defined as:
   (sum of elements at even indices) - (sum of elements at odd indices)

 Given an array `nums`, return the **maximum alternating sum** of any subsequence
 (after reindexing the subsequence).

 A subsequence is formed by deleting some elements (possibly none) while maintaining order.

 ------------------------------------------------------------

 🔸 Example 1:
 Input: nums = [4,2,5,3]
 Output: 7
 Explanation:
   Choose subsequence [4,2,5] → alternating sum = (4 + 5) - 2 = 7.

 🔸 Example 2:
 Input: nums = [5,6,7,8]
 Output: 8
 Explanation:
   Choose subsequence [8].

 🔸 Example 3:
 Input: nums = [6,2,1,2,4,5]
 Output: 10
 Explanation:
   Choose subsequence [6,1,5] → alternating sum = (6 + 5) - 1 = 10.

 ------------------------------------------------------------

 🔸 Constraints:
  1 <= nums.length <= 10^5
  1 <= nums[i] <= 10^5

 ------------------------------------------------------------
 🔹 APPROACHES
 ------------------------------------------------------------

 🔸 Approach 1: Recursive DP with Memoization
 - Use recursion with index `i` and a state `add` (1 if we are adding, 0 if subtracting).
 - Try two options at each index:
     1. Take current element with alternating sign.
     2. Skip current element.
 - Cache results to avoid recomputation.

class Solution {
    public long maxAlternatingSum(int[] nums) {
        Long[][] dp = new Long[nums.length][2];
        return recurser(nums, 0, 1, dp);
    }

    private long recurser(int[] nums, int i, int add, Long[][] dp) {
        if (i == nums.length) return 0;
        if (dp[i][add] != null) return dp[i][add];

        long take = (add == 1 ? nums[i] : -nums[i]) + recurser(nums, i + 1, 1 - add, dp);
        long skip = recurser(nums, i + 1, add, dp);
        return dp[i][add] = Math.max(take, skip);
    }
}

 ------------------------------------------------------------

 🔸 Approach 2: Iterative DP (Tabulation)
 - Use a 2D DP table:
     dp[i][0] → max alternating sum ending at index i with even turn
     dp[i][1] → max alternating sum ending at index i with odd turn
 - Transition:
     dp[i][0] = max(dp[i-1][1] - nums[i], dp[i-1][0])
     dp[i][1] = max(dp[i-1][0] + nums[i], dp[i-1][1])

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[][] dp = new long[n][2];
        dp[0][1] = nums[0];
        dp[0][0] = 0;

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][1] - nums[i], dp[i-1][0]);
            dp[i][1] = Math.max(dp[i-1][0] + nums[i], dp[i-1][1]);
        }

        return Math.max(dp[n-1][0], dp[n-1][1]);
    }
}

 ------------------------------------------------------------

 🔸 Approach 3: Space Optimized DP (Best Approach)
 - Maintain only two variables:
     `even` → max alternating sum when the last index taken is even.
     `odd` → max alternating sum when the last index taken is odd.
 - Transition:
     even = max(even, odd - nums[i])
     odd = max(odd, even + nums[i])
 - This reduces space to O(1) while keeping O(n) time.

 ✅ Active Code (Final Optimal Implementation)
*/

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long even = 0;
        long odd = nums[0];
        for (int i = 1; i < n; i++) {
            even = Math.max(even, odd - nums[i]);
            odd = Math.max(odd, even + nums[i]);
        }
        return Math.max(even, odd);
    }
}

/*
 ------------------------------------------------------------
 🔹 Complexity:
   Time  → O(n)
   Space → O(1)

 🔹 Summary:
   - The problem can be reduced to tracking the best alternating sum sequence.
   - Greedy + DP merge elegantly in Approach 3.
   - Approach 3 is concise, efficient, and passes all constraints comfortably.
*/