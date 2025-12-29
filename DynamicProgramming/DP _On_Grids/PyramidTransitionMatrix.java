/*
 🔹 Problem: 756. Pyramid Transition Matrix
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Backtracking, Trie, Dynamic Programming, Memoization, String
 🔹 Link: https://leetcode.com/problems/pyramid-transition-matrix/

 ------------------------------------------------------------
 📝 Problem Statement:

You are stacking blocks to form a pyramid. Each block has a color, which is represented 
by a single letter. Each row of blocks contains one less block than the row beneath it 
and is centered on top.

To make the pyramid aesthetically pleasing, there are only specific triangular patterns 
that are allowed. A triangular pattern consists of a single block stacked on top of two 
blocks. The patterns are given as a list of three-letter strings allowed, where the first 
two characters of a pattern represent the left and right bottom blocks respectively, and 
the third character is the top block.

For example, "ABC" represents a triangular pattern with a 'C' block stacked on top of an 
'A' (left) and 'B' (right) block. Note that this is different from "BAC" where 'B' is on 
the left bottom and 'A' is on the right bottom.

You start with a bottom row of blocks bottom, given as a single string, that you must use 
as the base of the pyramid.

Given bottom and allowed, return true if you can build the pyramid all the way to the top 
such that every triangular pattern in the pyramid is in allowed, or false otherwise.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
Output: true
Explanation: The allowed triangular patterns are shown on the right.
Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.

Example 2:
Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
Output: false
Explanation: The allowed triangular patterns are shown on the right.
Starting from the bottom (level 4), there are multiple ways to build level 3, but trying 
all the possibilities, you will get always stuck before building level 1.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 <= bottom.length <= 6
 • 0 <= allowed.length <= 216
 • allowed[i].length == 3
 • The letters in all input strings are from the set {'A', 'B', 'C', 'D', 'E', 'F'}
 • All the values of allowed are unique

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Determine if we can build a complete pyramid using only allowed patterns.

📍 **Approach 1 (Trie + Backtracking):**
   - Build a 3-level Trie structure: left char → right char → top char
   - Use backtracking to try all possible patterns at each level
   - Navigate through Trie to check valid patterns
   - Recursively build pyramid level by level until reaching the top

📍 **Approach 2 (HashMap + Backtracking):**
   - Store patterns in HashMap: "leftRight" → list of possible tops
   - Simpler lookup compared to Trie
   - Use backtracking to explore all valid combinations
   - More intuitive and easier to understand

📍 **Approach 3 (Trie + Memoization):**
   - Combines Trie structure with memoization cache
   - Cache results for each intermediate level string
   - Avoids recomputing same pyramid configurations
   - Better performance for overlapping subproblems

📍 **Approach 4 (3D Boolean Array + Memoization - Optimized):**
   - Use 3D boolean array: allow[left][right][top]
   - O(1) lookup time for pattern validation
   - Memoization prevents redundant computations
   - Most space and time efficient approach

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Trie + Backtracking)
   ⏱️ Time Complexity: O(6^n) - worst case trying all combinations
   💾 Space Complexity: O(N) - recursion stack and Trie structure
   
   🧠 **Key Insight:**
   Trie provides structured way to store and lookup valid patterns efficiently.
   Three levels in Trie correspond to left, right, and top characters.
   
   💡 **Why it works:**
   - Trie naturally represents the hierarchical pattern structure
   - Backtracking explores all valid pyramid configurations
   - Each level reduces by 1 block until reaching single top block
 ------------------------------------------------------------

// import java.util.*;
//
// class Solution {
//     private static class Trie {
//         Trie[] children = new Trie[6];
//     }
//     
//     public boolean pyramidTransition(String bottom, List<String> allowed) {
//         Trie trie = new Trie();
//         
//         for(String s : allowed) {
//             Trie curr = trie;
//             
//             int left = s.charAt(0) - 'A';
//             if(curr.children[left] == null) {
//                 curr.children[left] = new Trie();
//             }
//             curr = curr.children[left];
//             
//             int right = s.charAt(1) - 'A';
//             if(curr.children[right] == null) {
//                 curr.children[right] = new Trie();
//             }
//             curr = curr.children[right];
//             
//             int top = s.charAt(2) - 'A';
//             if(curr.children[top] == null) {
//                 curr.children[top] = new Trie();
//             }
//         }
//         
//         return buildPyramid(bottom, trie);
//     }
//     
//     private boolean buildPyramid(String bottom, Trie trie) {
//         if(bottom.length() == 1) return true;
//         return buildLevel(bottom, trie, 0, new StringBuilder());
//     }
//     
//     private boolean buildLevel(String bottom, Trie trie, int pos, StringBuilder nextLevel) {
//         if(pos == bottom.length() - 1) {
//             return buildPyramid(nextLevel.toString(), trie);
//         }
//         
//         Trie curr = trie;
//         int left = bottom.charAt(pos) - 'A';
//         int right = bottom.charAt(pos + 1) - 'A';
//         
//         if(curr.children[left] == null) return false;
//         curr = curr.children[left];
//         
//         if(curr.children[right] == null) return false;
//         curr = curr.children[right];
//         
//         for(int i = 0; i < 6; i++) {
//             if(curr.children[i] != null) {
//                 char top = (char)('A' + i);
//                 nextLevel.append(top);
//                 if(buildLevel(bottom, trie, pos + 1, nextLevel)) {
//                     return true;
//                 }
//                 nextLevel.deleteCharAt(nextLevel.length() - 1);
//             }
//         }
//         
//         return false;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - HashMap + Backtracking)
   ⏱️ Time Complexity: O(6^n) - similar to Trie approach
   💾 Space Complexity: O(N + M) - N for recursion, M for HashMap storage
   
   🧠 **Key Insight:**
   HashMap provides simpler and more intuitive pattern lookup compared to Trie.
   Key is "leftRight" string, value is list of valid top characters.
   
   💡 **Why it works:**
   - Direct mapping from bottom pair to possible tops
   - Easier to understand and implement than Trie
   - Backtracking systematically explores all valid combinations
   - String concatenation for keys is straightforward
 ------------------------------------------------------------

// import java.util.*;
//
// class Solution {
//     private Map<String, List<Character>> map;
//     
//     public boolean pyramidTransition(String bottom, List<String> allowed) {
//         map = new HashMap<>();
//         
//         // Build map: "leftRight" -> list of possible tops
//         for(String s : allowed) {
//             String key = s.substring(0, 2);
//             map.putIfAbsent(key, new ArrayList<>());
//             map.get(key).add(s.charAt(2));
//         }
//         
//         return buildPyramid(bottom);
//     }
//     
//     private boolean buildPyramid(String bottom) {
//         if(bottom.length() == 1) return true;
//         return buildLevel(bottom, 0, new StringBuilder());
//     }
//     
//     private boolean buildLevel(String bottom, int pos, StringBuilder nextLevel) {
//         if(pos == bottom.length() - 1) {
//             return buildPyramid(nextLevel.toString());
//         }
//         
//         String key = "" + bottom.charAt(pos) + bottom.charAt(pos + 1);
//         List<Character> tops = map.get(key);
//         
//         if(tops == null) return false;
//         
//         for(char top : tops) {
//             nextLevel.append(top);
//             if(buildLevel(bottom, pos + 1, nextLevel)) {
//                 return true;
//             }
//             nextLevel.deleteCharAt(nextLevel.length() - 1);
//         }
//         
//         return false;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 3 (Commented - Trie + Memoization)
   ⏱️ Time Complexity: O(6^n) - with memoization reducing redundant work
   💾 Space Complexity: O(N + M) - N for recursion, M for memo cache
   
   🧠 **Key Insight:**
   Combining Trie with memoization avoids recomputing same intermediate levels.
   Cache stores whether a given level string can build to top successfully.
   
   💡 **Why it works:**
   - Same pyramid configurations may be reached through different paths
   - Memoization caches results for each unique level string
   - Significant speedup when overlapping subproblems exist
   - Trie still provides efficient pattern lookup
 ------------------------------------------------------------

// import java.util.*;
//
// class Solution {
//     private static class Trie {
//         Trie[] children = new Trie[6];
//     }
//     
//     private Map<String, Boolean> memo;
//     
//     public boolean pyramidTransition(String bottom, List<String> allowed) {
//         Trie trie = new Trie();
//         memo = new HashMap<>();
//         
//         for(String s : allowed) {
//             Trie curr = trie;
//             
//             int left = s.charAt(0) - 'A';
//             if(curr.children[left] == null) {
//                 curr.children[left] = new Trie();
//             }
//             curr = curr.children[left];
//             
//             int right = s.charAt(1) - 'A';
//             if(curr.children[right] == null) {
//                 curr.children[right] = new Trie();
//             }
//             curr = curr.children[right];
//             
//             int top = s.charAt(2) - 'A';
//             if(curr.children[top] == null) {
//                 curr.children[top] = new Trie();
//             }
//         }
//         
//         return buildPyramid(bottom, trie);
//     }
//     
//     private boolean buildPyramid(String bottom, Trie trie) {
//         if(bottom.length() == 1) return true;
//         
//         if(memo.containsKey(bottom)) {
//             return memo.get(bottom);
//         }
//         
//         boolean result = buildLevel(bottom, trie, 0, new StringBuilder());
//         memo.put(bottom, result);
//         return result;
//     }
//     
//     private boolean buildLevel(String bottom, Trie trie, int pos, StringBuilder nextLevel) {
//         if(pos == bottom.length() - 1) {
//             return buildPyramid(nextLevel.toString(), trie);
//         }
//         
//         Trie curr = trie;
//         int left = bottom.charAt(pos) - 'A';
//         int right = bottom.charAt(pos + 1) - 'A';
//         
//         if(curr.children[left] == null) return false;
//         curr = curr.children[left];
//         
//         if(curr.children[right] == null) return false;
//         curr = curr.children[right];
//         
//         for(int i = 0; i < 6; i++) {
//             if(curr.children[i] != null) {
//                 char top = (char)('A' + i);
//                 nextLevel.append(top);
//                 if(buildLevel(bottom, trie, pos + 1, nextLevel)) {
//                     return true;
//                 }
//                 nextLevel.deleteCharAt(nextLevel.length() - 1);
//             }
//         }
//         
//         return false;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 4 (✅ 3D Boolean Array + Memoization - Most Optimal)
   ⏱️ Time Complexity: O(6^n) - with memoization and O(1) lookups
   💾 Space Complexity: O(1) - 3D array is fixed size (6×6×6) + O(N) for memo and recursion
   
   🧠 **Key Insight:**
   Since we only have 6 possible characters (A-F), we can use a 3D boolean array
   where allow[left][right][top] indicates if the pattern is valid. This gives us
   O(1) lookup time instead of HashMap or Trie traversal.
   
   💡 **Why it works:**
   - 3D array: allow[left][right][] stores all valid tops for a (left, right) pair
   - O(1) pattern validation vs O(log N) for HashMap or O(depth) for Trie
   - Memoization caches whether each level string can build to completion
   - Backtracking tries all valid top blocks for each adjacent pair
   - Recursively builds pyramid level by level until single block remains
   - Fixed character set (A-F) makes array approach practical and efficient
 ------------------------------------------------------------
*/

