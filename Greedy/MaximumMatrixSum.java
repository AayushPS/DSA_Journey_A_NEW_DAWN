/*
 🔹 Problem: 1975. Maximum Matrix Sum
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Greedy, Matrix, Math
 🔹 Link: https://leetcode.com/problems/maximum-matrix-sum/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an n x n integer matrix. You can do the following operation any 
number of times:
• Choose any two adjacent elements of matrix and multiply each of them by -1.

Two elements are considered adjacent if and only if they share a border.

Your goal is to maximize the summation of the matrix's elements. Return the maximum 
sum of the matrix's elements using the operation mentioned above.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: matrix = [[1,-1],[-1,1]]
Output: 4
Explanation: We can follow the following steps to reach sum equals 4:
- Multiply the 2 elements in the first row by -1.
- Multiply the 2 elements in the first column by -1.

Example 2:
Input: matrix = [[1,2,3],[-1,-2,-3],[1,2,3]]
Output: 16
Explanation: We can follow the following step to reach sum equals 16:
- Multiply the 2 last elements in the second row by -1.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • n == matrix.length == matrix[i].length
 • 2 <= n <= 250
 • -10^5 <= matrix[i][j] <= 10^5

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize the sum of matrix elements using adjacent flip operations.

📍 **Approach (Greedy - Parity Observation):**
   - Key insight: Each operation flips signs of TWO adjacent elements
   - Count negative numbers in the matrix
   - If count is EVEN: all negatives can be eliminated → sum of absolute values
   - If count is ODD: exactly one negative must remain → keep smallest absolute value negative
   - Track: total sum of absolute values, count of negatives, minimum absolute value
   - Time: O(n²), Space: O(1)
   - Mathematical proof based on parity invariance

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Greedy - Parity Observation)
   ⏱️ Time Complexity: O(n²) - single pass through matrix
   💾 Space Complexity: O(1) - only tracking few variables
   
   🧠 **Key Insight:**
   The critical observation is about PARITY of negative numbers:
   - Each operation flips the sign of TWO elements simultaneously
   - This means the PARITY of negative count changes by ±2, 0, or stays same
   - If we have EVEN negatives: we can eliminate all of them (flip pairs)
   - If we have ODD negatives: exactly ONE must remain negative
   - When one must remain negative, choose the SMALLEST absolute value
   
   💡 **Why it works:**
   Mathematical proof:
   1. Each operation affects exactly 2 adjacent elements
   2. Three cases per operation:
      a) Both positive: count stays same (bad move)
      b) Both negative: count decreases by 2 (reduces negatives)
      c) One of each: count stays same (transfers negativity)
   3. Therefore, parity of negative count is invariant modulo 2
   4. If initial count is even, we can reach 0 negatives
   5. If initial count is odd, we can reach 1 negative (minimum)
   6. To maximize sum with 1 negative, choose smallest absolute value
   
   🎯 **Algorithm:**
   1. Initialize: totalSum = 0, negativeCount = 0, minAbsValue = MAX
   2. For each element in matrix:
      - If negative, toggle parity counter (using boolean)
      - Add absolute value to totalSum
      - Track minimum absolute value seen
   3. If negative count is ODD:
      - Subtract 2 × minAbsValue (flip smallest from + to -)
   4. Return totalSum
 ------------------------------------------------------------
*/

