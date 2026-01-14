/*
 🔹 Problem: Rod Cutting
 🔹 Platform: GeeksForGeeks
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Unbounded Knapsack
 🔹 Link: https://practice.geeksforgeeks.org/problems/rod-cutting0840/1

 ------------------------------------------------------------
 📝 Problem Statement:

Given a rod of length n inches and an array price[], where price[i] denotes the value of a piece of length i.
Your task is to determine the maximum value obtainable by cutting up the rod and selling the pieces.

Note:
- n = size of price array
- price[] is a 1-indexed array logically (price[0] corresponds to length 1)

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: price[] = [1, 5, 8, 9, 10, 17, 17, 20]
Output: 22
Explanation: The rod is cut into pieces of lengths 2 and 6 → 5 + 17 = 22

Example 2:
Input: price[] = [3, 5, 8, 9, 10, 17, 17, 20]
Output: 24
Explanation: Cut into 8 pieces of length 1 → 8 × 3 = 24

Example 3:
Input: price[] = [3]
Output: 3
Explanation: Only one piece of length 1 is possible.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ price.size() ≤ 10^3
 • 1 ≤ price[i] ≤ 10^6

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize revenue by cutting the rod into pieces such that the total price is maximum.

📍 **Approach 1 (Recursion + Memoization):**
   - Top-down DP similar to unbounded knapsack.
   - Either take current length or skip it.
   - Uses memo table to avoid recomputation.

📍 **Approach 2 (2D Tabulation):**
   - Bottom-up DP.
   - dp[i][j] represents max value using pieces up to length i for rod length j.

📍 **Approach 3 (1D Optimization):**
   - Space optimized DP.
   - Only keeps previous and current rows.

📍 **Approach 4 (✅ Space Optimized with Array Reuse - Most Optimal):**
   - Same DP as Approach 3.
   - Reuses arrays instead of re-allocating each iteration.
   - Best balance of time and memory.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Recursion + Memoization)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n²) + recursion stack

   🧠 **Key Insight:**
   At every step, choose whether to cut a piece of current length or move to smaller lengths.

   💡 **Why it works:**
   This is an unbounded knapsack problem where each length can be used multiple times.
 ------------------------------------------------------------

*/

// import statements
// import java.util.*;

// class Solution {
//     public int cutRod(int[] price) {
//         int n = price.length;
//         int[][] dp = new int[n+1][n+1];
//         for(int[] arr: dp) Arrays.fill(arr,-1);
//         return rodCutter(price,n,n,dp);
//     }
//     private int rodCutter(int[] prices, int n, int rod, int[][] dp){
//         if(rod==0||n==0){
//             return 0;
//         }
//         if(dp[n][rod]!=-1) return dp[n][rod]; 
//         if(n-1<rod){
//             return Math.max(
//                     rodCutter(prices,n,rod-n,dp) + prices[n-1],
//                     rodCutter(prices,n-1,rod,dp)
//                 );
//         }else return rodCutter(prices,n-1,rod,dp);
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (Commented - 2D Tabulation)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n²)

   🧠 **Key Insight:**
   dp[i][j] stores the maximum value using first i lengths to form rod of length j.

   💡 **Why it works:**
   Either skip the current length or use it (unbounded).
 ------------------------------------------------------------
*/

// class Solution {
//     public int cutRod(int[] price) {
//         int n = price.length;
//         int[][] dp = new int[n+1][n+1];
//         
//         for(int i = 1; i<=n; i++){
//             for(int j = 1; j<=n; j++){
//                 dp[i][j] = dp[i-1][j];
//                 if(i<=j) dp[i][j] = Math.max(dp[i][j], dp[i][j-i] + price[i-1]);
//             }
//         }
//         return dp[n][n];
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 3 (Commented - 1D Optimization)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n)

   🧠 **Key Insight:**
   Only the previous row is needed to compute the current row.

   💡 **Why it works:**
   Reduces memory by compressing DP table into one dimension.
 ------------------------------------------------------------
*/

// class Solution {
//     public int cutRod(int[] price) {
//         int n = price.length;
//         int[] old = new int[n+1];
//         
//         for(int i = 1; i<=n; i++){
//             int[] now = new int[n+1];
//             for(int j = 1; j<=n; j++){
//                 now[j] = old[j];
//                 if(i<=j) now[j] = Math.max(now[j], now[j-i] + price[i-1]);
//             }
//             old = now;
//         }
//         return old[n];
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 4 (✅ Space Optimized with Array Reuse - Most Optimal)
   ⏱️ Time Complexity: O(n²)
   💾 Space Complexity: O(n)

   🧠 **Key Insight:**
   Same DP transition as 1D optimization, but reuse arrays to avoid repeated allocations.

   💡 **Why it works:**
   - Treats the problem as unbounded knapsack.
   - For each length `i`, it updates all rod sizes `j`.
   - Reuses memory efficiently.
 ------------------------------------------------------------
*/

public class RodCutting {
    public int cutRod(int[] price) {
        int n = price.length;
        int[] old = new int[n+1];
        int[] now = new int[n+1];
        
        for(int i = 1; i<=n; i++){
            for(int j = 1; j<=n; j++){
                // Option 1: Do not take the current piece length i
                now[j] = old[j];
                
                // Option 2: Take the current piece length i (unbounded)
                if(i<=j) 
                    now[j] = Math.max(now[j], now[j-i] + price[i-1]);
            }
            
            // Swap arrays to reuse memory
            int[] temp = old;
            old = now;
            now = temp;
        }
        return old[n];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: price = [1, 5, 8, 9, 10, 17, 17, 20]
Rod Length = 8

Step 1: i = 1 → Try cutting pieces of length 1
Step 2: i = 2 → Try cutting pieces of length 2
Step 3: i = 3 → ...
...
Step 8: i = 8 → Consider full rod

At i = 2 and j = 8:
max(old[8], now[6] + price[1]) → max(20, 17 + 5) = 22

Final Result: 22 ✅

💡 Note: This is equivalent to the Unbounded Knapsack problem.
 ------------------------------------------------------------
*/
