package PrefixSum;

/*
 🔹 Problem: 1590. Make Sum Divisible by P
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Prefix Sum, Modular Arithmetic, Hash Map
 🔹 Link: https://leetcode.com/problems/make-sum-divisible-by-p/

 ------------------------------------------------------------
 🔸 Problem:

Given an array nums and an integer p, the goal is to remove the smallest  
contiguous subarray such that the sum of the remaining elements becomes  
divisible by p. Removing the entire array is not allowed.

Return the minimum length of such a subarray, or -1 if it is impossible.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
nums = [3,1,4,2], p = 6  
Output: 1

Example 2:
nums = [6,3,5,2], p = 9  
Output: 2

Example 3:
nums = [1,2,3], p = 3  
Output: 0

 ------------------------------------------------------------
 🔸 Constraints:

• 1 <= nums.length <= 100000  
• 1 <= nums[i] <= 1e9  
• 1 <= p <= 1e9  

 ------------------------------------------------------------
 🔹 Approach: Prefix Sum Mod Tracking Using Hash Map

Let S be the total sum of the array.  
If S % p = r and r = 0, no removal is needed.

Otherwise, a subarray with sum % p = r must be removed so that the  
remaining sum becomes divisible by p.

The goal is to find the shortest subarray with sum ≡ r (mod p).

Using prefix sums:

• Let prefix[i] = sum(nums[0..i])  
• For each index i, store prefix[i] % p in a map pointing to the index.  
• To find a matching previous prefix j that satisfies:

      (prefix[i] - prefix[j]) % p = r

  rearrange to:

      prefix[j] % p = (prefix[i] - r + p) % p

• Track the minimum window size i - j.

Edge conditions:
• The entire array cannot be removed.  
• If the best length equals n, return -1.  

Complexity:
• Time:  O(n)  
• Space: O(min(n, p))  

 ------------------------------------------------------------
*/

public class MakeSumDivisibleByP {
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;

        long total = 0;
        for (int x : nums) total += x;

        int remainder = (int)(total % p);
        if (remainder == 0) return 0;

        long prefix = 0;
        int best = Integer.MAX_VALUE;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        for (int i = 0; i < n; i++) {
            prefix += nums[i];
            int curr = (int)(prefix % p);

            int target = (curr - remainder + p) % p;

            if (map.containsKey(target))
                best = Math.min(best, i - map.get(target));

            map.put(curr, i);
        }

        return best == n || best == Integer.MAX_VALUE ? -1 : best;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
