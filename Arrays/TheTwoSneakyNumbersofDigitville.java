/*
───────────────────────────────────────────────────────────────
📘 Problem: 3289. The Two Sneaky Numbers of Digitville
💡 Difficulty: Easy
🧠 Topics: Array, Hashing, Sorting
🔗 Link: (provide link if needed)
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

In the town of Digitville, there was a list of numbers called nums containing integers from 0 to n - 1.
Each number was supposed to appear exactly once in the list, however, two mischievous numbers sneaked in an additional time,
making the list longer than usual.

Return an array of size two containing the two numbers (in any order) that appear twice.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 2 <= n <= 100
• nums.length == n + 2
• 0 <= nums[i] < n
• nums contains exactly two repeated elements
───────────────────────────────────────────────────────────────
*/

/* 
We present multiple approaches (less optimal → more optimal).
Each approach includes a detailed breakdown and complexity.
Only the most optimal approach is active (uncommented).
*/

// ───────────────────────────────────────────────────────────────
// 🥉 Approach 1 — HashSet (Simple direct)
// Detailed breakdown:
// Idea: Iterate through nums and keep a HashSet of seen numbers.
//       If a number is already in the set, it's a duplicate — collect it.
// Steps:
//  1. Create empty HashSet<Integer> seen.
//  2. Create result array res[2], index idx = 0.
//  3. For each x in nums:
//       if seen contains x -> res[idx++] = x
//       else add x to seen
//    Stop once idx == 2.
// Why it works:
//  - HashSet tracks seen numbers in O(1) average time per lookup/insertion.
// Edge-cases:
//  - Input guarantees exactly two repeated elements, so we will find exactly two.
// Complexity:
//  - Time: O(n)
//  - Space: O(n)
// (Simple and readable; fine for constraints but HashSet has overhead.)

/*
import java.util.*;

class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int[] mis = new int[2];
        int a = 0;
        for (int i : nums) {
            if (set.contains(i)) mis[a++] = i;
            else set.add(i);
            if (a == 2) break;
        }
        return mis;
    }
}
*/

// ───────────────────────────────────────────────────────────────
// 🥈 Approach 2 — Sorting (In-place / small memory)
// Detailed breakdown:
// Idea: Sort the array and scan adjacent elements to detect duplicates.
// Steps:
//  1. Sort nums (O(n log n)).
//  2. Iterate i from 1..nums.length-1:
//       if nums[i] == nums[i-1] -> duplicate found, record it.
//    Stop when two duplicates recorded.
// Why it works:
//  - Duplicates become adjacent after sorting.
// Edge-cases:
//  - Sorting changes order of elements (but answer can be in any order).
// Complexity:
//  - Time: O(n log n)
//  - Space: O(1) or O(n) depending on sort implementation
// (Works fine; slower than linear-time methods but minimal extra memory.)

/*
import java.util.*;

class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        Arrays.sort(nums);
        int[] res = new int[2];
        int idx = 0;
        for (int i = 1; i < nums.length && idx < 2; i++) {
            if (nums[i] == nums[i-1]) {
                res[idx++] = nums[i];
            }
        }
        return res;
    }
}
*/

// ───────────────────────────────────────────────────────────────
// 🏆 Approach 3 — Counting Array (Most optimal for given constraints)
// Detailed breakdown:
// Idea:
//  - Numbers are in [0, n-1] and nums.length == n+2. Use an int[] freq of size n to count occurrences.
//  - On incrementing freq[x], if it becomes 2, record x as one of the sneaky numbers.
// Steps:
//  1. Let n = nums.length - 2 (because input definition), allocate freq = new int[n].
//  2. Create result res[2], idx = 0.
//  3. For each x in nums:
//       freq[x]++
//       if freq[x] == 2: res[idx++] = x
//       if idx == 2: break
//  4. Return res.
// Why it works:
//  - Counting uses direct indexing (O(1)) per element and minimal overhead compared to HashSet.
//  - Given constraints (n ≤ 100), memory and time are trivial; counting is fastest and simplest.
// Edge-cases:
//  - The problem guarantees exactly two duplicates, so no further validation needed.
// Complexity:
//  - Time: O(n) — single pass over nums
//  - Space: O(n) — freq array of size n
//
// This is the active implementation below (clean, no extra comments inside code).

public class TheTwoSneakyNumbersofDigitville {
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;
        int[] freq = new int[n];
        int[] res = new int[2];
        int idx = 0;
        for (int x : nums) {
            freq[x]++;
            if (freq[x] == 2) {
                res[idx++] = x;
                if (idx == 2) break;
            }
        }
        return res;
    }
}
