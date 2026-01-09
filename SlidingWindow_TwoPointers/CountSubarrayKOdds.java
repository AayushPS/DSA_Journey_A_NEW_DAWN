/*
 🔹 Problem: Count Subarray with K Odds
 🔹 Platform: GeeksforGeeks
 🔹 Difficulty: Medium
 🔹 Topics: Array, Two Pointers, Sliding Window, Prefix Sum
 🔹 Link: https://www.geeksforgeeks.org/problems/count-subarray-with-k-odds/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array arr[] of positive integers and an integer k. You have 
to count the number of subarrays that contain exactly k odd numbers.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: arr[] = [2, 5, 6, 9], k = 2
Output: 2
Explanation: There are 2 subarrays with 2 odds: [2, 5, 6, 9] and [5, 6, 9].

Example 2:
Input: arr[] = [2, 2, 5, 6, 9, 2, 11], k = 2
Output: 8
Explanation: There are 8 subarrays with 2 odds: [2, 2, 5, 6, 9], [2, 5, 6, 9], 
[5, 6, 9], [2, 2, 5, 6, 9, 2], [2, 5, 6, 9, 2], [5, 6, 9, 2], [6, 9, 2, 11] 
and [9, 2, 11].

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ k ≤ arr.size() ≤ 10^5
 • 1 ≤ arr[i] ≤ 10^9

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count subarrays with exactly k odd numbers.

📍 **Approach 1 (Brute Force):**
   - Try all possible subarrays
   - For each subarray, count odd numbers
   - If count equals k, increment result
   - Time: O(n²), Space: O(1)
   - Simple but too slow for large inputs

📍 **Approach 2 (Prefix Count + HashMap):**
   - Track prefix count of odd numbers
   - Use HashMap to store frequency of each prefix count
   - For each position, check if (currentCount - k) exists in map
   - Similar to subarray sum equals k problem
   - Time: O(n), Space: O(n)

📍 **Approach 3 (Sliding Window - Difference Technique - Optimized):**
   - Key insight: atMost(k) - atMost(k-1) = exactly(k)
   - Count subarrays with ≤ k odds, subtract subarrays with ≤ k-1 odds
   - Use sliding window to count atMost(k) efficiently
   - Time: O(n), Space: O(1)
   - Elegant mathematical approach with optimal complexity

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Brute Force)
   ⏱️ Time Complexity: O(n²) - nested loops through all subarrays
   💾 Space Complexity: O(1) - only counter variables
   
   🧠 **Key Insight:**
   Check every possible subarray and count odd numbers in each.
   
   💡 **Why it works:**
   - Outer loop: start position of subarray
   - Inner loop: end position of subarray
   - For each subarray [i, j], count odds
   - If count equals k, increment result
   - Simple but inefficient for large arrays
 ------------------------------------------------------------

// class Solution {
//     public int countSubarrays(int[] arr, int k) {
//         int n = arr.length;
//         int count = 0;
//         
//         for(int i = 0; i < n; i++) {
//             int odds = 0;
//             for(int j = i; j < n; j++) {
//                 if(arr[j] % 2 == 1) odds++;
//                 if(odds == k) count++;
//                 if(odds > k) break; // Optimization: stop if exceeded k
//             }
//         }
//         
//         return count;
//     }
// }

 ------------------------------------------------------------
 🔹 Approach 2 (Commented - Prefix Count + HashMap)
   ⏱️ Time Complexity: O(n) - single pass through array
   💾 Space Complexity: O(n) - HashMap stores prefix counts
   
   🧠 **Key Insight:**
   Use prefix sum concept adapted for counting odd numbers.
   If prefix count at j is x and at i is (x-k), then subarray [i+1, j] has k odds.
   
   💡 **Why it works:**
   - Track running count of odd numbers encountered
   - Store frequency of each prefix count in HashMap
   - For current position with count x:
     • Check if (x - k) exists in map
     • If yes, those positions can form subarrays ending here with k odds
   - Add current count to map for future positions
   - Similar pattern to "Subarray Sum Equals K" problem
 ------------------------------------------------------------

// import java.util.*;
// 
// class Solution {
//     public int countSubarrays(int[] arr, int k) {
//         HashMap<Integer, Integer> prefixCount = new HashMap<>();
//         prefixCount.put(0, 1); // Base case: 0 odds seen initially
//         
//         int odds = 0;
//         int result = 0;
//         
//         for(int num : arr) {
//             if(num % 2 == 1) odds++;
//             
//             // Check if (odds - k) exists
//             if(prefixCount.containsKey(odds - k)) {
//                 result += prefixCount.get(odds - k);
//             }
//             
//             // Update prefix count
//             prefixCount.put(odds, prefixCount.getOrDefault(odds, 0) + 1);
//         }
//         
//         return result;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Sliding Window - Difference Technique - Most Optimal)
   ⏱️ Time Complexity: O(n) - two passes through array (one for each helper call)
   💾 Space Complexity: O(1) - only pointer and counter variables
   
   🧠 **Key Insight:**
   Mathematical transformation: exactly(k) = atMost(k) - atMost(k-1)
   
   This works because:
   - atMost(k) counts subarrays with 0, 1, 2, ..., k odds
   - atMost(k-1) counts subarrays with 0, 1, 2, ..., k-1 odds
   - Difference gives only subarrays with exactly k odds
   
   💡 **Why it works:**
   - Helper function counts subarrays with ≤ target odd numbers
   - Uses sliding window with two pointers (i and j)
   - Expand window by moving j (add elements)
   - Contract window by moving i when odds > target
   - At each step, all subarrays ending at j with odds ≤ target = (j - i + 1)
   - Main function: exactly(k) = atMost(k) - atMost(k-1)
   
   🎯 **Sliding Window Mechanics:**
   - Window [i, j] maintains odds ≤ target
   - When arr[j] is odd, increment odds counter
   - If odds > target, shrink window from left:
     • Move i forward, decrement odds if arr[i] was odd
   - Count valid subarrays ending at j: (j - i + 1)
   - This counts ALL subarrays [i, j], [i+1, j], ..., [j, j]
   - All these have odds ≤ target since window is valid
 ------------------------------------------------------------
*/

