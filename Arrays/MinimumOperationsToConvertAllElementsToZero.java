/*
───────────────────────────────────────────────────────────────
📘 Problem: 3542. Minimum Operations to Convert All Elements to Zero
💡 Difficulty: Medium
🧠 Topics: Stack, Monotonic Stack, Greedy, Array
🔗 Link: https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are given an integer array `nums` of size `n` consisting of non-negative integers.

You may perform operations on subarrays to make all elements equal to 0.

🔹 Operation:
Choose a subarray [i, j] and find its minimum non-negative integer `m`.
Then set **all occurrences of m** in that subarray to 0.

Return the **minimum number of operations** required to make all elements in `nums` equal to 0.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ n ≤ 10⁵  
• 0 ≤ nums[i] ≤ 10⁵
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach — Monotonic Stack (Greedy Counting)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

Think of the array as **layers of heights** where each unique positive height 
represents a value that must eventually be removed (turned to zero).

Every time we encounter a **new rising value** (greater than all previous ones 
in the active segment), we’ll need one new operation for that layer.

The stack helps us efficiently track these “active layers”:
1️⃣ When the current number is smaller → pop higher layers (they’re done).
2️⃣ When the current number is larger than the previous top → we start a new operation (push).
3️⃣ Ignore zeros since they don’t add layers.

───────────────────────────────────────────────────────────────
🔹 Example:
nums = [3, 1, 2, 1]

Process:
- 3 → stack = [3], count = 1
- 1 → smaller, pop 3, push 1 → count = 2
- 2 → larger than 1 → push 2 → count = 3
- 1 → smaller, pop 2 → stack = [1]

✅ Output = 3

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time:  O(n) → each element is pushed & popped once  
• Space: O(n) → stack usage
───────────────────────────────────────────────────────────────
✅ Clean, efficient, and optimal greedy solution.
───────────────────────────────────────────────────────────────
*/

import java.util.*;

public class MinimumOperationsToConvertAllElementsToZero {
    public int minOperations(int[] nums) {
        int operations = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int num : nums) {
            // Remove all higher values before current
            while (!stack.isEmpty() && num < stack.peekLast()) {
                stack.pollLast();
            }

            // Start a new "layer" only if needed
            if (num > 0 && (stack.isEmpty() || num > stack.peekLast())) {
                stack.add(num);
                operations++;
            }
        }

        return operations;
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Example Walkthroughs:
───────────────────────────────────────────────────────────────
Example 1:
nums = [0, 2]
→ stack builds as [2] → count = 1
✅ Output = 1

Example 2:
nums = [3, 1, 2, 1]
→ Layers observed = [3], [1], [2]
✅ Output = 3

Example 3:
nums = [1, 2, 1, 2, 1, 2]
→ Layers observed in sequence = [1], [2], [2], [2]
✅ Output = 4
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Time:  O(n)
Space: O(n)
───────────────────────────────────────────────────────────────
✅ Final Choice → Monotonic Stack (Greedy Counting)
───────────────────────────────────────────────────────────────
*/
