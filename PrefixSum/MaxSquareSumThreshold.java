package PrefixSum; 
/*
 🔹 Problem: 1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Binary Search, Matrix, Prefix Sum
 🔹 Link: https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/

 ------------------------------------------------------------
 📝 Problem Statement:

Given a m x n matrix mat and an integer threshold, return the maximum 
side-length of a square with a sum less than or equal to threshold or return 0 
if there is no such square.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
Output: 2
Explanation: The maximum side length of square with sum less than 4 is 2 as shown.

Example 2:
Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • m == mat.length
 • n == mat[i].length
 • 1 <= m, n <= 300
 • 0 <= mat[i][j] <= 10^4
 • 0 <= threshold <= 10^5

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find maximum square side length with sum ≤ threshold.

🔑 **Key Insights:**
   - Need fast submatrix sum queries → 2D Prefix Sum
   - Larger squares have larger sums (monotonic) → Binary Search
   - Combine: Binary search on side length + prefix sum for validation

📍 **Approach (Binary Search + 2D Prefix Sum):**
   - Build 2D prefix sum array for O(1) submatrix queries
   - Binary search on possible side lengths [0, min(m,n)]
   - For each candidate size, check if any square of that size exists
   - Time: O(m×n + log(min(m,n)) × m×n) = O(m×n×log(min(m,n)))
   - Space: O(m×n) for prefix sum array

 ------------------------------------------------------------
 🔹 Understanding 2D Prefix Sum:

Definition:
prefix[i][j] = sum of all elements in rectangle from (0,0) to (i,j)

Building Formula:
prefix[i][j] = mat[i][j] 
             + prefix[i-1][j]     // Add top
             + prefix[i][j-1]     // Add left
             - prefix[i-1][j-1]   // Subtract overlap

Query Formula (sum of rectangle from (r1,c1) to (r2,c2)):
sum = prefix[r2][c2]
    - prefix[r1-1][c2]     // Subtract top
    - prefix[r2][c1-1]     // Subtract left
    + prefix[r1-1][c1-1]   // Add back corner (subtracted twice)

Visual:
    c1      c2
r1  +-------+
    |   ?   |  ? = query region
r2  +-------+

prefix[r2][c2] includes everything from (0,0) to (r2,c2)
Subtract unwanted regions to isolate the query rectangle.

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Binary Search + 2D Prefix Sum - Optimal)
   ⏱️ Time Complexity: O(m×n×log(min(m,n))) - binary search × validation
   💾 Space Complexity: O(m×n) - 2D prefix sum array
   
   🧠 **Key Insight:**
   If a square of side k satisfies threshold, smaller squares also satisfy it.
   If a square of side k+1 violates threshold, larger squares also violate it.
   This monotonic property enables binary search.
   
   💡 **Why it works:**
   - 2D prefix sum enables O(1) submatrix sum queries
   - Binary search finds maximum valid side length efficiently
   - For each candidate size, check all possible square positions
   - If any square of size k works, k is valid
   - Search range: [0, min(rows, cols)]
   
   🎯 **Algorithm Steps:**
   1. Build 2D prefix sum array: O(m×n)
   2. Binary search on side length [0, min(m,n)]:
      - For mid value, check if any square of size mid exists
      - If exists: search right half (larger sizes)
      - If not: search left half (smaller sizes)
   3. Validation checks all possible (m-size+1)×(n-size+1) positions
   4. Return maximum valid size found
   
   📐 **Binary Search Bounds:**
   - Lower bound: 0 (no valid square)
   - Upper bound: min(m,n) (largest possible square)
   - Mid calculation: i + (j-i+1)/2 for upper-biased binary search
   - Upper-biased ensures we find maximum valid size
 ------------------------------------------------------------
*/

