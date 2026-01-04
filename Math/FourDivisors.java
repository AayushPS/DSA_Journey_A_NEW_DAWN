package Math;
/*
 🔹 Problem: 1390. Four Divisors
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Math, Number Theory
 🔹 Link: https://leetcode.com/problems/four-divisors/

 ------------------------------------------------------------
 📝 Problem Statement:

Given an integer array nums, return the sum of divisors of the integers in that 
array that have exactly four divisors. If there is no such integer in the array, 
return 0.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: nums = [21,4,7]
Output: 32
Explanation: 
21 has 4 divisors: 1, 3, 7, 21
4 has 3 divisors: 1, 2, 4
7 has 2 divisors: 1, 7
The answer is the sum of divisors of 21 only.

Example 2:
Input: nums = [21,21]
Output: 64

Example 3:
Input: nums = [1,2,3,4,5]
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= nums.length <= 10^4
 • 1 <= nums[i] <= 10^5

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Sum all divisors of numbers that have exactly 4 divisors.

📍 **Approach 1 (Brute Force - Linear Scan):**
   - For each number, iterate from 1 to n to find all divisors
   - Count divisors and calculate sum simultaneously
   - Check if count equals 4, return sum or -1
   - Time: O(n) per number, no early termination
   - Simple but inefficient for larger numbers

📍 **Approach 2 (Square Root Optimization):**
   - Key insight: divisors come in pairs (i, n/i)
   - Only iterate from 1 to √n
   - For each divisor i found, also count n/i
   - Careful handling when i = √n (perfect square)
   - Time: O(√n) per number
   - Significant speedup for large numbers

📍 **Approach 3 (Square Root + Early Termination + Caching - Optimized):**
   - Use static cache array to store results for each number
   - Iterate only up to √n to find divisor pairs
   - Early termination: stop immediately when count > 4
   - Use i*i <= num instead of sqrt() to avoid floating point
   - Cache prevents recalculation of repeated numbers
   - Time: O(√n) per unique number, O(1) for cached
   - Space: O(100001) for cache array

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Brute Force Linear Scan)
   ⏱️ Time Complexity: O(k × n) - where k is array length, n is max value
   💾 Space Complexity: O(100001) - cache array
   
   🧠 **Key Insight:**
   Use two-pointer technique to find divisors and their pairs.
   Start from both ends (1 and num) and move inward.
   
   💡 **Why it works:**
   - Always start with sum = 1 + num (guaranteed divisors)
   - For i from 2 to num-1, if num%i==0, found a divisor pair (i, num/i)
   - Track if we've found exactly one pair (4 divisors total)
   - If i==j (perfect square) or more than one pair found, return -1
   - Cache results to avoid recalculating same numbers
 ------------------------------------------------------------

// class Solution {
//     private static int[] cache = new int[100001];
//     
//     public int sumFourDivisors(int[] nums) {
//         int totalSum = 0;
//         for(int i : nums) {
//             if(cache[i] == 0) {
//                 cache[i] = has4(i);
//             }
//             if(cache[i] == -1) continue;
//             totalSum += cache[i];
//         }
//         return totalSum;
//     }
//     
//     private static int has4(int num) {
//         int sum = 0;
//         int i = 1, j = num;
//         sum += i + j;
//         boolean once = false;
//         i++;
//         
//         while(i < j) {
//             if(num % i == 0) {
//                 j = num / i;
//                 
//                 if(i == j) return -1; // Perfect square
//                 if(!once) {
//                     once = true;
//                     sum += i + j;
//                 } else {
//                     return -1; // More than 4 divisors
//                 }
//             }
//             i++;
//         }
//         
//         return once ? sum : -1;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Square Root Optimization)
   ⏱️ Time Complexity: O(k × √n) - iterate up to square root per number
   💾 Space Complexity: O(100001) - cache array
   
   🧠 **Key Insight:**
   Divisors come in pairs: if i divides num, then (i, num/i) are both divisors.
   Only need to check up to √num to find all divisor pairs.
   
   💡 **Why it works:**
   - Start with count=2, sum=1+num (1 and num are always divisors)
   - For each i from 2 to √num, if num%i==0:
     • Add i to sum and increment count
     • If i ≠ num/i (not perfect square), also add num/i and increment count
   - After loop, check if count == 4
   - Much faster than checking every number up to num
   - Cache prevents redundant calculations for duplicate numbers
 ------------------------------------------------------------

// class Solution {
//     private static int[] cache = new int[100001];
//     
//     public int sumFourDivisors(int[] nums) {
//         int totalSum = 0;
//         for(int i : nums) {
//             if(cache[i] == 0) {
//                 cache[i] = has4(i);
//             }
//             if(cache[i] == -1) continue;
//             totalSum += cache[i];
//         }
//         return totalSum;
//     }
//     
//     private static int has4(int num) {
//         int sum = 1 + num;
//         int count = 2;
//         int sqrt = (int)Math.sqrt(num);
//         
//         for(int i = 2; i <= sqrt; i++) {
//             if(num % i == 0) {
//                 sum += i;
//                 count++;
//                 if(i != num/i) {
//                     sum += num/i;
//                     count++;
//                 }
//             }
//         }
//         
//         return count == 4 ? sum : -1;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Square Root + Early Termination + Optimized - Most Optimal)
   ⏱️ Time Complexity: O(k × √n) - with early termination reducing average case
   💾 Space Complexity: O(100001) - static cache for all possible values
   
   🧠 **Key Insight:**
   Combine square root optimization with early termination. As soon as we detect
   more than 4 divisors, immediately return -1. Also use i*i <= num instead of
   computing sqrt() to avoid floating point operations.
   
   💡 **Why it works:**
   - Static cache array stores results for numbers 0-100000
   - Cache[num] = 0 means not computed, -1 means not 4 divisors, positive means sum
   - Start with count=2 (for 1 and num), sum=1+num
   - Iterate from 2 while i*i <= num (equivalent to i <= √num but faster)
   - For each divisor i found:
     • Add i to sum, increment count
     • If i*i ≠ num (not perfect square), also add num/i and increment count
     • If count > 4, immediately return -1 (early termination)
   - Only return sum if count == 4
   - Early termination saves significant time for numbers with many divisors
   - Using i*i instead of sqrt() avoids function call overhead
   - Cache ensures each unique number is computed only once
 ------------------------------------------------------------
*/

