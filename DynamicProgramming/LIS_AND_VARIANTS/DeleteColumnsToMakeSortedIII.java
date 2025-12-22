/*
 🔹 Problem: 960. Delete Columns to Make Sorted III
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Longest Increasing Subsequence
 🔹 Link: https://leetcode.com/problems/delete-columns-to-make-sorted-iii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array of n strings strs, all of the same length.

You may delete any set of column indices. After deletions, each individual
row must be lexicographically sorted (non-decreasing from left to right).

Return the minimum number of columns that need to be deleted.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: ["babca","bbazb"]
Output: 3

Example 2:
Input: ["edcba"]
Output: 4

Example 3:
Input: ["ghi","def","abc"]
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= strs.length <= 100
 • 1 <= strs[i].length <= 100
 • strs[i] consists of lowercase English letters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Keep the maximum number of columns such that every row remains
lexicographically sorted.

📍 DP + LIS on Columns:
- Treat each column as a character vector across rows
- Find the longest sequence of columns where for every row:
  column[j] <= column[i]
- This becomes a LIS-style DP over columns

Result:
Minimum deletions = total columns − longest valid sequence

Why optimal:
- Time: O(m² × n)
- Space: O(m)
- Standard LIS transformation
 ------------------------------------------------------------
*/

import java.util.*;

public class DeleteColumnsToMakeSortedIII {

    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        int[] dp = new int[m];
        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < i; j++) {
                if (isValid(strs, i, j)) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = 1;
        for (int v : dp) max = Math.max(max, v);

        return m - max;
    }

    private boolean isValid(String[] strs, int i, int j) {
        for (String s : strs) {
            if (s.charAt(i) < s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: ["babca","bbazb"]

Columns kept (LIS-like): indices [2,3]
Length kept = 2
Total columns = 5

Deletions = 5 − 2 = 3 ✅
 ------------------------------------------------------------
*/
