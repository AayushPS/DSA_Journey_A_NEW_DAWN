/*
 🔹 Problem: 646. Maximum Length of Pair Chain
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Sorting, Greedy
 🔹 Link: https://leetcode.com/problems/maximum-length-of-pair-chain/

 ------------------------------------------------------------
 🔸 Problem Statement:

You are given an array of n pairs `pairs` where pairs[i] = [lefti, righti] 
and lefti < righti.

A pair p2 = [c, d] follows a pair p1 = [a, b] if b < c.  
A chain of pairs can be formed in this fashion.

Return the **length of the longest chain** that can be formed.

You do not need to use all pairs.  
Pairs can be chosen and ordered freely.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: pairs = [[1,2],[2,3],[3,4]]
Output: 2
Explanation: The longest chain is [1,2] → [3,4].

Example 2:
Input: pairs = [[1,2],[7,8],[4,5]]
Output: 3
Explanation: The longest chain is [1,2] → [4,5] → [7,8].

 ------------------------------------------------------------
 🔸 Constraints:
 • n == pairs.length
 • 1 <= n <= 1000
 • -1000 <= lefti < righti <= 1000

 ------------------------------------------------------------
 🔹 Approach Summary:

✅ This problem is an adaptation of **Longest Increasing Subsequence (LIS)**,  
   where the condition `pairs[j][1] < pairs[i][0]` replaces `nums[j] < nums[i]`.

✅ **Core Idea:**
   - Sort pairs by their first element.
   - For each pair, find the maximum chain length ending at it.
   - Either include the pair (if it follows previous) or skip it.

✅ We can solve this using:
   1. Recursive + Memoization DP (O(N²))
   2. Iterative DP (Bottom-up) — cleaner and efficient.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Recursive + Memoization)
   - Sort pairs by start.
   - Explore including or excluding each pair.
   - Memoize results in a 2D DP array.
   - Time Complexity: O(N²)
   - Space Complexity: O(N²)
 ------------------------------------------------------------

// import java.util.*;
// class Solution {
//     public int findLongestChain(int[][] pairs) {
//         int n = pairs.length;
//         Arrays.sort(pairs, Comparator.comparingInt(i -> i[0]));
//         int[][] dp = new int[n][n + 1];
//         return lis(0, -1, pairs, dp);
//     }
//
//     private int lis(int i, int prev, int[][] pairs, int[][] dp) {
//         if (i == pairs.length) return 0;
//         if (dp[i][prev + 1] != 0) return dp[i][prev + 1];
//
//         int take = 0;
//         if (prev == -1 || pairs[prev][1] < pairs[i][0])
//             take = 1 + lis(i + 1, i, pairs, dp);
//
//         int notTake = lis(i + 1, prev, pairs, dp);
//         return dp[i][prev + 1] = Math.max(take, notTake);
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (✅ Iterative DP - Clean & Efficient)
   - Sort pairs by first element.
   - For each pair i, find longest valid chain ending at i.
   - dp[i] = max(dp[j] + 1) for all j < i where pairs[j][1] < pairs[i][0].
   - Keep track of overall maximum.
   - Time Complexity: O(N²)
   - Space Complexity: O(N)
 ------------------------------------------------------------
*/
import java.util.*;

public class MaximumLengthOfPairChain {
    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        Arrays.sort(pairs, Comparator.comparingInt(i -> i[0]));
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int max = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[j][1] < pairs[i][0]) {
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

pairs = [[1,2],[2,3],[3,4]]

After sorting: [[1,2],[2,3],[3,4]]

dp = [1,1,1]
i=1 → j=0 → [1,2] → [2,3] invalid (2 !< 2)
i=2 → j=0 → 2<3 → dp[2]=2
Final dp = [1,1,2]
Answer = 2 ✅
 ------------------------------------------------------------
*/
