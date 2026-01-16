/*
 🔹 Problem: 2943. Maximize Area of Square Hole in Grid
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Greedy, Sorting
 🔹 Link: https://leetcode.com/problems/maximize-area-of-square-hole-in-grid/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given the two integers, n and m and two integer arrays, hBars and vBars. 
The grid has n + 2 horizontal and m + 2 vertical bars, creating 1 x 1 unit cells. 
The bars are indexed starting from 1.

You can remove some of the bars in hBars from horizontal bars and some of the 
bars in vBars from vertical bars. Note that other bars are fixed and cannot be 
removed.

Return an integer denoting the maximum area of a square-shaped hole in the grid, 
after removing some bars (possibly none).

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: n = 2, m = 1, hBars = [2,3], vBars = [2]
Output: 4
Explanation:
The left image shows the initial grid formed by the bars. The horizontal bars 
are [1,2,3,4], and the vertical bars are [1,2,3].
One way to get the maximum square-shaped hole is by removing horizontal bar 2 
and vertical bar 2.

Example 2:
Input: n = 1, m = 1, hBars = [2], vBars = [2]
Output: 4
Explanation:
To get the maximum square-shaped hole, we remove horizontal bar 2 and vertical 
bar 2.

Example 3:
Input: n = 2, m = 3, hBars = [2,3], vBars = [2,4]
Output: 4
Explanation:
One way to get the maximum square-shaped hole is by removing horizontal bar 3, 
and vertical bar 4.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= n <= 10^9
 • 1 <= m <= 10^9
 • 1 <= hBars.length <= 100
 • 2 <= hBars[i] <= n + 1
 • 1 <= vBars.length <= 100
 • 2 <= vBars[i] <= m + 1
 • All values in hBars are distinct
 • All values in vBars are distinct

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize area of square hole by removing consecutive bars.

🔑 **Key Insight:**
   - Removing k consecutive bars creates a gap of (k+1) units
   - For square hole: need equal gaps horizontally and vertically
   - Maximum square side = min(maxHGap, maxVGap)
   - Area = side²

📍 **Approach (Greedy - Longest Consecutive Sequence):**
   - Find longest consecutive sequence in hBars (removable horizontal bars)
   - Find longest consecutive sequence in vBars (removable vertical bars)
   - If we remove k consecutive bars, we create gap of k+1 units
   - Square side length = min(maxHStreak, maxVStreak) + 1
   - Area = side²
   - Time: O(n log n + m log m), Space: O(1)

 ------------------------------------------------------------
 🔹 Understanding the Problem:

Grid Structure:
- Horizontal bars: indexed 1 to n+2
- Vertical bars: indexed 1 to m+2
- Bars at indices 1 and n+2 (horizontal) are FIXED (cannot remove)
- Bars at indices 1 and m+2 (vertical) are FIXED (cannot remove)
- Only bars in hBars and vBars can be removed

Creating a Hole:
- Removing consecutive bars creates a gap
- Example: Remove bars [2,3,4] → creates gap of 4 units (between bars 1 and 5)
- Removing k consecutive bars → gap of (k+1) units

Square Constraint:
- Hole must be square (equal width and height)
- Limited by minimum of horizontal and vertical gaps

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Greedy - Longest Consecutive Sequence)
   ⏱️ Time Complexity: O(h log h + v log v) - sorting both arrays
   💾 Space Complexity: O(1) - only counter variables
   
   🧠 **Key Insight:**
   The problem reduces to finding the longest consecutive sequence in each array.
   Removing k consecutive bars creates a hole of size k+1.
   
   💡 **Why it works:**
   - Sort both arrays to easily find consecutive sequences
   - Track longest consecutive sequence in hBars
   - Track longest consecutive sequence in vBars
   - Removing k bars creates gap of k+1 (bars are boundaries)
   - Square requires equal dimensions: side = min(hGap, vGap)
   - Area = side × side
   
   🎯 **Algorithm Steps:**
   1. Sort hBars and vBars
   2. Find longest consecutive sequence in hBars:
      - If hBars[i] = hBars[i-1] + 1, extend streak
      - Otherwise, reset streak to 1
   3. Find longest consecutive sequence in vBars (same logic)
   4. Calculate side = min(longestH, longestV) + 1
   5. Return side²
   
   📐 **Why +1 for gap size:**
   Removing bars [2,3,4] means:
   - Bars remaining: [1, _, _, _, 5]
   - Gap spans from after bar 1 to before bar 5
   - Gap size = 4 (one more than number of bars removed)
   - Formula: k bars removed → gap of k+1
 ------------------------------------------------------------
*/

import java.util.Arrays;

public class MaximizeSquareHoleArea {
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int hLen = hBars.length;
        int vLen = vBars.length;
        
