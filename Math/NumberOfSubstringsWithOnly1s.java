package Math;

/*
───────────────────────────────────────────────────────────────
📘 Problem: 1513. Number of Substrings With Only 1s
💡 Difficulty: Medium
🧠 Topics: Strings, Math, Counting, Sliding Window
🔗 Link: https://leetcode.com/problems/number-of-substrings-with-only-1s/
───────────────────────────────────────────────────────────────

You are given a binary string s.  
Return the number of substrings that consist ONLY of character '1'.

A substring of consecutive '1's of length L contributes:
        L * (L + 1) / 2
to the total count, because:
    • There are L substrings of length 1
    • L-1 substrings of length 2
    • …
    • 1 substring of length L

Since the answer may be large, return it modulo 1e9 + 7.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ s.length ≤ 100000  
• s[i] ∈ { '0', '1' }  
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach (Optimal) — Count Consecutive '1' Blocks
───────────────────────────────────────────────────────────────

🧠 Core idea:
Traverse the string, and whenever you encounter a block of consecutive '1's:
   Example:  "1111"
   Length = 4 → contributes 4⋅5/2 = 10 substrings

Algorithm:
1️⃣ Scan using pointer i  
2️⃣ Maintain a counter `count1` for consecutive '1's  
3️⃣ When you hit a '0' or end of string, compute contribution via:
        count1 * (count1 + 1) / 2  
   Add to result  
4️⃣ Reset count1 to 0 and continue  

This is O(n) with O(1) extra memory — absolutely optimal.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time:   O(n)  
• Space:  O(1)
───────────────────────────────────────────────────────────────
*/

public class NumberOfSubstringsWithOnly1s {
    private static final int MOD = 1_000_000_007;

    public int numSub(String s) {
        long ans = 0;
        int n = s.length();
        int count1 = 0;

        for (int i = 0; i < n; i++) {

            if (s.charAt(i) == '1') {
                // extend the block of consecutive ones
                count1++;
            } else {
                // block ended — compute contribution
                ans = (ans + (long) count1 * (count1 + 1) / 2) % MOD;
                count1 = 0;
            }
        }

        // final block (if string ends with '1')
        ans = (ans + (long) count1 * (count1 + 1) / 2) % MOD;

        return (int) ans;
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Dry Run Example (ASCII Explanation)
───────────────────────────────────────────────────────────────

Input:
    s = "0110111"

Break into blocks of '1's:

    0 11 0 111
      ↑     ↑
     L=2   L=3

Block contributions:
 • "11"   → 2⋅3/2 = 3
 • "111"  → 3⋅4/2 = 6

Total = 3 + 6 = 9

───────────────────────────────────────────────────────────────
🧩 Another Example

Input:
    s = "101"

Blocks:
 "1" , "1"
Each contributes 1

Total = 2

───────────────────────────────────────────────────────────────
*/

