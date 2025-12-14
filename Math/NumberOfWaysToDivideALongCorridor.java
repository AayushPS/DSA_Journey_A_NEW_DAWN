package Math;

import java.util.ArrayDeque;

/*
 🔹 Problem: 2147. Number of Ways to Divide a Long Corridor
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Prefix Counting, Combinatorics, String Processing
 🔹 Link: https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a corridor string consisting of 'S' (seats) and 'P' (plants).  
Dividers already exist before index 0 and after index n−1.  
You may optionally place dividers between any i−1 and i.

You must divide the corridor into sections where:
  • Each section contains *exactly two seats*.
  • Sections may contain any number of plants.

Return how many distinct valid ways exist.  
Two ways differ if any divider placement differs.

Return result modulo 1e9+7.  
If impossible, return 0.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "SSPPSPS"
Output: 3

Example 2:
Input: "PPSPSP"
Output: 1

Example 3:
Input: "S"
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ n ≤ 100,000  
 • corridor[i] ∈ {'S','P'}

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count all valid partitions where each segment contains EXACTLY 2 seats.

Your approach identifies every seat-pair segment and then computes choices by measuring the plant-span between such segments.

📍 **Approach 1 (Two-pointer seat grouping + combinatoric multiplication — Most Optimal for this solution)**

Steps:
1. Move through corridor using two pointers `i` and `j` to detect pairs of seats.
2. When two seats are found, record segment as `[i, j]`.
3. After collecting all seat-pair segments:
   • If no complete pair exists → return 0  
   • If exactly one segment → return 1  
4. For each consecutive segment pair:
   • Compute choices = (next.start - prev.end)  
   • Multiply choices into total ways.
5. Return total modulo 1e9+7.

Time Complexity: O(n)  
Space Complexity: O(n)

 ------------------------------------------------------------
 🔹 Approach 1 (Two-pointer seat-pair detection — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
      Each valid section must have exactly 2 seats.  
      Choices arise from gaps between consecutive seat-pair segments.

   💡 Why it works (according to this approach):
      The distance between segments determines the number of valid divider placements.

 ------------------------------------------------------------
*/

public class NumberOfWaysToDivideALongCorridor {

    public int numberOfWays(String corridor) {

        int i = 0, j = 0, n = corridor.length();
        int count = 0;

        ArrayDeque<int[]> segments = new ArrayDeque<>();

        while (i < n && j < n) {
            if (count == 0) {
                if (corridor.charAt(i) == 'P') {
                    i++;
                } else {
                    count++;
                    j = i + 1;
                }
            }
            else if (count == 1) {
                if (corridor.charAt(j) == 'P') {
                    j++;
                } else {
                    count--;
                    segments.addLast(new int[]{i, j});
                    i = j + 1;
                }
            }
        }

        if (count == 1) return 0;
        if (segments.size() == 0) return 0;
        if (segments.size() == 1) return 1;

        long ways = 1;
        int MOD = 1_000_000_007;

        int[] prev = segments.pollFirst();

        while (!segments.isEmpty()) {
            int[] next = segments.peek();
            long endPrev = prev[1];
            long startNext = next[0];

            if (endPrev < startNext) {
                ways = (ways * (startNext - endPrev)) % MOD;
            }

            prev = segments.pollFirst();
        }

        return (int) ways;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

corridor = "SSPPSPS"

Detected segments:
 [0,1], [4,6]

Gap = (4 - 1) = 3  
ways = 3

 ------------------------------------------------------------
*/
