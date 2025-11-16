/*
 🔹 Problem: 2125. Number of Laser Beams in a Bank
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Arrays, Strings, Counting
 🔹 Link: https://leetcode.com/problems/number-of-laser-beams-in-a-bank/

 ------------------------------------------------------------
 🔸 Problem Statement:

A bank's security system has devices represented by '1's in a binary matrix.
Each string in the array `bank` represents a row of rooms.

There exists **one laser beam** between two security devices if:
  1️⃣ They are in different rows (r1 < r2)
  2️⃣ All rows between r1 and r2 have **no devices**

Return the **total number of laser beams** between all valid pairs of devices.

 ------------------------------------------------------------
 🔸 Example:

Input:
["011001","000000","010100","001000"]

Output:
8

Explanation:
Row 0 → 3 devices  
Row 2 → 2 devices  
Row 3 → 1 device  
Beams = (3×2) + (2×1) = 8

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= m, n <= 500
 • bank[i][j] is either '0' or '1'

 ------------------------------------------------------------
 🔹 Approach Summary:

✅ Count number of devices ('1's) row-wise  
✅ Skip empty rows (0 devices)  
✅ Multiply consecutive non-empty rows: prev * curr  
✅ Accumulate all such products  

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Uses Extra Memory)
   Count '1's for all rows → store in array → multiply consecutive valid rows.
   - Time Complexity: O(m * n)
   - Space Complexity: O(m)
 ------------------------------------------------------------

// class Solution {
//     public int numberOfBeams(String[] bank) {
//         int n = bank.length;
//         int[] sumArr = new int[n];
//         int k = 0;
//         for (String s : bank) {
//             int count = 0;
//             for (char c : s.toCharArray()) {
//                 if (c == '1') count++;
//             }
//             sumArr[k++] = count;
//         }
//         int ans = 0;
//         int prev = 0;
//         for (int i = 0; i < n; i++) {
//             if (sumArr[i] == 0) continue;
//             if (prev != 0) ans += prev * sumArr[i];
//             prev = sumArr[i];
//         }
//         return ans;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (✅ Optimized - Constant Space)
   Keep track of only previous non-empty row's device count.
   - Time Complexity: O(m * n)
   - Space Complexity: O(1)
 ------------------------------------------------------------
*/

public class NumberOfLaserBeamsInABank {
    public int numberOfBeams(String[] bank) {
        int ans = 0, prev = 0, curr;
        for (String row : bank) {
            curr = 0;
            for (char c : row.toCharArray()) {
                if (c == '1') curr++;
            }
            if (curr == 0) continue;
            if (prev != 0) ans += prev * curr;
            prev = curr;
        }
        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

bank = ["011001", "000000", "010100", "001000"]
Row 0 → curr=3, prev=0 → skip addition
Row 1 → curr=0 → skip
Row 2 → curr=2, prev=3 → ans += 3×2 = 6
Row 3 → curr=1, prev=2 → ans += 2×1 = 2
Total ans = 8 ✅
 ------------------------------------------------------------
*/
