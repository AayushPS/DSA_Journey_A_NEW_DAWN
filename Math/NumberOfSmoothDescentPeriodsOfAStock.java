package Math;
/*
 🔹 Problem: 2110. Number of Smooth Descent Periods of a Stock
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Math, Sliding Window
 🔹 Link: https://leetcode.com/problems/number-of-smooth-descent-periods-of-a-stock/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an integer array `prices`, where prices[i] represents the stock
price on the i-th day.

A **smooth descent period** is a contiguous subarray where:
 • The price decreases by exactly 1 each day.
 • A single day is always considered a valid smooth descent period.

Return the total number of smooth descent periods.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [3,2,1,4]
Output: 7

Example 2:
Input: [8,6,7,7]
Output: 4

Example 3:
Input: [1]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ prices.length ≤ 100,000
 • 1 ≤ prices[i] ≤ 100,000

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count all contiguous subarrays where prices decrease by exactly 1.

📍 **Approach 1 (Consecutive Segment Counting — Most Optimal)**

Key Idea:
- Track lengths of maximal contiguous segments where:
      prices[i] + 1 == prices[i - 1]
- If a segment has length `len`, it contributes:
      len * (len + 1) / 2
  smooth descent periods.
- Reset the segment when the condition breaks.

This avoids nested loops and processes the array in one pass.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Linear Scan + Arithmetic Series — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(1)

   🧠 Key Insight:
      Every valid descent segment contributes the sum of its subarray counts.

   💡 Why it works:
      Each extension by one valid day adds exactly `len` new subarrays.

 ------------------------------------------------------------
*/

public class NumberOfSmoothDescentPeriodsOfAStock {

    public long getDescentPeriods(int[] prices) {
        long count = 0;
        int n = prices.length;

        int len = 1;              // current descent segment length
        int prev = prices[0];     // previous day's price

        for (int i = 1; i < n; i++) {
            if (prices[i] + 1 != prev) {
                // close previous segment
                count += (long) len * (len + 1) / 2;
                len = 0;
            }
            len++;
            prev = prices[i];
        }

        // add final segment
        count += (long) len * (len + 1) / 2;

        return count;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

prices = [3,2,1,4]

Segments:
[3,2,1] → len = 3 → 3*4/2 = 6
[4]     → len = 1 → 1

Total = 7

 ------------------------------------------------------------
*/
