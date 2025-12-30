package PrefixSum;
/*
 🔹 Problem: 840. Magic Squares In Grid
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Matrix, Hash Table, Math
 🔹 Link: https://leetcode.com/problems/magic-squares-in-grid/

 ------------------------------------------------------------
 📝 Problem Statement:

A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 
such that each row, column, and both diagonals all have the same sum.

Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

Note: while a magic square can only contain numbers from 1 to 9, grid may contain 
numbers up to 15.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
Output: 1
Explanation: 
The following subgrid is a 3 x 3 magic square:
[4,3,8]
[9,5,1]
[2,7,6]
while this one is not:
[3,8,4]
[5,1,9]
[7,6,2]
In total, there is only one magic square inside the given grid.

Example 2:
Input: grid = [[8]]
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • row == grid.length
 • col == grid[i].length
 • 1 <= row, col <= 10
 • 0 <= grid[i][j] <= 15

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count how many 3x3 subgrids in the given grid are magic squares.

📍 **Approach 1 (Brute Force):**
   - Iterate through all possible 3x3 subgrids
   - For each subgrid, check if it's a magic square
   - Validate: distinct numbers 1-9, all rows/cols/diagonals have same sum
   - Simple and straightforward implementation
   - Best for small grids (constraint: max 10x10)

📍 **Approach 2 (Prefix Sum Optimization):**
   - Precompute prefix sums for rows (horizontal) and columns (vertical)
   - Use prefix sums for O(1) row and column sum calculation
   - Still need to check diagonals and distinct 1-9 validation
   - Trade-off: more complex code for marginal performance gain on small grids
   - More useful for larger grids or repeated queries

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Brute Force)
   ⏱️ Time Complexity: O(n*m) - iterate all positions, each check is O(1) for 3x3
   💾 Space Complexity: O(1) - only using boolean array of size 10
   
   🧠 **Key Insight:**
   For a 3x3 magic square with numbers 1-9, the sum must always be 15.
   This is because (1+2+...+9)/3 = 45/3 = 15.
   
   💡 **Why it works:**
   - Check every possible 3x3 window in the grid
   - First validate distinct numbers from 1-9 using boolean array
   - Then verify all rows, columns, and diagonals sum to same value
   - Since grid is small (max 10x10), brute force is efficient enough
 ------------------------------------------------------------

// class Solution {
//     public int numMagicSquaresInside(int[][] grid) {
//         int n = grid.length, m = grid[0].length;
//         int count = 0;
//         
//         // Try every possible 3x3 window
//         for(int i = 0; i <= n-3; i++){
//             for(int j = 0; j <= m-3; j++){
//                 if(isMagicSquare(grid, i, j)) count++;
//             }
//         }
//         return count;
//     }
//     
//     private boolean isMagicSquare(int[][] grid, int r, int c) {
//         // Check distinct numbers from 1-9
//         boolean[] seen = new boolean[10];
//         for(int i = r; i < r+3; i++) {
//             for(int j = c; j < c+3; j++) {
//                 int val = grid[i][j];
//                 if(val < 1 || val > 9 || seen[val]) return false;
//                 seen[val] = true;
//             }
//         }
//         
//         // Calculate expected sum from first row
//         int sum = grid[r][c] + grid[r][c+1] + grid[r][c+2];
//         
//         // Check all rows
//         for(int i = r; i < r+3; i++) {
//             if(grid[i][c] + grid[i][c+1] + grid[i][c+2] != sum) return false;
//         }
//         
//         // Check all columns
//         for(int j = c; j < c+3; j++) {
//             if(grid[r][j] + grid[r+1][j] + grid[r+2][j] != sum) return false;
//         }
//         
//         // Check both diagonals
//         if(grid[r][c] + grid[r+1][c+1] + grid[r+2][c+2] != sum) return false;
//         if(grid[r][c+2] + grid[r+1][c+1] + grid[r+2][c] != sum) return false;
//         
//         return true;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Prefix Sum Optimization)
   ⏱️ Time Complexity: O(n*m) - preprocessing + checking all windows
   💾 Space Complexity: O(n*m) - two prefix sum arrays
   
   🧠 **Key Insight:**
   Use prefix sums to compute row and column sums in O(1) time instead of O(3).
   For a 10x10 grid, this optimization doesn't provide significant speedup,
   but demonstrates a technique useful for larger grids or repeated queries.
   
   💡 **Why it works:**
   - prefixRight[i][j] stores cumulative sum of row i from column 0 to j-1
   - prefixDown[i][j] stores cumulative sum of column j from row 0 to i-1
   - Row sum = prefixRight[i][j+1] - prefixRight[i][j-2] (range sum)
   - Column sum = prefixDown[i+1][j] - prefixDown[i-2][j] (range sum)
   - Still validate distinct 1-9 and check diagonal sums separately
   - Iterate from position (2,2) onwards to ensure 3x3 window fits
 ------------------------------------------------------------
*/

