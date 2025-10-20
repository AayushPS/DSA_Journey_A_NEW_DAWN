/*
 🔹 Problem: 2011. Final Value of Variable After Performing Operations
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: String, Simulation
 🔹 Link: https://leetcode.com/problems/final-value-of-variable-after-performing-operations/

 ------------------------------------------------------------
 🔸 Problem Statement:

There is a programming language that has only one integer variable `X`
and four types of operations that can be performed on it:

   1️⃣ ++X  → Increments X by 1 (prefix increment)
   2️⃣ X++  → Increments X by 1 (postfix increment)
   3️⃣ --X  → Decrements X by 1 (prefix decrement)
   4️⃣ X--  → Decrements X by 1 (postfix decrement)

Initially, X = 0.

Given an array of strings `operations`, where each element is one of the above four,
return the **final value of X** after performing all the operations.

 ------------------------------------------------------------
 🔸 Example 1:
 Input: operations = ["--X", "X++", "X++"]
 Output: 1
 Explanation:
   X = 0
   "--X" → X = -1
   "X++" → X = 0
   "X++" → X = 1 ✅

 ------------------------------------------------------------
 🔸 Example 2:
 Input: operations = ["++X","++X","X++"]
 Output: 3
 Explanation:
   X = 0 → 1 → 2 → 3 ✅

 ------------------------------------------------------------
 🔸 Example 3:
 Input: operations = ["X++","++X","--X","X--"]
 Output: 0
 Explanation:
   X = 0 → 1 → 2 → 1 → 0 ✅

 ------------------------------------------------------------
 🔸 Constraints:
  • 1 <= operations.length <= 100
  • Each operation ∈ {"++X", "X++", "--X", "X--"}

 ------------------------------------------------------------
 🔹 Approach:

✅ Observation:
   - Only two possible effects on `X`: +1 or -1.
   - Any operation containing '+' → increment.
   - Any operation containing '-' → decrement.

✅ So we simply:
   1️⃣ Initialize X = 0
   2️⃣ Loop through all operations
   3️⃣ Check middle character:
       - If '+', increment
       - If '-', decrement
   4️⃣ Return X

✅ Time Complexity: O(N)
✅ Space Complexity: O(1)
*/

class Solution {
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for (String op : operations) {
            if (op.charAt(1) == '+') x++;
            else x--;
        }
        return x;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Walkthrough:

 Input: ["X++","++X","--X","X--"]
 Steps:
   X = 0
   'X++' → X = 1
   '++X' → X = 2
   '--X' → X = 1
   'X--' → X = 0
 Final Output: 0 ✅
 ------------------------------------------------------------
 🔹 Complexity Summary:
   Time  → O(N)
   Space → O(1)
 ------------------------------------------------------------
*/