/*
 🔹 Problem: 3512. Minimum Operations to Make Array Sum Divisible by K
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Math, Modular Arithmetic
 🔹 Link: https://leetcode.com/problems/minimum-operations-to-make-array-sum-divisible-by-k/

 ------------------------------------------------------------
 🔸 Problem:

Given an integer array nums and an integer k, an operation allows selecting  
any index i and replacing nums[i] with nums[i] − 1.

Determine the minimum number of operations needed so that the total sum  
of the array becomes divisible by k.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
nums = [3,9,7], k = 5  
Output: 4

Example 2:
nums = [4,1,3], k = 4  
Output: 0

Example 3:
nums = [3,2], k = 6  
Output: 5

 ------------------------------------------------------------
 🔸 Constraints:

• 1 <= nums.length <= 1000  
• 1 <= nums[i] <= 1000  
• 1 <= k <= 100  

 ------------------------------------------------------------
 🔹 Approach: Modular Adjustment of Total Sum

Let S be the sum of all elements in nums.

Goal:  
Find the smallest x ≥ 0 such that:

    (S - x) % k = 0

Because each operation decreases the array sum by exactly 1, the answer  
equals:

    S % k

Rationale:

• If S % k = r, it takes exactly r decrements to reach the nearest  
  smaller multiple of k.  
• No other operation provides a better reduction because all operations  
  decrease the total by 1 only.

Time Complexity:  O(n) for summing, O(1) for result  
Space Complexity: O(1)

 ------------------------------------------------------------
*/

import java.util.Arrays;

public class MinimumOperationsToMakeArraySumDivisibleByK {
    public int minOperations(int[] nums, int k) {
        return Arrays.stream(nums).sum() % k;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
