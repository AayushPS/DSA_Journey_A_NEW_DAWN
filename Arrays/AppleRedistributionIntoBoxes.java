/*
 🔹 Problem: 3074. Apple Redistribution into Boxes
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: Array, Greedy, Sorting
 🔹 Link: https://leetcode.com/problems/apple-redistribution-into-boxes/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array apple of size n and an array capacity of size m.

There are n packs where the ith pack contains apple[i] apples. There are m boxes 
as well, and the ith box has a capacity of capacity[i] apples.

Return the minimum number of boxes you need to select to redistribute these n packs 
of apples into boxes.

Note that, apples from the same pack can be distributed into different boxes.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: apple = [1,3,2], capacity = [4,3,1,5,2]
Output: 2
Explanation: We will use boxes with capacities 4 and 5.
It is possible to distribute the apples as the total capacity is greater than 
or equal to the total number of apples.

Example 2:
Input: apple = [5,5,5], capacity = [2,4,2,7]
Output: 4
Explanation: We will need to use all the boxes.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= n == apple.length <= 50
 • 1 <= m == capacity.length <= 50
 • 1 <= apple[i], capacity[i] <= 50
 • The input is generated such that it's possible to redistribute packs of apples into boxes

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the minimum number of boxes needed to hold all apples.

📍 **Approach (Greedy + Sorting):**
   - Calculate total number of apples from all packs
   - Sort capacity array to access largest boxes first
   - Greedily select boxes starting from largest capacity
   - Keep subtracting box capacity from total apples until all apples are accommodated
   - Count represents the minimum number of boxes needed

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Greedy + Sorting - Optimal)
   ⏱️ Time Complexity: O(m log m + n) - where m is capacity array length, n is apple array length
   💾 Space Complexity: O(1) - excluding sorting space
   
   🧠 **Key Insight:**
   To minimize the number of boxes, we should always use the largest capacity boxes first.
   This greedy approach ensures we need the fewest boxes possible.
   
   💡 **Why it works:**
   - We first calculate total apples (sum of all packs)
   - Sorting capacity in ascending order allows us to iterate from largest (end) to smallest
   - By greedily picking the largest available box each time, we maximize capacity usage
   - We stop as soon as remaining apples <= 0, giving us minimum box count
   - This greedy strategy is optimal because using smaller boxes first would never 
     result in fewer total boxes than using larger boxes first
 ------------------------------------------------------------
*/

import java.util.Arrays;

public class AppleRedistributionIntoBoxes {
    public int minimumBoxes(int[] apple, int[] capacity) {
        // Calculate total number of apples
        int sum = 0;
        for (int i : apple) {
            sum += i;
        }
        
        // Sort capacity array to access largest boxes first
        Arrays.sort(capacity);
        
        // Count minimum boxes needed
        int count = 0;
        
        // Iterate from largest to smallest capacity
        for (int j = capacity.length - 1; j >= 0; j--) {
            count++;
            sum -= capacity[j];
            
            // If all apples are accommodated, stop
            if (sum <= 0) break;
        }
        
        return count;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: apple = [1,3,2], capacity = [4,3,1,5,2]

Step 1: Calculate total apples
sum = 1 + 3 + 2 = 6

Step 2: Sort capacity array
capacity = [1,2,3,4,5] (ascending order)

Step 3: Iterate from largest capacity (right to left)
Iteration 1: j=4, capacity[4]=5
  → count = 1
  → sum = 6 - 5 = 1
  → sum > 0, continue

Iteration 2: j=3, capacity[3]=4
  → count = 2
  → sum = 1 - 4 = -3
  → sum <= 0, break

Final Result: count = 2 ✅

💡 Note: We used boxes with capacity 5 and 4, which together can hold 9 apples (more than enough for 6 apples).
 ------------------------------------------------------------
*/