import java.util.*;

public class PyramidTransitionMatrix {
    private boolean[][][] allow;
    private Map<String, Boolean> memo;
    
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        // Initialize 3D boolean array for O(1) pattern lookup
        allow = new boolean[6][6][];
        memo = new HashMap<>();
        
        // Build allow array from allowed patterns
        for(String s : allowed) {
            int left = s.charAt(0) - 'A';
            int right = s.charAt(1) - 'A';
            int top = s.charAt(2) - 'A';
            
            if(allow[left][right] == null) {
                allow[left][right] = new boolean[6];
            }
            allow[left][right][top] = true;
        }
        
        return buildPyramid(bottom);
    }
    
    private boolean buildPyramid(String bottom) {
        // Base case: reached the top of pyramid
        if(bottom.length() == 1) return true;
        
        // Check memoization cache
        if(memo.containsKey(bottom)) {
            return memo.get(bottom);
        }
        
        // Try to build next level
        boolean result = buildLevel(bottom, 0, new StringBuilder());
        memo.put(bottom, result);
        return result;
    }
    
    private boolean buildLevel(String bottom, int pos, StringBuilder nextLevel) {
        // Completed building current level, move to next level up
        if(pos == bottom.length() - 1) {
            return buildPyramid(nextLevel.toString());
        }
        
        // Get adjacent pair indices
        int left = bottom.charAt(pos) - 'A';
        int right = bottom.charAt(pos + 1) - 'A';
        
        // No valid patterns for this pair
        if(allow[left][right] == null) return false;
        
        // Try all valid top blocks for this pair (backtracking)
        for(int i = 0; i < 6; i++) {
            if(allow[left][right][i]) {
                char top = (char)('A' + i);
                nextLevel.append(top);
                
                // Recursively try to complete the level
                if(buildLevel(bottom, pos + 1, nextLevel)) {
                    return true;
                }
                
                // Backtrack: remove last character and try next option
                nextLevel.deleteCharAt(nextLevel.length() - 1);
            }
        }
        
        return false;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]

