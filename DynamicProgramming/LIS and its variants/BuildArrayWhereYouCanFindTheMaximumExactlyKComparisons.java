/*
───────────────────────────────────────────────────────────────
📘 Problem: 1420. Build Array Where You Can Find The Maximum Exactly K Comparisons
💡 Difficulty: Hard
🧠 Topics: Dynamic Programming
🔗 Link: https://leetcode.com/problems/build-array-where-you-can-find-the-maximum-exactly-k-comparisons/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:
You are given three integers `n`, `m`, and `k`.

You must build an array `arr` of **exactly `n` integers** such that:
1. `1 <= arr[i] <= m` for all `0 <= i < n`.
2. After running the “search algorithm” to find the maximum,  
   the total **search cost** equals `k`.

Algorithm to find the maximum:
- Initialize `search_cost = 0` and `max_seen = 0`
- For each `arr[i]`:  
  - If `arr[i] > max_seen`:  
    - `max_seen = arr[i]`  
    - `search_cost++`

Return the **number of ways** to build such an array, modulo `10⁹ + 7`.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 <= n <= 50  
• 1 <= m <= 100  
• 0 <= k <= n
───────────────────────────────────────────────────────────────
*/

// NOTE: All approaches are presented in order (less optimal → more optimal).
// Only the final approach is active (uncommented). Others are retained for study.

// ───────────────────────────────────────────────────────────────
// 🥉 Approach 1: Recursive + Memoization (Top-down DFS)
// Detailed Breakdown:
// 1) State definition:
//    - i = current index (0..n)
//    - spent = number of times max has been updated so far (search cost used so far)
//    - max = current maximum value seen so far (0..m). Use 0 as "no element yet" sentinel.
//    - dp[i][spent][max] caches result for that state.
//
// 2) Recurrence / Transition:
//    For each possible value x in 1..m:
//      - if x > max: newSpent = spent + 1, newMax = x
//      - else:     newSpent = spent,     newMax = max
//      add ways = dfs(i+1, newSpent, newMax)
//
//    Base case:
//      - If i == n: return 1 if spent == k else 0
//
// 3) Complexity reasoning:
//    - There are O(n * k * m) distinct states (i, spent, max).
//    - For each state we loop up to m possible choices, so naive bound O(n * k * m²).
//    - With memoization, each state computed once: O(n * k * m²) time.
//    - Space: O(n * k * m) for memo + recursion stack O(n).
//
// 4) Why it works / correctness sketch:
//    - We enumerate all arrays by index, tracking the only information that matters for future decisions:
//      the amount of search cost already used, and the current maximum value. This is sufficient due to
//      the problem structure: only relative comparisons to the current max matter.
//
// 5) Edge-cases & notes:
//    - Use modulo at each addition.
//    - Using int dp with -1 sentinel for "unknown" is standard.
//
// Time Complexity: O(n * k * m²)
// Space Complexity: O(n * k * m)
/*
class Solution {
    private static final int MOD = 1_000_000_007;

    public int numOfArrays(int n, int m, int k) {
        int[][][] dp = new int[n][k+1][m+1];
        for (int[][] i : dp) for (int[] j : i) Arrays.fill(j, -1);
        return dfs(0, 0, 0, n, m, k, dp);
    }

    private int dfs(int i, int spent, int max, int n, int m, int k, int[][][] dp) {
        if (i == n) return spent == k ? 1 : 0;
        if (spent > k) return 0;
        if (dp[i][spent][max] != -1) return dp[i][spent][max];

        long ans = 0;
        for (int x = 1; x <= m; x++) {
            if (x > max) ans += dfs(i + 1, spent + 1, x, n, m, k, dp);
            else ans += dfs(i + 1, spent, max, n, m, k, dp);
            if (ans >= MOD) ans -= MOD;
        }
        return dp[i][spent][max] = (int) ans;
    }
}
*/

