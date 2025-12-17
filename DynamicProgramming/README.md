# Dynamic Programming 💡

## What This Topic Covers
Dynamic programming optimization technique. This section covers:
- Overlapping subproblems identification
- Optimal substructure property
- Memoization and tabulation approaches
- State space reduction techniques

## Common Problems & Patterns
- **Fibonacci Variants** - Classic DP introduction problems
- **Longest Common Subsequence** - String/array comparison problems
- **Knapsack Problems** - 0/1, unbounded, and fractional knapsack
- **Coin Change** - Minimum coins and ways to make change
- **Edit Distance** - String transformation problems
- **Maximum Subarray** - Kadane's algorithm and variants
- **Stock Trading** - Buy/sell optimization problems

## Problems Solved

| Problem | Platform | Difficulty | Summary |
|---------|----------|------------|----------|
| [1911. Maximum Alternating Subsequence Sum](./LIS_AND_VARIANTS/MaximumAlternatingSubsequenceSum.java) | LeetCode | Medium | Find maximum alternating sum of any subsequence with comprehensive DP approaches |
| [300. Longest Increasing Subsequence](./LIS_AND_VARIANTS/LongestIncreasingSubsequence.java) | LeetCode | Medium | Find the length of the longest strictly increasing subsequence using DP |
| [646. Maximum Length of Pair Chain](./LIS_AND_VARIANTS/MaximumLengthOfPairChain.java) | LeetCode | Medium | Find the length of the longest chain of pairs in increasing order using DP |
| [1048. Longest String Chain](./LIS_AND_VARIANTS/LongestStringChain.java) | LeetCode | Medium | LIS-style DP based on word length and predecessor relation |
| [1420. Build Array Where You Can Find The Maximum Exactly K Comparisons](./LIS_AND_VARIANTS/BuildArrayWhereYouCanFindTheMaximumExactlyKComparisons.java) | LeetCode | Hard | Prefix Sum optimized 2D DP Approach to find the states |
| [2926. Maximum Balanced Subsequence Sum](./LIS_AND_VARIANTS/MaximumBalancedSubsequenceSum.java) | LeetCode | Hard | Find maximum sum of balanced subsequence using DP with coordinate compression and binary search |
| [368. Largest Divisible Subset](./LIS_AND_VARIANTS/LargestDivisibleSubset.java) | LeetCode | Medium | Find the largest subset where every pair of elements is divisible using DP and sorting |
| [474. Ones and Zeroes](./Miscellaneous/OnesAndZeroes.java) | LeetCode | Medium | Return the size of the largest subset of String Array such that the total number of 0's is at most `m`, and the total number of 1's is at most `n` |
| [1143. Longest Common Subsequence + Print the LCS](./Longest_Common_SubSequence_AND_VARIANTS/LCS.java) | LeetCode | Medium | Computes the Length of actual Longest Common Subsequence and provides the string |
| [Shortest Common Supersequence (Length Only)](./Longest_Common_SubSequence_AND_VARIANTS/ShortestCommonSuperSequenceLength.java) | GeeksForGeeks | Medium | Given two strings `s1` and `s2`, return the **length** of the shortest string that has both `s1` and `s2` as *subsequences* |
| [1092. Shortest Common Supersequence (Return the string)](./Longest_Common_SubSequence_AND_VARIANTS/ShortestCommonSuperSequenceREURNING_STRING.java) | LeetCode | Hard | Given two strings str1 and str2, return the shortest string which has both str1 and str2 as subsequences. |
| [72. Edit Distance](./DP_On_Strings/EditDistance.java) | LeetCode | Medium | Find the minimum number of operations (Insert, Delete, Replace) required to transform word1 → word2. |
| [647. Palindromic Substrings](./DP_On_Strings/PalindromicSubstrings.java) | LeetCode | Medium | Return the total number of palindromic substrings from string given. |
| [5. Longest Palindromic Substring](./DP_On_Strings/LongestPalindromicSubstring.java) | LeetCode | Medium | Return the longest palindromic substring. |
| [516. Longest Palindromic Subsequence](./DP_On_Strings/LongestPalindromicSubsequence.java) | LeetCode | Medium | Return the longest paindromic subsequence |
| [1312. Minimum Insertion Steps to Make a String Palindrome](./DP_On_Strings/MinimumInsertionStepsToMakeAStringPalindrome.java) | LeetCode | Medium | Find the Minimum number of Insertions required to make the string a palindrome. |
| [131. Palindrome Partitioning](./DP_On_Strings/PalindromePartitioning.java) | LeetCode | Medium | Return ALL possible partitions where every substring in the partition is a palindrome. |
| [132. Palindrome Partitioning II](./DP_On_Strings/PalindromePartitioningII.java) | LeetCode | Hard | Given a string s, partition it such that every substring is a palindrome and Return the **minimum number of cuts** needed. |
| [62. Unique Paths](./DP%20_On_Grids/UniquePaths.java) | LeetCode | Medium | A robot is on an m x n grid, starting at the top-left corner (0,0) and must reach the bottom-right corner (m-1,n-1). It may only move **right** or **down**. Return the number of unique paths possible. |
| [63. Unique Paths 2](./DP%20_On_Grids/UniquePaths2.java) | LeetCode | Medium | A robot moves in an m x n grid from top-left to bottom-right, only moving RIGHT or DOWN. Some cells are blocked by obstacles (represented as 1). Robot cannot step on obstacles. Return the number of unique valid paths from start to finish. |
| [2435. Paths in Matrix Whose Sum Is Divisible by K](./DP%20_On_Grids/PathsInMatrixWhoseSumIsDivisibleByK.java) | LeetCode | Hard | Count how many distinct paths end at (m−1,n−1) such that the **sum of all visited values is divisible by k**. |
| [64. Minimum Path Sum](./DP%20_On_Grids/MaximumPathSum.java) | LeetCode | Medium | find a path from (0,0) to (m−1,n−1) moving only RIGHT or DOWN, such that the **sum of all values on the path is minimized**. |
| [1594. Maximum Non-Negative Product in a Matrix](./DP%20_On_Grids/MaximumNonNegativeProductInAMatrix.java) | LeetCode | Medium | Starting at (0,0). Determine the maximum non-negative product of all elements along a path to (m-1,n-1). |
| [174. Dungeon Game](./DP%20_On_Grids/DungeonGame.java) | LeetCode | Hard | Determine the minimum initial health required so that the knight can reach the princess at (m−1,n−1) from (0,0). |
| [3363. Find the Maximum Number of Fruits Collected](./DP%20_On_Grids/FindTheMaximumNumberOfFruitsCollected.java) | LeetCode | Hard | A Complex Movement on grid solved using DP for scalability to generate maximum output |
| [3578. Count Partitions With Max-Min Difference at Most K](./Miscellaneous/CountPartitionsWithMaxMinDifferenceAtMostK.java) | LeetCode | Medium | Return **the total number of valid partitions** where every partition follows  max(segment) - min(segment) ≤ k. |
| [3562. Maximum Profit from Trading Stocks with Discounts](./KnapSack_and_Variants/MaximumProfitFromTradingStocksWithDiscounts.java) | LeetCode | Hard | Maximize profit by strategically buying/selling stocks in a tree hierarchy with 50% discounts for subordinates when boss buys |
| [Subset Sum Problem](./KnapSack_and_Variants/SubsetSumProblem.java) | GeeksForGeeks | Medium | Determine whether there exists a subset of an array whose sum is exactly equal to a given target sum using 0/1 Knapsack DP approach |
| [3573. Best Time to Buy and Sell Stock V](./KnapSack_and_Variants/BestTimeToBuyAndSellStockV.java) | LeetCode | Medium | Maximize profit with up to k transactions (both normal and short selling) using state-machine DP with 3 states |

## Implementation Notes
Solutions are written in **Java** with clean, optimized implementations focusing on:
- Bottom-up and top-down DP approaches
- Space optimization techniques (1D arrays, rolling arrays)
- State transition analysis and recurrence relations
- Time and space complexity optimizations

---
*Part of DSA Journey: A New Dawn 🌅*
