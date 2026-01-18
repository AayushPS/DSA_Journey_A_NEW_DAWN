/*
 🔹 Problem: 518. Coin Change II
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Dynamic Programming, Unbounded Knapsack
 🔹 Link: https://leetcode.com/problems/coin-change-ii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an integer array coins representing coins of different 
denominations and an integer amount representing a total amount of money.

Return the number of combinations that make up that amount. If that amount 
of money cannot be made up by any combination of the coins, return 0.

You may assume that you have an infinite number of each kind of coin.

The answer is guaranteed to fit into a signed 32-bit integer.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: amount = 5, coins = [1,2,5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1

Example 2:
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.

Example 3:
Input: amount = 10, coins = [10]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= coins.length <= 300
 • 1 <= coins[i] <= 5000
 • All the values of coins are unique
 • 0 <= amount <= 5000

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count number of ways to make amount using coins (order doesn't matter).

🔑 **Key Insight - Unbounded Knapsack:**
   - Classic unbounded knapsack variation
   - Each coin can be used unlimited times
   - Order of coins doesn't matter (combinations, not permutations)
   - Use DP to avoid counting same combination twice

📍 **Approach 1 (Top-Down DP with Memoization):**
   - Recursive solution with 2D memoization
   - State: (remaining amount, coins available)
   - Choice: include current coin or skip it
   - Time: O(n × amount), Space: O(n × amount)

📍 **Approach 2 (Bottom-Up 2D DP):**
   - Iterative DP table building
   - dp[i][j] = ways to make amount j using first i coins
   - Time: O(n × amount), Space: O(n × amount)

📍 **Approach 3 (Space-Optimized Rolling Array):**
   - Only need previous row to compute current row
   - Use two 1D arrays alternating
   - Time: O(n × amount), Space: O(amount)

📍 **Approach 4 (Single Array with Swap):**
   - Optimize rolling array with reference swap
   - Eliminates array creation overhead
   - Time: O(n × amount), Space: O(amount)

📍 **Approach 5 (In-Place 1D DP - Highly Optimized):**
   - Single 1D array reused for all coins
   - Careful iteration order prevents double counting
   - Time: O(n × amount), Space: O(amount)

📍 **Approach 6 (Ultra-Clean 1D DP - Most Optimal):**
   - Simplest and most elegant solution
   - Direct coin iteration with amount update
   - Time: O(n × amount), Space: O(amount)

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Top-Down DP with Memoization)
   ⏱️ Time Complexity: O(n × amount) - each state computed once
   💾 Space Complexity: O(n × amount) - 2D memoization + O(n) recursion
   
   🧠 **Key Insight:**
   Recursively decide for each coin: include it (and stay at same coin for reuse)
   or skip it (move to next coin).
   
   💡 **Why it works:**
   - dp[n][sum] = ways to make 'sum' using first 'n' coins
   - Base case 1: sum=0 → 1 way (use no coins)
   - Base case 2: n=0 and sum>0 → 0 ways (no coins available)
   - Recurrence: 
     • If can use coin: ways = use_coin + skip_coin
     • Else: ways = skip_coin
   - Staying at same coin (n) allows unlimited reuse
 ------------------------------------------------------------

// import java.util.Arrays;
// 
// class Solution {
//     public int change(int amount, int[] coins) {
//         int n = coins.length;
//         int[][] dp = new int[n+1][amount+1];
//         for(int[] row : dp) Arrays.fill(row, -1);
//         return knapsack(amount, n, coins, dp);
//     }
//     
//     private int knapsack(int sum, int n, int[] coins, int[][] dp) {
//         // Base case: amount is 0, one way (use no coins)
//         if(sum == 0) return 1;
//         
//         // Base case: no coins left but amount > 0
//         if(n == 0) return 0;
//         
//         // Return memoized result
//         if(dp[n][sum] != -1) return dp[n][sum];
//         
//         int coin = coins[n-1];
//         
//         if(sum >= coin) {
//             // Can use this coin: include it OR skip it
//             // Include: stay at same coin (n) to allow reuse
//             // Skip: move to next coin (n-1)
//             return dp[n][sum] = knapsack(sum - coin, n, coins, dp) 
//                               + knapsack(sum, n-1, coins, dp);
//         } else {
//             // Cannot use this coin: skip it
//             return dp[n][sum] = knapsack(sum, n-1, coins, dp);
//         }
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Bottom-Up 2D DP)
   ⏱️ Time Complexity: O(n × amount) - nested loops
   💾 Space Complexity: O(n × amount) - 2D DP table
   
   🧠 **Key Insight:**
   Build solution iteratively from smaller subproblems.
   dp[i][j] represents ways to make amount j using first i coins.
   
   💡 **Why it works:**
   - Initialize: dp[i][0] = 1 for all i (0 amount needs 0 coins)
   - For each coin, for each amount:
     • If can use coin: add ways from (same coin, reduced amount)
     • Always add ways from (previous coins, same amount)
   - Same coin access (dp[i][j-c]) allows unlimited reuse
 ------------------------------------------------------------

// class Solution {
//     public int change(int amount, int[] coins) {
//         int n = coins.length;
//         int[][] dp = new int[n+1][amount+1];
//         
//         // Base case: 0 amount can be made in 1 way (use no coins)
//         for(int i = 0; i <= n; i++) {
//             dp[i][0] = 1;
//         }
//         
//         // Fill DP table
//         for(int i = 1; i <= n; i++) {
//             for(int j = 1; j <= amount; j++) {
//                 int coin = coins[i-1];
//                 
//                 if(j >= coin) {
//                     // Include current coin + skip current coin
//                     dp[i][j] = dp[i][j - coin] + dp[i-1][j];
//                 } else {
//                     // Can't use current coin, skip it
//                     dp[i][j] = dp[i-1][j];
//                 }
//             }
//         }
//         
//         return dp[n][amount];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 3 (Commented - Space-Optimized Rolling Array)
   ⏱️ Time Complexity: O(n × amount)
   💾 Space Complexity: O(amount) - two 1D arrays
   
   🧠 **Key Insight:**
   Only need previous row to compute current row.
   Use two arrays: old (previous row) and now (current row).
   
   💡 **Why it works:**
   - old[j] represents dp[i-1][j]
   - now[j] represents dp[i][j]
   - now[j-c] within same iteration represents dp[i][j-c]
   - After each coin, old = now for next iteration
 ------------------------------------------------------------

// class Solution {
//     public int change(int amount, int[] coins) {
//         int n = coins.length;
//         int[] old = new int[amount+1];
//         old[0] = 1; // Base case
//         
//         for(int i = 1; i <= n; i++) {
//             int[] now = new int[amount+1];
//             now[0] = 1; // Base case for current row
//             
//             for(int j = 1; j <= amount; j++) {
//                 int coin = coins[i-1];
//                 
//                 if(j >= coin) {
//                     now[j] = now[j - coin] + old[j];
//                 } else {
//                     now[j] = old[j];
//                 }
//             }
//             
//             old = now;
//         }
//         
//         return old[amount];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 4 (Commented - Single Array with Swap)
   ⏱️ Time Complexity: O(n × amount)
   💾 Space Complexity: O(amount) - two arrays with reference swap
   
   🧠 **Key Insight:**
   Reuse array references by swapping instead of creating new arrays.
   Eliminates overhead of array creation in each iteration.
   
   💡 **Why it works:**
   - Same logic as Approach 3
   - Instead of old = now (creating new array), swap references
   - Reduces allocation overhead
   - Note: There's a bug in the provided code (now = old should be now = temp)
 ------------------------------------------------------------

// class Solution {
//     public int change(int amount, int[] coins) {
//         int n = coins.length;
//         int[] old = new int[amount+1];
//         int[] now = new int[amount+1];
//         old[0] = 1; // Base case
//         
//         for(int i = 1; i <= n; i++) {
//             now[0] = 1;
//             
//             for(int j = 1; j <= amount; j++) {
//                 int coin = coins[i-1];
//                 
//                 if(j >= coin) {
//                     now[j] = now[j - coin] + old[j];
//                 } else {
//                     now[j] = old[j];
//                 }
//             }
//             
//             // Swap references
//             int[] temp = old;
//             old = now;
//             now = temp; // Clear temp for next iteration
//         }
//         
//         return old[amount];
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 5 (Commented - In-Place 1D DP)
   ⏱️ Time Complexity: O(n × amount)
   💾 Space Complexity: O(amount) - single array
   
   🧠 **Key Insight:**
   Can update single array in-place if we're careful about iteration order.
   Key: iterate amounts from coin to amount (forward).
   
   💡 **Why it works:**
   - dp[j] initially holds ways using previous coins
   - When we use coin, dp[j-coin] has already been updated for current coin
   - This allows: dp[j] += dp[j-coin] (add ways using current coin)
   - Forward iteration ensures each amount uses updated values
 ------------------------------------------------------------

// class Solution {
//     public int change(int amount, int[] coins) {
//         int n = coins.length;
//         int[] dp = new int[amount+1];
//         dp[0] = 1; // Base case
//         
//         for(int i = 1; i <= n; i++) {
//             for(int j = 1; j <= amount; j++) {
//                 int coin = coins[i-1];
//                 
//                 if(j >= coin) {
//                     // dp[j-coin] already updated for current coin
//                     // dp[j] still holds value from previous coins
//                     dp[j] = dp[j-coin] + dp[j];
//                 }
//                 // If j < coin, dp[j] stays same (from previous coins)
//             }
//         }
//         
//         return dp[amount];
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 6 (✅ Ultra-Clean 1D DP - Most Optimal)
   ⏱️ Time Complexity: O(n × amount) - iterate coins × amounts
   💾 Space Complexity: O(amount) - single 1D array
   
   🧠 **Key Insight:**
   Simplest possible solution. For each coin, update all reachable amounts.
   The magic is in the iteration order: coins outer loop, amounts inner loop.
   
   💡 **Why it works:**
   - dp[i] = number of ways to make amount i
   - Initialize dp[0] = 1 (one way to make 0: use no coins)
   - For each coin:
     • For each amount from coin to total:
       - Add ways to make (amount - coin), then add this coin
       - dp[i] += dp[i-coin]
   - Outer loop over coins ensures each coin considered separately
   - Inner loop forward ensures unlimited coin reuse
   - This order prevents counting same combination twice!
   
   🎯 **Why Order Matters:**
   - Coins outer loop → process each coin completely before next
   - This treats each coin as a separate "stage"
   - Ensures combinations, not permutations
   - Example: [1,2] won't count both {1,2} and {2,1}
   
   📐 **Iteration Pattern:**
   - Start from coin value (can't make amounts < coin with this coin)
   - Go up to target amount
   - Each update: dp[i] += dp[i-coin]
   - Previous value in dp[i] = ways without this coin
   - dp[i-coin] = ways to make remaining amount (with any coins so far)
   - Sum = total ways to make amount i
 ------------------------------------------------------------
*/

