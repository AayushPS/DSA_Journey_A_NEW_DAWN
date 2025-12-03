package Stacks_Queues;
/*
 🔹 Problem: 946. Validate Stack Sequences
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Stack, Simulation
 🔹 Link: https://leetcode.com/problems/validate-stack-sequences/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given two arrays:
 - pushed[]: order in which elements are pushed into a stack.
 - popped[]: desired sequence of popped elements.

All elements are distinct.

Determine whether popped[] can be obtained through a valid series of
push + pop operations on an initially empty stack.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input:
pushed = [1,2,3,4,5]
popped = [4,5,3,2,1]
Output: true

Example 2:
Input:
pushed = [1,2,3,4,5]
popped = [4,3,5,1,2]
Output: false

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ pushed.length ≤ 1000  
 • Values in pushed[] are unique  
 • popped is a permutation of pushed

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Simulate push/pop operations to verify stack validity.

📍 **Approach 1 (Stack Simulation with Push Pointer - Most Optimal):**
   - Use a stack to simulate the real process.
   - Maintain a pointer on pushed[]:
       * Keep pushing until top of stack matches popped[i].
       * If we exhaust pushed[] and still can't match popped[i] → invalid.
   - Pop when the top matches popped[i].

   **Time Complexity:** O(n)  
   **Space Complexity:** O(n)

   **Why optimal?**
   - Every element is pushed and popped at most once.
   - Perfectly mimics actual stack behavior.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Stack Simulation - Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   If an element is required to pop, the stack must already contain it.
   So push until it appears or until no more pushes are possible.

   💡 Why it works:
   The stack must match the relative constraints imposed by popped[].
 ------------------------------------------------------------
*/

import java.util.ArrayDeque;

public class ValidateStackSequences {

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int n = popped.length;
        int ptr = 0;

        for (int target : popped) {
            // Push until the required element appears on top
            while (stack.isEmpty() || stack.peekLast() != target) {
                if (ptr >= n) return false;
                stack.addLast(pushed[ptr++]);
            }
            stack.pollLast(); // pop the matching element
        }

        return true;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

pushed = [1,2,3,4,5]
popped = [4,5,3,2,1]

Need 4 → push 1,2,3,4 → pop  
Need 5 → push 5 → pop  
Need 3 → top = 3 → pop  
Need 2 → pop  
Need 1 → pop  

Valid sequence → true

 ------------------------------------------------------------
*/
