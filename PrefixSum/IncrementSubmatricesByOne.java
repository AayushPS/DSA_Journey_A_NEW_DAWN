package PrefixSum;
/*
───────────────────────────────────────────────────────────────
📘 Problem: 2536. Increment Submatrices by One
💡 Difficulty: Medium
🧠 Topics: Difference Array, Prefix Sum, Matrix Manipulation
🔗 Link: https://leetcode.com/problems/increment-submatrices-by-one/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

Given an `n x n` matrix initialized with zeros, process each query  
`[row1, col1, row2, col2]` and increment every cell inside that submatrix by 1.

Return the final matrix.

───────────────────────────────────────────────────────────────
📌 Constraints:
• 1 ≤ n ≤ 500  
• 1 ≤ queries.length ≤ 10⁴  
• 0 ≤ row1 ≤ row2 < n  
• 0 ≤ col1 ≤ col2 < n  

───────────────────────────────────────────────────────────────
*/



/*
───────────────────────────────────────────────────────────────
🥉 Approach 1 — Direct Brute Force (TLE for large input)
───────────────────────────────────────────────────────────────
💡 Idea:
For each query, loop through every cell in the submatrix and increment it.

❌ Time:  O(q * n²) → Too slow  
❌ Space: O(1)

Included only for conceptual understanding.
*/

/*
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] mat = new int[n][n];
        for (int[] q : queries) {
            for (int i = q[0]; i <= q[2]; i++) {
                for (int j = q[1]; j <= q[3]; j++) {
                    mat[i][j]++;
                }
            }
        }
        return mat;
    }
}
*/



/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — Row-Wise Difference Array (Optimized)
───────────────────────────────────────────────────────────────
💡 Idea:
For each query, instead of updating the entire submatrix:
- For each row in the range, apply a difference array technique:
      mat[row][c1]++  
      mat[row][c2+1]--  

After processing all queries:
- Restore actual values via prefix sum on each row.

✔ Faster than brute force  
✔ Still O(n * q)  
✔ Works because each row can be handled independently  

───────────────────────────────────────────────────────────────
🧮 Time:  O(n*q + n²)
🧮 Space: O(n²)
*/

/*
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] mat = new int[n][n];

        for (int[] q : queries) {
            for (int row = q[0]; row <= q[2]; row++) {
                mat[row][q[1]]++;
                if (q[3] + 1 < n) mat[row][q[3] + 1]--;
            }
        }

        // Convert difference rows to actual values
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                mat[i][j] += mat[i][j - 1];
            }
        }

        return mat;
    }
}
*/



/*
───────────────────────────────────────────────────────────────
🥇 Approach 3 — 2D Difference Array + 2D Prefix Sum (Optimal)
───────────────────────────────────────────────────────────────
💡 Idea:

Use the **2D difference array** method:

For a submatrix add operation:
    diff[r1][c1]       += 1
    diff[r1][c2 + 1]   -= 1
    diff[r2 + 1][c1]   -= 1
    diff[r2 + 1][c2+1] += 1

After processing all queries:
1️⃣ Apply horizontal prefix sum  
2️⃣ Apply vertical prefix sum  

Result: full matrix reconstructed with all increments.

✔ Best possible approach  
✔ Handles 10⁴ queries & n=500 easily  

───────────────────────────────────────────────────────────────
🧮 Time:  O(n² + q)
🧮 Space: O(n²)
───────────────────────────────────────────────────────────────
*/

class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] mat = new int[n][n];

        // Apply 2D diff updates
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1];
            int r2 = q[2] + 1, c2 = q[3] + 1;

            mat[r1][c1]++;

            if (c2 < n) mat[r1][c2]--;
            if (r2 < n) mat[r2][c1]--;
            if (r2 < n && c2 < n) mat[r2][c2]++;
        }

        // Horizontal prefix sums
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                mat[i][j] += mat[i][j - 1];
            }
        }

        // Vertical prefix sums
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] += mat[i - 1][j];
            }
        }

        return mat;
    }
}



/*
───────────────────────────────────────────────────────────────
🧮 Final Summary:
───────────────────────────────────────────────────────────────
Approach 1: Brute force  
• Time:  O(q*n²)  
• Space: O(1)  
❌ Too slow

Approach 2: Row Difference Array  
• Time:  O(n*q + n²)  
• Space: O(n²)  
✔ Faster

Approach 3: 2D Difference + Prefix (Optimal)  
• Time:  O(n² + q)  
• Space: O(n²)  
✔ Best solution  
✔ Final choice (uncommented)

───────────────────────────────────────────────────────────────
*/