        // Sort to find consecutive sequences easily
        Arrays.sort(hBars);
        Arrays.sort(vBars);
        
        // Find longest consecutive sequence in horizontal bars
        int longestStreakH = 1;
        int currentStreak = 1;
        int prev = hBars[0];
        
        for(int i = 1; i < hLen; i++) {
            if(hBars[i] == prev + 1) {
                // Consecutive bar found, extend streak
                currentStreak++;
            } else {
                // Streak broken, update max and reset
                longestStreakH = Math.max(longestStreakH, currentStreak);
                currentStreak = 1;
            }
            prev = hBars[i];
        }
        // Don't forget to check final streak
        longestStreakH = Math.max(longestStreakH, currentStreak);
        
        // Find longest consecutive sequence in vertical bars
        int longestStreakV = 1;
        currentStreak = 1;
        prev = vBars[0];
        
        for(int i = 1; i < vLen; i++) {
            if(vBars[i] == prev + 1) {
                // Consecutive bar found, extend streak
                currentStreak++;
            } else {
                // Streak broken, update max and reset
                longestStreakV = Math.max(longestStreakV, currentStreak);
                currentStreak = 1;
            }
            prev = vBars[i];
        }
        // Don't forget to check final streak
        longestStreakV = Math.max(longestStreakV, currentStreak);
        
        // Square side is limited by smaller gap
        // Add 1 because k consecutive bars create gap of k+1
        int sideLength = Math.min(longestStreakH, longestStreakV) + 1;
        
        return sideLength * sideLength;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: n = 2, m = 3, hBars = [2,3], vBars = [2,4]

Grid structure:
- Horizontal bars: [1, 2, 3, 4] (indices 1 to n+2 = 4)
- Vertical bars: [1, 2, 3, 4, 5] (indices 1 to m+2 = 5)
- Removable horizontal: [2, 3]
- Removable vertical: [2, 4]

Step 1: Sort arrays
hBars = [2, 3] (already sorted)
vBars = [2, 4] (already sorted)

Step 2: Find longest consecutive in hBars
i=0: hBars[0]=2, prev=2, currentStreak=1
i=1: hBars[1]=3, prev=2
  → 3 == 2+1 ✓ (consecutive)
  → currentStreak = 2
Final check: longestStreakH = max(1, 2) = 2

Step 3: Find longest consecutive in vBars
i=0: vBars[0]=2, prev=2, currentStreak=1
i=1: vBars[1]=4, prev=2
  → 4 != 2+1 ✗ (not consecutive)
  → longestStreakV = max(1, 1) = 1
  → currentStreak = 1, prev=4
Final check: longestStreakV = max(1, 1) = 1

Step 4: Calculate square size
sideLength = min(2, 1) + 1 = 1 + 1 = 2

Step 5: Calculate area
area = 2 × 2 = 4 ✅

Explanation:
- Remove horizontal bars [2,3]: creates gap of 3 units (but limited by vertical)
- Remove vertical bar [2]: creates gap of 2 units (only one consecutive bar)
- Square limited by smaller dimension: 2×2 = 4

Visual:
Remove hBars [2,3] and vBar [2]:

Before:           After removal:
H1 ────────      H1 ────────
H2 ────────      (removed)
H3 ────────      (removed)
H4 ────────      H4 ────────
│ │ │ │ │        │   │ │ │
V1V2V3V4V5       V1  V3V4V5
                    └──┘ 2×2 hole

 ------------------------------------------------------------
 🔍 Understanding Gap Creation:

Example: hBars = [2, 3, 4, 5]

Case 1: Remove bars [2, 3, 4]
Grid: [1, _, _, _, 5, 6, ...]
Gap: From after bar 1 to before bar 5 = 4 units
Formula: 3 bars removed → 3+1 = 4 units gap ✓

Case 2: Remove bars [2]
Grid: [1, _, 3, 4, ...]
Gap: From after bar 1 to before bar 3 = 2 units
Formula: 1 bar removed → 1+1 = 2 units gap ✓

Case 3: Remove bars [3, 4, 5]
Grid: [1, 2, _, _, _, 6, ...]
Gap: From after bar 2 to before bar 6 = 4 units
Formula: 3 bars removed → 3+1 = 4 units gap ✓

General rule: k consecutive bars → (k+1) unit gap

