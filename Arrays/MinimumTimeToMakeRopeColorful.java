/*
───────────────────────────────────────────────────────────────
📘 Problem: 1578. Minimum Time to Make Rope Colorful
💡 Difficulty: Medium
🧠 Topics: Greedy, String, Array
🔗 Link: https://leetcode.com/problems/minimum-time-to-make-rope-colorful/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

Alice has `n` balloons on a rope.  
Each balloon has a color `colors[i]` and a removal cost `neededTime[i]`.

To make the rope *colorful*, no two adjacent balloons can have the same color.  
Bob can remove balloons, taking `neededTime[i]` seconds to remove the i-th balloon.

Return the **minimum total time** required to make the rope colorful.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• n == colors.length == neededTime.length  
• 1 ≤ n ≤ 10⁵  
• 1 ≤ neededTime[i] ≤ 10⁴  
• colors contains only lowercase English letters  
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 1 — Greedy (Optimal)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:
We only need to remove balloons when two or more **consecutive colors are the same**.  
In each such group, we should **keep the balloon with the highest time** (since removing it costs more)  
and **remove all others**.

🔹 Algorithm:
1️⃣ Initialize total time = 0.  
2️⃣ Traverse the string with two pointers (`a` and `b`), or simply iterate linearly.  
3️⃣ For each sequence of same-colored balloons:
   - Sum all removal times (`addAll`).
   - Track the maximum removal time (`max`).
   - Add `(sum - max)` to total → cost to remove all but one.  
4️⃣ Move to the next color group.

🧮 Example:
colors = "aabaa", neededTime = [1,2,3,4,1]

Groups:
- "aa" → remove 1 → cost 1
- "aa" (at end) → remove 1 → cost 1  
Total = 2

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n) — one pass over the string  
• Space: O(1) — only variables for tracking sums and maxima  
───────────────────────────────────────────────────────────────
✅ Best practical and conceptual solution.
*/

class Solution {
    public int minCost(String colors, int[] neededTime) {
        int n = neededTime.length;
        int res = 0;
        int a = 0;

        while (a < n) {
            int b = a, sum = 0, max = 0;
            char c = colors.charAt(a);

            // Traverse group of same colors
            while (b < n && colors.charAt(b) == c) {
                sum += neededTime[b];
                max = Math.max(max, neededTime[b]);
                b++;
            }

            // Remove all but the most expensive one
            res += sum - max;
            a = b;
        }

        return res;
    }
}

/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — Single Pointer Sliding Comparison
───────────────────────────────────────────────────────────────
💡 Idea:
Compare each balloon with the previous one:
- If colors are same → remove the one with smaller neededTime.
  Add that smaller cost to total.
- Else → move on.

More compact than two-pointer grouping.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n)  
• Space: O(1)
───────────────────────────────────────────────────────────────
*/

 /*
class Solution {
    public int minCost(String colors, int[] neededTime) {
        int total = 0;
        for (int i = 1; i < colors.length(); i++) {
            if (colors.charAt(i) == colors.charAt(i - 1)) {
                total += Math.min(neededTime[i], neededTime[i - 1]);
                neededTime[i] = Math.max(neededTime[i], neededTime[i - 1]); 
            }
        }
        return total;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥉 Approach 3 — Using Stack (for Conceptual Clarity)
───────────────────────────────────────────────────────────────
💡 Idea:
Push balloons into a stack.  
If the current balloon color == stack top color:
- Pop smaller cost one, add its cost to total.
Else push normally.

Works fine, but space overhead and unnecessary for a linear sequence.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n)
• Space: O(n)
───────────────────────────────────────────────────────────────
*/

/*
class Solution {
    public int minCost(String colors, int[] neededTime) {
        int total = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (int i = 1; i < colors.length(); i++) {
            if (colors.charAt(i) == colors.charAt(stack.peek())) {
                total += Math.min(neededTime[i], neededTime[stack.peek()]);
                if (neededTime[i] > neededTime[stack.peek()]) {
                    stack.pop();
                    stack.push(i);
                }
            } else {
                stack.push(i);
            }
        }

        return total;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Approach 1 (Two-pointer greedy):  
• Time: O(n)  
• Space: O(1)

Approach 2 (Single pass compare):  
• Time: O(n)  
• Space: O(1)

Approach 3 (Stack):  
• Time: O(n)  
• Space: O(n)

✅ Final Choice: Approach 1 — most readable, robust, and optimal.
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🧩 Dry Run Example:
───────────────────────────────────────────────────────────────
colors = "abaac", neededTime = [1,2,3,4,5]

Group 1: "a" (index 0) → keep  
Group 2: "b" (index 1) → keep  
Group 3: "aa" (index 2,3) → remove smaller cost 3 → cost += 3  
Group 4: "c" → keep  
Total = 3
───────────────────────────────────────────────────────────────
*/
