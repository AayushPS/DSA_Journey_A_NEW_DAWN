/*
 🔹 Problem: 961. N-Repeated Element in Size 2N Array
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Array, Hash Table
 🔹 Link: https://leetcode.com/problems/n-repeated-element-in-size-2n-array/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an integer array nums with the following properties:
• nums.length == 2 * n
• nums contains n + 1 unique elements
• Exactly one element of nums is repeated n times

Return the element that is repeated n times.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [1,2,3,3]
Output: 3

Example 2:
Input: nums = [2,1,2,5,3,2]
Output: 2

Example 3:
Input: nums = [5,1,5,2,5,3,5,4]
Output: 5

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 <= n <= 5000
 • nums.length == 2 * n
 • 0 <= nums[i] <= 10^4
 • nums contains n + 1 unique elements and one of them is repeated exactly n times

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the element that appears exactly n times in an array of size 2n.

📍 **Approach 1 (Frequency Counter):**
   - Use HashMap to count frequency of each element
   - Return element with frequency == n
   - Time: O(n), Space: O(n)
   - Straightforward but uses extra space for all elements

📍 **Approach 2 (Hash Set - Early Detection - Optimized):**
   - Use HashSet to track seen elements
   - First duplicate encountered must be the n-repeated element
   - Time: O(n), Space: O(n) worst case but typically much less
   - More efficient as it stops immediately upon finding duplicate
   - Since one element appears n times and array has 2n elements,
     the repeated element MUST appear again before we see all n+1 unique elements

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Hash Set - Early Detection)
   ⏱️ Time Complexity: O(n) - single pass through array, early termination possible
   💾 Space Complexity: O(n) - HashSet can store up to n elements in worst case
   
   🧠 **Key Insight:**
   Since the array has size 2n and contains n+1 unique elements, with one element
   appearing n times, we're guaranteed to encounter a duplicate early. The first
   duplicate we find MUST be the n-repeated element because all other elements
   appear at most once.
   
   💡 **Why it works:**
   - Array size: 2n
   - Unique elements: n+1
   - One element appears n times, all others appear once
   - Total: n (repeated) + (n+1-1) (others appearing once) = 2n ✓
   - As we iterate, we track seen elements in a HashSet
   - The FIRST time we encounter a duplicate, it's the n-repeated element
   - This is because only one element repeats, and it repeats n times
   - No need to count frequencies - first duplicate is guaranteed to be the answer
   - Early termination makes this very efficient in practice
 ------------------------------------------------------------
*/

import java.util.HashSet;

public class NRepeatedElement {
    public int repeatedNTimes(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        
        for(int i : nums) {
            // If we've seen this element before, it's the answer
            if(set.contains(i)) {
                return i;
            }
            // Mark this element as seen
            set.add(i);
        }
        
        return -1;  // Should never reach here given problem constraints
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: nums = [2,1,2,5,3,2]
n = 3 (array length = 6 = 2*3)

Iteration process:

Step 1: i = 2
- set.contains(2) = false
- set.add(2) → set = {2}

Step 2: i = 1
- set.contains(1) = false
- set.add(1) → set = {2, 1}

Step 3: i = 2
- set.contains(2) = true ✓
- Return 2

Result: 2 ✅

Explanation:
We found the duplicate on the 3rd iteration. The element 2 appears 3 times (n=3)
in the array, while all other elements (1, 5, 3) appear exactly once.

💡 Note: We didn't need to see all occurrences of 2 or even traverse the entire
array. The first duplicate is always the answer!

 ------------------------------------------------------------
 🔍 Another Example:

Input: nums = [5,1,5,2,5,3,5,4]
n = 4 (array length = 8 = 2*4)

Step 1: i = 5 → set = {5}
Step 2: i = 1 → set = {5, 1}
Step 3: i = 5 → set.contains(5) = true ✓ → Return 5

Result: 5 ✅

Even though 5 appears 4 times total, we only needed to iterate 3 elements
to find our answer!

 ------------------------------------------------------------
 🔍 Edge Case Example:

Input: nums = [1,2,3,3]
n = 2 (array length = 4 = 2*2)

Step 1: i = 1 → set = {1}
Step 2: i = 2 → set = {1, 2}
Step 3: i = 3 → set = {1, 2, 3}
Step 4: i = 3 → set.contains(3) = true ✓ → Return 3

Result: 3 ✅

Worst case scenario where we need to see all unique elements before finding
the duplicate, but still O(n) time and we stop immediately after.

⚡ Performance Analysis:
This HashSet approach is optimal for the given constraints:
- Handles arrays up to size 10,000 (n = 5000, 2n = 10,000)
- Average case: finds answer in first n+1 iterations (often much sooner)
- Worst case: O(2n) = O(n) time when duplicate is at the end
- Space: O(n) for storing up to n unique elements in HashSet
- HashSet operations (contains, add) are O(1) average case
- Early termination makes this extremely fast in practice
- For the maximum constraint (n = 5000), this completes in milliseconds
 ------------------------------------------------------------
*/