 ------------------------------------------------------------
 🔍 Why Greedy Works:

Optimal Substructure:
- Maximum square area depends only on maximum gaps
- Larger gap always better than smaller gap
- No need to consider all combinations

Greedy Choice:
- Always choose longest consecutive sequence
- This gives largest possible gap in each dimension
- Square limited by smaller of the two gaps

Proof:
- Let H = longest consecutive in hBars (creates gap of H+1)
- Let V = longest consecutive in vBars (creates gap of V+1)
- Any other choice gives smaller or equal gaps
- Therefore, min(H, V) + 1 is optimal square side

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Single removable bar in each dimension
hBars = [2], vBars = [2]
- longestStreakH = 1
- longestStreakV = 1
- side = min(1, 1) + 1 = 2
- area = 4 ✓

Case 2: No consecutive bars
hBars = [2, 4, 6], vBars = [2, 5]
- longestStreakH = 1 (no consecutive)
- longestStreakV = 1 (no consecutive)
- side = 2
- area = 4 ✓

Case 3: All bars consecutive (maximum possible)
hBars = [2, 3, 4, 5], vBars = [2, 3, 4]
- longestStreakH = 4
- longestStreakV = 3
- side = min(4, 3) + 1 = 4
- area = 16 ✓

Case 4: Unequal consecutive sequences
hBars = [2, 3, 4, 5, 6], vBars = [2, 3]
- longestStreakH = 5 (all consecutive)
- longestStreakV = 2 (all consecutive)
- side = min(5, 2) + 1 = 3
- area = 9 ✓

Case 5: Multiple separate consecutive sequences
hBars = [2, 3, 5, 6, 7], vBars = [2, 4, 5, 6]
- longestStreakH = 3 ([5,6,7])
- longestStreakV = 3 ([4,5,6])
- side = min(3, 3) + 1 = 4
- area = 16 ✓

Case 6: Large n and m with small arrays
n = 10^9, m = 10^9, hBars = [2, 3], vBars = [2]
- longestStreakH = 2
- longestStreakV = 1
- side = 2
- area = 4 ✓
- Note: n and m don't affect answer, only hBars and vBars matter

 ------------------------------------------------------------
 🔍 Common Pitfalls:

❌ Mistake 1: Forgetting +1 for gap size
Wrong: side = min(longestH, longestV)
Correct: side = min(longestH, longestV) + 1
Reason: k bars → (k+1) gap

❌ Mistake 2: Not checking final streak
for(int i = 1; i < len; i++) { ... }
// Missing: longestStreak = Math.max(longestStreak, currentStreak);
Reason: Last streak might be the longest

❌ Mistake 3: Off-by-one in consecutive check
Wrong: if(arr[i] == arr[i-1])
Correct: if(arr[i] == prev + 1)
Reason: Need consecutive (differ by 1), not equal

❌ Mistake 4: Assuming sorted input
Wrong: Directly iterating without sorting
Correct: Arrays.sort() first
Reason: Input not guaranteed sorted

❌ Mistake 5: Using n and m in calculation
Wrong: Considering grid dimensions
Correct: Only use hBars and vBars arrays
Reason: n and m define grid size but don't affect removable bars

 ------------------------------------------------------------
 🔍 Alternative Implementation (Cleaner):

```java
public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
    int maxH = findLongestConsecutive(hBars);
    int maxV = findLongestConsecutive(vBars);
    int side = Math.min(maxH, maxV) + 1;
    return side * side;
}

private int findLongestConsecutive(int[] bars) {
    if(bars.length == 0) return 0;
    
    Arrays.sort(bars);
    int maxStreak = 1;
    int currentStreak = 1;
    
    for(int i = 1; i < bars.length; i++) {
        if(bars[i] == bars[i-1] + 1) {
            currentStreak++;
        } else {
            maxStreak = Math.max(maxStreak, currentStreak);
            currentStreak = 1;
        }
    }
    
    return Math.max(maxStreak, currentStreak);
}
```

Benefits:
- Cleaner separation of concerns
- Reusable helper function
- Easier to test and debug
- More readable main logic

⚡ Performance Analysis:
This greedy approach efficiently handles maximum constraints:
- n and m up to 10^9 (don't affect algorithm complexity)
- hBars and vBars up to 100 elements each
- Sorting: O(100 log 100) ≈ 664 operations
- Linear scan: O(100) operations
- Total: ~800 operations maximum
- Extremely fast execution: <0.01ms
- Space: O(1) excluding sorting space
- Why this is optimal:
  • Can't avoid sorting for consecutive detection: O(n log n) lower bound
  • Linear scan is necessary to check all elements: O(n) lower bound
  • Combined O(n log n) is theoretically optimal
- The greedy approach works because:
  • Larger gaps always better (no trade-offs)
  • Independent dimensions (horizontal and vertical)
  • Square constraint is simple minimum operation
- Problem demonstrates:
  • Reduction to simpler problem (longest consecutive sequence)
  • Greedy choice property
  • Why constraints on n and m are red herrings
  • Clean algorithm design with helper functions
 ------------------------------------------------------------
*/