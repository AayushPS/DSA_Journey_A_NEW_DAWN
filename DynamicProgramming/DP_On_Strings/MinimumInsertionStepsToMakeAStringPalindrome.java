/*
───────────────────────────────────────────────────────────────
📘 Problem: 1312. Minimum Insertion Steps to Make a String Palindrome
💡 Difficulty: Hard
🧠 Topics: DP on Strings, Palindromes, LPS relation
🔗 Link: https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
───────────────────────────────────────────────────────────────

You are allowed to insert ANY character anywhere in the string.
Find the **minimum number of insertions** required to make the string a palindrome.

───────────────────────────────────────────────────────────────
🧠 Key Fact (very important)
Minimum insertions to make `s` palindrome:

        = n - LPS(s)

Where LPS = Longest Palindromic Subsequence.

But here we implement the **classical DP on substring** approach.

We use:
dp[i][j] = minimum insertions to make s[i…j] palindrome.

Transition:
- If s[i] == s[j] → dp[i][j] = dp[i+1][j-1]
- Else → 1 + min(dp[i+1][j], dp[i][j-1])
───────────────────────────────────────────────────────────────

Below file includes:
✔ Approach 1 — Top-down memoized recursion (commented)  
✔ Approach 2 — Bottom-up DP (uncommented, final answer)  
✔ ASCII dry-run to reinforce understanding  
───────────────────────────────────────────────────────────────
*/


/* 
───────────────────────────────────────────────────────────────
🥇 Approach 1 — Top-Down DP (Memoized Recursion)
(REFERENCE ONLY — commented)

Time: O(n²)
Space: O(n² + recursion stack)
───────────────────────────────────────────────────────────────

class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int[] row : dp) Arrays.fill(row, -1);

        return rec(0, n - 1, s, dp);
    }

    private int rec(int i, int j, String s, int[][] dp) {
        if (i >= j) return 0;   // single char or empty substring requires 0 insertions

        if (dp[i][j] != -1) return dp[i][j];

        if (s.charAt(i) == s.charAt(j))
            return dp[i][j] = rec(i + 1, j - 1, s, dp);

        return dp[i][j] =
                1 + Math.min(
                        rec(i + 1, j, s, dp),
                        rec(i, j - 1, s, dp)
                );
    }
}
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach 2 — Bottom-Up DP ✔ (UNCOMMENTED)
───────────────────────────────────────────────────────────────
This is the cleanest iterative solution.

We fill dp[i][j] for increasing substring lengths.

dp[i][j] = min insertions for substring s[i…j]

Base cases:
dp[i][i] = 0    (already palindrome)

Transition:
if s[i] == s[j] → dp[i][j] = dp[i+1][j−1]
else             dp[i][j] = 1 + min(dp[i+1][j], dp[i][j−1])

Return dp[0][n-1]

Time: O(n²)
Space: O(n²)
───────────────────────────────────────────────────────────────
💻 Final Code (Active)
───────────────────────────────────────────────────────────────
*/

public class MinimumInsertionStepsToMakeAStringPalindrome {
    public int minInsertions(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int L = 2; L <= n; L++) {
            for (int i = 0; i + L - 1 < n; i++) {
                int j = i + L - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }
}


/*
───────────────────────────────────────────────────────────────
🧪 Dry Run Example — s = "mbadm"
───────────────────────────────────────────────────────────────

String: m b a d m
Indices:0 1 2 3 4

L = 2 cases:
"mb" → 1
"ba" → 1
"ad" → 1
"dm" → 1

L = 3:
"mba" → 2
"bad" → 2
"adm" → 2

L = 4:
"mbad" → 3
"badm" → 3

L = 5:
"mbadm":
 s[0] = s[4] → both 'm'
 dp[0][4] = dp[1][3] = 2

Final answer = 2

Matching example:
We can make "mbdadbm" or "mdbabdm" with 2 insertions.

───────────────────────────────────────────────────────────────
🎯 Important Notes:
• This is **NOT** about making a palindrome substring.
• It's about *inserting characters* to make the whole string a palindrome.
• Equivalent to computing: n - LPS(s).

───────────────────────────────────────────────────────────────
*/