public class MaxSquareSumThreshold {
    public int maxSideLength(int[][] mat, int threshold) {
        int rows = mat.length;
        int cols = mat[0].length;
        
        // Build 2D prefix sum array
        int[][] prefix = buildPrefix2D(mat);
        
        // Binary search on side length
        int left = 0;
        int right = Math.min(rows, cols);
        
        while(left < right) {
            // Upper-biased mid to find maximum valid size
            int mid = left + (right - left + 1) / 2;
            
            // Check if any square of size 'mid' satisfies threshold
            boolean hasValidSquare = hasSquareWithSize(prefix, mid, threshold, rows, cols);
            
            if(hasValidSquare) {
                // Valid size found, try larger
                left = mid;
            } else {
                // Size too large, try smaller
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    /**
     * Check if there exists a square of given size with sum <= threshold
     */
    private boolean hasSquareWithSize(int[][] prefix, int size, int threshold, 
                                      int rows, int cols) {
        // Try all possible top-left positions for square of this size
        for(int i = 0; i + size - 1 < rows; i++) {
            for(int j = 0; j + size - 1 < cols; j++) {
                int endRow = i + size - 1;
                int endCol = j + size - 1;
                
                // Query sum of this square
                if(querySubmatrix(prefix, i, j, endRow, endCol, threshold)) {
                    return true; // Found valid square
                }
            }
        }
        return false; // No valid square of this size
    }
    
    /**
     * Build 2D prefix sum array
     * prefix[i][j] = sum of rectangle from (0,0) to (i,j)
     */
    private int[][] buildPrefix2D(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int[][] prefix = new int[rows][cols];
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                // Start with current cell value
                prefix[i][j] = mat[i][j];
                
                // Add prefix sum from top
                if(i > 0) {
                    prefix[i][j] += prefix[i-1][j];
                }
                
                // Add prefix sum from left
                if(j > 0) {
                    prefix[i][j] += prefix[i][j-1];
                }
                
                // Subtract overlap (added twice)
                if(i > 0 && j > 0) {
                    prefix[i][j] -= prefix[i-1][j-1];
                }
            }
        }
        
        return prefix;
    }
    
    /**
     * Query sum of submatrix from (r1,c1) to (r2,c2)
     * Returns true if sum <= threshold
     */
    private boolean querySubmatrix(int[][] prefix, int r1, int c1, int r2, int c2, 
                                   int threshold) {
        // Total includes everything from (0,0) to (r2,c2)
        int total = prefix[r2][c2];
        
        // Subtract region above query rectangle
        int top = (r1 > 0) ? prefix[r1-1][c2] : 0;
        
        // Subtract region to the left of query rectangle
        int left = (c1 > 0) ? prefix[r2][c1-1] : 0;
        
        // Add back corner (was subtracted twice)
        int corner = (r1 > 0 && c1 > 0) ? prefix[r1-1][c1-1] : 0;
        
        // Calculate sum of query rectangle
        int sum = total - top - left + corner;
        
        return sum <= threshold;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: mat = [[1,1,3,2,4,3,2],
              [1,1,3,2,4,3,2],
              [1,1,3,2,4,3,2]], threshold = 4

Matrix: 3×7
min(3,7) = 3 → maximum possible square size

Step 1: Build prefix sum

Original matrix (partial):
1  1  3  2  ...
1  1  3  2  ...
1  1  3  2  ...

Prefix sum (showing first few):
prefix[0][0] = 1
prefix[0][1] = 1 + 1 = 2
prefix[0][2] = 2 + 3 = 5
prefix[1][0] = 1 + 1 = 2
prefix[1][1] = 2 + 2 - 1 = 2 + 2 = 4
prefix[1][2] = 5 + 2 + 5 - 2 = 10

Step 2: Binary search

Iteration 1: left=0, right=3
- mid = 0 + (3-0+1)/2 = 2
- Check size 2:
  - Position (0,0): query [0,0] to [1,1]
    sum = prefix[1][1] = 4
    4 <= 4 ✓ Found!
- hasValidSquare = true
- left = 2

Iteration 2: left=2, right=3
- mid = 2 + (3-2+1)/2 = 3
- Check size 3:
  - Position (0,0): query [0,0] to [2,2]
    sum = prefix[2][2] = 1+1+3+1+1+3+1+1+3 = 15
    15 > 4 ✗
  - Try other positions... all fail
- hasValidSquare = false
- right = 2

Iteration 3: left=2, right=2
- left == right, exit

Result: 2 ✅

 ------------------------------------------------------------
 🔍 Understanding Prefix Sum Query:

Example: Query sum from (1,1) to (2,3)

Matrix:
  0 1 2 3
0 a b c d
1 e[f g h]
2 i[j k l]

Prefix[2][3] = sum from (0,0) to (2,3) = all cells

We want: f+g+h+j+k+l

Query formula:
sum = prefix[2][3]           (a+b+c+d+e+f+g+h+i+j+k+l)
    - prefix[0][3]           - (a+b+c+d)
    - prefix[2][0]           - (a+e+i)
    + prefix[0][0]           + (a)

= f+g+h+j+k+l ✓

Why add corner back?
- Subtracting top removes (a+b+c+d)
- Subtracting left removes (a+e+i)
- (a) was subtracted twice, add it back once

 ------------------------------------------------------------
 🔍 Why Upper-Biased Binary Search?

Goal: Find MAXIMUM valid size

Regular mid: mid = (left + right) / 2
Upper-biased: mid = left + (right - left + 1) / 2

Example: left=2, right=3
- Regular: mid = (2+3)/2 = 2 (lower-biased)
- Upper: mid = 2 + (3-2+1)/2 = 2 + 1 = 3 (upper-biased)

Why upper-biased for maximization?
- Tests larger values first
- If left=2 is valid, checks 3 before settling
- Ensures we don't miss the maximum

Without upper bias, might infinite loop when left+1=right!

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: No valid square (all values > threshold)
mat = [[10,10],[10,10]], threshold = 1
- Even size 1 (single cell) exceeds threshold
- Result: 0 ✓

Case 2: Entire matrix is valid square
mat = [[1,1],[1,1]], threshold = 100
- Size 2 square: sum = 4 <= 100
- Result: 2 ✓

Case 3: Only size 1 squares valid
mat = [[5,5],[5,5]], threshold = 5
- Size 1: sum = 5 ✓
- Size 2: sum = 20 ✗
- Result: 1 ✓

Case 4: Non-square matrix (m ≠ n)
mat = [[1,1,1,1,1]], threshold = 3 (1×5 matrix)
- Max possible: min(1,5) = 1
- Result: 1 or 0 depending on values ✓

Case 5: Threshold is 0
mat = [[0,0],[0,0]], threshold = 0
- Only squares with all zeros valid
- Result: 2 if all zeros, 0 otherwise ✓

Case 6: Large sparse matrix
mat = 300×300 with mostly zeros, threshold = 10
- Binary search avoids checking all sizes
- Efficient even for large matrix ✓

 ------------------------------------------------------------
 🔍 Optimization Analysis:

Brute Force (without binary search):
- Check sizes 0 to min(m,n) sequentially
- For each size, check all positions
- Time: O(min(m,n) × m × n)
- For 300×300: ~27 million operations

With Binary Search:
- Check only O(log(min(m,n))) sizes
- For each, check all positions
- Time: O(log(min(m,n)) × m × n)
- For 300×300: log(300) ≈ 9, so ~900K operations
- Speedup: ~30x!

Prefix Sum Benefit:
- Without: each query is O(size²)
- With: each query is O(1)
- For size 100: 10,000x speedup per query!

Combined optimization is multiplicative!

 ------------------------------------------------------------
 🔍 Common Pitfalls:

❌ Mistake 1: Off-by-one in bounds
Wrong: i + size < rows
Correct: i + size - 1 < rows
Reason: size k square ends at index i+k-1

❌ Mistake 2: Lower-biased binary search
Wrong: mid = (left + right) / 2
Correct: mid = left + (right - left + 1) / 2
Reason: Can cause infinite loop, miss maximum

❌ Mistake 3: Forgetting corner in prefix query
Wrong: sum = prefix[r2][c2] - prefix[r1-1][c2] - prefix[r2][c1-1]
Correct: Add + prefix[r1-1][c1-1]
Reason: Corner subtracted twice, must add back

❌ Mistake 4: Not handling boundary conditions
Wrong: prefix[r1-1][c2] without checking r1 > 0
Correct: (r1 > 0) ? prefix[r1-1][c2] : 0
Reason: Negative index out of bounds

❌ Mistake 5: Wrong prefix sum formula
Wrong: prefix[i][j] = prefix[i-1][j] + prefix[i][j-1]
Correct: Also add mat[i][j] and subtract overlap
Reason: Current cell must be included

 ------------------------------------------------------------
 🔍 Why This Problem is Important:

Teaches fundamental techniques:
1. 2D Prefix Sum - essential for matrix problems
2. Binary Search on Answer - optimization pattern
3. Combining multiple techniques effectively
4. Handling 2D boundary conditions properly

Similar problems using same patterns:
- 304. Range Sum Query 2D - Immutable
- 1277. Count Square Submatrices with All Ones
- 1139. Largest 1-Bordered Square
- 1314. Matrix Block Sum

Real-world applications:
- Image processing (finding regions with property)
- Geographical data analysis (finding zones)
- Resource allocation (optimal area selection)
- Computer vision (object detection)

⚡ Performance Analysis:
This combined approach efficiently handles maximum constraints:
- Matrix size up to 300×300 = 90,000 cells
- Prefix sum building: O(90,000) operations
- Binary search iterations: log(300) ≈ 9
- Each iteration checks: up to 90,000 positions
- Each position query: O(1) with prefix sum
- Total: ~900,000 operations vs 27 million for brute force
- Execution time: <50ms for maximum input
- Space: 90,000 integers = ~360KB
- The combination is powerful:
  • Binary search: reduces iterations by 30x
  • Prefix sum: reduces each query from O(k²) to O(1)
  • Combined multiplicative benefit
- This demonstrates:
  • When to apply multiple optimization techniques
  • Binary search on answer pattern
  • 2D prefix sum mastery
  • Proper binary search implementation for maximization
- Why both optimizations are necessary:
  • Binary search alone: still slow queries
  • Prefix sum alone: still many sizes to check
  • Together: optimal solution
 ------------------------------------------------------------
*/