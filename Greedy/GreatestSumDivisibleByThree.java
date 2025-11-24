/*
 🔹 Problem: 1262. Greatest Sum Divisible by Three
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Dynamic Programming, Greedy, Math
 🔹 Link: https://leetcode.com/problems/greatest-sum-divisible-by-three/

 ------------------------------------------------------------
 🔸 Problem Statement:

Given an integer array nums, return the maximum possible sum of elements 
such that the total sum is divisible by 3.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: nums = [3,6,5,1,8]
Output: 18

Example 2:
Input: nums = [4]
Output: 0

Example 3:
Input: nums = [1,2,3,4,4]
Output: 12

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= nums.length <= 4 * 10⁴
 • 1 <= nums[i] <= 10⁴

 ------------------------------------------------------------
 🔹 Approach Summary:

✅ **Goal:** Maximize sum such that sum % 3 == 0.

---

### ✔ Approach 1: Recursive + Memoization (DP) — O(N)
Tracks index + remainder. Classic include/exclude DP.
(Commented)

### ✔ Approach 2: Iterative DP Table — O(N)
dp[i][r] = best sum starting at index i with remainder r.
(Commented)

### ✔ Approach 3: Space Optimized DP (1D) — O(N)
DP over remainders 0,1,2 using rolling array.
(Commented)

### ✔ Approach 4: Greedy (Most Optimal) — O(N)
Compute:
• Total sum  
• Track smallest mod–1 numbers  
• Track smallest mod–2 numbers  

Adjust sum by removing minimum required values.

🔥 Fastest and cleanest. This is the **uncommented working code**.

 ------------------------------------------------------------
 🔹 Approach 1: Recursive + Memoization (Commented)

class Solution {
    public int maxSumDivThree(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][3];
        for (int[] row : dp) Arrays.fill(row, -1);
        return solve(nums, 0, 0, dp);
    }

    private int solve(int[] nums, int i, int rem, int[][] dp) {
        if (i == nums.length) return rem == 0 ? 0 : Integer.MIN_VALUE;
        if (dp[i][rem] != -1) return dp[i][rem];

        int take = nums[i] + solve(nums, i + 1, (rem + nums[i]) % 3, dp);
        int skip = solve(nums, i + 1, rem, dp);

        return dp[i][rem] = Math.max(take, skip);
    }
}

 ------------------------------------------------------------
 🔹 Approach 2: Iterative DP Table (Commented)

class Solution {
    public int maxSumDivThree(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][3];

        dp[n][0] = 0;
        dp[n][1] = Integer.MIN_VALUE;
        dp[n][2] = Integer.MIN_VALUE;

        for (int i = n - 1; i >= 0; i--) {
            for (int r = 0; r < 3; r++) {
                dp[i][r] = Math.max(
                    dp[i + 1][(r + nums[i]) % 3] + nums[i],
                    dp[i + 1][r]
                );
            }
        }

        return dp[0][0];
    }
}

 ------------------------------------------------------------
 🔹 Approach 3: Space Optimized 1D DP (Commented)

class Solution {
    public int maxSumDivThree(int[] nums) {
        int[] dp = {0, Integer.MIN_VALUE, Integer.MIN_VALUE};

        for (int x : nums) {
            int[] newDP = new int[3];
            for (int r = 0; r < 3; r++) {
                newDP[r] = Math.max(
                    dp[(r + x) % 3] + x,
                    dp[r]
                );
            }
            dp = newDP;
        }

        return dp[0];
    }
}

 ------------------------------------------------------------
 🔹 Approach 4 (Final Working Code): Greedy — Optimal O(N)

*/

class Solution {
    public int maxSumDivThree(int[] nums) {
        int sum = 0;

        int rem11 = Integer.MAX_VALUE, rem12 = Integer.MAX_VALUE;
        int rem21 = Integer.MAX_VALUE, rem22 = Integer.MAX_VALUE;

        for (int x : nums) {
            sum += x;
            int m = x % 3;

            if (m == 1) {
                if (x < rem11) {
                    rem12 = rem11;
                    rem11 = x;
                } else if (x < rem12) {
                    rem12 = x;
                }
            } 
            else if (m == 2) {
                if (x < rem21) {
                    rem22 = rem21;
                    rem21 = x;
                } else if (x < rem22) {
                    rem22 = x;
                }
            }
        }

        if (sum % 3 == 0) return sum;

        int reduction = Integer.MAX_VALUE;

        if (sum % 3 == 1) {
            if (rem11 != Integer.MAX_VALUE) reduction = rem11;
            if (rem21 != Integer.MAX_VALUE && rem22 != Integer.MAX_VALUE)
                reduction = Math.min(reduction, rem21 + rem22);
        } else { // sum % 3 == 2
            if (rem21 != Integer.MAX_VALUE) reduction = rem21;
            if (rem11 != Integer.MAX_VALUE && rem12 != Integer.MAX_VALUE)
                reduction = Math.min(reduction, rem11 + rem12);
        }

        return reduction == Integer.MAX_VALUE ? 0 : sum - reduction;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
