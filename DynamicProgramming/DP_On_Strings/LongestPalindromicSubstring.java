/*
───────────────────────────────────────────────────────────────
📘 Problem: 5. Longest Palindromic Substring
💡 Difficulty: Medium
🧠 Topics: String, Dynamic Programming, Expand Around Center
🔗 Link: https://leetcode.com/problems/longest-palindromic-substring/
───────────────────────────────────────────────────────────────

Given a string `s`, return the **longest palindromic substring**.

A palindrome reads the same forward and backward.

───────────────────────────────────────────────────────────────
🧠 Key Idea (High-Level)
There are two classical ways to find the longest palindromic substring:

1️⃣ **Dynamic Programming (Bottom-Up)**  
2️⃣ **Expand Around Center** ← Most intuitive & efficient in practice  

Both run in **O(n²)** time for `n ≤ 1000`.

───────────────────────────────────────────────────────────────
🥇 Approach 1 — Dynamic Programming (Bottom-Up)
───────────────────────────────────────────────────────────────
💡 Idea:
Use dp[i][j] = true if substring s[i..j] is a palindrome.

Rules:
- Single char → always palindrome  
- Two char → palindrome only if both are equal  
- Longer → s[i] == s[j] AND dp[i+1][j-1]  

Track the maximum length and starting index.

🧮 Complexity:
• Time: O(n²)  
• Space: O(n²)

(LEFT AS COMMENTED REFERENCE — NOT ACTIVE)
*/

/*
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int max = 1;
        int Mi = 0;

        // Single characters
        for (int i = 0; i < n; i++) dp[i][i] = true;

        // Length >= 2
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (len == 2) dp[i][j] = s.charAt(i) == s.charAt(j);
                else dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i+1][j-1];

                if (dp[i][j] && len > max) {
                    max = len;
                    Mi = i;
                }
            }
        }
        return s.substring(Mi, Mi + max);
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 2 — Expand Around Center ✔ (UNCOMMENTED)
───────────────────────────────────────────────────────────────
💡 Idea:
Each palindrome is centered at:
- A single index (odd length), or  
- A pair of indices (even length)

Expand outward while the characters match.

Track longest substring found.

🧮 Complexity:
• Time: O(n²)  
• Space: O(1)

───────────────────────────────────────────────────────────────
💻 Code (Active)
───────────────────────────────────────────────────────────────
*/

public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";

        String maxS = "";
        int max = 0;

        for (int i = 0; i < s.length(); i++) {

            // Odd length palindrome
            int a = i, b = i;
            while (a - 1 >= 0 && b + 1 < s.length() && s.charAt(a - 1) == s.charAt(b + 1)) {
                a--;
                b++;
            }
            if (b - a + 1 > max) {
                maxS = s.substring(a, b + 1);
                max = b - a + 1;
            }

            // Even length palindrome
            a = i;
            b = i + 1;
            while (a >= 0 && b < s.length() && s.charAt(a) == s.charAt(b)) {
                a--;
                b++;
            }
            if (b - a - 1 > max) {
                maxS = s.substring(a + 1, b);
                max = b - a - 1;
            }
        }

        return maxS;
    }
}

/*
───────────────────────────────────────────────────────────────
🧪 Dry Run Example
───────────────────────────────────────────────────────────────
Input: s = "babad"

Centers checked:
i = 0 : "b" → max = "b"
i = 1 : odd → "bab" → max = "bab"
i = 2 : odd → "aba" → same length valid alternative
i = 3 : "d"
i = 4 : "a"

Final output: "bab" (or "aba")

───────────────────────────────────────────────────────────────
🎯 Notes:
• Expand-around-center is optimal for interviews.
• DP approach is educational but uses more memory.
• The file is formatted exactly for GitHub documentation style.

───────────────────────────────────────────────────────────────
*/
