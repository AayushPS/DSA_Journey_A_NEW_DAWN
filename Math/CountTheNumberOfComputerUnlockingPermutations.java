package Math;

/*
 🔹 Problem: 3577. Count the Number of Computer Unlocking Permutations
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Greedy, Sorting, Combinatorics
 🔹 Link: https://leetcode.com/problems/count-the-number-of-computer-unlocking-permutations/

 ------------------------------------------------------------
 📝 Problem Statement:

There are n computers labeled from 0 to n-1.
• Computer 0 is already unlocked (root).
• You can unlock computer i using any previously unlocked computer j if:
  
        j < i  AND  complexity[j] < complexity[i]

A permutation P of [0…n−1] is a valid unlocking order if:
• P[0] = 0  
• For every index t>0, computer P[t] has some earlier position u<t such that:
        P[u] < P[t] AND complexity[P[u]] < complexity[P[t]]

Return the number of valid permutations modulo 1e9+7.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [1,2,3]
Output: 2
Valid permutations:
  [0,1,2]
  [0,2,1]

Example 2:
Input: [3,3,3,4,4,4]
Output: 0
No computer other than 0 has strictly higher complexity → impossible.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ n ≤ 100,000
 • 1 ≤ complexity[i] ≤ 1e9

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Determine how many permutations allow unlocking all computers.

📍 **Approach 1 (Greedy Validity Check + Factorial Count — Most Optimal)**

Key Observations:

1. To unlock computer i, we need **some j < i** such that:
        complexity[j] < complexity[i]
   Since j must be < i, only earlier-labeled machines can unlock later-labeled ones.

2. The only computer initially unlocked is computer 0.

3. Therefore:
   - For ANY i>0, complexity[i] **must be strictly greater than** complexity[0].
   - If **any** complexity[i] ≤ complexity[0], unlocking is impossible → answer = 0.

4. If all complexities[i] > complexity[0]:
   - Then *any order* of the remaining (n−1) computers is valid.
   - Why? Because 0 < every i, and complexity[0] < complexity[i], so computer 0 can unlock everyone.
   - After unlocking additional computers, nothing restricts order further.

Thus:

      Answer = (n - 1)! % MOD

Time Complexity: O(n)  
Space Complexity: O(1)

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Greedy + Factorial Counting — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(1)

   🧠 Key Insight:
     Computer 0 must be able to unlock every other computer directly.
     If that is not true even once → no ordering works.

   💡 Why it works:
     The constraints collapse into a simple global rule thanks to j<i unlock condition.

 ------------------------------------------------------------
*/

public class CountTheNumberOfComputerUnlockingPermutations {

    public int countPermutations(int[] complexity) {
        int n = complexity.length;
        int root = complexity[0];

        // If any computer cannot be unlocked by computer 0 → impossible
        for (int i = 1; i < n; i++) {
            if (complexity[i] <= root) {
                return 0;
            }
        }

        long ans = 1;
        long MOD = 1_000_000_007;

        // Compute (n-1)! modulo MOD
        for (int i = 2; i < n; i++) {
            ans = (ans * i) % MOD;
        }

        return (int) ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

complexity = [1,2,3]
All > 1 → valid.
Answer = (3 - 1)! = 2! = 2.

complexity = [3,3,3,4,4,4]
complexity[1] = 3 ≤ 3 → impossible → 0.

 ------------------------------------------------------------
*/
