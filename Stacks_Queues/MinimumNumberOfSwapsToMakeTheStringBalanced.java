package Stacks_Queues;
/*
 🔹 Problem: 1963. Minimum Number of Swaps to Make the String Balanced
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Stack, Two-Pointers, Greedy, String
 🔹 Link: https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a string `s` of even length, containing exactly n/2 '[' and n/2 ']'.

A string is considered **balanced** if:
• It is empty, or  
• It is AB where A and B are balanced strings, or  
• It is [C] where C is balanced  

You may swap **any two indices any number of times**.

Return the **minimum number of swaps** needed to make the string balanced.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "][]["
Output: 1

Example 2:
Input: "]]][[["
Output: 2

Example 3:
Input: "[]"
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ s.length ≤ 1,000,000  
 • s.length is even  
 • s has equal numbers of '[' and ']'

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Minimize swaps needed to fix bracket imbalance.

📍 **Approach 1 (Two-pointer Swap Simulation — Suboptimal but Valid):**
   - Scan from left to right tracking imbalance.
   - When more ']' than '[' appears, find the nearest '[' from the right and swap.
   - Explicitly simulates the process.
   - Time: O(n), but involves pointer movement & swapping logic.

📍 **Approach 2 (Greedy Imbalance Counting — Most Optimal):**
   - Track the net number of unmatched '[' brackets.
   - Every time imbalance becomes negative, it means a ']' has no matching '[' before it.
   - The final required number of swaps = (remaining_imbalance + 1) / 2
   - Pure linear pass, no pointer movement, no actual swapping required.

   **This is the chosen final approach due to simplicity and optimality.**

 ------------------------------------------------------------
 🔹 Approach 1 (Commented — Two-pointer Swap Simulation)
   ⏱️ Time Complexity: O(n)
   💾 Space Complexity: O(1)

   🧠 Key Insight:
   Fix imbalance by pulling a '[' from the nearest right side.

   💡 Why it works:
   Ensures each time you repair imbalance with the minimal viable swap.
 -------------------------------------------------------------

/*
class Solution {
    public int minSwaps(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int j = n - 1;

        while (arr[j] != '[') j--;

        int open = 0, close = 0, swaps = 0;

        for (int i = 0; i < n; i++) {
            if (arr[i] == '[') open++;
            else close++;

            if (close > open) {
                char temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                swaps++;
                open++;
                close--;
                while (arr[j] != '[') j--;
            }
        }

        if (open != close) swaps += open / 2;
        return swaps;
    }
}
*/
/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Imbalance Counting Formula — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(1)
   
   🧠 Key Insight:
   The number of swaps needed is half of the maximum depth of imbalance.

   💡 Why it works:
   - Each unmatched ']' indicates a missing '[' before it.
   - The imbalance can only be fixed by swapping '[' from later in the string.
   - Mathematical derivation gives exact minimum swaps:
         swaps = (max_imbalance + 1) / 2
 ------------------------------------------------------------
*/

public class MinimumNumberOfSwapsToMakeTheStringBalanced {

    public int minSwaps(String s) {
        int imbalance = 0;  // counts '[' that aren't matched yet

        for (char c : s.toCharArray()) {
            if (c == '[') {
                imbalance++;
            } else if (imbalance > 0) {
                imbalance--; // match a '['
            }
        }

        return (imbalance + 1) / 2;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: "][]["
Traverse:
] → imbalance = 0 (problematic)
[ → imbalance = 1
] → imbalance = 0
[ → imbalance = 1

Final imbalance = 1  
Answer = (1 + 1) / 2 = 1 swap

 ------------------------------------------------------------
*/