public class FourDivisors {
    private static int[] cache = new int[100001];
    
    public int sumFourDivisors(int[] nums) {
        int totalSum = 0;
        
        for(int num : nums) {
            // Check if result is cached
            if(cache[num] == 0) {
                cache[num] = has4(num);
            }
            // Skip if doesn't have exactly 4 divisors
            if(cache[num] == -1) continue;
            
            totalSum += cache[num];
        }
        
        return totalSum;
    }
    
    private static int has4(int num) {
        // Start with 1 and num (always divisors)
        int sum = 1 + num;
        int count = 2;
        
        // Check divisors up to √num
        for(int i = 2; i * i <= num; i++) {
            if(num % i == 0) {
                // Found divisor i
                sum += i;
                count++;
                
                // Check if num/i is a different divisor
                if(i * i != num) {
                    sum += num / i;
                    count++;
                }
                
                // Early termination: more than 4 divisors found
                if(count > 4) return -1;
            }
        }
        
        // Return sum only if exactly 4 divisors found
        return count == 4 ? sum : -1;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: nums = [21, 4, 7]

Processing num = 21:
- sum = 1 + 21 = 22, count = 2
- i = 2: 21 % 2 ≠ 0
- i = 3: 21 % 3 == 0
  → sum = 22 + 3 = 25, count = 3
  → 3 * 3 = 9 ≠ 21, so add 21/3 = 7
  → sum = 25 + 7 = 32, count = 4
- i = 4: 4 * 4 = 16 ≤ 21, but 21 % 4 ≠ 0
- i = 5: 5 * 5 = 25 > 21, loop ends
- count == 4 ✓ → return 32
- cache[21] = 32

Processing num = 4:
- sum = 1 + 4 = 5, count = 2
- i = 2: 4 % 2 == 0
  → sum = 5 + 2 = 7, count = 3
  → 2 * 2 = 4 == num (perfect square), don't add again
- i = 3: 3 * 3 = 9 > 4, loop ends
- count = 3 ≠ 4 → return -1
- cache[4] = -1

Processing num = 7:
- sum = 1 + 7 = 8, count = 2
- i = 2: 2 * 2 = 4 ≤ 7, but 7 % 2 ≠ 0
- i = 3: 3 * 3 = 9 > 7, loop ends
- count = 2 ≠ 4 → return -1
- cache[7] = -1

Final calculation:
totalSum = 32 + 0 + 0 = 32 ✅

 ------------------------------------------------------------
 🔍 Numbers with Exactly 4 Divisors:

Pattern Analysis:
A number has exactly 4 divisors if and only if it is:
1. The cube of a prime number (p³)
   Example: 8 = 2³ → divisors: {1, 2, 4, 8}
   
2. The product of two distinct primes (p × q where p ≠ q)
   Example: 21 = 3 × 7 → divisors: {1, 3, 7, 21}
   Example: 15 = 3 × 5 → divisors: {1, 3, 5, 15}

Why this matters:
- If num = p³: divisors are {1, p, p², p³} → 4 divisors
- If num = p×q: divisors are {1, p, q, p×q} → 4 divisors
- Any other factorization leads to ≠ 4 divisors

Examples within [1, 100000]:
- p³: 8, 27, 125, 343, 1331, 2197, ...
- p×q: 6, 10, 14, 15, 21, 22, 26, 33, 34, 35, ...

 ------------------------------------------------------------
 🔍 Edge Cases Handled:

Case 1: Perfect squares (e.g., 4, 9, 16)
- num = 4: divisors = {1, 2, 4} → 3 divisors ✗
- When i*i == num, we don't double-count
- Correctly returns -1

Case 2: Prime numbers (e.g., 7, 11, 13)
- num = 7: divisors = {1, 7} → 2 divisors ✗
- No divisors found in loop
- Correctly returns -1

Case 3: Numbers with many divisors (e.g., 12)
- num = 12: divisors = {1, 2, 3, 4, 6, 12} → 6 divisors
- i = 2: count becomes 4
- i = 3: count becomes 6, exceeds 4
- Early termination: returns -1 immediately ✓

Case 4: Duplicate numbers in array
- Example: [21, 21]
- First occurrence: compute and cache[21] = 32
- Second occurrence: use cached value directly
- Result: 32 + 32 = 64 ✓

⚡ Performance Analysis:
This optimized approach efficiently handles maximum constraints:
- Array size up to 10^4 numbers
- Each number up to 10^5 (√100000 ≈ 316)
- With caching: unique numbers computed once, duplicates use cache
- Early termination: numbers with many divisors exit quickly
- Using i*i instead of sqrt(): avoids floating point operations
- For worst case (all unique, all need √n checks): O(10^4 × 316) ≈ 3.16M ops
- For best case (many duplicates): O(unique × √n + duplicates × 1)
- Cache array size is fixed (100001 integers ≈ 400KB memory)
- The cache optimization provides substantial benefit when:
  • Array has duplicate numbers
  • Multiple test cases reuse same Solution instance
  • Numbers cluster in certain ranges
 ------------------------------------------------------------
*/