// ───────────────────────────────────────────────────────────────
// 🥈 Approach 2: Iterative 3D DP (Bottom-up) — direct conversion of recursion
// Detailed Breakdown:
// 1) State definition (same as above):
//    dp[i][spent][max] = number of arrays of length i with `spent` updates and current max = max.
//
// 2) Initialization:
//    dp[0][0][0] = 1  (empty array, cost 0, no max)
//
// 3) Transitions (iterate i from 0..n-1):
//    From dp[i][spent][max] = curWays:
//
//    - Case A: pick value <= max:
//       There are exactly `max` choices (1..max), each keeps spent the same and max the same.
//       dp[i+1][spent][max] += curWays * max
//
//    - Case B: pick value > max:
//       For each newMax = max+1 .. m, pick exactly value = newMax (one choice per newMax).
//       dp[i+1][spent+1][newMax] += curWays
//
// 4) Complexity reasoning:
//    - The inner loop iterates newMax from max+1..m giving O(m) cost per (i,spent,max).
//    - Total states O(n * k * m), inner O(m) ⇒ O(n * k * m²).
//
// 5) Why it works:
//    - Equivalent to DFS but computed level by level — exact same states, but iterative.
//
// 6) Practical notes:
//    - Avoid processing states with curWays == 0 to save time.
//    - Use long for multiplications then mod down.
//
// Time Complexity: O(n * k * m²)
// Space Complexity: O(n * k * m)
 /*
class Solution {
    private static final int MOD = 1_000_000_007;

    public int numOfArrays(int n, int m, int k) {
        int[][][] dp = new int[n+1][k+1][m+1];
        dp[0][0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int spent = 0; spent <= k; spent++) {
                for (int max = 0; max <= m; max++) {
                    int cur = dp[i][spent][max];
                    if (cur == 0) continue;

                    if (max > 0) {
                        long add = (long) cur * max % MOD;
                        dp[i+1][spent][max] = (int) ((dp[i+1][spent][max] + add) % MOD);
                    }

                    if (spent + 1 <= k) {
                        for (int newMax = max + 1; newMax <= m; newMax++) {
                            dp[i+1][spent+1][newMax] = (int) ((dp[i+1][spent+1][newMax] + cur) % MOD);
                        }
                    }
                }
            }
        }

        long ans = 0;
        for (int max = 1; max <= m; max++) ans = (ans + dp[n][k][max]) % MOD;
        return (int) ans;
    }
}
*/

// ───────────────────────────────────────────────────────────────
// 🥇 Approach 3: Space-Optimized DP (keep only prev/curr layers)
// Detailed Breakdown:
// 1) Observation: dp[i+1] depends only on dp[i]. So we only need two layers:
//    prev[spent][max] and curr[spent][max].
//
// 2) Transitions (same as approach 2) but use prev → curr swap.
//    - Case A (extend existing max): curr[spent][max] += prev[spent][max] * max
//    - Case B (introduce new max): for newMax=max+1..m: curr[spent+1][newMax] += prev[spent][max]
//
// 3) Complexity:
//    - Time still O(n * k * m²) because of inner newMax loop.
//    - Space drops to O(k * m).
//
// 4) Why it helps:
//    - Cuts memory drastically, making it feasible for larger n (but here constraints already modest).
//    - Simpler to code than maintaining full 3D.
//
// 5) Practical micro-optimizations:
//    - Skip prev states that are zero.
//    - Use modular arithmetic carefully.
//
// Time Complexity: O(n * k * m²)
// Space Complexity: O(k * m)
/*
class Solution {
    private static final int MOD = 1_000_000_007;

    public int numOfArrays(int n, int m, int k) {
        int[][] prev = new int[k+1][m+1];
        int[][] curr = new int[k+1][m+1];
        prev[0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int[] row : curr) Arrays.fill(row, 0);
            for (int spent = 0; spent <= k; spent++) {
                for (int max = 0; max <= m; max++) {
                    int val = prev[spent][max];
                    if (val == 0) continue;

                    if (max > 0) {
                        long add = (long) val * max % MOD;
                        curr[spent][max] = (int) ((curr[spent][max] + add) % MOD);
                    }

                    if (spent + 1 <= k) {
                        for (int newMax = max + 1; newMax <= m; newMax++) {
                            curr[spent + 1][newMax] = (int) ((curr[spent + 1][newMax] + val) % MOD);
                        }
                    }
                }
            }
            int[][] tmp = prev; prev = curr; curr = tmp;
        }

        long ans = 0;
        for (int max = 1; max <= m; max++) ans = (ans + prev[k][max]) % MOD;
        return (int) ans;
    }
}
*/