public class MaximumMatrixSum {
    public long maxMatrixSum(int[][] matrix) {
        long totalSum = 0;
        int minAbsValue = Integer.MAX_VALUE;
        boolean hasOddNegatives = false;
        
        // Single pass through matrix
        for(int[] row : matrix) {
            for(int el : row) {
                // Count negatives using parity (toggle boolean)
                if(el < 0) {
                    hasOddNegatives = !hasOddNegatives;
                    el = -el; // Convert to positive for calculations
                }
                
                // Track minimum absolute value
                minAbsValue = Math.min(minAbsValue, el);
                
                // Add absolute value to total
                totalSum += el;
            }
        }
        
        // If odd number of negatives, one must remain negative
        // Choose the smallest absolute value to minimize loss
        if(hasOddNegatives) {
            totalSum -= 2 * minAbsValue;
        }
        
        return totalSum;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: matrix = [[1,2,3],[-1,-2,-3],[1,2,3]]

Processing:
Row 0:
  el = 1: positive → totalSum = 1, minAbsValue = 1, hasOddNegatives = false
  el = 2: positive → totalSum = 3, minAbsValue = 1, hasOddNegatives = false
  el = 3: positive → totalSum = 6, minAbsValue = 1, hasOddNegatives = false

Row 1:
  el = -1: negative → hasOddNegatives = true, el = 1
          → totalSum = 7, minAbsValue = 1
  el = -2: negative → hasOddNegatives = false, el = 2
          → totalSum = 9, minAbsValue = 1
  el = -3: negative → hasOddNegatives = true, el = 3
          → totalSum = 12, minAbsValue = 1

Row 2:
  el = 1: positive → totalSum = 13, minAbsValue = 1, hasOddNegatives = true
  el = 2: positive → totalSum = 15, minAbsValue = 1, hasOddNegatives = true
  el = 3: positive → totalSum = 18, minAbsValue = 1, hasOddNegatives = true

Final state:
- totalSum = 18 (sum of all absolute values)
- minAbsValue = 1
- hasOddNegatives = true (3 negatives is odd)

Calculation:
- Since hasOddNegatives = true, subtract 2 × minAbsValue
- totalSum = 18 - 2 × 1 = 16

Result: 16 ✅

Explanation:
We had 3 negatives (odd count), so one must remain negative.
The optimal choice is to keep the smallest absolute value (1) negative.
Final configuration: [1, 2, 3, -1, 2, 3, 1, 2, 3] → sum = 16

 ------------------------------------------------------------
 🔍 Another Example:

Input: matrix = [[1,-1],[-1,1]]

Processing:
  el = 1: positive → totalSum = 1, minAbsValue = 1, hasOddNegatives = false
  el = -1: negative → hasOddNegatives = true, el = 1
          → totalSum = 2, minAbsValue = 1
  el = -1: negative → hasOddNegatives = false, el = 1
          → totalSum = 3, minAbsValue = 1
  el = 1: positive → totalSum = 4, minAbsValue = 1, hasOddNegatives = false

Final state:
- totalSum = 4
- hasOddNegatives = false (2 negatives is even)

Result: 4 ✅

Explanation:
Even number of negatives (2) means we can eliminate all negatives.
Operations:
1. Flip row 0: [1,-1] → [-1,1]
2. Flip col 0: [-1,-1] → [1,1]
Final: [[1,1],[1,1]] → sum = 4

 ------------------------------------------------------------
 🔍 Operation Mechanics Deep Dive:

Understanding why parity matters:

Case 1: Two adjacent negatives
[-3, -5] → flip both → [3, 5]
Net effect: -2 negatives (parity changes by 2)

Case 2: One negative, one positive
[-3, 5] → flip both → [3, -5]
Net effect: still 1 negative (parity unchanged)

Case 3: Two positives
[3, 5] → flip both → [-3, -5]
Net effect: +2 negatives (bad move, never do this)

Key observation:
- We can always transfer negativity between adjacent cells
- We can eliminate pairs of negatives
- Parity (odd/even) of negative count is INVARIANT modulo 2

Visual example with 3 negatives:
[-2, -4, -6, 8]
Step 1: Flip first two → [2, 4, -6, 8] (1 negative left)
We cannot eliminate the last negative without creating another!

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: All positive numbers
matrix = [[1,2],[3,4]]
- No negatives (even count = 0)
- hasOddNegatives = false
- Return sum of all values: 1+2+3+4 = 10 ✓

Case 2: All negative numbers (even count)
matrix = [[-1,-2],[-3,-4]]
- 4 negatives (even count)
- hasOddNegatives = false
- Return sum of absolute values: 1+2+3+4 = 10 ✓

Case 3: All negative numbers (odd count)
matrix = [[-1,-2],[-3]]  (hypothetical 2x2 with 3 elements)
- 3 negatives (odd count)
- hasOddNegatives = true
- minAbsValue = 1
- totalSum = 6 - 2×1 = 4 ✓

Case 4: Contains zero
matrix = [[0,-5],[3,2]]
- 1 negative (odd count)
- minAbsValue = 0 (zero is smallest)
- totalSum = 0+5+3+2 = 10
- Result = 10 - 2×0 = 10
- Perfect! Zero absorbs the remaining negative ✓

Case 5: Large numbers
matrix = [[-100000,-100000],[100000,100000]]
- 2 negatives (even)
- No adjustment needed
- Result = 400000 (fits in long) ✓

 ------------------------------------------------------------
 🔍 Why Boolean Toggle for Parity?

Instead of counting negatives with an integer:
int negativeCount = 0;
for(...) {
    if(el < 0) negativeCount++;
}
if(negativeCount % 2 == 1) ...

We use boolean toggle:
boolean hasOddNegatives = false;
for(...) {
    if(el < 0) hasOddNegatives = !hasOddNegatives;
}
if(hasOddNegatives) ...

Benefits:
✅ More memory efficient (1 bit vs 32 bits)
✅ Faster (no modulo operation needed)
✅ Clearer intent (we only care about parity, not count)
✅ Elegant: toggle naturally represents parity switching

⚡ Performance Analysis:
This greedy approach is optimal for the given constraints:
- Matrix size up to 250×250 = 62,500 elements
- Single pass: O(62,500) operations
- No recursion, no sorting, no complex data structures
- Space: O(1) - only 3 variables (long, int, boolean)
- Time per element: O(1) - simple comparisons and arithmetic
- Total time: ~0.1ms for 250×250 matrix
- The mathematical insight (parity invariance) reduces what could be an
  exponential search problem into a simple linear scan
- No need to simulate actual operations or track adjacency
- Works for any matrix size within constraints
- Handles edge cases (zeros, all positive, all negative) naturally
 ------------------------------------------------------------
*/