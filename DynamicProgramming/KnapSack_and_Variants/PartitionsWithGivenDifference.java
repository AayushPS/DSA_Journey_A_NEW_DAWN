/*
 🔹 Problem: Partitions with Given Difference
 🔹 Platform: GeeksForGeeks
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Subset Sum
 🔹 Link: https://www.geeksforgeeks.org/problems/partitions-with-given-difference/1

 ------------------------------------------------------------
 📝 Problem Statement:

Given an array of integers and a value diff, count the number of ways
to partition the array into two subsets S1 and S2 such that:

|sum(S1) − sum(S2)| = diff

Each element must belong to exactly one subset.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: arr = [5, 2, 6, 4], diff = 3
Output: 1

Example 2:
Input: arr = [1, 1, 1, 1], diff = 0
Output: 6

Example 3:
Input: arr = [3, 2, 7, 1], diff = 4
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ arr.length ≤ 50
 • 0 ≤ diff ≤ 50
 • 0 ≤ arr[i] ≤ 6

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Count the number of valid partitions whose subset sum difference
equals the given value.

📍 Mathematical Transformation:
Let:
S1 − S2 = diff  
S1 + S2 = totalSum  

⇒ S1 = (totalSum + diff) / 2

So the problem reduces to:
👉 Count subsets with sum = (totalSum − diff) / 2

📍 Approach (DP – Subset Count):
- Use DP to count number of subsets with target sum
- dp[i][j] = number of ways using first i elements to form sum j

Why optimal:
- Converts partition problem into subset-count DP
- Efficient under given constraints
 ------------------------------------------------------------
*/

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Bottom-Up DP – Count Subsets)
   ⏱️ Time Complexity: O(n × target)
   💾 Space Complexity: O(n × target)
 ------------------------------------------------------------
*/

public class PartitionsWithGivenDifference {

    public int countPartitions(int[] arr, int diff) {
        int n = arr.length;
        int sum = 0;

        for (int v : arr) {
            sum += v;
        }

        if (sum - diff < 0 || (sum - diff) % 2 != 0) {
            return 0;
        }

        int target = (sum - diff) / 2;

        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j) {
                    dp[i][j] += dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        return dp[n][target];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

arr = [1,1,1,1], diff = 0
Total sum = 4

Target = (4 − 0) / 2 = 2

Number of subsets with sum = 2:
C(4,2) = 6

Final Answer: 6 ✅
 ------------------------------------------------------------
*/
