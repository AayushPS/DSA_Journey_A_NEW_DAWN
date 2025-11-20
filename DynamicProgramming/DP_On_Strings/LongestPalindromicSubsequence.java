/*
───────────────────────────────────────────────────────────────
📘 Problem: 516. Longest Palindromic Subsequence
💡 Difficulty: Medium
🧠 Topics: Dynamic Programming, String
🔗 Link: https://leetcode.com/problems/longest-palindromic-subsequence/
───────────────────────────────────────────────────────────────

Given a string `s`, return the **length of the longest palindromic subsequence (LPS)**.

A **subsequence** is NOT required to be contiguous — only order must be preserved.

───────────────────────────────────────────────────────────────
🧠 Core Concept (Very Important)
LPS of `s` is basically the **LCS between s and reverse(s)**,
but the classical DP formulation (dp[i][j]) is more intuitive:

Let dp[i][j] = length of LPS inside substring s[i…j].

Recurrence:
- If s[i] == s[j] → 2 + dp[i+1][j−1]
- Else → max(dp[i+1][j], dp[i][j−1])

───────────────────────────────────────────────────────────────
🥇 Approach 1 — Top-Down DP (Memoized Recursion)
───────────────────────────────────────────────────────────────
✔ Natural recursive definition  
✔ Simpler to think  
✘ Stack depth = O(n), overhead of recursion  
✘ Uses dp array + recursion stack

Time: O(n²)
Space: O(n²)

(LEFT AS COMMENTED REFERENCE ONLY)
*/

/*
class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        return lps(0, n - 1, s, dp);
    }

    private int lps(int i, int j, String s, int[][] dp) {
        if (i == j) return 1;
        if (i > j) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s.charAt(i) == s.charAt(j))
            return dp[i][j] = 2 + lps(i + 1, j - 1, s, dp);

        return dp[i][j] =
                Math.max(lps(i + 1, j, s, dp),
                         lps(i, j - 1, s, dp));
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 2 — Bottom-Up DP ✔ (UNCOMMENTED)
───────────────────────────────────────────────────────────────
Most stable & recommended for large input.

dp[i][j] = LPS inside substring s[i..j]
We fill table from smaller lengths → larger.

Base cases:
- all dp[i][i] = 1 (single char is palindrome)

Transition:
- If s[i] == s[j] → dp[i][j] = 2 + dp[i+1][j-1]
- Else → dp[i][j] = max(dp[i+1][j], dp[i][j-1])

Time: O(n²)
Space: O(n²)

───────────────────────────────────────────────────────────────
💻 Code (Active)
───────────────────────────────────────────────────────────────
*/

public class LongestPalindromicSubsequence {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];

        // Base case: single characters
        for (int i = 0; i < n; i++) dp[i][i] = 1;

        // Fill from smaller substrings to bigger ones
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i + L - 1 < n; i++) {
                int j = i + L - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                        dp[i + 1][j],
                        dp[i][j - 1]
                    );
                }
            }
        }

        return dp[0][n - 1];
    }
}

/*
───────────────────────────────────────────────────────────────
🧪 Dry Run Example (s = "bbbab")
───────────────────────────────────────────────────────────────

Indices:
 0 1 2 3 4
 b b b a b

dp table (simplified diagonal view):

Length-1 substrings:
dp[i][i] = 1

Length-2:
"bb" → 2  
"bb" → 2  
"ba" → 1  
"ab" → 1  

Length-3:
"bbb" → 3  
"bba" → 2  
"bab" → 3  

Length-4:
"bbba" → 3  
"bbab" → 3  

Length-5:
"bbbab" → 4  ← LPS = "bbbb"

Final answer = 4

───────────────────────────────────────────────────────────────
🎯 Notes:
• LPS is a **subsequence**, so unlike longest palindromic substring,
  it does NOT need to be contiguous.
• Classical exam/FAANG DP problem — MUST KNOW.
• Could also be solved using LCS(s, reverse(s)) — same complexity.

───────────────────────────────────────────────────────────────
*/
