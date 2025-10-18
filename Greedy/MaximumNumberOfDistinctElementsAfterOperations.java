/*
 🔹 Problem: 3397. Maximum Number of Distinct Elements After Operations
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Arrays, Greedy, Sorting
 🔹 Link: https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/

 ------------------------------------------------------------
 🔸 Problem Statement:
 You are given an integer array `nums` and an integer `k`.

 You are allowed to perform the following operation on each element at most once:
 ➤ Add any integer within the range [-k, k] to that element.

 Return the **maximum number of distinct elements** that can be formed after all possible operations.

 ------------------------------------------------------------
 🔸 Example 1:
 Input: nums = [1,2,2,3,3,4], k = 2  
 Output: 6  
 Explanation:
   - Apply operations: [-1,0,1,2,3,4]
   - All six numbers become distinct.

 🔸 Example 2:
 Input: nums = [4,4,4,4], k = 1  
 Output: 3  
 Explanation:
   - Add -1 to nums[0], +1 to nums[1] → [3,5,4,4]
   - Maximum distinct elements = 3

 ------------------------------------------------------------
 🔸 Constraints:
  1 <= nums.length <= 10^5  
  1 <= nums[i] <= 10^9  
  0 <= k <= 10^9

 ------------------------------------------------------------
 🔹 Approach (Greedy + Sorting):

 1️⃣ Sort the array `nums` so that we can handle elements in increasing order.
 2️⃣ Maintain a variable `prev` representing the last chosen distinct value.
 3️⃣ For each element `i`:
     - If we can adjust it to stay greater than `prev` within the allowed range [-k, +k],
       we assign:  prev = max(prev + 1, i - k)
     - Count it as a distinct number.
 4️⃣ This ensures each element is pushed minimally forward to maintain uniqueness.

 ✅ Greedy works because we always pick the smallest possible valid distinct number
    for each sorted element, ensuring room for future elements.

 ------------------------------------------------------------
 🔸 Implementation:
*/

import java.util.*;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int prev = Integer.MIN_VALUE, dist = 0;
        for (int i : nums) {
            if (prev < i + k) {
                prev = Math.max(prev + 1, i - k);
                dist++;
            }
        }
        return dist;
    }
}

/*
 ------------------------------------------------------------
 🔹 Complexity:
   Time  → O(n log n)
   Space → O(1)

 🔹 Intuition Summary:
   - Sorting allows us to expand from smallest to largest.
   - Each element is greedily shifted to the nearest possible unique value.
   - This yields the maximum count of distinct numbers achievable under ±k constraints.
*/