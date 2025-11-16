/*
───────────────────────────────────────────────────────────────
📘 Problem: 368. Largest Divisible Subset
💡 Difficulty: Medium
🧠 Topics: Dynamic Programming, Sorting, Subsequence
🔗 Link: https://leetcode.com/problems/largest-divisible-subset/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

Given a set of distinct positive integers `nums`, return the **largest subset** 
`answer` such that for every pair `(answer[i], answer[j])` in the subset:
    answer[i] % answer[j] == 0  OR  answer[j] % answer[i] == 0

If multiple valid subsets exist, return any of them.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ nums.length ≤ 1000  
• 1 ≤ nums[i] ≤ 2 × 10⁹  
• All elements in nums are distinct
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥉 Approach 1 — Recursive Backtracking (Exhaustive Search)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

1️⃣ Sort the array to ensure that smaller numbers come first.
2️⃣ Use recursion to try including or excluding each element:
    - If the current number divides the previous included number (or no previous element),
      include it and move forward.
    - Else, skip it.
3️⃣ Keep track of the longest valid subset found so far.

⚠️ This approach works conceptually but is **exponential in time**.
It’s useful only for understanding, not for practical constraints.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(2ⁿ)  
• Space: O(n)
───────────────────────────────────────────────────────────────
*/

/*
class Solution {
    List<Integer> res = new ArrayList<>();

    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        dfs(0, -1, new ArrayList<>(), nums);
        return res;
    }

    private void dfs(int i, int prev, List<Integer> curr, int[] nums) {
        if (i == nums.length) {
            if (curr.size() > res.size()) res = new ArrayList<>(curr);
            return;
        }

        // Try including current element
        if (prev == -1 || nums[i] % nums[prev] == 0) {
            curr.add(nums[i]);
            dfs(i + 1, i, curr, nums);
            curr.remove(curr.size() - 1);
        }

        // Try skipping current element
        dfs(i + 1, prev, curr, nums);
    }
}
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach 2 — Dynamic Programming (Optimal)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

1️⃣ Sort the array → ensures divisibility can be checked in ascending order.
2️⃣ Let dp[i] = length of the largest divisible subset ending at index i.
3️⃣ Transition:
       For each j < i:
           if nums[i] % nums[j] == 0
               dp[i] = max(dp[i], dp[j] + 1)
               prev[i] = j  // keep track for reconstruction
4️⃣ Track the index with the largest dp value (`maxIndex`).
5️⃣ Reconstruct the subset using `prev[]` links.

This is a **classic LIS (Longest Increasing Subsequence)** pattern,  
but the condition is divisibility instead of ordering.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n²)
• Space: O(n)
───────────────────────────────────────────────────────────────
✅ Accepted solution — efficient and elegant.
───────────────────────────────────────────────────────────────
*/

import java.util.*;

public class LargestDivisibleSubset {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        // dp[0][i] = length of subset ending at i
        // dp[1][i] = previous index (for backtracking)
        int[][] dp = new int[2][n];
        Arrays.fill(dp[0], 1);
        Arrays.fill(dp[1], -1);

        int maxIndex = 0; // index of last element in the longest subset

        // Compute DP transitions
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[0][j] + 1 > dp[0][i]) {
                    dp[0][i] = dp[0][j] + 1;
                    dp[1][i] = j;
                }
            }
            if (dp[0][i] > dp[0][maxIndex]) maxIndex = i;
        }

        // Reconstruct the subset
        List<Integer> res = new ArrayList<>();
        while (maxIndex != -1) {
            res.add(nums[maxIndex]);
            maxIndex = dp[1][maxIndex];
        }

        return res;
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Example Walkthrough:
───────────────────────────────────────────────────────────────
Input: nums = [1, 2, 4, 8]

Step 1: Sort → [1, 2, 4, 8]
Step 2: DP build:
  i=0 → dp[0]=1
  i=1 → 2%1=0 → dp[1]=2
  i=2 → 4%2=0 → dp[2]=3
  i=3 → 8%4=0 → dp[3]=4

Reconstruction → [8,4,2,1]
Reversed → [1,2,4,8]

✅ Output = [1,2,4,8]
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Approach 1 — Recursive Backtracking: 
• Time: O(2ⁿ)
• Space: O(n)

Approach 2 — Dynamic Programming:
• Time: O(n²)
• Space: O(n)

✅ Final Choice → Approach 2 (Dynamic Programming)
───────────────────────────────────────────────────────────────
*/
