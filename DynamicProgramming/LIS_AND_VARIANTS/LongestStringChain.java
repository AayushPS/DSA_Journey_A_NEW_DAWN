/*
───────────────────────────────────────────────────────────────
📘 Problem: 1048. Longest String Chain
💡 Difficulty: Medium
🧠 Topics: Dynamic Programming, String, Sorting
🔗 Link: https://leetcode.com/problems/longest-string-chain/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are given an array of words where each word consists of lowercase English letters.

`wordA` is a **predecessor** of `wordB` if and only if we can insert exactly one letter anywhere in `wordA`
without changing the order of other characters to make it equal to `wordB`.

A **word chain** is a sequence of words `[word1, word2, ..., wordk]` where each word is the predecessor
of the next one.

Return the length of the **longest possible word chain** that can be formed from the given list of words.

───────────────────────────────────────────────────────────────
🧠 Example 1:

Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bda","bdca"].

───────────────────────────────────────────────────────────────
🧠 Example 2:

Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: ["xb","xbc","cxbc","pcxbc","pcxbcf"]

───────────────────────────────────────────────────────────────
🧠 Example 3:

Input: words = ["abcd","dbqca"]
Output: 1
───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 <= words.length <= 1000  
• 1 <= words[i].length <= 16  
• words[i] consists only of lowercase English letters
───────────────────────────────────────────────────────────────

⚙️ Approach Summary:
We use a **Dynamic Programming** technique similar to the **Longest Increasing Subsequence (LIS)** pattern.

✅ Sort all words by length (shortest → longest).  
✅ For each word `i`, check all shorter words `j < i`:  
   - If `words[j]` is a valid predecessor of `words[i]`,  
     then `dp[i] = max(dp[i], dp[j] + 1)`.  
✅ Keep track of the maximum chain length seen so far.

───────────────────────────────────────────────────────────────
🧩 Helper Function — isPredecessor(s1, s2):
Checks whether `s1` can become `s2` by inserting exactly one character anywhere.
───────────────────────────────────────────────────────────────
*/

import java.util.Arrays;
import java.util.Comparator;

public class LongestStringChain {
    public int longestStrChain(String[] words) {
        int n = words.length;
        Arrays.sort(words, Comparator.comparingInt(i -> i.length()));  // Sort by length
        byte[] dp = new byte[n];
        Arrays.fill(dp, (byte) 1);
        int ans = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (isPredecessor(words[j], words[i])) {
                    dp[i] = (byte) Math.max(dp[i], 1 + dp[j]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    private static boolean isPredecessor(String s1, String s2) {
        if (s1.length() + 1 != s2.length()) return false;
        boolean skipped = false;
        int i = 0, j = 0;
        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                if (skipped) return false;
                skipped = true;
                j++;
            }
        }
        return true;
    }
}

/*
───────────────────────────────────────────────────────────────
🧩 Alternate Approach (Top-Down Recursion + Memoization)
---------------------------------------------------------------
class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(i -> i.length()));
        int[][] dp = new int[words.length][words.length + 1];
        return dfs(0, -1, dp, words);
    }

    private int dfs(int i, int prev, int[][] dp, String[] words) {
        if (i == words.length) return 0;
        if (dp[i][prev + 1] != 0) return dp[i][prev + 1];

        int take = (prev == -1 || isPredecessor(words[prev], words[i])) ? 1 + dfs(i + 1, i, dp, words) : 0;
        int notTake = dfs(i + 1, prev, dp, words);

        return dp[i][prev + 1] = Math.max(take, notTake);
    }
}
───────────────────────────────────────────────────────────────
🧮 Time & Space Complexity:
• Time Complexity: O(n² * L)  — n = number of words, L = max word length  
• Space Complexity: O(n)
───────────────────────────────────────────────────────────────
*/
