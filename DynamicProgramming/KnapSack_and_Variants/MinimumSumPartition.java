/*
 🔹 Problem: Minimum Sum Partition
 🔹 Platform: GeeksForGeeks
 🔹 Difficulty: Hard
 🔹 Topics: Dynamic Programming, Subset Sum
 🔹 Link: https://www.geeksforgeeks.org/problems/minimum-sum-partition3317/1

 ------------------------------------------------------------
 📝 Problem Statement:

Given an array of non-negative integers, partition it into two subsets
such that the absolute difference between the sums of the two subsets
is minimized.

Return the minimum possible difference.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [1, 6, 11, 5]
Output: 1

Example 2:
Input: [1, 4]
Output: 3

Example 3:
Input: [1]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ arr.length × (sum of array elements) ≤ 100000
 • 1 ≤ arr[i] ≤ 100000

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Split the array into two subsets with minimum absolute sum difference.

📍 Approach:
- Let totalSum = sum of all elements
- Try to form a subset with sum as close as totalSum / 2 as possible
- Use Subset Sum DP to track achievable sums
- Minimize |totalSum − 2 × subsetSum|

Why optimal:
- Converts problem to classic subset-sum DP
- Guarantees minimum difference
- Efficient under given constraints

 ------------------------------------------------------------
*/

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Bottom-Up DP – Subset Sum)
   ⏱️ Time Complexity: O(n × totalSum)
   💾 Space Complexity: O(n × totalSum)
 ------------------------------------------------------------
*/

public class MinimumSumPartition {

    public int minDifference(int[] arr) {
        int n = arr.length;
        int sum = 0;

        for (int v : arr) {
            sum += v;
        }

        boolean[][] dp = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j) {
                    dp[i][j] |= dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int s1 = 0; s1 <= sum; s1++) {
            if (dp[n][s1]) {
                ans = Math.min(ans, Math.abs(sum - 2 * s1));
            }
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

arr = [1, 6, 11, 5]
Total sum = 23

Possible subset sums close to 23/2 = 11:
s1 = 11 → |23 − 2×11| = 1

Final Answer: 1 ✅
 ------------------------------------------------------------
*/
