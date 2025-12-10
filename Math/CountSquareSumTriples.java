package Math;
/*
 🔹 Problem: 1925. Count Square Sum Triples
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Math, Number Theory, Brute Force
 🔹 Link: https://leetcode.com/problems/count-square-sum-triples/

 ------------------------------------------------------------
 📝 Problem Statement:

A *square triple* (a, b, c) satisfies:

        a² + b² = c²  
        1 ≤ a, b, c ≤ n  

Return how many such ordered triples exist.

Note:
• (a, b, c) and (b, a, c) count as **two** triples if a ≠ b.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: n = 5
Output: 2  
(3,4,5), (4,3,5)

Example 2:
Input: n = 10
Output: 4  
(3,4,5), (4,3,5), (6,8,10), (8,6,10)

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ n ≤ 250

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count all ordered pairs (a, b) where a² + b² is a perfect square ≤ n².

📍 **Approach 1 (Brute-Force + Square Check — Most Optimal for n ≤ 250):**
   - Iterate over all possible a and b (1..n).
   - Compute s = a² + b².
   - Check if √s is an integer c and c ≤ n.
   - Increment count.

   **Why optimal here?**
   - n ≤ 250 → O(n²) = 62,500 iterations → trivial for constraints.
   - No need for sieve or precomputation.

   **Time Complexity:** O(n²)  
   **Space Complexity:** O(1)

   **Potential micro-boost:** use integer arithmetic instead of double, but double is acceptable due to small limits.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Brute-Force Square Check — Most Optimal)
   - Time Complexity: O(n²)
   - Space Complexity: O(1)

   🧠 Key Insight:
     Every valid triple satisfies a² + b² = c²; ordered pairs count separately.

   💡 Why it works:
     Direct enumeration matches constraints and guarantees correctness.

 ------------------------------------------------------------
*/

public class CountSquareSumTriples {

    public int countTriples(int n) {
        int count = 0;

        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= n; b++) {

                double c = Math.sqrt(a * a + b * b);
                int ci = (int) c;

                if (c == ci && ci <= n) {
                    count++;
                }
            }
        }

        return count;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

n = 5  
Pairs (a,b):
(3,4) → 9 + 16 = 25 → √25 = 5 → valid  
(4,3) → valid  

Total = 2

 ------------------------------------------------------------
*/
