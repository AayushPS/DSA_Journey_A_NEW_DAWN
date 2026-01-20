package BitManipulation;

/*
 🔹 Problem: 3315. Construct the Minimum Bitwise Array II
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Bit Manipulation, Greedy
 🔹 Link: https://leetcode.com/problems/construct-the-minimum-bitwise-array-ii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array nums consisting of n prime integers.

You need to construct an array ans of length n, such that for each index i:
ans[i] OR (ans[i] + 1) == nums[i]

Additionally, you must minimize each value of ans[i].

If it is not possible to find such a value, set ans[i] = -1.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [2, 3, 5, 7]
Output: [-1, 1, 4, 3]

Explanation:
i = 0 → No possible ans[0], so -1  
i = 1 → 1 OR 2 = 3  
i = 2 → 4 OR 5 = 5  
i = 3 → 3 OR 4 = 7  

Example 2:
Input: nums = [11, 13, 31]
Output: [9, 12, 15]

Explanation:
9 OR 10 = 11  
12 OR 13 = 13  
15 OR 16 = 31  

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ nums.length ≤ 100
 • 2 ≤ nums[i] ≤ 10^9
 • nums[i] is a prime number

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:**  
For each prime number y, find the smallest x such that:
x OR (x + 1) == y

📍 **Approach 1 (Bit Greedy Search):**
   - Try to identify the lowest bit position where flipping helps.
   - Construct x by unsetting a specific bit of y.
   - Return the minimal valid x.

This is the only approach used.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Bit Manipulation - Most Optimal)
   ⏱️ Time Complexity: O(n * log(max(nums)))
   💾 Space Complexity: O(1) extra per element

   🧠 **Key Insight:**
   For x OR (x + 1) to equal y, the transition point is the **lowest zero bit** in y.

   💡 **Why it works:**
   - Starting from k = 1, keep shifting left.
   - Find the first k such that (y | k) > y → this means k points to a zero bit.
   - Step back to previous bit (k >> 1).
   - Flip that bit in y using XOR to get minimal x.
 ------------------------------------------------------------
*/

// Import statements
import java.util.*;

public class ConstructMinimumBitwiseArray {

    public int[] minBitwiseArray(List<Integer> nums) {

        int n = nums.size();
        int[] arr = new int[n];

        int i = 0;

        // Process each number in the input list
        for (int a : nums) {

            // Special case: for 2, no valid answer exists
            if (a == 2) 
                arr[i++] = -1;

            // Otherwise compute the minimal valid x
            else 
                arr[i++] = obtain(a);
        }

        return arr;
    }

    // Finds the minimum x such that: x OR (x + 1) == y
    private int obtain(int y) {

        int k = 1;

        while (true) {

            // Find the first bit position where OR increases the value
            if ((y | k) > y) {

                // Step back to previous bit
                k >>= 1;

                // Flip that bit to get minimal x
                return y ^ k;
            }

            // Move to next bit
            k <<= 1;
        }
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: nums = [7]

Binary of 7 = 111

Start k = 1 (001)
(7 | 1) = 111 → not > 7 → shift

k = 2 (010)
(7 | 2) = 111 → not > 7 → shift

k = 4 (100)
(7 | 4) = 111 → not > 7 → shift

k = 8 (1000)
(7 | 8) = 1111 = 15 > 7 → found

k >>= 1 → k = 4

Return: 7 ^ 4 = 3

Check: 3 OR 4 = 7 ✅

Final Result: 3

💡 Note:
This method finds the lowest zero bit in y and flips the previous bit to minimize x.
 ------------------------------------------------------------
*/
