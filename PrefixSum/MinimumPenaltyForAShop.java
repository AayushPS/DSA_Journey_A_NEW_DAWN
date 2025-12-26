package PrefixSum;
/*
 🔹 Problem: 2483. Minimum Penalty for a Shop
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Greedy, Prefix Sum
 🔹 Link: https://leetcode.com/problems/minimum-penalty-for-a-shop/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a string customers consisting of characters:
'Y' → customers come at that hour
'N' → no customers come at that hour

If the shop closes at hour j:
• Every open hour with no customers adds +1 penalty
• Every closed hour with customers adds +1 penalty

Return the earliest hour to close the shop that minimizes penalty.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "YYNY"
Output: 2

Example 2:
Input: "NNNNN"
Output: 0

Example 3:
Input: "YYYY"
Output: 4

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ customers.length ≤ 100000
 • customers consists only of 'Y' and 'N'

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Find the earliest closing hour with minimum penalty.

📍 Approach 1: Prefix Arrays (Open / Close Count)
- Track penalties for staying open vs closing
- Compute total penalty for each closing hour
- Works but uses extra space

📍 Approach 2 (✅ Optimized): Greedy Scoring
- Treat 'Y' as +1 (benefit to stay open)
- Treat 'N' as -1 (penalty to stay open)
- Find prefix with maximum score
- Closing right after that index minimizes penalty

Why optimal:
- Time: O(n)
- Space: O(1)
- Elegant greedy transformation

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Prefix Count)
   ⏱️ Time Complexity: O(n)
   💾 Space Complexity: O(n)
 ------------------------------------------------------------

 // class Solution {
 //     public int bestClosingTime(String customers) {
 //         int n = customers.length();
 //         int[] open = new int[n];
 //         int[] close = new int[n];
 //
 //         if (customers.charAt(0) == 'Y') {
 //             open[0] = 0;
 //             close[0] = 1;
 //         } else {
 //             open[0] = 1;
 //             close[0] = 0;
 //         }
 //
 //         for (int i = 1; i < n; i++) {
 //             if (customers.charAt(i) == 'Y') {
 //                 open[i] = open[i - 1];
 //                 close[i] = close[i - 1] + 1;
 //             } else {
 //                 open[i] = open[i - 1] + 1;
 //                 close[i] = close[i - 1];
 //             }
 //         }
 //
 //         int minPenalty = close[n - 1];
 //         int idx = 0;
 //
 //         for (int i = 0; i < n; i++) {
 //             int penalty = open[i] + close[n - 1] - close[i];
 //             if (penalty < minPenalty) {
 //                 minPenalty = penalty;
 //                 idx = i + 1;
 //             }
 //         }
 //
 //         return idx;
 //     }
 // }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Greedy – Most Optimal)
   ⏱️ Time Complexity: O(n)
   💾 Space Complexity: O(1)
 ------------------------------------------------------------
*/

public class MinimumPenaltyForAShop {

    public int bestClosingTime(String customers) {
        int score = 0;
        int maxScore = 0;
        int bestTime = -1;

        for (int i = 0; i < customers.length(); i++) {
            score += customers.charAt(i) == 'Y' ? 1 : -1;
            if (score > maxScore) {
                maxScore = score;
                bestTime = i;
            }
        }

        return bestTime + 1;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

customers = "YYNY"

Score progression:
Y → +1 (best at 0)
Y → +2 (best at 1)
N → +1
Y → +2

Max score at index 1 → close at 2

Answer: 2 ✅
 ------------------------------------------------------------
*/
