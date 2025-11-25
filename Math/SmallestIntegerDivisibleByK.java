package Math;
/*
 🔹 Problem: 1015. Smallest Integer Divisible by K
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Math, Modular Arithmetic
 🔹 Link: https://leetcode.com/problems/smallest-integer-divisible-by-k/

 ------------------------------------------------------------
 🔸 Problem Statement:

Given a positive integer k, return the length of the smallest positive integer n
such that:
    • n is divisible by k
    • n consists only of the digit '1'

If no such integer exists, return -1.

Note: n may be extremely large (beyond 64-bit), so you must work using modulo.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: k = 1
Output: 1

Example 2:
Input: k = 2
Output: -1

Example 3:
Input: k = 3
Output: 3

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= k <= 100000

 ------------------------------------------------------------
 🔹 Approach Summary:

We construct the number consisting only of 1's **digit-by-digit**, but never actually build it.

Let n represent the current number modulo k.

Start with:
    n = 1

For each additional digit:
    n = (n * 10 + 1) % k

Stop when:
    n == 0   → the constructed number is divisible by k

We iterate at most k times because modulo states repeat.

⚠ Quick elimination:
If k is divisible by 2 or 5 → impossible (since a number of all 1's cannot end in an even digit or 5)

This is the standard and optimal approach.

 ------------------------------------------------------------
 🔹 Final Working Code (Your Original Logic)

*/

class Solution {
    public int smallestRepunitDivByK(int k) {
        int n = 1;
        if (k % 2 == 0 || k % 5 == 0) return -1;
        if (n % k == 0) return n;

        for (int i = 1; i <= k; i++) {
            if (n == 0) return i;
            n = (n * 10 + 1) % k;
        }

        return -1;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
