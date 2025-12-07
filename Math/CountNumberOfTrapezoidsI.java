package Math;
/*
 🔹 Problem: 3623. Count Number of Trapezoids I
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Geometry, Hashing, Combinatorics
 🔹 Link: https://leetcode.com/problems/count-number-of-trapezoids-i/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given `points`, where each point has coordinates (x, y).

A *horizontal trapezoid* is a convex quadrilateral that has at least one pair of
horizontal sides — meaning two vertices share the same y-value and another two
vertices share some (possibly different) y-value.

Horizontal sides ↔ points having identical y-coordinates.

We must count all **unique horizontal trapezoids** formed by selecting any 4 distinct points.

Return the result modulo 1e9 + 7.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [[1,0],[2,0],[3,0],[2,2],[3,2]]
Output: 3

Example 2:
Input: [[0,0],[1,0],[0,1],[2,1]]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 4 ≤ points.length ≤ 100000  
 • −1e8 ≤ xi, yi ≤ 1e8  
 • All points are distinct.

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count all sets of 4 points forming a horizontal trapezoid.

📍 **Approach 1 (Hash by Y-Level + Combinatorics - Most Optimal):**
   - A horizontal trapezoid requires:
       * Choose 2 points from some horizontal level y₁  
       * Choose 2 points from another horizontal level y₂ (y₁ ≠ y₂)  
   - If a horizontal level has `k` points → C(k, 2) possible segments.
   - Let f[y] = number of pairs from level y.
   - Total trapezoids = Σ over pairs of levels ( f[y₁] * f[y₂] ).

   Efficient computation:
   - Extract all C(cnt[y], 2) values into an array.
   - Compute pairwise sum using prefix accumulation for O(m) time.

   **Time Complexity:** O(n)  
   **Space Complexity:** O(m) where m = number of distinct y-coordinates.

   **Why optimal?**
   - Avoids O(m²) pairwise iteration.
   - Uses prefix sums to compute cross-products efficiently.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Hash + Combinatorics - Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   Any horizontal trapezoid is fully determined by **choosing 2 horizontal levels**
   and picking **2 points** from each of those levels.

   💡 Why it works:
   Counts are independent; combinations multiply cleanly.
 ------------------------------------------------------------
*/

import java.util.HashMap;
import java.util.Map;

public class CountNumberOfTrapezoidsI {

    public int countTrapezoids(int[][] points) {
        Map<Integer, Integer> map = new HashMap<>();

        // Count points per y-coordinate
        for (int[] p : points) {
            map.put(p[1], map.getOrDefault(p[1], 0) + 1);
        }

        long MOD = 1_000_000_007;
        long ans = 0;

        long[] arr = new long[map.size()];
        int idx = 0;

        // Compute C(k, 2) for each y-level
        for (int count : map.values()) {
            arr[idx++] = combinations(count) % MOD;
        }

        // prefix accumulate pairwise contributions
        long prefix = arr[0];
        for (int i = 1; i < arr.length; i++) {
            ans = (ans + (arr[i] * prefix) % MOD) % MOD;
            prefix = (prefix + arr[i]) % MOD;
        }

        return (int) ans;
    }

    private static long combinations(int k) {
        return (long) k * (k - 1) / 2;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

points:
y = 0 → [1,0], [2,0], [3,0] → C(3,2)=3  
y = 2 → [2,2], [3,2] → C(2,2)=1  

arr = [3, 1]

prefix process:
prefix = 3
i=1 → ans += 1 * 3 = 3

Result = 3

 ------------------------------------------------------------
*/
