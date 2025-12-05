/*
 🔹 Problem: 3432. Count Partitions with Even Sum Difference
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy / Math
 🔹 Topics: Prefix Sum, Parity, Array Processing
 🔹 Link: https://leetcode.com/problems/count-partitions-with-even-sum-difference/

 ------------------------------------------------------------
 📝 Problem Statement:

Given an integer array nums of length n, a partition index i (0 ≤ i < n - 1)
splits the array into:

 • Left  = nums[0..i]  
 • Right = nums[i+1..n-1]

We must count how many partitions produce an **even value** for:

      (sum(left) - sum(right))

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [10,10,3,7,6]
Output: 4

Example 2:
Input: [1,2,2]
Output: 0

Example 3:
Input: [2,4,6,8]
Output: 3

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ n ≤ 100  
 • 1 ≤ nums[i] ≤ 100

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count valid partition points where the difference of sums is even.

📍 **Approach 1 (Prefix Sum + Parity Check — Most Optimal)**

Key Observation:
Let:
  L = prefix sum up to index i  
  R = totalSum - L  

Difference:
  diff = L - R = L - (totalSum - L) = 2L - totalSum  

This is even **iff totalSum and L have the same parity**.

Implementation:
 - Compute totalSum  
 - Sweep through prefix sums L  
 - For each i < n-1, check if `(totalSum - 2*L)` is even  
 - Count valid cases  

Time Complexity: O(n)  
Space Complexity: O(1)

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Prefix Parity Check — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(1)

   🧠 Key Insight:
   A difference is even ⇔ both sum(left) and sum(right) have the same parity.

   💡 Why it works:
   Because `2L - totalSum` is even ⇔ L and totalSum share parity.
 ------------------------------------------------------------
*/

public class CountPartitionsWithEvenSumDifference {

    public int countPartitions(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;

        int prefix = 0;
        int ans = 0;
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            prefix += nums[i];
            if (((total - 2 * prefix) & 1) == 0) ans++;
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

nums = [10,10,3,7,6], total = 36

i=0: L=10 → diff = 36 - 2*10 = 16 → even → count  
i=1: L=20 → diff = 36 - 40 = -4 → even → count  
i=2: L=23 → diff = 36 - 46 = -10 → even → count  
i=3: L=30 → diff = 36 - 60 = -24 → even → count  

Total = 4

 ------------------------------------------------------------
*/

