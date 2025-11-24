package BitManipulation;
/*
 🔹 Problem: 1018. Binary Prefix Divisible By 5
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Math, Bit Manipulation, Prefix
 🔹 Link: https://leetcode.com/problems/binary-prefix-divisible-by-5/

 ------------------------------------------------------------
 🔸 Problem Statement:

You are given a binary array nums (0-indexed).

Define xi as the number represented by the binary prefix nums[0..i].
Return an array where answer[i] is true if xi is divisible by 5.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
Input: nums = [0,1,1]
Output: [true,false,false]

Example 2:
Input: nums = [1,1,1]
Output: [false,false,false]

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= nums.length <= 10⁵
 • nums[i] ∈ {0,1}

 ------------------------------------------------------------
 🔹 Approach Summary:

✅ **Goal:** For each prefix binary number, check divisibility by 5.

### ✔ Key Insight:
You **never** build the full binary number—because prefixes grow exponentially and overflow.

Maintain only:
- A running modulo value: `a = (a * 2 + nums[i]) % 5`
- If remainder becomes 0 → divisible by 5.

Why this works:
Binary left-shift is `a * 2`.  
Appending current bit is `+ nums[i]`.  
Taking `% 5` ensures the value never exceeds 4.

This is the only optimal and meaningful approach.

 ------------------------------------------------------------
 🔹 Final Working Code (Optimal)

*/

import java.util.*;

public class BinaryPrefixDivisibleBy5 {
    public List<Boolean> prefixesDivBy5(int[] nums) {
        Boolean[] ans = new Boolean[nums.length];
        int a = 0;

        for (int i = 0; i < nums.length; i++) {
            a = (a * 2 + nums[i]) % 5;  // rolling modulo
            ans[i] = (a == 0);
        }

        return Arrays.asList(ans);
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
