/*
 🔹 Problem: 2141. Maximum Running Time of N Computers
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Binary Search, Greedy, Sorting
 🔹 Link: https://leetcode.com/problems/maximum-running-time-of-n-computers/

 ------------------------------------------------------------
 📝 Problem Statement:

You have n computers and an array batteries[] where batteries[i] is the number
of minutes the i-th battery can power a computer.

A battery can be inserted into any computer. At any integer moment you may swap
batteries between computers instantly (no time cost). Batteries do NOT recharge.

Goal: Run **all n computers simultaneously** for the **maximum possible minutes**.

Return that maximum number of minutes.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: n = 2, batteries = [3,3,3]
Output: 4

Example 2:
Input: n = 2, batteries = [1,1,1,1]
Output: 2

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ n ≤ batteries.length ≤ 100000
 • 1 ≤ batteries[i] ≤ 1e9

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the maximum integer T such that all n computers can run T minutes simultaneously.

📍 **Approach 1 (Greedy Water-Filling / Leveling):**
   - Sort batteries and repeatedly raise the lower batteries to match next level.
   - Use the “leveling” idea like filling buckets to determine when leftover capacity runs out.
   - Time: O(k log k)
   - Good intuition, mathematically tight, no trial & error.

📍 **Approach 2 (Binary Search on Answer) - Optimized:**
   - Binary search T (the runtime), check feasibility using:
         sum(min(b[i], T)) >= n * T
   - Uses the fact that the total usable energy toward T minutes is capped at min(b[i], T).
   - Time: O(k log MAX)
   - **This is chosen as the final most optimal approach due to simplicity and monotonic feasibility.**

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Greedy Leveling)
   ⏱️ Time Complexity: O(k log k)
   💾 Space Complexity: O(1)

   🧠 Key Insight:
   Raise computers’ running times evenly; if you can't push to the next level, distribute leftover energy.

   💡 Why it works:
   Follows a water-filling model balancing total battery energy across n machines.
 -------------------------------------------------------------

/*
// Java
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        if(n==1) return Arrays.stream(batteries).mapToLong(i->(long)i).sum();
        Arrays.sort(batteries);
        int k = batteries.length;
        int[] live = Arrays.copyOfRange(batteries, k-n, k);
        long remainingSum = 0;
        for(int i = 0; i < k-n; i++){
            remainingSum += batteries[i];
        }
        for(int i = 0; i < n-1; i++){
            long diff = (live[i+1] - live[i]) * (long)(i+1);
            if(diff > remainingSum){
                return live[i] + remainingSum / (long)(i+1);
            }
            remainingSum -= diff;
        }
        return live[n-1] + remainingSum / n;
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Binary Search on Answer - Most Optimal)
   - Time Complexity: O(k log MAX)
   - Space Complexity: O(1)
   
   🧠 Key Insight:
   You only need to test whether a given time T is feasible using:
       total = Σ min(b[i], T)
       feasible iff total ≥ n*T

   💡 Why it works:
   - Feasibility is monotonic: if T works, all smaller times also work.
   - Enables a clean binary search for the maximum T.
   - Avoids sorting and complex leveling calculations.
 ------------------------------------------------------------
*/

public class MaximumRunningTimeOfNComputers {

    public long maxRunTime(int n, int[] batteries) {
        long sum = Arrays.stream(batteries).mapToLong(i -> (long)i).sum();
        long l = Arrays.stream(batteries).min().getAsInt();
        long r = sum / n;

        if (n == 1) return sum;

        long res = 0;
        int k = batteries.length;

        while (l <= r) {
            long mid = l + (r - l) / 2;
            long target = n * mid;
            long sumnow = 0;

            for (int i = 0; i < k; i++) {
                sumnow += Math.min(batteries[i], mid);
                if (sumnow >= target) {
                    res = mid;
                    l = mid + 1;
                    break;
                }
            }

            if (sumnow < target) {
                r = mid - 1;
            }
        }

        return res;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

n = 2, batteries = [3,3,3]
sum = 9
Binary search range: [3, 9/2 = 4]

mid = 3 → feasible  
mid = 4 → feasible  
mid = 5 → not feasible  

Final answer = 4

 ------------------------------------------------------------
*/

