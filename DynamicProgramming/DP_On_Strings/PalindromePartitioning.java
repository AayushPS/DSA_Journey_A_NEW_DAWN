/*
───────────────────────────────────────────────────────────────
📘 Problem: 131. Palindrome Partitioning
💡 Difficulty: Medium
🧠 Topics: Backtracking, Dynamic Programming, Palindrome Checking
🔗 Link: https://leetcode.com/problems/palindrome-partitioning/
───────────────────────────────────────────────────────────────

Given a string s (length ≤ 16), return ALL possible partitions where every
substring in the partition is a palindrome.

───────────────────────────────────────────────────────────────
🎯 Goal:
Break s into substrings such that:
    each substring is a palindrome
Return ALL valid partitions.

Example:
Input:  "aab"
Output: [["a","a","b"], ["aa","b"]]

───────────────────────────────────────────────────────────────
*/


/*
================================================================
🥇 APPROACH 1 — PURE BACKTRACK + ON-THE-FLY PALINDROME CHECK
================================================================
💡 Idea:
- Try to cut at each index.
- Before cutting, check if substring s[lastIdx..i] is palindrome.
- If yes → recurse deeper.
- Backtrack to explore all possibilities.

⏱ Complexity:
Worst-case every substring is palindrome → O(n * 2^n).
n ≤ 16, acceptable.

───────────────────────────────────────────────────────────────
*/
/*
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        recurse(res, new ArrayList<>(), 0, 0, s);
        return res;
    }

    private void recurse(List<List<String>> res, List<String> adding, int lastIdx, int i, String s) {

        // When we've cut exactly at the end
        if (i == s.length() && lastIdx == s.length()) {
            res.add(new ArrayList(adding));
            return;
        }
        if (i == s.length()) return;

        if (isPalindrome(s, lastIdx, i)) {
            adding.add(s.substring(lastIdx, i + 1));        // take the cut
            recurse(res, adding, i + 1, i + 1, s);
            adding.remove(adding.size() - 1);               // backtrack

            // also try skipping cut here
            recurse(res, adding, lastIdx, i + 1, s);
        } else {
            recurse(res, adding, lastIdx, i + 1, s);
        }
    }

    private boolean isPalindrome(String s, int i, int j) {
        while (i <= j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
*/


/*
================================================================
🥈 APPROACH 2 — DP TABLE + BACKTRACK (FASTER PALINDROME CHECK)
================================================================
💡 Idea:
1️⃣ Precompute DP table:
      dp[i][j] = true if s[i..j] is palindrome.
2️⃣ Backtracking:
      At index i, try all j ≥ i where dp[i][j] is true.
      Recurse from j+1.

This avoids recomputing palindrome checks repeatedly.

⏱ Complexity:
DP palindrome fill → O(n^2)
Backtracking → O(n * 2^n)

Much faster than Approach 1 in practice.

───────────────────────────────────────────────────────────────
*/

import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        int n = s.length();

        // Build DP table
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) dp[i][i] = true;

        for (int L = 2; L <= n; L++) {
            for (int i = 0; i + L - 1 < n; i++) {
                int j = i + L - 1;
                if (L == 2) dp[i][j] = (s.charAt(i) == s.charAt(j));
                else dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
            }
        }

        // Perform DFS
        backtrack(res, new ArrayList<>(), 0, s, dp);
        return res;
    }

    private void backtrack(List<List<String>> res, List<String> path, int start, String s, boolean[][] dp) {
        if (start == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (dp[start][end]) {
                path.add(s.substring(start, end + 1));
                backtrack(res, path, end + 1, s, dp);
                path.remove(path.size() - 1);
            }
        }
    }
}



/*
───────────────────────────────────────────────────────────────
🧪 DRY RUN — s = "aab"
───────────────────────────────────────────────────────────────

DP Table (true = palindrome):
   0 1 2
0 [T T F]
1 [F T F]
2 [F F T]

Backtracking:
start=0:
   end=0 → "a"
      start=1:
         end=1 → "a"
            start=2:
               end=2 → "b"
                  start=3 → add ["a","a","b"]

   end=1 → "aa"
      start=2:
         end=2 → "b"
            start=3 → add ["aa","b"]

───────────────────────────────────────────────────────────────
✔ Final Output:
[["a","a","b"], ["aa","b"]]
───────────────────────────────────────────────────────────────
*/
