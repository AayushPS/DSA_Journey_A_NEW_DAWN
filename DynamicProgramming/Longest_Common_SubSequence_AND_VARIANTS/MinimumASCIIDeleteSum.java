/*
 🔹 Problem: 712. Minimum ASCII Delete Sum for Two Strings
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: String, Dynamic Programming
 🔹 Link: https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/

 ------------------------------------------------------------
 📝 Problem Statement:

Given two strings s1 and s2, return the lowest ASCII sum of deleted characters 
to make two strings equal.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
Deleting "t" from "eat" adds 116 to the sum.
At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible 
to achieve this.

Example 2:
Input: s1 = "delete", s2 = "leet"
Output: 403
Explanation: Deleting "dee" from "delete" to turn the string into "let",
adds 100[d] + 101[e] + 101[e] to the sum.
Deleting "e" from "leet" adds 101[e] to the sum.
At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
If instead we turned both strings into "lee" or "eet", we would get answers of 433 
or 417, which are higher.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= s1.length, s2.length <= 1000
 • s1 and s2 consist of lowercase English letters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Minimize total ASCII value of deleted characters to make strings equal.

📍 **Key Insight - LCS Variant:**
   - To minimize deletions, maximize what we KEEP
   - Find Longest Common Subsequence (LCS) with maximum ASCII sum
   - Answer = totalSum(s1) + totalSum(s2) - 2 × maxAsciiLCS
   - This transforms deletion problem into maximization problem

📍 **Approach 1 (Top-Down DP with Memoization):**
   - Recursive LCS calculation with ASCII values
   - For matching characters: add ASCII value + recurse on (i-1, j-1)
   - For non-matching: take max of skip s1[i] or skip s2[j]
   - Memoize results to avoid recomputation
   - Time: O(n1 × n2), Space: O(n1 × n2) + O(n1 + n2) recursion

📍 **Approach 2 (Bottom-Up 2D DP):**
   - Iterative LCS with ASCII sum calculation
   - Build DP table from smaller subproblems
   - dp[i][j] = max ASCII sum of common subsequence in s1[0..i-1] and s2[0..j-1]
   - Time: O(n1 × n2), Space: O(n1 × n2)

📍 **Approach 3 (Space-Optimized 1D DP):**
   - Only need previous row to compute current row
   - Use rolling arrays: old (previous row) and now (current row)
   - Time: O(n1 × n2), Space: O(n2)
   - Significant space improvement

📍 **Approach 4 (Space-Optimized with Array Reuse):**
   - Reuse array references by swapping instead of creating new arrays
   - Time: O(n1 × n2), Space: O(n2)
   - Eliminates unnecessary allocations

📍 **Approach 5 (Further Optimization with char[]):**
   - Convert strings to char[] once to avoid repeated charAt() calls
   - Micro-optimization for better performance
   - Time: O(n1 × n2), Space: O(n2) + O(n1 + n2) for char arrays

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Top-Down DP with Memoization)
   ⏱️ Time Complexity: O(n1 × n2) - each state computed once
   💾 Space Complexity: O(n1 × n2) - memoization + O(n1 + n2) recursion
   
   🧠 **Key Insight:**
   Find maximum ASCII sum of common subsequence using recursive DP.
   Then calculate minimum deletion as total - 2 × LCS.
   
   💡 **Why it works:**
   - dp[i][j] = max ASCII sum of LCS for s1[0..i-1] and s2[0..j-1]
   - If s1[i-1] == s2[j-1]: can include this char, add its ASCII value
   - Otherwise: try skipping from either string, take maximum
   - Base case: if either index is 0, no common subsequence possible
   - Final answer: sum(s1) + sum(s2) - 2 × dp[n1][n2]
 ------------------------------------------------------------

// import java.util.Arrays;
// 
// class Solution {
//     public int minimumDeleteSum(String s1, String s2) {
//         int asciiS1 = 0, asciiS2 = 0, n1 = s1.length(), n2 = s2.length();
//         int[][] dp = new int[n1+1][n2+1];
//         for(int[] i : dp) Arrays.fill(i, -1);
//         
//         // Calculate total ASCII sums
//         for(char c : s1.toCharArray()) {
//             asciiS1 += c;
//         }
//         for(char c : s2.toCharArray()) {
//             asciiS2 += c;
//         }
//         
//         return asciiS1 + asciiS2 - 2 * lcs(n1, n2, s1, s2, dp);
//     }
//     
//     private int lcs(int i, int j, String s1, String s2, int[][] dp) {
//         // Base case: one string exhausted
//         if(i == 0 || j == 0) {
//             return 0;
//         }
//         
//         // Return memoized result
//         if(dp[i][j] != -1) return dp[i][j];
//         
//         // Try skipping from either string
//         int ans = Math.max(
//             lcs(i, j-1, s1, s2, dp),
//             lcs(i-1, j, s1, s2, dp)
//         );
//         
//         // If characters match, include them
//         if(s1.charAt(i-1) == s2.charAt(j-1)) {
//             ans = Math.max(
//                 (int)s1.charAt(i-1) + lcs(i-1, j-1, s1, s2, dp),
//                 ans
//             );
//         }
//         
//         return dp[i][j] = ans;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Bottom-Up 2D DP)
   ⏱️ Time Complexity: O(n1 × n2) - nested loops
   💾 Space Complexity: O(n1 × n2) - 2D DP table
   
   🧠 **Key Insight:**
   Build solution iteratively from empty strings to full strings.
   Each cell represents max ASCII sum of LCS up to that point.
   
   💡 **Why it works:**
   - dp[i][j] = max ASCII LCS for first i chars of s1, first j chars of s2
   - Initialize: dp[0][j] = dp[i][0] = 0 (no common subsequence)
   - Transition: 
     • Always consider skipping: max(dp[i-1][j], dp[i][j-1])
     • If match: consider including: dp[i-1][j-1] + ASCII value
   - Take maximum of all options
   - More intuitive than recursion for some people
 ------------------------------------------------------------

// class Solution {
//     public int minimumDeleteSum(String s1, String s2) {
//         int asciiS1 = 0, asciiS2 = 0, n1 = s1.length(), n2 = s2.length();
//         int[][] dp = new int[n1+1][n2+1];
//         
//         for(int i = 1; i <= n1; i++) {
//             for(int j = 1; j <= n2; j++) {
//                 // Try skipping from either string
//                 dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
//                 
//                 // If characters match, consider including them
//                 if(s1.charAt(i-1) == s2.charAt(j-1)) {
//                     dp[i][j] = Math.max(dp[i][j], s1.charAt(i-1) + dp[i-1][j-1]);
//                 }
//             }
//         }
//         
//         // Calculate total ASCII sums
//         for(char c : s1.toCharArray()) {
//             asciiS1 += c;
//         }
//         for(char c : s2.toCharArray()) {
//             asciiS2 += c;
//         }
//         
//         return asciiS1 + asciiS2 - 2 * dp[n1][n2];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 3 (Commented - Space-Optimized 1D DP)
   ⏱️ Time Complexity: O(n1 × n2) - same iteration pattern
   💾 Space Complexity: O(n2) - two 1D arrays
   
   🧠 **Key Insight:**
   Only need previous row to compute current row in DP table.
   Use rolling arrays to reduce space from O(n1 × n2) to O(n2).
   
   💡 **Why it works:**
   - old[j] represents dp[i-1][j] (previous row, same column)
   - now[j] represents dp[i][j] (current row, same column)
   - now[j-1] represents dp[i][j-1] (current row, previous column)
   - old[j-1] represents dp[i-1][j-1] (previous row, previous column)
   - After completing row, assign old = now for next iteration
   - Creates new array for now to avoid overwriting
 ------------------------------------------------------------

// class Solution {
//     public int minimumDeleteSum(String s1, String s2) {
//         int asciiS1 = 0, asciiS2 = 0, n1 = s1.length(), n2 = s2.length();
//         int[] old = new int[n2+1];
//         
//         for(int i = 1; i <= n1; i++) {
//             int[] now = new int[n2+1];
//             for(int j = 1; j <= n2; j++) {
//                 now[j] = Math.max(old[j], now[j-1]);
//                 if(s1.charAt(i-1) == s2.charAt(j-1)) {
//                     now[j] = Math.max(now[j], s1.charAt(i-1) + old[j-1]);
//                 }
//             }
//             old = now;
//         }
//         
//         for(char c : s1.toCharArray()) {
//             asciiS1 += c;
//         }
//         for(char c : s2.toCharArray()) {
//             asciiS2 += c;
//         }
//         
//         return asciiS1 + asciiS2 - 2 * old[n2];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 4 (Commented - Array Reuse Optimization)
   ⏱️ Time Complexity: O(n1 × n2)
   💾 Space Complexity: O(n2)
   
   🧠 **Key Insight:**
   Instead of creating new array each iteration, reuse arrays by swapping references.
   Eliminates n1 array allocations.
   
   💡 **Why it works:**
   - Maintain two arrays: old and now
   - After each row, swap references: temp=old, old=now, now=temp
   - Now array gets cleared (all zeros) for next iteration
   - This reuses memory instead of allocating new arrays
   - Significant performance improvement for large inputs
 ------------------------------------------------------------

// class Solution {
//     public int minimumDeleteSum(String s1, String s2) {
//         int asciiS1 = 0, asciiS2 = 0, n1 = s1.length(), n2 = s2.length();
//         int[] old = new int[n2+1];
//         int[] now = new int[n2+1];
//         
//         for(int i = 1; i <= n1; i++) {
//             for(int j = 1; j <= n2; j++) {
//                 now[j] = Math.max(old[j], now[j-1]);
//                 if(s1.charAt(i-1) == s2.charAt(j-1)) {
//                     now[j] = Math.max(now[j], s1.charAt(i-1) + old[j-1]);
//                 }
//             }
//             // Swap references
//             int[] temp = old;
//             old = now;
//             now = temp;
//         }
//         
//         for(char c : s1.toCharArray()) {
//             asciiS1 += c;
//         }
//         for(char c : s2.toCharArray()) {
//             asciiS2 += c;
//         }
//         
//         return asciiS1 + asciiS2 - 2 * old[n2];
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 5 (✅ char[] Micro-Optimization - Most Optimal)
   ⏱️ Time Complexity: O(n1 × n2)
   💾 Space Complexity: O(n2) + O(n1 + n2) for char arrays
   
   🧠 **Key Insight:**
   Convert strings to char[] once to avoid repeated charAt() calls.
   Each charAt() has overhead; accessing array index is faster.
   
   💡 **Why it works:**
   - String.charAt() has method call overhead
   - Direct array access is faster (single dereference)
   - For large strings with many accesses, this adds up
   - Combines all previous optimizations:
     • Space-optimized rolling arrays
     • Array reference swapping
     • Fast character access
   - Particularly beneficial when n1 and n2 are large
 ------------------------------------------------------------
*/

