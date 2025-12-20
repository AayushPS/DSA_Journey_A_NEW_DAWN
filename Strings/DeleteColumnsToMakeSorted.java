/*
 🔹 Problem: 944. Delete Columns to Make Sorted
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Array, String
 🔹 Link: https://leetcode.com/problems/delete-columns-to-make-sorted/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array of n strings strs, all of the same length.
The strings form a grid where each string is a row.

You must delete columns that are not sorted lexicographically
(top to bottom). Return the number of columns deleted.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: ["cba","daf","ghi"]
Output: 1

Example 2:
Input: ["a","b"]
Output: 0

Example 3:
Input: ["zyx","wvu","tsr"]
Output: 3

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= strs.length <= 100
 • 1 <= strs[i].length <= 1000
 • strs[i] consists of lowercase English letters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Identify columns where characters decrease lexicographically
from one row to the next.

📍 Approach (Single Pass Column Check):
- Traverse column by column
- For each column, compare characters row-wise
- If any decrease is found, mark column for deletion

Why optimal:
- Time: O(n * m)
- Space: O(1)
- Direct scan, no extra memory
 ------------------------------------------------------------
*/

public class DeleteColumnsToMakeSorted {

    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        int count = 0;

        for (int col = 0; col < m; col++) {
            for (int row = 1; row < n; row++) {
                if (strs[row].charAt(col) < strs[row - 1].charAt(col)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input:
["cba","daf","ghi"]

Column 0: c → d → g (sorted)
Column 1: b → a ❌ (not sorted) → delete
Column 2: a → f → i (sorted)

Final Result: 1 ✅
 ------------------------------------------------------------
*/
