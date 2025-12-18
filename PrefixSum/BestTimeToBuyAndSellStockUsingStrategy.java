package PrefixSum;

/*
 🔹 Problem: 3652. Best Time to Buy and Sell Stock using Strategy
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Prefix Sum, Sliding Window, Array
 🔹 Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-using-strategy/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given:
- prices[i]: stock price on day i
- strategy[i]: action on day i
    -1 → buy
     0 → hold
     1 → sell
- an even integer k

You may perform **at most one modification**:
- Choose exactly k consecutive days
- First k/2 days → set to HOLD (0)
- Last k/2 days → set to SELL (1)

Profit = Σ(strategy[i] × prices[i])

Return the maximum possible profit.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
prices   = [4,2,8]
strategy = [-1,0,1]
k = 2
Output = 10

Example 2:
prices   = [5,4,3]
strategy = [1,1,0]
k = 2
Output = 9

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ prices.length ≤ 1e5
 • 1 ≤ prices[i] ≤ 1e5
 • -1 ≤ strategy[i] ≤ 1
 • k is even

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize profit by optionally modifying one window of size k.

Key Observations:
- Original profit can be computed using prefix sums.
- Modification removes original contribution of the window.
- New contribution:
    - First k/2 → 0
    - Last k/2 → +prices[i]

Approach:
1. Compute prefix sum of original profit.
2. Compute prefix sum of prices.
3. Slide a window of size k.
4. For each window:
   - Remove original window contribution.
   - Add sum of prices in second half of window.
5. Track maximum.

 ------------------------------------------------------------
 🔹 Approach (✅ Prefix Sum + Sliding Window — Most Optimal)
   ⏱️ Time Complexity: O(n)
   💾 Space Complexity: O(n)

   🧠 Key Insight:
      Modification impact can be computed in O(1) using prefix sums.

   💡 Why it works:
      Only one window modification allowed → brute sliding window is enough.

 ------------------------------------------------------------
*/

public class BestTimeToBuyAndSellStockUsingStrategy {

    public long maxProfit(int[] prices, int[] strategy, int k) {

        int n = prices.length;

        long[] normal = new long[n];
        long[] prefixPrice = new long[n];

        // Prefix sum of original profit
        normal[0] = (long) prices[0] * strategy[0];
        for (int i = 1; i < n; i++) {
            normal[i] = normal[i - 1] + (long) prices[i] * strategy[i];
        }

        // Prefix sum of prices
        prefixPrice[0] = prices[0];
        for (int i = 1; i < n; i++) {
            prefixPrice[i] = prefixPrice[i - 1] + prices[i];
        }

        long maxProfit = normal[n - 1];
        int half = k / 2;

        int left = 0;
        int right = k - 1;

        while (right < n) {

            int mid = right - half;

            long leftProfit  = (left == 0) ? 0 : normal[left - 1];
            long rightProfit = (right == n - 1) ? 0 : (normal[n - 1] - normal[right]);

            long modifiedProfit = prefixPrice[right] - prefixPrice[mid];

            long total = leftProfit + modifiedProfit + rightProfit;
            maxProfit = Math.max(maxProfit, total);

            left++;
            right++;
        }

        return maxProfit;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

prices   = [4,2,8]
strategy = [-1,0,1]
k = 2

Original profit = 4

Window [0,1]:
- First half → 0
- Second half → sell day 1 → +2
- Plus existing sell at day 2 → +8
Total = 10

Final Answer = 10 ✅

 ------------------------------------------------------------
*/