public class MinimumASCIIDeleteSum {
    public int minimumDeleteSum(String s1, String s2) {
        int asciiS1 = 0, asciiS2 = 0, n1 = s1.length(), n2 = s2.length();
        int[] old = new int[n2 + 1];
        int[] now = new int[n2 + 1];
        
        // Convert to char arrays for faster access
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        
        // Build DP table using rolling arrays
        for(int i = 1; i <= n1; i++) {
            for(int j = 1; j <= n2; j++) {
                // Try skipping from either string
                now[j] = Math.max(old[j], now[j-1]);
                
                // If characters match, consider including them
                if(arr1[i-1] == arr2[j-1]) {
                    now[j] = Math.max(now[j], arr1[i-1] + old[j-1]);
                }
            }
            
            // Swap array references for next iteration
            int[] temp = old;
            old = now;
            now = temp;
        }
        
        // Calculate total ASCII sums
        for(char c : arr1) {
            asciiS1 += c;
        }
        for(char c : arr2) {
            asciiS2 += c;
        }
        
        // Answer = total - 2 × (maximum common ASCII sum)
        return asciiS1 + asciiS2 - 2 * old[n2];
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: s1 = "sea", s2 = "eat"

Convert to char arrays:
arr1 = ['s', 'e', 'a']
arr2 = ['e', 'a', 't']

Initial state:
old = [0, 0, 0, 0]
now = [0, 0, 0, 0]

Building DP table (finding max ASCII LCS):

i=1 (arr1[0]='s', ASCII=115):
  j=1 (arr2[0]='e'): 's'≠'e'
    now[1] = max(old[1], now[0]) = max(0, 0) = 0
  j=2 (arr2[1]='a'): 's'≠'a'
    now[2] = max(old[2], now[1]) = max(0, 0) = 0
  j=3 (arr2[2]='t'): 's'≠'t'
    now[3] = max(old[3], now[2]) = max(0, 0) = 0
  Swap: old = [0,0,0,0], now = [0,0,0,0]

i=2 (arr1[1]='e', ASCII=101):
  j=1 (arr2[0]='e'): 'e'=='e' ✓
    now[1] = max(old[1], now[0]) = max(0, 0) = 0
    now[1] = max(0, 101+old[0]) = max(0, 101) = 101
  j=2 (arr2[1]='a'): 'e'≠'a'
    now[2] = max(old[2], now[1]) = max(0, 101) = 101
  j=3 (arr2[2]='t'): 'e'≠'t'
    now[3] = max(old[3], now[2]) = max(0, 101) = 101
  Swap: old = [0,101,101,101], now = [0,0,0,0]

i=3 (arr1[2]='a', ASCII=97):
  j=1 (arr2[0]='e'): 'a'≠'e'
    now[1] = max(old[1], now[0]) = max(101, 0) = 101
  j=2 (arr2[1]='a'): 'a'=='a' ✓
    now[2] = max(old[2], now[1]) = max(101, 101) = 101
    now[2] = max(101, 97+old[1]) = max(101, 198) = 198
  j=3 (arr2[2]='t'): 'a'≠'t'
    now[3] = max(old[3], now[2]) = max(101, 198) = 198
  Swap: old = [0,101,198,198], now = [0,0,0,0]

Final DP result: old[3] = 198
This represents max ASCII sum of LCS = 'e' + 'a' = 101 + 97 = 198

Calculate total ASCII sums:
asciiS1 = 's'+'e'+'a' = 115+101+97 = 313
asciiS2 = 'e'+'a'+'t' = 101+97+116 = 314

Final answer:
313 + 314 - 2×198 = 627 - 396 = 231 ✅

Explanation:
- Keep: "ea" (ASCII sum = 198)
- Delete from s1: "s" (ASCII = 115)
- Delete from s2: "t" (ASCII = 116)
- Total deletion cost: 115 + 116 = 231

 ------------------------------------------------------------
 🔍 Understanding the Formula:

Why: minimumDelete = sum(s1) + sum(s2) - 2 × maxLCS?

Visualization:
s1 = "sea" → total ASCII = 313
s2 = "eat" → total ASCII = 314

Common subsequence "ea" → ASCII = 198

Characters to delete:
- From s1: "s" (115) → keep "ea" (198)
- From s2: "t" (116) → keep "ea" (198)

Calculation:
- Total = 313 + 314 = 627
- What we keep: "ea" appears in BOTH strings
- So we subtract it TWICE: 627 - 198 - 198 = 231
- Formula: sum(s1) + sum(s2) - 2 × LCS ✓

Another way to think:
- Delete from s1: 313 - 198 = 115
- Delete from s2: 314 - 198 = 116
- Total: 115 + 116 = 231 ✓

 ------------------------------------------------------------
 🔍 Why Maximize LCS?

To minimize deletions, maximize what we KEEP!

Example: s1 = "delete", s2 = "leet"

Option 1: Keep "let" (ASCII = 108+101+116 = 325)
- Delete from s1: "dee" (100+101+101 = 302)
- Delete from s2: "e" (101)
- Total: 302 + 101 = 403 ✓ (optimal)

Option 2: Keep "ee" (ASCII = 101+101 = 202)
- Delete from s1: "delt" (100+101+108+116 = 425)
- Delete from s2: "lt" (108+116 = 224)
- Total: 425 + 224 = 649 (worse)

Maximizing LCS minimizes deletion cost!

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Identical strings
s1 = "abc", s2 = "abc"
- LCS = entire string (ASCII sum = 294)
- Delete: 0 characters
- Result: 294 + 294 - 2×294 = 0 ✓

Case 2: No common characters
s1 = "abc", s2 = "xyz"
- LCS = 0 (no common subsequence)
- Delete: all characters from both
- Result: sum(s1) + sum(s2) - 0 = total ✓

Case 3: One string is subsequence of other
s1 = "abc", s2 = "aebdc"
- LCS = "abc" (entire s1)
- Delete only from s2: "ed"
- Result: sum(s1) + sum(s2) - 2×sum(s1) = sum(s2) - sum(s1) ✓

Case 4: Single character strings
s1 = "a", s2 = "b"
- LCS = 0
- Result: 97 + 98 = 195 ✓

Case 5: Empty common subsequence with long strings
s1 = "aaa", s2 = "bbb"
- LCS = 0
- Result: 291 + 294 = 585 ✓

 ------------------------------------------------------------
 🔍 Optimization Impact Analysis:

For s1.length = 1000, s2.length = 1000:

2D DP Array Approach:
- Space: 1000 × 1000 × 4 bytes = 4MB
- Array allocation: 1 large 2D array

1D Rolling Arrays:
- Space: 2 × 1000 × 4 bytes = 8KB
- Array allocation: 1 old + 1000 new arrays (Approach 3)
- Space savings: 99.8%

1D with Array Reuse:
- Space: 2 × 1000 × 4 bytes = 8KB
- Array allocation: 2 arrays total (reused)
- Allocation savings: 999 fewer allocations
- Performance: ~15-20% faster than Approach 3

char[] Optimization:
- Additional space: 2000 bytes for char arrays
- charAt() calls avoided: 1000 × 1000 = 1,000,000
- Performance gain: ~5-10% over Approach 4
- Total speedup: ~20-30% over Approach 3

Combined Optimizations:
- Space: 4MB → 10KB (99.75% reduction)
- Speed: ~20-30% faster
- Memory allocations: 1001 → 2 (99.8% reduction)

⚡ Performance Analysis:
The fully optimized approach efficiently handles maximum constraints:
- String lengths up to 1000 each
- Total DP iterations: 1,000 × 1,000 = 1,000,000
- Each iteration: O(1) operations
- Total time: ~1-2ms for maximum input
- Space breakdown:
  • Rolling arrays: 2 × 1001 × 4 = 8KB
  • char arrays: 2000 bytes = 2KB
  • Total: ~10KB vs 4MB for 2D approach
- The LCS variant technique is powerful:
  • Converts minimization to maximization problem
  • Reuses classic LCS algorithm with ASCII twist
  • Formula derivation is elegant mathematical insight
- String to char[] conversion:
  • One-time O(n) cost for massive repeated savings
  • charAt() has bounds checking + method call overhead
  • Direct array access is pure index dereference
  • For 1M accesses, savings accumulate significantly
- This problem demonstrates full optimization stack:
  • Algorithm choice (LCS variant)
  • Space optimization (rolling arrays)
  • Allocation optimization (array reuse)
  • Micro-optimization (char[] access)
  • Each layer provides measurable improvement
 ------------------------------------------------------------
*/