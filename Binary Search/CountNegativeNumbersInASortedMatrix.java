/*
 🔹 Problem: 1351. Count Negative Numbers in a Sorted Matrix
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Matrix, Binary Search, Two Pointers
 🔹 Link: https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/

 ------------------------------------------------------------
 📝 Problem Summary:

You are given an m × n matrix sorted in non-increasing order
both row-wise and column-wise.

Return the total count of negative numbers in the matrix.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ m, n ≤ 100
 • -100 ≤ grid[i][j] ≤ 100
 • Rows and columns are sorted in non-increasing order

 ------------------------------------------------------------
 💡 Optimal Approach (O(m + n)):

Start from the top-right corner.
• If current value < 0 → all values below are negative
• Otherwise move right/left accordingly

This leverages the sorted structure efficiently.

 ------------------------------------------------------------
 ⏱️ Time Complexity: O(m + n)
 💾 Space Complexity: O(1)

 ------------------------------------------------------------
*/

/*
 ------------------------------------------------------------
 🔹 Brute Force (Commented – O(m × n))
 ------------------------------------------------------------

 // class Solution {
 //     public int countNegatives(int[][] grid) {
 //         int count = 0;
 //         for (int[] row : grid) {
 //             for (int val : row) {
 //                 if (val < 0) count++;
 //             }
 //         }
 //         return count;
 //     }
 // }
*/

/*
 ------------------------------------------------------------
 🔹 Binary Search per Row (Commented – O(m log n))
 ------------------------------------------------------------

 // class Solution {
 //     public int countNegatives(int[][] grid) {
 //         int m = grid.length, n = grid[0].length;
 //         int count = 0;
 //
 //         for (int i = 0; i < m; i++) {
 //             int idx = firstNegative(grid[i]);
 //             if (idx != -1) count += (n - idx);
 //         }
 //         return count;
 //     }
 //
 //     private int firstNegative(int[] row) {
 //         int l = 0, r = row.length - 1, ans = -1;
 //         while (l <= r) {
 //             int mid = l + (r - l) / 2;
 //             if (row[mid] < 0) {
 //                 ans = mid;
 //                 r = mid - 1;
 //             } else {
 //                 l = mid + 1;
 //             }
 //         }
 //         return ans;
 //     }
 // }
*/

/*
 ------------------------------------------------------------
 🔹 Optimal Approach (✅ Two Pointers – O(m + n))
 ------------------------------------------------------------
*/

public class CountNegativeNumbersInASortedMatrix {

    public int countNegatives(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int row = 0;
        int col = n - 1;
        int count = 0;

        while (row < m && col >= 0) {
            if (grid[row][col] < 0) {
                count += (m - row);
                col--;
            } else {
                row++;
            }
        }
        return count;
    }
}
