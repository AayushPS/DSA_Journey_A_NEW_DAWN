package Math;

/*
 🔹 Problem: 1523. Count Odd Numbers in an Interval Range
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Math
 🔹 Link: https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/

 ------------------------------------------------------------
 📝 Problem Statement:

Given two integers `low` and `high`, return how many **odd numbers** exist in the inclusive range `[low, high]`.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: low = 3, high = 7
Output: 3
(odd numbers are 3, 5, 7)

Example 2:
Input: low = 8, high = 10
Output: 1
(odd number is 9)

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 0 ≤ low ≤ high ≤ 1e9

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count how many odd integers lie between two boundaries.

📍 **Approach 1 (Mathematical Range Adjustment — Most Optimal):**
   - If `low` is odd → extend range down by 1 (to make it even).
   - If `high` is odd → extend range up by 1 (to make it even).
   - Now the interval `[low, high]` starts and ends at even numbers.
   - The count of odd numbers inside becomes:

         (high - low) / 2

   - This works because every pair `(even, odd)` or `(odd, even)` increments the count by 1.

   **Time Complexity:** O(1)  
   **Space Complexity:** O(1)

   💡 **Why this is optimal:**  
   No looping, no parity handling inside the range, direct arithmetic.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Mathematical Parity Adjustment — Most Optimal)
   - Time Complexity: O(1)
   - Space Complexity: O(1)

   🧠 Key Insight:
     Expanding odd boundaries makes endpoints even → simplifies counting.

   💡 Why it works:
     Odd numbers alternate at every +1 step; half the interval length yields the count.

 ------------------------------------------------------------
*/

public class CountOddNumbersInAnIntervalRange {

    public int countOdds(int low, int high) {
        if (low % 2 != 0) low--;   // Make low even
        if (high % 2 != 0) high++; // Make high even
        return (high - low) / 2;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

low = 3, high = 7
low → 2
high → 8
(8 - 2) / 2 = 3

Odd numbers: 3, 5, 7

 ------------------------------------------------------------
*/
