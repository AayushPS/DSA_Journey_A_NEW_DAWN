/*
───────────────────────────────────────────────────────────────
📘 Problem: 2926. Maximum Balanced Subsequence Sum
💡 Difficulty: Hard
🧠 Topics: Dynamic Programming, Binary Search, TreeMap, Optimization
🔗 Link: https://leetcode.com/problems/maximum-balanced-subsequence-sum/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are given a 0-indexed integer array `nums`.

A subsequence of `nums` having length `k` and consisting of indices 
i₀ < i₁ < ... < iₖ₋₁ is **balanced** if the following holds:

    nums[iⱼ] - nums[iⱼ₋₁] >= iⱼ - iⱼ₋₁   for every j in [1, k - 1].

A subsequence of length 1 is considered balanced.

Return the **maximum possible sum** of elements in a balanced subsequence of `nums`.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ nums.length ≤ 10⁵  
• -10⁹ ≤ nums[i] ≤ 10⁹
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥉 Approach 1 — Recursive + Memoization (Top-Down DP)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

We treat the problem like a variant of LIS (Longest Increasing Subsequence),
but with a custom constraint:

    nums[i] - nums[j] >= i - j

At each index, we decide:
1️⃣ Include `nums[i]` in our subsequence if the condition holds relative to previous element.
2️⃣ Or skip it and move ahead.

We memoize on (index, prevIndex) to avoid recomputation.

⚠️ This approach gives correct results but is **too slow (O(n²))** 
for large arrays (n up to 10⁵).

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n²)
• Space: O(n²)
───────────────────────────────────────────────────────────────
*/

 /*
class Solution {
    public long maxBalancedSubsequenceSum(int[] nums) {
        long[][] dp = new long[nums.length][nums.length + 1];
        for (long[] a : dp) Arrays.fill(a, Long.MIN_VALUE);

        long res = lis(0, -1, nums, dp);

        // Handle case when all values are negative
        if (res == 0) {
            res = Integer.MIN_VALUE;
            for (int n : nums) {
                res = Math.max(res, n);
            }
        }

        return res;
    }

    private long lis(int i, int prev, int[] nums, long[][] dp) {
        if (i == nums.length) return 0;
        if (dp[i][prev + 1] != Long.MIN_VALUE) return dp[i][prev + 1];

        // Option 1: Take current element if it satisfies balance condition
        long takes = prev == -1 || nums[i] - i >= nums[prev] - prev
                     ? nums[i] + lis(i + 1, i, nums, dp)
                     : Long.MIN_VALUE;

        // Option 2: Skip current element
        long notTakes = lis(i + 1, prev, nums, dp);

        return dp[i][prev + 1] = Math.max(takes, notTakes);
    }
}
*/


/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — Iterative DP (Bottom-Up)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

We compute dp[i] = maximum balanced subsequence sum ending at index i.

Transition:
    For each j < i,
    if nums[i] - i >= nums[j] - j  →  
        dp[i] = max(dp[i], nums[i] + dp[j])

We keep track of the global max while updating dp[i].

⚠️ Still O(n²), so not efficient for large input, 
but conceptually simpler than recursion.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n²)
• Space: O(n)
───────────────────────────────────────────────────────────────
*/

 /*
class Solution {
    public long maxBalancedSubsequenceSum(int[] nums) {
        int n = nums.length;
        long[] dp = new long[n];
        long max = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            dp[i] = nums[i];
            for (int j = 0; j < i; j++) {
                if (nums[i] - i >= nums[j] - j) {
                    dp[i] = Math.max(dp[i], nums[i] + dp[j]);
                }
            }
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach 3 — Optimized Using TreeMap (Efficient)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

To make the transition efficient, we note:
The condition `nums[i] - i >= nums[j] - j` depends only on `(nums[x] - x)`.

We can store pairs `(key = nums[j] - j, value = dp[j])` in a TreeMap.

For each index `i`:
1️⃣ Compute keyNow = nums[i] - i.  
2️⃣ Find the best subsequence ending at any j ≤ i satisfying key ≤ keyNow  
   → this is done using `floorEntry(keyNow)`.  
3️⃣ Update dp[i] = max(nums[i], nums[i] + bestPrev).  
4️⃣ Maintain map to remove dominated entries (smaller sums for larger keys).

This gives a **monotonic structure** for efficient DP transition.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n log n) — each insertion / query / cleanup is log n.
• Space: O(n)
───────────────────────────────────────────────────────────────
✅ Final Optimal Solution.
───────────────────────────────────────────────────────────────
*/

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class MaximumBalancedSubsequenceSum {
    public long maxBalancedSubsequenceSum(int[] nums) {
        int n = nums.length;
        long max = Long.MIN_VALUE + 1;
        TreeMap<Integer, Long> map = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            // For negative or zero values, handle directly
            if (nums[i] <= 0) {
                max = Math.max(max, nums[i]);
                continue;
            }

            int keyNow = nums[i] - i;
            Map.Entry<Integer, Long> entry = map.floorEntry(keyNow);

            long valToPut = nums[i];
            if (entry != null) {
                valToPut = Math.max(valToPut, entry.getValue() + nums[i]);
            }

            // If same key exists with better value, skip update
            Long existing = map.get(keyNow);
            if (existing != null && existing >= valToPut) continue;

            // Insert/Update current entry
            map.put(keyNow, valToPut);
            max = Math.max(max, valToPut);

            // Remove all dominated entries (future keys with lower value)
            Iterator<Map.Entry<Integer, Long>> it = map.tailMap(keyNow, false).entrySet().iterator();
            while (it.hasNext() && it.next().getValue() < valToPut) {
                it.remove();
            }
        }

        return max;
    }
}


/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Approach 1 — Recursive + Memoization: 
• Time: O(n²)
• Space: O(n²)

Approach 2 — Iterative DP:
• Time: O(n²)
• Space: O(n)

Approach 3 — TreeMap Optimization:
• Time: O(n log n)
• Space: O(n)

✅ Final Choice → Approach 3 (TreeMap-based DP)
───────────────────────────────────────────────────────────────
*/
