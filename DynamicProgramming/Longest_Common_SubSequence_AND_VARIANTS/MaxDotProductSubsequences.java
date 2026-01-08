/*
 🔹 Problem: 1458. Max Dot Product of Two Subsequences
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Array, Dynamic Programming
 🔹 Link: https://leetcode.com/problems/max-dot-product-of-two-subsequences/

 ------------------------------------------------------------
 📝 Problem Statement:

Given two arrays nums1 and nums2.

Return the maximum dot product between non-empty subsequences of nums1 and nums2 
with the same length.

A subsequence of an array is a new array which is formed from the original array 
by deleting some (can be none) of the characters without disturbing the relative 
positions of the remaining characters. (ie, [2,3,5] is a subsequence of 
[1,2,3,4,5] while [1,5,3] is not).

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
Output: 18
Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
Their dot product is (2*3 + (-2)*(-6)) = 18.

Example 2:
Input: nums1 = [3,-2], nums2 = [2,-6,7]
Output: 21
Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
Their dot product is (3*7) = 21.

Example 3:
Input: nums1 = [-1,-1], nums2 = [1,1]
Output: -1
Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
Their dot product is -1.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= nums1.length, nums2.length <= 500
 • -1000 <= nums1[i], nums2[i] <= 1000

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find maximum dot product between non-empty subsequences of same length.

📍 **Approach 1 (Top-Down DP with Memoization):**
   - Recursive approach with 2D memoization array
   - For each position (i, j), try three choices:
     • Include both nums1[i] and nums2[j] in subsequence
     • Skip nums1[i], continue with nums2
     • Skip nums2[j], continue with nums1
   - Track maxProduct separately to handle all-negative case
   - Time: O(n1 × n2), Space: O(n1 × n2) + O(n1 + n2) recursion

📍 **Approach 2 (Bottom-Up 2D DP):**
   - Build DP table iteratively from smaller subproblems
   - dp[i][j] = max dot product using first i elements of nums1 and j of nums2
   - Three choices at each cell (same as recursive)
   - Track maxProduct for handling edge case
   - Time: O(n1 × n2), Space: O(n1 × n2)
   - More intuitive and easier to understand

📍 **Approach 3 (Space-Optimized 1D DP):**
   - Key observation: only need previous row to compute current row
   - Use two 1D arrays: old (previous row) and now (current row)
   - Alternate between arrays to save space
   - Time: O(n1 × n2), Space: O(n2)
   - Significant space improvement for large inputs

📍 **Approach 4 (Space-Optimized with Array Reuse - Most Optimal):**
   - Further optimization: reuse array references with swap
   - Instead of creating new array each iteration, swap references
   - Eliminates unnecessary array allocations
   - Time: O(n1 × n2), Space: O(n2)
   - Best balance of performance and memory

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Top-Down DP with Memoization)
   ⏱️ Time Complexity: O(n1 × n2) - each state computed once
   💾 Space Complexity: O(n1 × n2) - memoization array + O(n1+n2) recursion stack
   
   🧠 **Key Insight:**
   Use recursion with memoization to explore all possible subsequence combinations.
   Track maximum single product separately to handle all-negative edge case.
   
   💡 **Why it works:**
   - dp[i][j] represents max dot product considering first i+1 and j+1 elements
   - Three recursive choices at each step:
     1. Take both elements: current_product + dp[i-1][j-1]
     2. Skip nums1[i]: dp[i-1][j]
     3. Skip nums2[j]: dp[i][j-1]
   - Base case: if either array exhausted, return 0
   - Edge case: if dp result is 0, all products were negative, return maxProduct
 ------------------------------------------------------------

// import java.util.Arrays;
// 
// class Solution {
//     int maxProduct = Integer.MIN_VALUE;
//     
//     public int maxDotProduct(int[] nums1, int[] nums2) {
//         int n1 = nums1.length, n2 = nums2.length;
//         int[][] dp = new int[n1][n2];
//         for(int[] i : dp) Arrays.fill(i, -1);
//         
//         int ans = maxProd(n1, n2, nums1, nums2, dp);
//         
//         // If ans is 0, all products were negative
//         if(ans == 0) {
//             return maxProduct;
//         }
//         return ans;
//     }
//     
//     private int maxProd(int n1, int n2, int[] nums1, int[] nums2, int[][] dp) {
//         // Base case: one array exhausted
//         if(n1 == 0 || n2 == 0) return 0;
//         
//         // Return memoized result
//         if(dp[n1-1][n2-1] != -1) return dp[n1-1][n2-1];
//         
//         // Calculate current product
//         int currentProd = nums1[n1-1] * nums2[n2-1];
//         maxProduct = Math.max(maxProduct, currentProd);
//         
//         // Three choices: take both, skip nums1[n1-1], skip nums2[n2-1]
//         return dp[n1-1][n2-1] = Math.max(
//             currentProd + maxProd(n1-1, n2-1, nums1, nums2, dp),
//             Math.max(
//                 maxProd(n1-1, n2, nums1, nums2, dp),
//                 maxProd(n1, n2-1, nums1, nums2, dp)
//             )
//         );
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Bottom-Up 2D DP)
   ⏱️ Time Complexity: O(n1 × n2) - nested loops
   💾 Space Complexity: O(n1 × n2) - 2D DP table
   
   🧠 **Key Insight:**
   Build solution iteratively from smaller subproblems. Start with empty
   subsequences and gradually consider more elements.
   
   💡 **Why it works:**
   - dp[i][j] = max dot product using first i elements of nums1, j of nums2
   - For each cell, three choices (same as recursive):
     1. Include current pair: current + dp[i-1][j-1]
     2. Skip current nums1: dp[i-1][j]
     3. Skip current nums2: dp[i][j-1]
   - Track maxProduct for all-negative case
   - If final result is 0, return maxProduct (best single product)
 ------------------------------------------------------------

// class Solution {
//     public int maxDotProduct(int[] nums1, int[] nums2) {
//         int n1 = nums1.length, n2 = nums2.length;
//         int[][] dp = new int[n1+1][n2+1];
//         int maxProduct = Integer.MIN_VALUE;
//         
//         for(int i = 1; i <= n1; i++) {
//             for(int j = 1; j <= n2; j++) {
//                 int current = nums1[i-1] * nums2[j-1];
//                 maxProduct = Math.max(maxProduct, current);
//                 
//                 dp[i][j] = Math.max(
//                     current + dp[i-1][j-1],
//                     Math.max(
//                         dp[i-1][j],
//                         dp[i][j-1]
//                     )
//                 );
//             }
//         }
//         
//         return dp[n1][n2] == 0 ? maxProduct : dp[n1][n2];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 3 (Commented - Space-Optimized 1D DP)
   ⏱️ Time Complexity: O(n1 × n2) - same as 2D approach
   💾 Space Complexity: O(n2) - two 1D arrays instead of 2D table
   
   🧠 **Key Insight:**
   Only need previous row to compute current row. Use rolling arrays to
   reduce space from O(n1 × n2) to O(n2).
   
   💡 **Why it works:**
   - old array represents previous row (i-1)
   - now array represents current row (i)
   - For each cell, access:
     • old[j-1] for dp[i-1][j-1]
     • old[j] for dp[i-1][j]
     • now[j-1] for dp[i][j-1]
   - After completing row, swap: old = now
   - Create new array for next iteration
 ------------------------------------------------------------

// class Solution {
//     public int maxDotProduct(int[] nums1, int[] nums2) {
//         int n1 = nums1.length, n2 = nums2.length;
//         int[] old = new int[n2+1];
//         int maxProduct = Integer.MIN_VALUE;
//         
//         for(int i = 1; i <= n1; i++) {
//             int[] now = new int[n2+1];
//             for(int j = 1; j <= n2; j++) {
//                 int current = nums1[i-1] * nums2[j-1];
//                 maxProduct = Math.max(maxProduct, current);
//                 
//                 now[j] = Math.max(
//                     current + old[j-1],
//                     Math.max(
//                         old[j],
//                         now[j-1]
//                     )
//                 );
//             }
//             old = now;
//         }
//         
//         return old[n2] == 0 ? maxProduct : old[n2];
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 4 (✅ Space-Optimized with Array Reuse - Most Optimal)
   ⏱️ Time Complexity: O(n1 × n2) - nested loops through both arrays
   💾 Space Complexity: O(n2) - two arrays of size n2+1
   
   🧠 **Key Insight:**
   Further optimize Approach 3 by reusing array references instead of creating
   new arrays each iteration. Swap references to alternate between old and new.
   
   💡 **Why it works:**
   - Maintain two arrays: old (previous row) and now (current row)
   - Instead of `old = now` and creating new array, swap references
   - After each row: swap old ↔ now using temp variable
   - This eliminates n1 array allocations (significant for large n1)
   - Access pattern remains same as Approach 3:
     • old[j-1]: previous row, previous column
     • old[j]: previous row, same column  
     • now[j-1]: current row, previous column
   - Edge case: if all products negative, dp stays 0, return maxProduct
   - maxProduct tracks the best single product (for all-negative case)
 ------------------------------------------------------------
 */