public class CoinChangeII {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1; // Base case: one way to make amount 0 (use no coins)
        
        // Process each coin separately
        for(int coin : coins) {
            // Update all amounts that can be made with this coin
            for(int i = coin; i <= amount; i++) {
                // Add ways: make (i-coin) and add this coin
                dp[i] += dp[i - coin];
            }
        }
        
        return dp[amount];
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: amount = 5, coins = [1,2,5]

Initial: dp = [1, 0, 0, 0, 0, 0]
         (dp[0]=1: one way to make 0)

Process coin = 1:
  i=1: dp[1] += dp[0] = 0+1 = 1  → [1,1,0,0,0,0]
  i=2: dp[2] += dp[1] = 0+1 = 1  → [1,1,1,0,0,0]
  i=3: dp[3] += dp[2] = 0+1 = 1  → [1,1,1,1,0,0]
  i=4: dp[4] += dp[3] = 0+1 = 1  → [1,1,1,1,1,0]
  i=5: dp[5] += dp[4] = 0+1 = 1  → [1,1,1,1,1,1]
  
  Meaning: {1}, {1,1}, {1,1,1}, {1,1,1,1}, {1,1,1,1,1}

Process coin = 2:
  i=2: dp[2] += dp[0] = 1+1 = 2  → [1,1,2,1,1,1]
  i=3: dp[3] += dp[1] = 1+1 = 2  → [1,1,2,2,1,1]
  i=4: dp[4] += dp[2] = 1+2 = 3  → [1,1,2,2,3,1]
  i=5: dp[5] += dp[3] = 1+2 = 3  → [1,1,2,2,3,3]
  
