/*
 🔹 Problem: 3573. Best Time to Buy and Sell Stock V
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Dynamic Programming, State Machine, Stock Trading
 🔹 Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-v/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an integer array prices where prices[i] is the stock price on day i,
and an integer k representing the maximum number of transactions allowed.

Each transaction can be:
  • Normal transaction: buy → sell (profit = sell - buy)
  • Short selling transaction: sell → buy back (profit = sell - buy back)

Rules:
  • At most k transactions total
  • Transactions cannot overlap
  • No buying and selling on the same day

Return the maximum total profit.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: prices = [1,7,9,8,2], k = 2
Output: 14

Example 2:
Input: prices = [12,16,19,19,8,1,19,13,9], k = 3
Output: 36

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ prices.length ≤ 1000
 • 1 ≤ prices[i] ≤ 1e9
 • 1 ≤ k ≤ prices.length / 2

 ------------------------------------------------------------
 📌 Approach Summary:

We model the problem as a **state-machine DP** with 3 states:

State 0 → Neutral (no open transaction)  
State 1 → Holding stock after BUY (normal transaction)  
State 2 → Holding short position after SELL (short transaction)

Let:
dp[state][t] = maximum profit from current day onward
               with `t` transactions already used.

Transitions:
- From Neutral:
    • Buy → state 1
    • Sell → state 2
    • Skip
- From Buy-hold:
    • Sell → consume 1 transaction → back to Neutral
    • Skip
- From Short-hold:
    • Buy back → consume 1 transaction → back to Neutral
    • Skip

We optimize space by keeping only:
- `next[state][t]` → day i+1
- `curr[state][t]` → day i

 ------------------------------------------------------------
 🔹 Approach 1 (Commented – Top-down DP + Memoization)
   ⏱️ Time: O(n × k)
   💾 Space: O(n × k)

------------------------------------------------------------
*/

/*
class Solution {
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[3][n][k+1];
        return dfs(0, 0, n, k, prices, dp);
    }

    private long dfs(int state, int i, int n, int k, int[] prices, long[][][] dp) {
        if (i == n || k == 0) {
            return state == 0 ? 0 : Integer.MIN_VALUE / 2;
        }
        if (dp[state][i][k] != 0) return dp[state][i][k];

        if (state == 0) {
            return dp[state][i][k] = Math.max(
                Math.max(
                    dfs(1, i+1, n, k, prices, dp) - prices[i],
                    dfs(2, i+1, n, k, prices, dp) + prices[i]
                ),
                dfs(0, i+1, n, k, prices, dp)
            );
        } else if (state == 1) {
            return dp[state][i][k] = Math.max(
                dfs(0, i+1, n, k-1, prices, dp) + prices[i],
                dfs(1, i+1, n, k, prices, dp)
            );
        } else {
            return dp[state][i][k] = Math.max(
                dfs(0, i+1, n, k-1, prices, dp) - prices[i],
                dfs(2, i+1, n, k, prices, dp)
            );
        }
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented – Bottom-up 3D DP)
   ⏱️ Time: O(n × k)
   💾 Space: O(n × k)
 ------------------------------------------------------------

class Solution {
    public long maximumProfit(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[3][n+1][k+1];

        for (int j = 0; j <= k; j++) {
            dp[0][n][j] = 0;
            dp[1][n][j] = dp[2][n][j] = Integer.MIN_VALUE / 2;
        }

        for (int i = n-1; i >= 0; i--) {
            for (int j = k-1; j >= 0; j--) {
                dp[0][i][j] = Math.max(
                    Math.max(dp[1][i+1][j] - prices[i],
                             dp[2][i+1][j] + prices[i]),
                    dp[0][i+1][j]
                );
                dp[1][i][j] = Math.max(
                    dp[0][i+1][j+1] + prices[i],
                    dp[1][i+1][j]
                );
                dp[2][i][j] = Math.max(
                    dp[0][i+1][j+1] - prices[i],
                    dp[2][i+1][j]
                );
            }
        }
        return dp[0][0][0];
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Space-Optimized Bottom-up DP – Most Optimal)
   ⏱️ Time Complexity: O(n × k)
   💾 Space Complexity: O(k)

   🧠 Key Insight:
   Only next-day DP states are required → compress day dimension.

   💡 Why it works:
   State transitions depend solely on day i+1, not earlier days.

 ------------------------------------------------------------
*/

public class BestTimeToBuyAndSellStockV {

    public long maximumProfit(int[] prices, int k) {

        int n = prices.length;
        long[][] next = new long[3][k + 1];
        long[][] curr = new long[3][k + 1];

        // Base case initialization
        for (int j = 0; j <= k; j++) {
            next[0][j] = 0;
            next[1][j] = Integer.MIN_VALUE / 2;
            next[2][j] = Integer.MIN_VALUE / 2;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = k - 1; j >= 0; j--) {

                // Neutral state
                curr[0][j] = Math.max(
                    Math.max(
                        next[1][j] - prices[i], // buy
                        next[2][j] + prices[i]  // short sell
                    ),
                    next[0][j]                 // skip
                );

                // Holding after buy
                curr[1][j] = Math.max(
                    next[0][j + 1] + prices[i], // sell
                    next[1][j]                  // hold
                );

                // Holding after short sell
                curr[2][j] = Math.max(
                    next[0][j + 1] - prices[i], // buy back
                    next[2][j]                  // hold
                );
            }

            long[][] temp = next;
            next = curr;
            curr = temp;
        }

        return next[0][0];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

prices = [1,7,9,8,2], k = 2

Transactions:
  Buy 1 → Sell 9  (profit = 8)
  Short 8 → Buy 2 (profit = 6)

Total = 14

 ------------------------------------------------------------
*/
