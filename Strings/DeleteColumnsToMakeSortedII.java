/*
 🔹 Problem: 955. Delete Columns to Make Sorted II
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, String, Greedy
 🔹 Link: https://leetcode.com/problems/delete-columns-to-make-sorted-ii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array of n strings strs, all of the same length.

You may delete any set of column indices. After deletions, the resulting
array must be lexicographically sorted (strs[0] <= strs[1] <= ...).

Return the minimum number of columns that need to be deleted.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: ["ca","bb","ac"]
Output: 1

Example 2:
Input: ["xc","yb","za"]
Output: 0

Example 3:
Input: ["zyx","wvu","tsr"]
Output: 3

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= strs.length <= 100
 • 1 <= strs[i].length <= 100
 • strs[i] consists of lowercase English letters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Ensure rows are lexicographically sorted using minimum column deletions.

📍 Greedy + State Tracking:
- Process columns left to right
- Track which adjacent rows are already confirmed sorted
- If a column breaks order for any unresolved pair → delete it
- Otherwise, lock in ordering where possible

Why optimal:
- Time: O(n * m)
- Space: O(n)
- Greedy decision is irreversible and optimal
 ------------------------------------------------------------
*/

public class DeleteColumnsToMakeSortedII {

    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        int deletions = 0;

        boolean[] sorted = new boolean[n];
        sorted[0] = true;

        outer:
        for (int col = 0; col < m; col++) {

            for (int row = 1; row < n; row++) {
                if (!sorted[row] && strs[row].charAt(col) < strs[row - 1].charAt(col)) {
                    deletions++;
                    continue outer;
                }
            }

            for (int row = 1; row < n; row++) {
                if (!sorted[row] && strs[row].charAt(col) > strs[row - 1].charAt(col)) {
                    sorted[row] = true;
                }
            }

            boolean allSorted = true;
            for (boolean b : sorted) {
                if (!b) {
                    allSorted = false;
                    break;
                }
            }
            if (allSorted) break;
        }

        return deletions;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: ["ca","bb","ac"]

Column 0:
c > b ❌ → delete column

Column 1:
a < b < c ✅

Final Result: 1 ✅
 ------------------------------------------------------------
*/