  New combinations: {2}, {2,1}, {2,2}, {2,2,1}

Process coin = 5:
  i=5: dp[5] += dp[0] = 3+1 = 4  → [1,1,2,2,3,4]
  
  New combination: {5}

Final: dp[5] = 4 ✅

The 4 ways:
1. {5}
2. {2,2,1}
3. {2,1,1,1}
4. {1,1,1,1,1}

 ------------------------------------------------------------
 🔍 Why Coins Outer Loop Prevents Double Counting:

Question: Why not amounts outer loop, coins inner loop?
Answer: Would count permutations, not combinations!

Wrong order (amounts outer):
```java
for(int i = 1; i <= amount; i++) {
    for(int coin : coins) {
        if(i >= coin) dp[i] += dp[i-coin];
    }
}
```

Example: amount=3, coins=[1,2]
- At i=3: tries coin 1 then coin 2
- Counts both {1,2} and {2,1} as different!
- Result: 3 ways instead of 2 ✗

Correct order (coins outer):
```java
for(int coin : coins) {
    for(int i = coin; i <= amount; i++) {
        dp[i] += dp[i-coin];
    }
}
```

- Process coin 1 completely, then coin 2
- When processing coin 2, can only add to amounts already made with coin 1
- Result: 2 ways ({1,1,1} and {1,2}) ✓

