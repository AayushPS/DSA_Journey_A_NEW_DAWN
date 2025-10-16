/*
 🔹 Problem: 2598. Smallest Missing Non-negative Integer After Operations
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Arrays, Math, Hashing, Modulo Arithmetic
 🔹 Link: https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/

 🔸 Problem Statement:
 You are given a 0-indexed integer array `nums` and an integer `value`.

 In one operation, you can add or subtract `value` from any element of `nums`.
 The MEX (Minimum Excluded Value) of an array is the smallest missing non-negative integer.

 The task is to find the maximum possible MEX of the array after performing any number of such operations.

 🔸 Example 1:
 Input: nums = [1,-10,7,13,6,8], value = 5
 Output: 4
 Explanation:
   - Add value to nums[1] twice → [1,0,7,13,6,8]
   - Subtract value from nums[2] once → [1,0,2,13,6,8]
   - Subtract value from nums[3] twice → [1,0,2,3,6,8]
   MEX = 4 (maximum possible)

 🔸 Example 2:
 Input: nums = [1,-10,7,13,6,8], value = 7
 Output: 2

 🔸 Constraints:
  1 <= nums.length, value <= 10^5
  -10^9 <= nums[i] <= 10^9

 ------------------------------------------------------------

 🔹 Approach:
 1. Compute the modulo class of each element: (nums[i] % value + value) % value
 2. Maintain frequency of each remainder modulo `value`.
 3. Incrementally check from 0 upwards:
    - If we can still form this number using available elements of that remainder, decrement frequency.
    - Else, that number is the smallest missing integer (MEX).
 4. The result is the first i where we can't assign any element of its modulo class.

 🔹 Complexity:
   Time  → O(n)
   Space → O(value)

 ------------------------------------------------------------

 🔹 Implementation:
*/

class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] freqArr = new int[value];
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            freqArr[((nums[i] % value) + value) % value]++;
        }
        int i = 0;
        while (true) {
            int mod = i % value;
            if (freqArr[mod] == 0) return i;
            freqArr[mod]--;
            i++;
        }
    }
}

/*
 🔹 Summary:
   - Converts problem to modular bucket counting.
   - Each modulo bucket can cover that many integers in its sequence.
   - The first number without a bucket element determines the final MEX.
*/