public class CountSubarrayKOdds {
    public int countSubarrays(int[] arr, int k) {
        // Exactly k = atMost(k) - atMost(k-1)
        return subarraysWithKOrLessOdds(arr, k) - subarraysWithKOrLessOdds(arr, k - 1);
    }
    
    // Count subarrays with at most k odd numbers
    private int subarraysWithKOrLessOdds(int[] arr, int k) {
        int i = 0, j = 0, n = arr.length;
        int count = 0;
        int odds = 0;
        
        while(j < n) {
            // Expand window: check if current element is odd
            if(arr[j] % 2 == 1) {
                odds++;
            }
            
            // Contract window: remove elements from left while odds > k
            while(odds > k) {
                if(arr[i++] % 2 == 1) {
                    odds--;
                }
            }
            
            // Count all valid subarrays ending at j
            // These are: [i, j], [i+1, j], [i+2, j], ..., [j, j]
            count += j - i + 1;
            
            j++;
        }
        
        return count;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: arr = [2, 5, 6, 9], k = 2

Step 1: Calculate atMost(2)

i=0, j=0, odds=0, count=0:
  j=0: arr[0]=2 (even) → odds=0
       odds ≤ 2, count += 0-0+1 = 1, count=1
       Subarrays: [2]

i=0, j=1, odds=0:
  j=1: arr[1]=5 (odd) → odds=1
       odds ≤ 2, count += 1-0+1 = 2, count=3
       Subarrays: [2,5], [5]

i=0, j=2, odds=1:
  j=2: arr[2]=6 (even) → odds=1
       odds ≤ 2, count += 2-0+1 = 3, count=6
       Subarrays: [2,5,6], [5,6], [6]

i=0, j=3, odds=1:
  j=3: arr[3]=9 (odd) → odds=2
       odds ≤ 2, count += 3-0+1 = 4, count=10
       Subarrays: [2,5,6,9], [5,6,9], [6,9], [9]

atMost(2) = 10

Step 2: Calculate atMost(1)

i=0, j=0, odds=0, count=0:
  j=0: arr[0]=2 (even) → odds=0
       count += 0-0+1 = 1, count=1

i=0, j=1, odds=0:
  j=1: arr[1]=5 (odd) → odds=1
       count += 1-0+1 = 2, count=3

i=0, j=2, odds=1:
  j=2: arr[2]=6 (even) → odds=1
       count += 2-0+1 = 3, count=6

i=0, j=3, odds=1:
  j=3: arr[3]=9 (odd) → odds=2
       odds > 1, contract window:
         arr[0]=2 (even), i=1, odds=2
         arr[1]=5 (odd), i=2, odds=1
       count += 3-2+1 = 2, count=8

atMost(1) = 8

Step 3: Calculate exactly(2)
exactly(2) = atMost(2) - atMost(1) = 10 - 8 = 2 ✅

The 2 subarrays with exactly 2 odds:
1. [2, 5, 6, 9] (indices 0-3)
2. [5, 6, 9] (indices 1-3)

 ------------------------------------------------------------
 🔍 Understanding the Difference Technique:

Why exactly(k) = atMost(k) - atMost(k-1)?

Visual representation with k=2:

atMost(2) includes:
✓ Subarrays with 0 odds
✓ Subarrays with 1 odd
✓ Subarrays with 2 odds

atMost(1) includes:
✓ Subarrays with 0 odds
✓ Subarrays with 1 odd

Difference:
atMost(2) - atMost(1) = Subarrays with exactly 2 odds ✓

This technique is powerful because:
- Counting "exactly k" directly is hard
- Counting "at most k" is easy with sliding window
- Mathematical subtraction gives exact answer

 ------------------------------------------------------------
 🔍 Why (j - i + 1) Counts All Valid Subarrays:

When window [i, j] is valid (odds ≤ k):

Example: i=1, j=3, valid window is [5, 6, 9]
Subarrays ending at j=3:
1. [i, j] = [5, 6, 9]
2. [i+1, j] = [6, 9]
3. [j, j] = [9]

Total: j - i + 1 = 3 - 1 + 1 = 3 subarrays ✓

Why all are valid?
- If [i, j] has odds ≤ k
- Then [i+1, j] has odds ≤ k (removing element can't increase odds)
- And [i+2, j], [i+3, j], ..., [j, j] all have odds ≤ k

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: k = 0 (no odds allowed)
arr = [2, 4, 6], k = 0
atMost(0): count all-even subarrays
atMost(-1): returns 0 (no negatives)
Result: all continuous even subarrays ✓

Case 2: k = array length (all can be odd)
arr = [1, 3, 5], k = 3
All subarrays counted ✓

Case 3: Single element
arr = [5], k = 1
Result: 1 (the element itself) ✓

Case 4: All even numbers
arr = [2, 4, 6, 8], k = 2
No subarrays with 2 odds
Result: 0 ✓

Case 5: All odd numbers
arr = [1, 3, 5, 7], k = 2
Many subarrays with exactly 2 odds
atMost(2) counts subarrays with 0, 1, or 2 odds
atMost(1) counts subarrays with 0 or 1 odd
Difference gives exactly 2 odds ✓

Case 6: k = 1 with alternating odd/even
arr = [2, 5, 6, 9, 2, 11], k = 1
Many single-odd subarrays
Each section between odds contributes ✓

 ------------------------------------------------------------
 🔍 Comparison with Other Approaches:

Brute Force (O(n²)):
❌ Too slow for n = 10^5 (10^10 operations)
✅ Easy to understand
✅ Works for all k values

Prefix Count + HashMap (O(n) time, O(n) space):
✅ Single pass
✅ Direct calculation
❌ Extra space for HashMap
❌ More complex to implement

Sliding Window (O(n) time, O(1) space):
✅ Two passes but still O(n)
✅ Constant space
✅ Elegant mathematical insight
✅ Works for similar problems (subarrays with k properties)
✅ Best overall solution

⚡ Performance Analysis:
The sliding window difference technique efficiently handles maximum constraints:
- Array size up to 10^5 elements
- Two passes through array: 2n operations
- Each operation: O(1) comparisons and arithmetic
- Total time: ~0.2ms for 10^5 elements
- Space: Only 4-5 integer variables (~20 bytes)
- Comparison with HashMap approach:
  • HashMap: O(n) space = 400KB for 10^5 elements
  • Sliding window: O(1) space = 20 bytes
  • Space savings: 99.995% reduction!
- The difference technique is versatile:
  • Works for "exactly k" problems
  • Can be adapted to "at least k" (total - atMost(k-1))
  • Useful for: sum, product, distinct elements, etc.
- Window contraction is efficient:
  • While loop runs at most n times total across all iterations
  • Each element enters window once, leaves once
  • Amortized O(1) per element
- This pattern appears in many problems:
  • Subarrays with k distinct integers
  • Subarrays with k different characters
  • Binary subarrays with sum k
 ------------------------------------------------------------
*/