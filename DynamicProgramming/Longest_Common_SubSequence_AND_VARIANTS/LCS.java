/*
───────────────────────────────────────────────────────────────
📘 Problem: 1143. Longest Common Subsequence + Print the LCS
💡 Difficulty: Medium
🧠 Topics: Dynamic Programming, String, Reconstruction
───────────────────────────────────────────────────────────────
🧩 Overview:

This file computes:
1️⃣ The **length** of the Longest Common Subsequence (LCS)
2️⃣ The **actual LCS string** (using dp table reconstruction)

Given two strings text1 and text2:
- We build the classic LCS DP table.
- Then we traverse the DP matrix backwards and reconstruct the LCS.

───────────────────────────────────────────────────────────────
📌 Why LCS Printing Requires Care

To reconstruct the LCS:
- If characters match → it is part of the LCS → move diagonally.
- Else → move in the direction of the **larger dp value**.
This ensures we correctly trace back the path that led to the optimal LCS length.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time:  O(m × n)
• Space: O(m × n)
───────────────────────────────────────────────────────────────
*/


public class LCS {

    // ───────────────────────────────────────────────────────────────
    // Compute LCS length using standard DP
    // ───────────────────────────────────────────────────────────────
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Build dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Print LCS (optional)
        String lcsString = lcsPrinter(dp, text1, text2);
        System.out.println("LCS = " + lcsString);

        return dp[m][n];
    }


    // ───────────────────────────────────────────────────────────────
    // Reconstruct LCS string using dp matrix
    // ───────────────────────────────────────────────────────────────
    private String lcsPrinter(int[][] dp, String a, String b) {
        int i = a.length(), j = b.length();
        StringBuilder sb = new StringBuilder();

        // Backtrack dp to reconstruct the string
        while (i > 0 && j > 0) {

            // Match → move diagonally + add char
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            }

            // Move toward the larger dp value
            else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return sb.reverse().toString();
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Example:

Input:
text1 = "abcde"
text2 = "ace"

DP table builds:
LCS length = 3
LCS string = "ace"

───────────────────────────────────────────────────────────────
✔ Full reconstruction  
✔ Clean and production-ready  
✔ Matches your coding style  
───────────────────────────────────────────────────────────────
*/