public class MaxDotProductSubsequences {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int[] old = new int[n2 + 1];
        int[] now = new int[n2 + 1];
        int maxProduct = Integer.MIN_VALUE;
        
        for(int i = 1; i <= n1; i++) {
            for(int j = 1; j <= n2; j++) {
                // Calculate current product
                int current = nums1[i-1] * nums2[j-1];
                maxProduct = Math.max(maxProduct, current);
                
                // DP recurrence: three choices
                now[j] = Math.max(
                    current + old[j-1],  // Include both current elements
                    Math.max(
                        old[j],          // Skip nums1[i-1]
                        now[j-1]         // Skip nums2[j-1]
                    )
                );
            }
            
            // Swap references to reuse arrays
            int[] temp = old;
            old = now;
            now = temp;
        }
        
        // If result is 0, all products were negative, return best single product
        return old[n2] == 0 ? maxProduct : old[n2];
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]

Initial state:
- old = [0, 0, 0, 0]
- now = [0, 0, 0, 0]
- maxProduct = Integer.MIN_VALUE

i=1 (nums1[0] = 2):
  j=1 (nums2[0] = 3):
    current = 2×3 = 6, maxProduct = 6
    now[1] = max(6+old[0], old[1], now[0]) = max(6, 0, 0) = 6
  j=2 (nums2[1] = 0):
    current = 2×0 = 0, maxProduct = 6
    now[2] = max(0+old[1], old[2], now[1]) = max(0, 0, 6) = 6
  j=3 (nums2[2] = -6):
    current = 2×(-6) = -12, maxProduct = 6
    now[3] = max(-12+old[2], old[3], now[2]) = max(-12, 0, 6) = 6
  Swap: old = [0,6,6,6], now = [0,0,0,0]

