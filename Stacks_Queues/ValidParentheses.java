package Stacks_Queues;
/*
 🔹 Problem: 20. Valid Parentheses
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Stack, String, Simulation
 🔹 Link: https://leetcode.com/problems/valid-parentheses/

 ------------------------------------------------------------
 📝 Problem Statement:

Given a string `s` containing only the characters:  
'(', ')', '{', '}', '[' and ']'  

Determine if the string is *valid*. A string is valid if:

 • Open brackets are closed by the same type of bracket  
 • Open brackets are closed in the correct order  
 • Every closing bracket has a corresponding open bracket  

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "()"
Output: true

Example 2:
Input: "()[]{}"
Output: true

Example 3:
Input: "(]"
Output: false

Example 4:
Input: "([])"
Output: true

Example 5:
Input: "([)]"
Output: false

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ s.length ≤ 10⁴  
 • s contains only parentheses characters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Validate bracket-pairing correctness using a stack.

📍 **Approach 1 (Stack-Based Validation - Most Optimal):**
   - Iterate through characters:
       * On an opening bracket → push to stack.
       * On a closing bracket → stack top must match exact open type.
   - If mismatch or stack empties incorrectly → invalid.
   - At the end, stack must be empty.

   **Time Complexity:** O(n)  
   **Space Complexity:** O(n)

   **Why optimal?**
   - Single linear traversal  
   - Perfectly models bracket pairing rules  
   - No unnecessary extra structures

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Stack Simulation - Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   Closing brackets must match the *most recent* unmatched opening bracket → LIFO → stack.

   💡 Why it works:
   Stack precisely models nested and ordered bracket structures.

 ------------------------------------------------------------
*/

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            // Opening brackets → push
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // Closing bracket must match stack top
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }

        // Valid only if stack is empty
        return stack.isEmpty();
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: "([])"

Stack operations:
'(' → push  
'[' → push  
']' → pop '['  
')' → pop '('  

Stack empty → valid

 ------------------------------------------------------------
*/

