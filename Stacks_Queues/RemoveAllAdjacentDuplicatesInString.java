package Stacks_Queues;

/*
 🔹 Problem: 1047. Remove All Adjacent Duplicates In String
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Stack, String Processing
 🔹 Link: https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a string `s` consisting of lowercase English letters.

A **duplicate removal** consists of removing two **adjacent and equal** characters.
You repeat duplicate removals until no more are possible.

The final resulting string is guaranteed to be unique.

Return that final string.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "abbaca"
Output: "ca"

Example 2:
Input: "azxxzy"
Output: "ay"

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ s.length ≤ 10⁵
 • s contains only lowercase letters

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Repeatedly remove adjacent equal characters until the string stabilizes.

📍 **Approach 1 (Stack Simulation Using Byte Array - Most Optimal):**
   - Treat the output as a stack.
   - Iterate through characters:
       * If current char equals the top of stack → pop.
       * Else → push.
   - Byte array reduces overhead and increases memory locality.
   - Time: O(n), Space: O(n)

   **Why optimal?**
   - Every character is pushed and popped at most once.
   - No repeated rescanning of the string.
   - Explicit byte-stack is faster than Java's built-in stack structures.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Stack Simulation - Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   Adjacent duplicate removal behaves exactly like resolving a stack of characters:
   remove the top if it matches the current character.

   💡 Why it works:
   Local, linear, and guarantees final stabilized string without recursion.
 ------------------------------------------------------------
*/

public class RemoveAllAdjacentDuplicatesInString {

    public String removeDuplicates(String s) {
        byte[] arr = new byte[s.length()];
        int top = -1;

        for (char c : s.toCharArray()) {
            if (top != -1 && arr[top] == (byte)(c - 'a')) {
                top--; // pop
            } else {
                arr[++top] = (byte)(c - 'a'); // push
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            sb.append((char)(arr[i] + 'a'));
        }
        return sb.toString();
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: "abbaca"

Step-by-step stack:
a
a, b
a, b, b → pop → a
a, a → pop → "" 
push c → "c"
push a → "ca"

Final Output: "ca"
 ------------------------------------------------------------
*/
