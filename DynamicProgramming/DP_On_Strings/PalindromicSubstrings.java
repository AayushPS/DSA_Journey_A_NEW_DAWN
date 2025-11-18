package DP_On_Strings;

/*
───────────────────────────────────────────────────────────────
📘 Problem: 647. Palindromic Substrings
💡 Difficulty: Medium
🧠 Topics: String, Dynamic Programming, Expand Around Center
🔗 Link: https://leetcode.com/problems/palindromic-substrings/
───────────────────────────────────────────────────────────────

Given a string `s`, return the total number of palindromic substrings.

A substring is palindromic if it reads the same forwards and backwards.
Single characters always count as palindromes.

───────────────────────────────────────────────────────────────
🧠 Key Idea (High-level)
Every single index can be the **center** of a palindrome.

You can detect palindromes using:
1. Top-down recursion with memoization  
2. Expand-around-center  
3. Bottom-up DP  

All three methods correctly count palindromic substrings.

───────────────────────────────────────────────────────────────
🥇 Approach 1 — Recursive + Memoization (Top-Down DP)
───────────────────────────────────────────────────────────────
💡 Idea:
- Use `dp[i][j]` = whether substring `s[i..j]` is palindrome.
- Recursively shrink from edges inward.
- Memoize results to avoid repeated checks.

🧮 Complexity:
- Time: O(n²)
- Space: O(n²) for memo

(LEFT AS COMMENTED REFERENCE — NOT ACTIVE)
*/

/*
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        Boolean[][] dp = new Boolean[n][n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                count += check(s, i, j, dp) ? 1 : 0;
            }
        }
        return count;
    }

    private boolean check(String s, int i, int j, Boolean[][] dp) {
        if (j < i) return true;
        if (dp[i][j] != null) return dp[i][j];
        if (s.charAt(i) == s.charAt(j))
            return dp[i][j] = check(s, i + 1, j - 1, dp);
        return dp[i][j] = false;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — Expand Around Center (Most Intuitive)
───────────────────────────────────────────────────────────────
💡 Idea:
- Every palindrome is centered at:
    • A single index (odd length)  
    • A pair of indices (even length)
- Expand outward while characters match.

🧮 Complexity:
- Time: O(n²)
- Space: O(1)

(LEFT AS COMMENTED REFERENCE — NOT ACTIVE)
*/

/*
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        int count = n;

        for (int i = 0; i < n; i++) {

            // Odd length palindromes
            int a = i, b = i;
            while (a - 1 >= 0 && b + 1 < n && s.charAt(a - 1) == s.charAt(b + 1)) {
                a--;
                b++;
                count++;
            }

            // Even length palindromes
            a = i;
            b = i + 1;
            while (a >= 0 && b < n && s.charAt(a) == s.charAt(b)) {
                a--;
                b++;
                count++;
            }
        }
        return count;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥉 Approach 3 — Bottom-Up DP (Tabulation) ✔ (UNCOMMENTED)
───────────────────────────────────────────────────────────────
💡 Idea:
Let dp[i][j] = true if s[i..j] is palindrome.

We fill the table by increasing substring length:
- length = 1 → always palindrome  
- length = 2 → check equality  
- length ≥ 3 → s[i]==s[j] && dp[i+1][j-1]

Every time dp[i][j] is true → count++.

───────────────────────────────────────────────────────────────
🧮 Complexity:
Time: O(n²)  
Space: O(n²)  

───────────────────────────────────────────────────────────────
💻 Code (Active Solution)
───────────────────────────────────────────────────────────────
*/

public class PalindromicSubstrings {
    public int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n+1][n+1];
        int count = 0;

        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (i == j) {
                    dp[i][j] = true;
                }
                else if (j == i + 1) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                }
                else {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i+1][j-1];
                }

                if (dp[i][j]) count++;
            }
        }
        return count;
    }
}

/*
───────────────────────────────────────────────────────────────
🧪 Dry Run Example
───────────────────────────────────────────────────────────────
Input: "aaa"

Substrings:
i j   substring   palindrome?
0 0     "a"          ✓
0 1     "aa"         ✓
0 2     "aaa"        ✓
1 1     "a"          ✓
1 2     "aa"         ✓
2 2     "a"          ✓

Total = 6

───────────────────────────────────────────────────────────────
🎯 Final Notes:
• All three approaches are valid O(n²) solutions.  
• Approach 3 (DP) is included as the final uncommented version.  
• The file is GitHub-ready, structured, and consistent with your styles.

───────────────────────────────────────────────────────────────
*/
