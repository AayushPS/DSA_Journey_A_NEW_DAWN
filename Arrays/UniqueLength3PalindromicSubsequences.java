/*
───────────────────────────────────────────────────────────────
📘 Problem: 1930. Unique Length-3 Palindromic Subsequences
💡 Difficulty: Medium
🧠 Topics: String, Hashing, Two-Pointers
🔗 Link: https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
───────────────────────────────────────────────────────────────

We need to count the **number of UNIQUE palindromic subsequences** of length 3.

A palindrome of length 3 looks like:
        a _ a
So the structure is:
        same outer char + ANY center char.

Thus for each character 'x':
    Find its first index L and last index R.
    Any unique middle character in s[L+1 … R-1]
    forms a palindrome: x y x

───────────────────────────────────────────────────────────────
🔥 Key Insight
Rather than checking all subsequences (too big), fix the outer char:

For each char c ∈ ['a'..'z']:
    find first occurrence f[c]
    find last occurrence l[c]
    if f[c] < l[c]:
         count distinct characters in between.
───────────────────────────────────────────────────────────────
⏱ Complexity
• Time: O(26 * n) → effectively O(n)  
• Space: O(1) — only arrays of size 26  
───────────────────────────────────────────────────────────────
*/

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UniqueLength3PalindromicSubsequences {

    public int countPalindromicSubsequence(String s) {
        Set<Character> set = new HashSet<>();
        int res = 0;

        // firstAndLast[x][0] = first index of char x
        // firstAndLast[x][1] = last index of char x
        int[][] firstAndLast = new int[26][2];
        for (int[] a : firstAndLast) Arrays.fill(a, -1);

        // Find FIRST occurrences
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (firstAndLast[c - 'a'][0] == -1) {
                firstAndLast[c - 'a'][0] = i;
            }
        }

        // Find LAST occurrences
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (firstAndLast[c - 'a'][1] == -1) {
                firstAndLast[c - 'a'][1] = i;
            }
        }

        // For each character, count distinct middle chars
        for (int[] a : firstAndLast) {
            int L = a[0], R = a[1];

            if (L == -1 || L + 1 >= R) continue; // no room for middle char

            for (int mid = L + 1; mid < R; mid++) {
                set.add(s.charAt(mid));
            }

            res += set.size();
            set.clear();
        }

        return res;
    }
}



/*
───────────────────────────────────────────────────────────────
🧪 DRY RUN — Example: s = "aabca"
───────────────────────────────────────────────────────────────

We check each character a..z.

Character 'a':
  first = 0, last = 4
  middle zone = "abc"
  distinct chars = { 'a','b','c' }
  Palindromes formed:
      a a a
      a b a
      a c a
  Count = 3

Character 'b':
  first = 2, last = 2 → no middle → skip

Character 'c':
  first = 3, last = 3 → skip

FINAL ANSWER = 3

Matches expected output.
───────────────────────────────────────────────────────────────
✔ Clean
✔ Optimal
✔ No duplicates counted
───────────────────────────────────────────────────────────────
*/
