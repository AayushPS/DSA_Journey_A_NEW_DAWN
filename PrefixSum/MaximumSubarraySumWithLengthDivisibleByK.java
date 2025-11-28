package PrefixSum;
/*
 🔹 Problem: 3381. Maximum Subarray Sum With Length Divisible by K
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Prefix Sum, Modular Arithmetic, Subarray Optimization
 🔹 Link: https://leetcode.com/problems/maximum-subarray-sum-with-length-divisible-by-k/

 ------------------------------------------------------------
 🔸 Problem Statement:

You are given an integer array nums and an integer k.

Your task is to find the maximum possible subarray sum such that  
the **length of the subarray is divisible by k**.

Return that maximum sum.  
Subarray may contain negative numbers.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input:  nums = [1,2], k = 1
Output: 3

Example 2:
Input:  nums = [-1,-2,-3,-4,-5], k = 4
Output: -10

Example 3:
Input:  nums = [-5,1,2,-3,4], k = 2
Output: 4

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= k <= nums.length <= 2×10⁵  
 • -10⁹ <= nums[i] <= 10⁹  

 ------------------------------------------------------------
 🔹 Approach Summary:

This problem requires selecting a subarray whose length is divisible by k.

Key idea:
If a subarray nums[l..r] has length divisible by k, then:

      (r - l + 1) % k == 0  
  →   (r+1) % k == l % k

So the length condition turns into a **prefix index modulo equality**.

Define prefix sum:
      P[i] = nums[0] + ... + nums[i-1]

Subarray sum from l to r:
      sum = P[r+1] - P[l]

For subarray length to be divisible by k:
      (r+1) % k == l % k

So for each index i, we need the **smallest prefix sum** seen so far with  
the same remainder `(i % k)`.

We maintain:
   minPrefix[rem] = smallest prefix sum P[j] where j % k == rem

Whenever we compute prefix sum at index i:
   candidate = prefix[i] - minPrefix[i % k]
   update answer
   update minPrefix[i % k]

This gives:
   • O(n) time  
   • O(k) space  

This is the optimal approach.

 ------------------------------------------------------------
 🔹 Final Working Code (Your Approach)

*/

import java.util.Arrays;

public class MaximumSubarraySumWithLengthDivisibleByK {
    public long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long[] minprefix = new long[k];
        Arrays.fill(minprefix, Long.MAX_VALUE);
        minprefix[0] = 0;

        long prefix = 0;
        long ans = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            prefix += nums[i];
            int r = (i + 1) % k;

            if (minprefix[r] != Long.MAX_VALUE)
                ans = Math.max(ans, prefix - minprefix[r]);

            minprefix[r] = Math.min(minprefix[r], prefix);
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/