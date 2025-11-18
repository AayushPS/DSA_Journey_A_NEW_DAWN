package BitManipulation;
/*
──────────────────────────────────────────────────────────────────────────
📘 Problem: 717. 1-bit and 2-bit Characters
💡 Difficulty: Easy
🧠 Topics: Array, Bit Manipulation, Greedy
🔗 Link: https://leetcode.com/problems/1-bit-and-2-bit-characters/
──────────────────────────────────────────────────────────────────────────

You are given a binary array `bits` that encodes characters:

• A one-bit character: 0  
• A two-bit character: 10 or 11

The array **always ends with a 0**, and your task is to determine whether
the **last character must be a 1-bit character**.

──────────────────────────────────────────────────────────────────────────
📌 Intuition

Three key facts:

1️⃣ If you see a **0**, it represents a 1-bit character.  
2️⃣ If you see a **1**, it MUST be the start of a 2-bit character.  
3️⃣ The question is:  
   **Does decoding force the last `0` to be alone (1-bit), or can it be part of a 2-bit pair?**

Your approach checks:

- If the second-last is `0` → immediately YES (since `0 0` → last is single)  
- Otherwise, count trailing ones before final zero:
  - Odd count → last zero belongs to a 2-bit character → FALSE  
  - Even count → last zero stands alone → TRUE  

──────────────────────────────────────────────────────────────────────────
🥇 Approach 1 — Count trailing ones before the final zero  
──────────────────────────────────────────────────────────────────────────
💡 Idea Breakdown:

Let `bits` end with `… 1 1 1 1 0`

To know if this last `0` is **independent**, count how many `1`s appear right before it:

- If count is **even** → they pair up as 2-bit characters → final `0` stands alone → TRUE  
- If count is **odd** → last `1` pairs with final `0` → FALSE  

Special cases included:

✔ If array is `[0]` → TRUE  
✔ If the second-last bit is `0` → last must be single  

──────────────────────────────────────────────────────────────────────────
🧮 Complexity
Time: O(n)  
Space: O(1)  

──────────────────────────────────────────────────────────────────────────
💻 Code (Your Original Implementation — Unmodified)
──────────────────────────────────────────────────────────────────────────
*/

public class OneBitAndTwoBitCharacters {
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        if(n==1 && bits[0]==0) return true;

        // If second last is zero → last must be a single-bit character
        if(bits[n-2]==0) return true;
        else{
            int l = 0;
            int i = n-2;

            // Count how many 1s appear before final zero
            for(;i>=0 && bits[i]==1; i--){
                l++;
            }

            // If trailing ones count is even → valid single-bit zero
            if(l % 2 == 0) return true;
        }
        return false;
    }
}

/*
──────────────────────────────────────────────────────────────────────────
🧪 Dry Run Example
──────────────────────────────────────────────────────────────────────────

Example: bits = [1,1,1,0]

Index:        0 1 2 3
Values:       1 1 1 0

Step-by-step:
- Check n==1? No.
- Check bits[n-2] == 0?  bits[2] = 1 → NO.
- Count trailing ones before last zero:
    bits[2] = 1 → count = 1
    bits[1] = 1 → count = 2
    bits[0] = 1 → count = 3

Count = 3 → odd → final zero is part of "10" → NOT a 1-bit character.

Return FALSE.

──────────────────────────────────────────────────────────────────────────
Example 2: bits = [1,0,0]

Index:        0 1 2
Values:       1 0 0

- bits[n-2] = bits[1] = 0 → immediate TRUE

──────────────────────────────────────────────────────────────────────────
🎯 Final Notes:
• This implementation matches your logic exactly.
• The file is GitHub-ready, documented, polished, and properly structured.
──────────────────────────────────────────────────────────────────────────
*/