// ───────────────────────────────────────────────────────────────
// 🏆 Approach 4: Prefix-Sum Optimized DP (Final — active)
// Detailed Breakdown (deep):
//
// 1) Key bottleneck in previous approaches:
//    - For "introduce new max" we looped newMax from max+1..m, which costs O(m) per state,
//      giving O(m²) factor overall. We need to replace this inner loop with O(1) work.
//
// 2) Idea:
//    - For a fixed `spent` and `max`, when we consider values > max, each `newMax` receives
//      exactly `prev[spent][max]` ways (one for choosing that `newMax` at current position).
//    - So dp contribution to dp[i+1][spent+1][newMax] from all old `max` < newMax equals
//      sum_{oldMax = 0..newMax-1} prev[spent][oldMax].
//
//    - If we precompute prefix sums:
//         prefix[spent][y] = sum_{t=0..y} prev[spent][t]
//      then the contribution from oldMax in [0..newMax-1] is prefix[spent][newMax-1].
//
// 3) Concrete transition used:
//    - For extending existing max (choose value ≤ max): there are `max` choices → contributes
//        prev[spent][max] * max
//    - For forming new max (choose a value > old max): contributions are exactly
//        prefix[spent-1][max-1]  (this sums over all previous oldMax < max)
//
//    So for curr[spent][max] (i.e. after placing element i):
//      curr[spent][max] = prev[spent][max] * max + prefix[spent-1][max-1]
//
// 4) Initialization/details:
//    - prev[0][0] = 1  (no elements, no cost, no max)
//    - prefix[*][*] computed for every spent before using it to compute curr layer
//
// 5) Complexity:
//    - For each i (n iterations), for spent in [1..k], for max in [1..m] we do O(1) operations.
//    - Total time O(n * k * m). Space O(k * m).
//
// 6) Correctness sketch:
//    - prefix[spent-1][max-1] accounts for choosing any previous smaller max and picking a new value
//      equal to `max` at this position — that increments spent by 1 and sets new max to exactly `max`.
//    - prev[spent][max] * max accounts for picking any of the max choices that don't change the maximum.
//    - Combining both yields all possible arrays extended by one element.
//
// 7) Edge-cases:
//    - If k == 0 and n > 0 → impossible because every non-empty array must have at least one max found; our states handle this automatically.
//    - Make sure to handle m==1 (only max=1 possible).
//
// Time Complexity: O(n * k * m)
// Space Complexity: O(k * m)

import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int numOfArrays(int n, int m, int k) {
        int[][] prev = new int[k+1][m+1];
        int[][] curr = new int[k+1][m+1];
        int[][] prefix = new int[k+1][m+1];
        prev[0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int[] row : curr) Arrays.fill(row, 0);

            // Build prefix sums for prev layer
            for (int spent = 0; spent <= k; spent++) {
                prefix[spent][0] = prev[spent][0];
                for (int max = 1; max <= m; max++) {
                    prefix[spent][max] = (prefix[spent][max-1] + prev[spent][max]) % MOD;
                }
            }

            for (int spent = 1; spent <= k; spent++) {
                for (int max = 1; max <= m; max++) {
                    long extendSameMax = (long) prev[spent][max] * max % MOD;
                    long makeNewMax = prefix[spent-1][max-1];
                    curr[spent][max] = (int) ((extendSameMax + makeNewMax) % MOD);
                }
            }

            int[][] tmp = prev;
            prev = curr;
            curr = tmp;
        }

        long ans = 0;
        for (int max = 1; max <= m; max++) ans = (ans + prev[k][max]) % MOD;
        return (int) ans;
    }
}


// ───────────────────────────────────────────────────────────────
// ✅ Summary Table (final):
/*
| Approach | Key idea | Time Complexity | Space Complexity |
|----------|----------|-----------------|------------------|
| 1 Top-down | DFS with memo over (i,spent,max) | O(n * k * m²) | O(n * k * m) |
| 2 Bottom-up | Full 3D iteration over i,spent,max | O(n * k * m²) | O(n * k * m) |
| 3 Layered | Keep only prev/curr 2D layers | O(n * k * m²) | O(k * m) |
| 4 Prefix-sum ✅ | Replace inner m-loop by prefix sums → O(m) transition | O(n * k * m) | O(k * m) |
*/
