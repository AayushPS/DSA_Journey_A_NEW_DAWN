/*
 🔹 Problem: 3583. Count Special Triplets
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Hashing, Frequency Counting, Combinatorics
 🔹 Link: https://leetcode.com/problems/count-special-triplets/

 ------------------------------------------------------------
 📝 Problem Statement:

A *special triplet* is a triplet of indices (i, j, k) such that:

    0 ≤ i < j < k < n  
    nums[i] = nums[j] * 2  
    nums[k] = nums[j] * 2  

You must count all such triplets.

Return the answer modulo 1e9+7.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [6,3,6]
Output: 1  
Valid triplet: (0,1,2)

Example 2:
Input: nums = [0,1,0,0]
Output: 1  
Valid triplet: (0,2,3)

Example 3:
Input: nums = [8,4,2,8,4]
Output: 2  
Valid triplets: (0,1,3), (1,2,4)

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 3 ≤ n ≤ 100,000  
 • 0 ≤ nums[i] ≤ 100,000  

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count (i, j, k) with i < j < k such that:
        nums[i] = 2 * nums[j] AND nums[k] = 2 * nums[j]

📍 **Approach 1 (Two Hash Maps: Left & Right Frequencies — Most Optimal)**

Key Idea:
- As we sweep from left to right, treat current index j as the *middle* element.
- `prev` = frequencies of elements to the right of j.  
- `next` = frequencies of elements to the left of j.

For each nums[j]:
- Required: nums[i] = 2 * nums[j] and nums[k] = 2 * nums[j]
    → look up:
        p = count of nums[k] = 2 * nums[j] in prev  
        n = count of nums[i] = 2 * nums[j] in next  
- Contribution to answer = p * n

Update maps:
- Decrement prev count for nums[j]
- Increment next count for nums[j]

Why optimal?
- One pass with O(1) hashmap lookups.
- O(n) time, O(n) space.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Left/Right Hash Counting — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
     For each position j, the number of valid i choices × number of valid k choices gives total triplets.

   💡 Why it works:
     Order is naturally enforced by separating frequencies into "left" and "right".

 ------------------------------------------------------------
*/

import java.util.HashMap;
import java.util.Map;

public class CountSpecialTriplets {

    public int specialTriplets(int[] nums) {
        Map<Integer, Integer> right = new HashMap<>();
        Map<Integer, Integer> left = new HashMap<>();

        long MOD = 1_000_000_007;
        long count = 0;

        // Initialize right frequency map
        for (int x : nums) {
            right.put(x, right.getOrDefault(x, 0) + 1);
        }

        // Process each j
        for (int x : nums) {
            // remove current from right side
            right.put(x, right.get(x) - 1);

            int need = x * 2;
            int r = right.getOrDefault(need, 0); // possible k positions
            int l = left.getOrDefault(need, 0);  // possible i positions

            count = (count + (long) r * l % MOD) % MOD;

            // add current to left side
            left.put(x, left.getOrDefault(x, 0) + 1);
        }

        return (int) count;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (nums = [6, 3, 6]):

Initial:
 right = {6:2, 3:1}, left = {}

j = 6:
  right[6]-- → right becomes {6:1, 3:1}
  need = 12 → no matches → +0
  left[6]++

j = 3:
  right[3]-- → right becomes {6:1, 3:0}
  need = 6 → r = 1 (right), l = 1 (left)
  count += 1 × 1 = 1
  left[3]++

j = 6:
  right[6]-- → right = {6:0, 3:0}
  need = 12 → no matches

Final answer = 1

 ------------------------------------------------------------
*/
