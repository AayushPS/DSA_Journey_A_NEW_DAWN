/*
 🔹 Problem: 3075. Maximize Happiness of Selected Children
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Greedy, Sorting
 🔹 Link: https://leetcode.com/problems/maximize-happiness-of-selected-children/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array happiness of length n and an integer k.

There are n children in a queue. In each of the k turns, you select
one child. When a child is selected, the happiness of all unselected
children decreases by 1 (not below 0).

Return the maximum total happiness you can obtain by selecting k children.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: happiness = [1,2,3], k = 2
Output: 4

Example 2:
Input: happiness = [1,1,1,1], k = 2
Output: 1

Example 3:
Input: happiness = [2,3,4,5], k = 1
Output: 5

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ happiness.length ≤ 200000
 • 1 ≤ happiness[i] ≤ 1e8
 • 1 ≤ k ≤ n

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Pick k children such that the sum of their adjusted happiness
values is maximized.

📍 Greedy Strategy:
- Always pick the child with the highest remaining happiness
- Each pick causes a cumulative decrement of 1 for future picks
- After sorting, the i-th picked child loses i happiness

Effective happiness = max(happiness[i] − pickedCount, 0)

Why optimal:
- Greedy choice is optimal because decrements apply uniformly
- Sorting once + linear pass
- Handles large constraints efficiently

 ------------------------------------------------------------
*/

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Greedy + Sorting)
   ⏱️ Time Complexity: O(n log n)
   💾 Space Complexity: O(1)
 ------------------------------------------------------------
*/

import java.util.*;

public class MaximizeHappinessOfSelectedChildren {

    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);
        int n = happiness.length;

        long ans = 0;
        int picked = 0;

        for (int i = n - 1; i >= 0 && picked < k; i--) {
            int effective = happiness[i] - picked;
            if (effective <= 0) break;
            ans += effective;
            picked++;
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

happiness = [1,2,3], k = 2
Sorted: [1,2,3]

Pick 3 → +3
Remaining decrement = 1

Pick 2 − 1 = 1 → +1

Total = 4 ✅
 ------------------------------------------------------------
*/