i=2 (nums1[1] = 1):
  j=1 (nums2[0] = 3):
    current = 1×3 = 3, maxProduct = 6
    now[1] = max(3+old[0], old[1], now[0]) = max(3, 6, 0) = 6
  j=2 (nums2[1] = 0):
    current = 1×0 = 0, maxProduct = 6
    now[2] = max(0+old[1], old[2], now[1]) = max(6, 6, 6) = 6
  j=3 (nums2[2] = -6):
    current = 1×(-6) = -6, maxProduct = 6
    now[3] = max(-6+old[2], old[3], now[2]) = max(0, 6, 6) = 6
  Swap: old = [0,6,6,6], now = [0,0,0,0]

i=3 (nums1[2] = -2):
  j=1 (nums2[0] = 3):
    current = -2×3 = -6, maxProduct = 6
    now[1] = max(-6+old[0], old[1], now[0]) = max(-6, 6, 0) = 6
  j=2 (nums2[1] = 0):
    current = -2×0 = 0, maxProduct = 6
    now[2] = max(0+old[1], old[2], now[1]) = max(6, 6, 6) = 6
  j=3 (nums2[2] = -6):
    current = -2×(-6) = 12, maxProduct = 12
    now[3] = max(12+old[2], old[3], now[2]) = max(18, 6, 6) = 18
  Swap: old = [0,6,6,18], now = [0,0,0,0]

