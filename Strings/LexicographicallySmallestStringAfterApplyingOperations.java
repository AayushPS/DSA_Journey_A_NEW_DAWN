/*
 🔹 Problem: 1625. Lexicographically Smallest String After Applying Operations
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Strings, BFS, Simulation, Hashing
 🔹 Link: https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/

 ------------------------------------------------------------
 🔸 Problem Statement:

 You are given:
 - A numeric string `s` (even length, consisting of digits '0'–'9')
 - Two integers `a` and `b`.

 You can perform either of the following operations *any number of times* in any order:

 1️⃣ **Add Operation**:
     Add `a` to all digits at odd indices (0-indexed).  
     Values roll over (mod 10).  
     Example: s = "3456", a = 5 → "3951"

 2️⃣ **Rotate Operation**:
     Rotate the string `s` to the right by `b` positions.  
     Example: s = "3456", b = 1 → "6345"

 Return the **lexicographically smallest string** possible after applying these operations in any sequence.

 ------------------------------------------------------------
 🔸 Example 1:
 Input: s = "5525", a = 9, b = 2  
 Output: "2050"  

 Explanation:
   Start:  "5525"  
   Rotate: "2555"  
   Add:    "2454"  
   Add:    "2353"  
   Rotate: "5323"  
   Add:    "5222"  
   Add:    "5121"  
   Rotate: "2151"  
   Add:    "2050" ✅ (smallest possible)

 ------------------------------------------------------------
 🔸 Example 2:
 Input: s = "74", a = 5, b = 1  
 Output: "24"  
 Explanation:
   Start: "74"  
   Rotate → "47"  
   Add → "42"  
   Rotate → "24" ✅

 ------------------------------------------------------------
 🔸 Example 3:
 Input: s = "0011", a = 4, b = 2  
 Output: "0011" (no better string possible)

 ------------------------------------------------------------
 🔸 Constraints:
  2 <= s.length <= 100  
  s.length is even  
  s consists of digits from 0 to 9  
  1 <= a <= 9  
  1 <= b <= s.length - 1

 ------------------------------------------------------------
 🔹 Approach (BFS + Greedy Observation):

 ✅ The operations can generate many permutations of `s`.  
 ✅ However, since both operations are deterministic and repeatable,
    we can explore all reachable states using **BFS**.

 1️⃣ Start from the initial string.
 2️⃣ Maintain a queue (BFS) and a visited set to avoid repeats.
 3️⃣ For each state:
     - Try rotation by `b`.
     - Try adding `a` to all odd indices.
     - Push new states into the queue if unseen.
 4️⃣ Track the smallest lexicographical string found.

 This ensures we explore the full reachable space efficiently,
 since the total number of unique configurations is limited.

 ------------------------------------------------------------
 🔸 Implementation:
*/

import java.util.*;

public class LexicographicallySmallestStringAfterApplyingOperations {
    public String findLexSmallestString(String s, int a, int b) {
        int n = s.length();
        Set<String> visited = new HashSet<>();
        Deque<String> q = new ArrayDeque<>();
        q.addLast(s);
        visited.add(s);
        String min = s;
        int db = b % n;

        while (!q.isEmpty()) {
            String cur = q.pollFirst();
            if (cur.compareTo(min) < 0) {
                min = cur;
            }

            // 1️⃣ Rotate operation
            String rotated = cur.substring(n - db) + cur.substring(0, n - db);
            if (!visited.contains(rotated)) {
                visited.add(rotated);
                q.addLast(rotated);
            }

            // 2️⃣ Add operation on odd indices
            char[] arr = cur.toCharArray();
            for (int i = 1; i < arr.length; i += 2) {
                arr[i] = (char) ('0' + ((arr[i] - '0' + a) % 10));
            }
            String added = new String(arr);
            if (!visited.contains(added)) {
                visited.add(added);
                q.addLast(added);
            }
        }
        return min;
    }
}

/*
 ------------------------------------------------------------
 🔹 Complexity Analysis:
   Time  → O(N * 10 * N)  (each possible string explored once)
   Space → O(N * 10)      (visited states set)

 🔹 Key Insight:
   BFS guarantees lexicographical minimality once all reachable states are considered.
   The combination of rotation + modular addition ensures cyclic coverage of possible configurations.
*/