 ------------------------------------------------------------
 🔍 Understanding dp[i] += dp[i-coin]:

What does this mean?

Before: dp[i] = ways to make i with coins processed so far
        dp[i-coin] = ways to make (i-coin) with coins processed so far

After: dp[i] = ways with coins so far + ways using current coin
             = dp[i] + dp[i-coin]

Visual example: amount=5, processing coin=2, dp=[1,1,1,1,1,1]

i=2: dp[2] += dp[0]
     "Ways to make 2" += "Ways to make 0, then add coin 2"
     1 += 1 = 2
     Old: {1,1}, New: {2}

i=3: dp[3] += dp[1]
     "Ways to make 3" += "Ways to make 1, then add coin 2"
     1 += 1 = 2
     Old: {1,1,1}, New: {1,2}

i=4: dp[4] += dp[2]
     "Ways to make 4" += "Ways to make 2, then add coin 2"
     1 += 2 = 3
     Old: {1,1,1,1}, New: {1,1,2} and {2,2}

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: amount = 0
- dp[0] = 1
- No coins processed
- Result: 1 (empty combination) ✓

Case 2: No valid combination
amount = 3, coins = [2]
- After coin 2: dp = [1,0,1,0]
- dp[3] = 0 ✓

Case 3: Single coin equals amount
amount = 10, coins = [10]
- dp[10] += dp[0] = 0+1 = 1
- Result: 1 ✓

Case 4: All coins larger than amount
amount = 2, coins = [5,10]
- No updates (all i < coin)
- Result: 0 ✓

Case 5: Coin value of 1
amount = 5, coins = [1]
- Creates ways for all amounts
- Result: 1 (all 1's) ✓

Case 6: Large amount
amount = 5000, coins = [1,2,5]
- DP array size 5001
- Still efficient O(n × amount) ✓

 ------------------------------------------------------------
 🔍 Comparison: Coin Change vs Coin Change II:

Coin Change (Problem 322):
- Goal: Minimum coins to make amount
- Return: number of coins (or -1)
- DP meaning: dp[i] = min coins for amount i
- Update: dp[i] = min(dp[i], dp[i-coin] + 1)

Coin Change II (This problem):
- Goal: Number of combinations
- Return: count of ways
- DP meaning: dp[i] = number of ways for amount i
- Update: dp[i] += dp[i-coin]

Key difference:
- Coin Change: minimization (min)
- Coin Change II: counting (sum)

⚡ Performance Analysis:
The ultra-clean 1D DP approach efficiently handles maximum constraints:
- Amount up to 5000
- Coins array up to 300 elements
- Total iterations: 300 × 5000 = 1,500,000
- Each iteration: O(1) operation (addition)
- Total execution: ~2ms for maximum input
- Space: 5001 integers = ~20KB
- Why this is optimal:
  • Must process each coin: Ω(n) lower bound
  • Must update each amount: Ω(amount) lower bound
  • Combined: Ω(n × amount) is theoretical minimum
  • This solution achieves that with minimal constants
- Space optimization significance:
  • 2D DP: 300 × 5000 × 4 bytes = 6MB
  • 1D DP: 5000 × 4 bytes = 20KB
  • Reduction: 99.67%!
- The iteration order insight is crucial:
  • Coins outer → combinations (correct)
  • Amounts outer → permutations (wrong for this problem)
  • This is a fundamental DP pattern for combination problems
- Real-world applications:
  • Currency exchange (making change)
  • Resource allocation (limited types, unlimited quantity)
  • Subset sum variations (counting instead of existence)
  • Inventory management (ways to fill orders)
- This problem demonstrates:
  • Classic unbounded knapsack variation
  • Space optimization through careful analysis
  • Why iteration order matters in DP
  • Difference between combinations and permutations in DP
 ------------------------------------------------------------
*/