Step 1: Build allow array
allow[1][2][2] = true  (B,C → C)
allow[2][3][4] = true  (C,D → E)
allow[2][4][0] = true  (C,E → A)
allow[5][5][5] = true  (F,F → F)

Step 2: buildPyramid("BCD")
  → buildLevel("BCD", 0, "")
  
Step 3: Process position 0 (B,C pair)
  → left=1, right=2
  → allow[1][2][2]=true → try top='C'
  → nextLevel = "C"
  → buildLevel("BCD", 1, "C")

Step 4: Process position 1 (C,D pair)
  → left=2, right=3
  → allow[2][3][4]=true → try top='E'
  → nextLevel = "CE"
  → pos=2 (== bottom.length-1)
  → buildPyramid("CE")

Step 5: buildPyramid("CE")
  → buildLevel("CE", 0, "")
  
Step 6: Process position 0 (C,E pair)
  → left=2, right=4
  → allow[2][4][0]=true → try top='A'
  → nextLevel = "A"
  → pos=1 (== bottom.length-1)
  → buildPyramid("A")

Step 7: buildPyramid("A")
  → length=1, return true ✅

Final Result: true ✅

💡 Note: The pyramid built is:
    A
   C E
  B C D

All patterns used (BCC, CDE, CEA) are in the allowed list.
 ------------------------------------------------------------
*/