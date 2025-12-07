package Miscellaneous;

import java.util.TreeMap;

/*
 🔹 Problem: 3578. Count Partitions With Max-Min Difference at Most K
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: DP, Sliding Window, Prefix Sum, Balanced BST (TreeMap)
 🔹 Link: https://leetcode.com/problems/count-partitions-with-max-min-difference-at-most-k/

 ------------------------------------------------------------
 📝 Problem Statement:

You must partition `nums` into one or more **non-empty contiguous segments** such that:

     max(segment) - min(segment) ≤ k

Return **the total number of valid partitions**, modulo 1e9+7.

A partition is a decomposition of the array into consecutive segments.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [9,4,1,3,7], k = 4
Output: 6

Example 2:
Input: nums = [3,3,4], k = 0
Output: 2

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ nums.length ≤ 50,000  
 • 1 ≤ nums[i] ≤ 1e9  
 • 0 ≤ k ≤ 1e9  

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count partitions where every segment's max–min ≤ k.

📍 **Approach 1 (Sliding Window + DP + Prefix Sum — Most Optimal)**

Key ideas:
- Maintain a sliding window `[left, i]` where max-min ≤ k.
- For each ending index `i`, valid partitions end at `i` but start at any position `j` such that:
    
      j ∈ [left … i]
    
- Let `dp[x]` = number of ways to partition first `x` elements.
- Then:

      dp[i+1] = sum(dp[j]) for j ∈ [left … i]

- Use prefix sums to compute the range sum in **O(1)**.
- Use a TreeMap to maintain window min/max efficiently.

Time Complexity: **O(n log n)**  
Space Complexity: **O(n)**

💡 Why it’s optimal:
- Sliding window ensures each index moves at most once.
- Prefix sums avoid inner loops.
- TreeMap keeps max and min dynamic without recomputation.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ DP + Sliding Window + Prefix Sum — Most Optimal)
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   The segment ending at i is valid only if the sliding window's max-min ≤ k.

   💡 Why it works:
   DP decomposes the problem; sliding window enforces constraints; prefix sums allow fast accumulation.

 ------------------------------------------------------------
*/

public class CountPartitionsWithMaxMinDifferenceAtMostK {

    private static final int MOD = 1_000_000_007;

    public int countPartitions(int[] nums, int k) {
        int n = nums.length;

        long[] dp = new long[n + 1];     // dp[i] = # partitions for nums[0..i-1]
        long[] prefix = new long[n + 1]; // prefix[i] = sum(dp[0..i])
        dp[0] = 1;
        prefix[0] = 1;

        TreeMap<Integer, Integer> freq = new TreeMap<>();
        int left = 0;

        for (int i = 0; i < n; i++) {

            // expand window
            freq.put(nums[i], freq.getOrDefault(nums[i], 0) + 1);

            // shrink window while invalid
            while (left <= i && freq.lastKey() - freq.firstKey() > k) {
                int count = freq.get(nums[left]);
                if (count == 1) freq.remove(nums[left]);
                else freq.put(nums[left], count - 1);
                left++;
            }

            // dp[i+1] = sum(dp[left..i])
            long sum = prefix[i] - (left > 0 ? prefix[left - 1] : 0);
            sum = (sum % MOD + MOD) % MOD;

            dp[i + 1] = sum;
            prefix[i + 1] = (prefix[i] + dp[i + 1]) % MOD;
        }

        return (int) dp[n];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (High-level):

nums = [9,4,1,3,7], k = 4

Sliding window maintains valid segments.
dp accumulates number of ways ending each index.
Final dp[n] = 6.

 ------------------------------------------------------------
*/