public class MagicSquaresInGrid {
    public int numMagicSquaresInside(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        
        // Build prefix sum arrays
        int[][] prefixRight = new int[n][m+1];
        int[][] prefixDown = new int[n+1][m];
        
        // Build horizontal prefix sums (row-wise)
        for(int i = 0; i < n; i++){
            prefixRight[i][1] = grid[i][0]; 
            for(int j = 1; j < m; j++){
                prefixRight[i][j+1] = prefixRight[i][j] + grid[i][j];
            }
        }
        
        // Build vertical prefix sums (column-wise)
        for(int j = 0; j < m; j++){
            prefixDown[1][j] = grid[0][j]; 
            for(int i = 1; i < n; i++){
                prefixDown[i+1][j] = prefixDown[i][j] + grid[i][j];
            }
        }
        
        int count = 0;
        
        // Check each possible 3x3 window (bottom-right corner at i,j)
        for(int i = 2; i < n; i++){
            for(int j = 2; j < m; j++){
                // Quick check: must have distinct 1-9
                if(!hasDistinct1to9(grid, i, j)) continue;
                
                // Check diagonal sums
                int diagonal = diagonalSum(grid, i, j);
                if(diagonal == -1) continue; // Diagonals don't match
                
                // Check all rows and columns using prefix sums
                if(
                    // Row i (bottom row)
                    diagonal == (prefixRight[i][j+1] - prefixRight[i][j-2]) && 
                    // Row i-1 (middle row)
                    diagonal == (prefixRight[i-1][j+1] - prefixRight[i-1][j-2]) &&
                    // Row i-2 (top row)
                    diagonal == (prefixRight[i-2][j+1] - prefixRight[i-2][j-2]) &&
                    // Column j (right column)
                    diagonal == (prefixDown[i+1][j] - prefixDown[i-2][j]) &&
                    // Column j-1 (middle column)
                    diagonal == (prefixDown[i+1][j-1] - prefixDown[i-2][j-1]) &&
                    // Column j-2 (left column)
                    diagonal == (prefixDown[i+1][j-2] - prefixDown[i-2][j-2])
                ) {
                    count++;
                }
            }
        }
        return count;
    }
    
    // Check if 3x3 subgrid has distinct numbers from 1-9
    private boolean hasDistinct1to9(int[][] grid, int i, int j) {
        boolean[] seen = new boolean[10];
        for(int r = i-2; r <= i; r++) {
            for(int c = j-2; c <= j; c++) {
                int val = grid[r][c];
                if(val < 1 || val > 9 || seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }
    
    // Calculate diagonal sums; return sum if equal, -1 if not
    private int diagonalSum(int[][] grid, int i, int j){ 
        // Main diagonal (top-left to bottom-right)
        int sumA = grid[i][j] + grid[i-1][j-1] + grid[i-2][j-2];
        // Anti-diagonal (top-right to bottom-left)
        int sumB = grid[i][j-2] + grid[i-1][j-1] + grid[i-2][j];
        return sumA == sumB ? sumA : -1;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]

Step 1: Build prefix sums
prefixRight (horizontal cumulative sums):
[0, 4, 7, 15, 19]
[0, 9, 14, 15, 24]
[0, 2, 9, 15, 17]

prefixDown (vertical cumulative sums):
[0, 0, 0, 0, 0]
[4, 3, 8, 4, 0]
[13, 8, 9, 13, 0]
[15, 15, 15, 15, 0]

Step 2: Check position (2, 2) - 3x3 window ending at row 2, col 2
Grid section:
[4, 3, 8]
[9, 5, 1]
[2, 7, 6]

Step 3: Check distinct 1-9
{4,3,8,9,5,1,2,7,6} = {1,2,3,4,5,6,7,8,9} ✓

Step 4: Check diagonals
Main diagonal: 4 + 5 + 6 = 15
Anti-diagonal: 8 + 5 + 2 = 15
Both equal ✓

Step 5: Check rows using prefix sums
Row 0: prefixRight[0][3] - prefixRight[0][0] = 15 - 0 = 15 ✓
Row 1: prefixRight[1][3] - prefixRight[1][0] = 15 - 0 = 15 ✓
Row 2: prefixRight[2][3] - prefixRight[2][0] = 15 - 0 = 15 ✓

Step 6: Check columns using prefix sums
Col 0: prefixDown[3][0] - prefixDown[0][0] = 15 - 0 = 15 ✓
Col 1: prefixDown[3][1] - prefixDown[0][1] = 15 - 0 = 15 ✓
Col 2: prefixDown[3][2] - prefixDown[0][2] = 15 - 0 = 15 ✓

All checks passed → count = 1

Step 7: Check position (2, 3) - 3x3 window ending at row 2, col 3
Grid section:
[3, 8, 4]
[5, 1, 9]
[7, 6, 2]

Step 8: Check distinct 1-9
{3,8,4,5,1,9,7,6,2} = {1,2,3,4,5,6,7,8,9} ✓

Step 9: Check diagonals
Main diagonal: 3 + 1 + 2 = 6
Anti-diagonal: 4 + 1 + 7 = 12
Not equal ✗ → Skip this window

Final Result: count = 1 ✅

💡 Note: For a valid magic square with numbers 1-9, the sum must always be 15,
because (1+2+3+4+5+6+7+8+9)/3 = 45/3 = 15.

⚡ Performance Analysis:
The prefix sum optimization provides minimal benefit for the given constraints (max 10x10 grid).
However, this technique scales well and would be significantly beneficial for:
- Grids up to 1000x1000 or larger
- Multiple queries on the same grid
- When checking larger subgrids (not just 3x3)
For the current problem with small constraints, Approach 1 (Brute Force) is actually
more practical due to its simplicity and similar performance.
 ------------------------------------------------------------
*/