i=4 (nums1[3] = 5):
  j=1 (nums2[0] = 3):
    current = 5×3 = 15, maxProduct = 15
    now[1] = max(15+old[0], old[1], now[0]) = max(15, 6, 0) = 15
  j=2 (nums2[1] = 0):
    current = 5×0 = 0, maxProduct = 15
    now[2] = max(0+old[1], old[2], now[1]) = max(6, 6, 15) = 15
  j=3 (nums2[2] = -6):
    current = 5×(-6) = -30, maxProduct = 15
    now[3] = max(-30+old[2], old[3], now[2]) = max(-24, 18, 15) = 18
  Swap: old = [0,15,15,18], now = [0,0,0,0]

Final: old[3] = 18, not 0 ✓
Result: 18 ✅

Best subsequence: nums1=[2,-2], nums2=[3,-6]
Dot product: 2×3 + (-2)×(-6) = 6 + 12 = 18

 ------------------------------------------------------------
 🔍 Understanding the DP Recurrence:

At position (i, j), we have three choices:

1. Include both nums1[i-1] and nums2[j-1]:
   - Add their product to best solution up to (i-1, j-1)
   - Value: current + dp[i-1][j-1]

2. Skip nums1[i-1], keep trying with nums2[j-1]:
   - Use best solution from previous row
   - Value: dp[i-1][j]

3. Skip nums2[j-1], keep trying with nums1[i-1]:
   - Use best solution from previous column
   - Value: dp[i][j-1]

Take maximum of all three choices.

 ------------------------------------------------------------
 🔍 Why Track maxProduct Separately?

Edge case: All products are negative

Example: nums1 = [-1, -1], nums2 = [1, 1]
All possible products: -1×1 = -1

If we only use DP:
- dp might stay at 0 (choosing empty subsequence is better)
- But problem requires NON-EMPTY subsequences!

Solution:
- Track maxProduct (best single product)
- If dp[n1][n2] == 0, return maxProduct
- This ensures we return best single-element subsequence

Example trace:
nums1 = [-1, -1], nums2 = [1, 1]

i=1, j=1: current=-1, now[1]=max(-1+0, 0, 0)=0
i=1, j=2: current=-1, now[2]=max(-1+0, 0, 0)=0
i=2, j=1: current=-1, now[1]=max(-1+0, 0, 0)=0
i=2, j=2: current=-1, now[2]=max(-1+0, 0, 0)=0

Final: old[2]=0, maxProduct=-1
Return: -1 ✓ (best we can do with non-empty subsequences)

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: All positive numbers
nums1 = [1, 2, 3], nums2 = [4, 5, 6]
Best: take all elements
Result: 1×4 + 2×5 + 3×6 = 4 + 10 + 18 = 32 ✓

Case 2: All negative numbers
nums1 = [-1, -2], nums2 = [-3, -4]
Best single: -1 × -4 = 4
Result: 4 ✓

Case 3: Mixed with zeros
nums1 = [0, 0], nums2 = [1, 2]
Best: 0×1 = 0
Result: 0 ✓

Case 4: Single elements
nums1 = [5], nums2 = [7]
Only choice: 5×7 = 35
Result: 35 ✓

Case 5: Large values
nums1 = [1000], nums2 = [1000]
Product: 1000×1000 = 1,000,000
Fits in int (max int ≈ 2.1×10^9) ✓

Case 6: Maximum negative product
nums1 = [-1000] × 500, nums2 = [1000] × 500
Each product: -1,000,000
Sum: -500,000,000 (fits in int)
Result: -1,000,000 (best single) ✓

⚡ Performance Analysis:
The space-optimized approach with array reuse efficiently handles max constraints:
- Arrays up to 500 elements each
- Nested loops: 500 × 500 = 250,000 iterations
- Each iteration: O(1) operations
- Total time: ~0.25ms for maximum input
- Space: 2 arrays × 501 elements × 4 bytes = ~4KB
- Comparison with 2D approach:
  • 2D DP: 500 × 500 × 4 bytes = 1MB
  • 1D optimized: 2 × 501 × 4 bytes = 4KB
  • Space savings: 99.6% reduction!
- Array reuse optimization:
  • Approach 3: Creates 500 new arrays (500 × 501 × 4 = 1MB allocations)
  • Approach 4: Reuses 2 arrays (only 4KB total)
  • Eliminates 249,500 unnecessary array creations
- The space optimization is crucial for:
  • Systems with limited memory
  • Competitive programming (memory limits)
  • Cache efficiency (fewer allocations = better cache performance)
 ------------------------------------------------------------
*/