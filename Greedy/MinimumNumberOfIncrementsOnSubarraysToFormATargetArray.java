/*
───────────────────────────────────────────────────────────────
📘 Problem: 1526. Minimum Number of Increments on Subarrays to Form a Target Array
💡 Difficulty: Hard
🧠 Topics: Array, Greedy
🔗 Link: https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:
You are given an integer array `target`. You also have an integer array `initial` of the same size,
where all elements are initially zeros.

In one operation, you can choose any subarray of `initial` and increment each value by one.

Return the minimum number of operations required to form the `target` array from `initial`.

───────────────────────────────────────────────────────────────
🧠 Example 1:
Input: target = [1,2,3,2,1]
Output: 3
Explanation:
[0,0,0,0,0]
→ increment entire array: [1,1,1,1,1]
→ increment subarray [1..3]: [1,2,2,2,1]
→ increment single element [2]: [1,2,3,2,1]

───────────────────────────────────────────────────────────────
🧠 Example 2:
Input: target = [3,1,1,2]
Output: 4

───────────────────────────────────────────────────────────────
🧠 Example 3:
Input: target = [3,1,5,4,2]
Output: 7
───────────────────────────────────────────────────────────────

🔒 Constraints:
1 <= target.length <= 10^5  
1 <= target[i] <= 10^5

───────────────────────────────────────────────────────────────
⚙️ Approach Explanation:

👉 Observation:
Each time you increase a subarray, only the **difference between consecutive elements** matters.

If `target[i]` > `target[i-1]`, you need `(target[i] - target[i-1])` additional operations 
to increase elements up to that new height.

Otherwise, if it’s smaller or equal, no new operations are needed at that point.

Hence, the total operations required =
  `target[0] + sum(max(0, target[i] - target[i-1])) for i = 1..n-1`

This is a **greedy observation** that works in O(n) time.

───────────────────────────────────────────────────────────────
🧮 Time & Space Complexity:
- Time Complexity: O(n)
- Space Complexity: O(1)
───────────────────────────────────────────────────────────────
*/

class Solution {
    public int minNumberOperations(int[] target) {
        int count = target[0];  // The first element requires this many increments directly.
        for (int i = 1; i < target.length; i++) {
            // Only count positive increases.
            count += Math.max(0, target[i] - target[i - 1]);
        }
        return count;
    }
}

/*
───────────────────────────────────────────────────────────────
💭 Alternate Thought (DP Perspective):
Though the problem can be framed as a dynamic programming one
where we compute minimum operations for each prefix, 
the greedy difference-based approach dominates in both simplicity and efficiency.
───────────────────────────────────────────────────────────────
*/
