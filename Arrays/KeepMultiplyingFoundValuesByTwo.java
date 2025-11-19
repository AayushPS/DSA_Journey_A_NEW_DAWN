/*
───────────────────────────────────────────────────────────────
📘 Problem: 2154. Keep Multiplying Found Values by Two
💡 Difficulty: Easy
🧠 Topics: Hashing, Simulation
🔗 Link: https://leetcode.com/problems/keep-multiplying-found-values-by-two/
───────────────────────────────────────────────────────────────

You are given:
- An array `nums`.
- An integer `original`.

You repeatedly:
1️⃣ Check if `original` exists in `nums`.  
2️⃣ If yes → multiply it by 2  
3️⃣ If no → stop and return `original`.

───────────────────────────────────────────────────────────────
🧠 Key Idea:
Use a fast lookup structure so we can check "is original present?" in O(1).

Since constraints say all values ≤ 1000, a **boolean array of size 1001** is the fastest choice.

Algorithm:
- Build `present[val] = true` for all values in nums.
- While `original` exists in `present[]`:
    → original *= 2
- Return final original.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n + log(final_value))  
• Space: O(1) (boolean array of fixed size)

───────────────────────────────────────────────────────────────
💻 Approach 1 — Boolean Lookup (Optimal) ✔ (UNCOMMENTED)
───────────────────────────────────────────────────────────────
*/

public class KeepMultiplyingFoundValuesByTwo {
    public int findFinalValue(int[] nums, int original) {
        boolean[] set = new boolean[1001];

        // Mark presence of each number
        for (int val : nums) {
            set[val] = true;
        }

        // Multiply until missing
        while (original < 1001 && set[original]) {
            original *= 2;
        }

        return original;
    }
}

/*
───────────────────────────────────────────────────────────────
🧪 Dry Run Example
───────────────────────────────────────────────────────────────
nums = [5,3,6,1,12], original = 3

Lookup table (set[]):
3 → true  
6 → true  
12 → true  
others irrelevant.

Steps:
original = 3 → present → becomes 6  
original = 6 → present → becomes 12  
original = 12 → present → becomes 24  
original = 24 → NOT present → STOP

Output = 24
───────────────────────────────────────────────────────────────

🎯 Clean, constant-time lookup approach.
───────────────────────────────────────────────────────────────
*/
