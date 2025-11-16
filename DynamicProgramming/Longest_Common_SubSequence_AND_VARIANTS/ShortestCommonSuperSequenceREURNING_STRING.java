/*
───────────────────────────────────────────────────────────────
📘 Problem: 1092. Shortest Common Supersequence (Return the string)
💡 Difficulty: Hard
🧠 Topics: Dynamic Programming, String Reconstruction
🔗 Link: https://leetcode.com/problems/shortest-common-supersequence/
───────────────────────────────────────────────────────────────

Given two strings str1 and str2, return the shortest string which has both
str1 and str2 as subsequences. If multiple answers exist, return any one.

This file includes:
- The DP-based solution that reconstructs and returns one shortest common supersequence (SCS).
- A full DP table visualization example (as comments).
- An ASCII-style reconstruction trace (as comments).

───────────────────────────────────────────────────────────────
🧠 Key idea (brief):
Compute DP table dp[i][j] = length of shortest common supersequence for
prefixes str1[0..i-1] and str2[0..j-1]. Then backtrack from dp[m][n] to
reconstruct one valid SCS:

- If str1[i-1] == str2[j-1] → take that char and move diagonally.
- Else → move toward the neighbor (up or left) which gives smaller dp value,
       and append the corresponding character from that string.
- After finishing, append remaining characters from either string.

Time: O(m * n)
Space: O(m * n)
───────────────────────────────────────────────────────────────
*/

class ShortestCommonSupersequenceREURNING_STRING {

    /**
     * Returns one shortest common supersequence of s1 and s2.
     */
    public static String shortestCommonSupersequence(String s1, String s2) {
        int m = s1.length() + 1;
        int n = s2.length() + 1;
        int[][] dp = new int[m][n];

        // Build DP table for SCS lengths
        buildSCSDP(s1, s2, m, n, dp);

        // Reconstruct SCS string using dp table
        return buildSCSString(s1, s2, m - 1, n - 1, dp);
    }

    // Fill dp table: dp[i][j] = SCS length for s1[0..i-1], s2[0..j-1]
    private static void buildSCSDP(String s1, String s2, int m, int n, int[][] dp) {
        for (int i = 0; i < m; i++) dp[i][0] = i;
        for (int j = 0; j < n; j++) dp[0][j] = j;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
    }

    // Reconstruct SCS string from dp table (backtracking)
    private static String buildSCSString(String s1, String s2, int i, int j, int[][] dp) {
        StringBuilder sb = new StringBuilder();

        // Backtrack while both indices > 0
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                // Characters match — include once
                sb.append(s1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // dp from left (i, j-1) is smaller → include s2[j-1]
                sb.append(s2.charAt(j - 1));
                j--;
            } else {
                // dp from top (i-1, j) is smaller or equal → include s1[i-1]
                sb.append(s1.charAt(i - 1));
                i--;
            }
        }

        // Append remaining characters from s1 or s2
        while (i > 0) {
            sb.append(s1.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            sb.append(s2.charAt(j - 1));
            j--;
        }

        return sb.reverse().toString();
    }
}


/*
───────────────────────────────────────────────────────────────
DP Table Visualization (Option A) — example:
  s1 = "abac"
  s2 = "cab"

We build dp with sizes (m+1)×(n+1) where
m = |s1| = 4, n = |s2| = 3, and dp[0][*], dp[*][0] are base cases.

Indexing:
   j→    ""   c   a   b
i
""      0    1   2   3
a       1    2   1   2
b       2    3   2   3
a       3    4   3   4
c       4    3   4   5

Explanation of the table above:
- dp[0][j] = j (when s1 prefix is empty, scs is just length j)
- dp[i][0] = i (when s2 prefix is empty)
- For example dp[4][3] = 5 → shortest common supersequence length is 5.

(You may compute this table by running the DP recurrence shown in code.)
───────────────────────────────────────────────────────────────


ASCII Reconstruction Path (Option D) — same example (trace only)
We backtrack from dp[4][3] to dp[0][0]. The following trace demonstrates the decisions.

Start: i=4, j=3  (s1[i-1]= 'c', s2[j-1] = 'b')
 dp[4][3] = 5
 Compare s1[3]='c' vs s2[2]='b' -> not equal
 dp[i-1][j] = dp[3][3] = 4
 dp[i][j-1] = dp[4][2] = 4
 equal (4 == 4) → per tie-break we take s1[i-1] (implementation takes top if <= left)
 Action: append s1[3] = 'c'  → sb.append('c')
 Move: i = 3, j = 3

Now: i=3, j=3  (s1[2]='a', s2[2]='b')
 dp[3][3] = 4
 s1[2] != s2[2]
 dp[2][3] = 3
 dp[3][2] = 3
 equal → append s1[2] = 'a'
 Move: i = 2, j = 3

Now: i=2, j=3  (s1[1]='b', s2[2]='b')
 chars equal → append 'b'
 Move: i = 1, j = 2

Now: i=1, j=2  (s1[0]='a', s2[1]='a')
 chars equal → append 'a'
 Move: i = 0, j = 1

Remaining: i=0, j=1 → append remaining s2[0] = 'c'

Collected (in append order): c, a, b, a, c
Reverse → "cabac"

Final SCS: "cabac" (length 5), matches dp[4][3] = 5
───────────────────────────────────────────────────────────────
Notes on tie-breaking:
 - When dp[i-1][j] == dp[i][j-1], either branch yields a valid shortest result.
 - The implementation chooses s1[i-1] when dp[i-1][j] <= dp[i][j-1]
   (you may adjust tie-breaking to prefer s2 if you want different valid outputs).

───────────────────────────────────────────────────────────────
HOW TO USE:
- The code returns one shortest common supersequence string.
- The DP table and ASCII trace above are included as documentation (comments).
───────────────────────────────────────────────────────────────
*/
