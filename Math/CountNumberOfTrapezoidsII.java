package Math;
/*
 🔹 Problem: 3625. Count Number of Trapezoids II
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Geometry, Combinatorics, Hashing, Slopes
 🔹 Link: https://leetcode.com/problems/count-number-of-trapezoids-ii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array `points`, where points[i] = [xi, yi] represents a point in 2D.

A trapezoid is a convex quadrilateral with at least one pair of parallel sides.
Two lines are parallel iff they have the same slope.

Return the number of unique trapezoids formed by choosing any 4 distinct points.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]
Output: 2

Example 2:
Input: points = [[0,0],[1,0],[0,1],[2,1]]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 4 ≤ points.length ≤ 500
 • -1000 ≤ xi, yi ≤ 1000
 • All points are pairwise distinct.

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal: Count all convex quadrilaterals having at least one pair of parallel sides (trapezoids).

📍 Approach 1 (Slope + Line Grouping + Inclusion-Exclusion - Most Optimal):

   Core idea:
   - Any trapezoid is determined by two distinct, parallel segments (two lines with same slope but different intercepts).
   - We work in terms of lines formed by all point pairs.

   Steps:
   1. For each point pair (i, j), compute:
      - Direction vector (dx, dy) normalized to a canonical slope (sx, sy) using gcd and sign normalization.
      - Line "offset" / intercept-like term `des = sx * yi - sy * xi` to group lines with same slope but different positions.
   2. Maintain:
      - `t`: groups by normalized slope (sx, sy) → then by des → count of segments/lines.
      - `v`: same, but keyed by (dx, dy) to correct overcounting of coincident / overlapping structures.
   3. For each slope-group:
      - For each set of lines with same slope:
        - Suppose counts for intercepts are c1, c2, ..., ck.
        - Each pair of distinct lines from this slope-group is a candidate side-pair:
              total_pairs = sum over i<j ( ci * cj )
   4. Use `count(t)` to count all parallel-line-pairs that can define trapezoid side pairs.
   5. Subtract degenerate/non-convex or overcounted configurations using `count(v) / 2`.
      This is essentially an inclusion-exclusion correction based on more precise grouping with (dx, dy).

   Complexity:
   - O(n²) pairs of points.
   - Hashing and counting over slopes / intercepts: O(n²) expected.
   - Works within n ≤ 500.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Slope Hashing + Inclusion-Exclusion — Most Optimal)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n²) in worst case

   🧠 Key Insight:
   - A trapezoid is built from two distinct parallel lines.
   - Lines are defined by all point-pairs; group them by (slope, intercept) and count how many ways to choose line-pairs.
   - Use a correction term (`v`) to exclude invalid / overcounted cases.

   💡 Why it works:
   - Explicit enumeration of all lines lets you correctly account for all possible side-pairs.
   - Combinatorial counting over grouped lines avoids O(n⁴) brute-force over all quadruples of points.

 ------------------------------------------------------------
*/

import java.util.HashMap;

public class CountNumberOfTrapezoidsII {

    public int countTrapezoids(int[][] points) {
        // t: keyed by normalized slope -> (intercept-like value -> count of segments)
        HashMap<Integer, HashMap<Integer, Integer>> t = new HashMap<>();
        // v: keyed by raw (dx, dy) slope key -> (intercept-like value -> count), for correction
        HashMap<Integer, HashMap<Integer, Integer>> v = new HashMap<>();

        int n = points.length;

        for (int i = 0; i < n; i++) {
            int xi = points[i][0];
            int yi = points[i][1];
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - xi;
                int dy = points[j][1] - yi;

                // Normalize direction so (dx, dy) has a canonical sign
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }

                int g = gcd(dx, Math.abs(dy));
                int sx = dx / g;
                int sy = dy / g;

                // "des" groups lines by position (similar to intercept in integer form)
                int des = sx * yi - sy * xi;

                // compressed keys to use in HashMap (fit ranges with shift + offset)
                int keySlopeNorm = (sx << 12) | (sy + 2000);
                int keySlopeRaw  = (dx << 12) | (dy + 2000);

                t.computeIfAbsent(keySlopeNorm, k -> new HashMap<>())
                 .merge(des, 1, Integer::sum);

                v.computeIfAbsent(keySlopeRaw, k -> new HashMap<>())
                 .merge(des, 1, Integer::sum);
            }
        }

        // total trapezoids = all trapezoid candidates - degenerate/overcounted ones
        return count(t) - count(v) / 2;
    }

    // Count sum over groups: for each slope, for each intercept group counts ci,
    // we add sum over i<j (ci * cj)
    private int count(HashMap<Integer, HashMap<Integer, Integer>> map) {
        long ans = 0L;

        for (HashMap<Integer, Integer> inner : map.values()) {
            long sum = 0L;
            for (int val : inner.values()) sum += val;

            for (int val : inner.values()) {
                sum -= val;
                ans += (long) val * sum;
            }
        }

        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (High-level):

points = [[0,0],[1,0],[0,1],[2,1]]

All segments form lines:
 - Horizontal y=0
 - Horizontal y=1
 - Slanted line between (0,0)-(0,1)
 - Slanted lines between other pairs

t-grouping (by normalized slope):
   horizontal slope (1,0):
     y=0: count c1
     y=1: count c2
   vertical slope (0,1):
     x=0: count c3
   etc.

Valid trapezoids arise from choosing two distinct lines with same slope
and correct convex configuration; inclusion-exclusion via t and v gives
the final count = 1 for this input.

 ------------------------------------------------------------
*/
