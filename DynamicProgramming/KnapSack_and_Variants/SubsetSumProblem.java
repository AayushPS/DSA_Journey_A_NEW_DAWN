package DynamicProgramming.KnapSack_and_Variants;
/*
 🔹 Problem: Subset Sum Problem
 🔹 Platform: GFG
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Knapsack, Recursion
 🔹 Link: https://practice.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1

 ------------------------------------------------------------
 📝 Problem Statement:

Given an array of positive integers arr[] and an integer sum,
determine whether there exists a subset of arr[] whose sum is exactly equal to sum.

Each element can be taken at most once.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: arr = [3, 34, 4, 12, 5, 2], sum = 9
Output: true
Explanation: Subset {4, 3, 2} sums to 9.

Example 2:
Input: arr = [3, 34, 4, 12, 5, 2], sum = 30
Output: false

Example 3:
Input: arr = [1, 2, 3], sum = 6
Output: true

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ arr.length ≤ 200
 • 1 ≤ arr[i] ≤ 200
 • 1 ≤ sum ≤ 10⁴

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Decide whether any subset adds up exactly to `sum`.

📍 **Approach 1 (Top-Down DP + Memoization)**
   - Recursive include/exclude choice.
   - Memoization on (n, sum).
   - Easy to reason, but recursion overhead.

📍 **Approach 2 (Bottom-Up DP – 2D Table)**
   - Classic 0/1 Knapsack DP.
   - dp[i][j] = possible using first i elements to form sum j.
   - Clear but uses O(n × sum) space.

📍 **Approach 3 (Bottom-Up DP – 1D Rolling Array)**
   - Optimizes space by keeping only previous row.
   - O(sum) space.

📍 **Approach 4 (Bottom-Up DP – Two Arrays Reused ✅ Most Optimal)**
   - Uses two boolean arrays reused across iterations.
   - Clean, iterative, no recursion.
   - Best balance of clarity and space efficiency.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented – Recursion + Memoization)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(n × sum)

   🧠 Key Insight:
   Each element can either be included or excluded.

 ------------------------------------------------------------
*/

/*
class Solution {
    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;
        Boolean[][] dp = new Boolean[sum + 1][n + 1];
        return subsetSum(sum, n, dp, arr);
    }

    private static Boolean subsetSum(int sum, int n, Boolean[][] dp, int[] arr) {
        if (sum == 0) return true;
        if (n == 0) return false;
        if (dp[sum][n] != null) return dp[sum][n];

        if (arr[n - 1] <= sum) {
            return dp[sum][n] =
                subsetSum(sum - arr[n - 1], n - 1, dp, arr) ||
                subsetSum(sum, n - 1, dp, arr);
        }
        return dp[sum][n] = subsetSum(sum, n - 1, dp, arr);
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented – Bottom-Up DP Table)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(n × sum)
 ------------------------------------------------------------

class Solution {
    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (arr[i - 1] <= j)
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[n][sum];
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 3 (Commented – 1D Rolling DP)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(sum)
 ------------------------------------------------------------

class Solution {
    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;
        boolean[] old = new boolean[sum + 1];
        old[0] = true;

        for (int i = 1; i <= n; i++) {
            boolean[] now = new boolean[sum + 1];
            now[0] = true;
            for (int j = 1; j <= sum; j++) {
                if (arr[i - 1] <= j)
                    now[j] = old[j] || old[j - arr[i - 1]];
                else
                    now[j] = old[j];
            }
            old = now;
        }
        return old[sum];
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 4 (✅ Bottom-Up DP with Reused Arrays – Most Optimal)
   ⏱️ Time Complexity: O(n × sum)
   💾 Space Complexity: O(sum)

   🧠 Key Insight:
   Only the previous DP state is required at each step.

   💡 Why it works:
   Subset sum follows 0/1 knapsack logic — state depends only on previous row.

 ------------------------------------------------------------
*/

import java.util.*;

public class SubsetSumProblem {

    static Boolean isSubsetSum(int arr[], int sum) {

        int n = arr.length;
        boolean[] prev = new boolean[sum + 1];
        boolean[] curr = new boolean[sum + 1];

        prev[0] = true;

        for (int i = 1; i <= n; i++) {
            Arrays.fill(curr, false);
            curr[0] = true;

            for (int j = 1; j <= sum; j++) {
                if (arr[i - 1] <= j) {
                    curr[j] = prev[j] || prev[j - arr[i - 1]];
                } else {
                    curr[j] = prev[j];
                }
            }

            boolean[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[sum];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

arr = [3, 34, 4, 12, 5, 2], sum = 9

Valid subset:
4 + 3 + 2 = 9

Final Answer: true ✅

 ------------------------------------------------------------
*/
