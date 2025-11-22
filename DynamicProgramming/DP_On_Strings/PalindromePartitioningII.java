/*
───────────────────────────────────────────────────────────────
📘 Problem: 132. Palindrome Partitioning II
💡 Difficulty: Hard
🧠 Topics: Dynamic Programming, Palindrome, String, Partitioning
🔗 Link: https://leetcode.com/problems/palindrome-partitioning-ii/
───────────────────────────────────────────────────────────────

Given a string s, partition it such that every substring is a palindrome.
Return the **minimum number of cuts** needed.

Example:
s = "aab" → ["aa", "b"] → only 1 cut → answer = 1

───────────────────────────────────────────────────────────────
Core Idea
───────────────────────────────────────────────────────────────

1️⃣ Precompute a palindrome DP table:
    dp[i][j] = true if s[i..j] is a palindrome

2️⃣ Then solve the "min cuts" problem:
    - We want to split s into the minimum number of palindromic pieces.
    - If s[0..i] is palindrome → 0 cuts needed up to i.
    - Otherwise, try all possible last cuts j<i:
         if s[j+1..i] is palindrome, then:
             cuts[i] = min(cuts[i], cuts[j] + 1)

Result = cuts[n-1]

───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🥈 Approach 1 — Top-Down DP with Memo (Commented Out)
───────────────────────────────────────────────────────────────
Idea:
- Precompute palindrome table `dp[i][j]`.
- Then use recursion with memo to compute:
    f(i) = minimum number of palindromic pieces from index i to end.
- Answer (in terms of cuts) = f(0) - 1.

Time:  O(n²)
Space: O(n²)

(REFERENCE ONLY – NOT ACTIVE)
───────────────────────────────────────────────────────────────

class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        // Single character palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Fill palindromic substrings
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i + L - 1 < n; i++) {
                int j = i + L - 1;
                if (L == 2) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
            }
        }

        int[] cache = new int[n];
        Arrays.fill(cache, -1);

        // f(0) gives number of palindromic pieces, cuts = pieces - 1
        return recurse(0, s, dp, cache) - 1;
    }

    private int recurse(int i, String s, boolean[][] dp, int[] cache) {
        if (i == s.length()) return 0;
        if (cache[i] != -1) return cache[i];

        int min = s.length();
        for (int end = i; end < s.length(); end++) {
            if (dp[i][end]) {
                min = Math.min(min, 1 + recurse(end + 1, s, dp, cache));
            }
        }
        return cache[i] = min;
    }
}
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach 2 — Bottom-Up DP with Palindrome Table (ACTIVE)
───────────────────────────────────────────────────────────────
Idea:
1) Precompute dp[i][j] = is s[i..j] palindrome?
2) Define cache[i] = minimum cuts needed for substring s[0..i].

Transition:
- If s[0..i] is palindrome → cache[i] = 0 (no cut needed)
- Else:
    cache[i] = min over all j < i:
        if s[j+1..i] is palindrome:
             cache[i] = min(cache[i], cache[j] + 1)

Answer = cache[n-1].

Time:  O(n²)
Space: O(n²) for dp + O(n) for cache

───────────────────────────────────────────────────────────────
💻 Final Code
───────────────────────────────────────────────────────────────
*/

public class PalindromePartitioningII {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        // All single characters are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Precompute palindromic substrings
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i + L - 1 < n; i++) {
                int j = i + L - 1;
                if (L == 2) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
            }
        }

        int[] cache = new int[n + 1];
        cache[0] = 0;

        for (int i = 1; i < n; i++) {
            // If whole prefix s[0..i] is palindrome → zero cuts up to i
            if (dp[0][i]) {
                cache[i] = 0;
                continue;
            }
            cache[i] = n; // large initial value

            // Try all partition points
            for (int j = 0; j < i; j++) {
                if (dp[j + 1][i]) {
                    cache[i] = Math.min(cache[i], cache[j] + 1);
                }
            }
        }

        return cache[n - 1];
    }
}


/*
───────────────────────────────────────────────────────────────
🧪 Dry Run — s = "aab"
───────────────────────────────────────────────────────────────

s = "a a b"
     0 1 2

Palindrome table dp[i][j]:

"aa" at [0,1] → true
"a" , "a" , "b" → true
"ab" at [1,2] → false
"aab" at [0,2] → false

Now compute cache[i] = min cuts for s[0..i]:

i = 0: "a"
  dp[0][0] = true → cache[0] = 0  (no cut)

i = 1: "aa"
  dp[0][1] = true → cache[1] = 0  (entire "aa" is palindrome)

i = 2: "aab"
  dp[0][2] = false
  try j = 0 → check dp[1][2] ("ab") → false
  try j = 1 → check dp[2][2] ("b") → true
      cache[2] = min(INF, cache[1] + 1) = 0 + 1 = 1

Answer = cache[2] = 1

───────────────────────────────────────────────────────────────
Result:
Minimum cuts to partition "aab" into palindromes = 1
("aa" | "b")
───────────────────────────────────────────────────────────────
*/
