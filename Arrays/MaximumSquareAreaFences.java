/*
 🔹 Problem: 2975. Maximum Square Area by Removing Fences From a Field
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Hash Table, Enumeration, Sorting
 🔹 Link: https://leetcode.com/problems/maximum-square-area-by-removing-fences-from-a-field/

 ------------------------------------------------------------
 📝 Problem Statement:

There is a large (m - 1) x (n - 1) rectangular field with corners at (1, 1) 
and (m, n) containing some horizontal and vertical fences given in arrays 
hFences and vFences respectively.

Horizontal fences are from the coordinates (hFences[i], 1) to (hFences[i], n) 
and vertical fences are from the coordinates (1, vFences[i]) to (m, vFences[i]).

Return the maximum area of a square field that can be formed by removing some 
fences (possibly none) or -1 if it is impossible to make a square field.

Since the answer may be large, return it modulo 10^9 + 7.

Note: The field is surrounded by two horizontal fences from the coordinates 
(1, 1) to (1, n) and (m, 1) to (m, n) and two vertical fences from the 
coordinates (1, 1) to (m, 1) and (1, n) to (m, n). These fences cannot be removed.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: m = 4, n = 3, hFences = [2,3], vFences = [2]
Output: 4
Explanation: Removing the horizontal fence at 2 and the vertical fence at 2 
will give a square field of area 4.

Example 2:
Input: m = 6, n = 7, hFences = [2], vFences = [4]
Output: -1
Explanation: It can be proved that there is no way to create a square field 
by removing fences.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 3 <= m, n <= 10^9
 • 1 <= hFences.length, vFences.length <= 600
 • 1 < hFences[i] < m
 • 1 < vFences[i] < n
 • hFences and vFences are unique

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find maximum square area by removing fences to create gaps.

🔑 **Key Insight:**
   - Boundary fences at positions 1 and m (horizontal), 1 and n (vertical) are FIXED
   - We can remove internal fences to create gaps
   - A square requires equal horizontal and vertical gaps
   - Find all possible horizontal gaps, all possible vertical gaps
   - Return maximum gap that appears in both dimensions

📍 **Approach (HashSet Intersection):**
   - Calculate all possible horizontal gaps (distances between any two fences/boundaries)
   - Store horizontal gaps in HashSet
   - Calculate all possible vertical gaps
   - For each vertical gap, check if it exists in horizontal gaps
   - Track maximum common gap
   - Return gap² mod 10^9+7, or -1 if no common gap
   - Time: O(h² + v²) where h, v ≤ 600
   - Space: O(h²) for HashSet

 ------------------------------------------------------------
 🔹 Understanding the Problem:

Field Structure:
- Corners at (1,1) and (m,n)
- Boundary fences: x=1, x=m, y=1, y=n (FIXED, cannot remove)
- Internal horizontal fences: x = hFences[i] (removable)
- Internal vertical fences: y = vFences[i] (removable)

Creating Gaps:
- Gap = distance between two fences (with all fences between them removed)
- Example: Fences at x=1, x=3, x=5, x=7
  - Remove x=3,5 → gap from x=1 to x=7 = size 6
  - Remove x=5 → gap from x=3 to x=7 = size 4
  - Keep all → gaps of size 2 (1→3, 3→5, 5→7)

Possible Gaps:
- Between boundary and first/last fence
- Between any two fences (removing all fences in between)
- From boundary to boundary (removing all fences)

/*
 ------------------------------------------------------------
 🔹 Approach (✅ HashSet Intersection)
   ⏱️ Time Complexity: O(h² + v²) - enumerate all pairs
   💾 Space Complexity: O(h²) - storing all horizontal gaps
   
   🧠 **Key Insight:**
   A square can be formed if and only if there exists a gap size that can be 
   achieved both horizontally and vertically. Find all possible gaps in each 
   dimension, then find the maximum gap present in both.
   
   💡 **Why it works:**
   - All horizontal gaps = {distances between any two horizontal boundaries}
   - All vertical gaps = {distances between any two vertical boundaries}
   - Valid square sides = intersection of these two sets
   - Maximum square = (max of intersection)²
   
   🎯 **Gap Enumeration:**
   For horizontal dimension with boundaries at 1, m and fences at hFences[]:
   1. Distance from boundary 1 to each fence: hFences[i] - 1
   2. Distance between any two fences: hFences[i] - hFences[j] (i > j)
   3. Distance from each fence to boundary m: m - hFences[i]
   4. Distance from boundary 1 to boundary m: m - 1
   
   Similar enumeration for vertical dimension.
   
   📐 **Implementation Steps:**
   1. Sort both fence arrays
   2. Enumerate all horizontal gaps, store in HashSet
   3. Enumerate all vertical gaps, check against HashSet
   4. Track maximum common gap
   5. Return gap² % MOD, or -1 if no common gap
 ------------------------------------------------------------
*/

import java.util.*;

public class MaximumSquareAreaFences {
    private static final int MOD = 1_000_000_007;
    
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        Arrays.sort(hFences);
        Arrays.sort(vFences);
        
        Set<Integer> horizontalGaps = new HashSet<>();
        int hLen = hFences.length;
        int vLen = vFences.length;
        int maxValidGap = 0;
        
        // Calculate all possible horizontal gaps
        
        // 1. Gaps from left boundary (x=1) to each fence
        for(int fence : hFences) {
            horizontalGaps.add(fence - 1);
        }
        
        // 2. Gaps between any two fences (removing fences in between)
        for(int i = 0; i < hLen; i++) {
            for(int j = 0; j < i; j++) {
                horizontalGaps.add(hFences[i] - hFences[j]);
            }
        }
        
        // 3. Gap from left boundary to right boundary (remove all fences)
        horizontalGaps.add(m - 1);
        
        // 4. Gaps from each fence to right boundary (x=m)
        for(int fence : hFences) {
            horizontalGaps.add(m - fence);
        }
        
        // Now check all possible vertical gaps against horizontal gaps
        
        // 1. Gaps from bottom boundary (y=1) to each fence
        for(int fence : vFences) {
            int gap = fence - 1;
            if(horizontalGaps.contains(gap)) {
                maxValidGap = Math.max(maxValidGap, gap);
            }
        }
        
        // 2. Gaps between any two fences
        for(int i = 0; i < vLen; i++) {
            for(int j = 0; j < i; j++) {
                int gap = vFences[i] - vFences[j];
                if(horizontalGaps.contains(gap)) {
                    maxValidGap = Math.max(maxValidGap, gap);
                }
            }
        }
        
        // 3. Gap from bottom to top boundary (remove all fences)
        if(horizontalGaps.contains(n - 1)) {
            maxValidGap = Math.max(maxValidGap, n - 1);
        }
        
        // 4. Gaps from each fence to top boundary (y=n)
        for(int fence : vFences) {
            int gap = n - fence;
            if(horizontalGaps.contains(gap)) {
                maxValidGap = Math.max(maxValidGap, gap);
            }
        }
        
        // If no common gap found, impossible to form square
        if(maxValidGap == 0) return -1;
        
        // Calculate area with modulo (use long to prevent overflow)
        return (int)(((long)maxValidGap * (long)maxValidGap) % MOD);
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: m = 4, n = 3, hFences = [2,3], vFences = [2]

Field boundaries:
- Horizontal: x=1 (fixed), x=4 (fixed)
- Vertical: y=1 (fixed), y=3 (fixed)
- Internal horizontal fences: x=2, x=3
- Internal vertical fences: y=2

Step 1: Calculate horizontal gaps

Boundaries: 1, [2, 3], 4

Gap types:
1. From x=1 to fences:
   - 1 to 2: gap = 2-1 = 1
   - 1 to 3: gap = 3-1 = 2

2. Between fences:
   - 2 to 3: gap = 3-2 = 1

3. From boundary to boundary:
   - 1 to 4: gap = 4-1 = 3

4. From fences to x=4:
   - 2 to 4: gap = 4-2 = 2
   - 3 to 4: gap = 4-3 = 1

horizontalGaps = {1, 2, 3}

Step 2: Check vertical gaps

Boundaries: 1, [2], 3

Gap types:
1. From y=1 to fence 2:
   - gap = 2-1 = 1
   - Contains in horizontalGaps? YES ✓
   - maxValidGap = max(0, 1) = 1

2. No fence pairs (only one fence)

3. From y=1 to y=3:
   - gap = 3-1 = 2
   - Contains in horizontalGaps? YES ✓
   - maxValidGap = max(1, 2) = 2

4. From fence 2 to y=3:
   - gap = 3-2 = 1
   - Contains in horizontalGaps? YES ✓
   - maxValidGap = max(2, 1) = 2

Step 3: Calculate result
maxValidGap = 2
area = 2 × 2 = 4 ✅

Explanation:
Remove horizontal fence at x=2 and vertical fence at y=2:
- Creates horizontal gap from x=1 to x=3 (size 2)
- Creates vertical gap from y=1 to y=3 (size 2)
- Forms 2×2 square with area 4

 ------------------------------------------------------------
 🔍 Understanding Gap Types:

Visual example with hFences = [2, 4, 6] and boundaries at 1 and 8:

Positions: 1 ─ 2 ─ 3 ─ 4 ─ 5 ─ 6 ─ 7 ─ 8
           │   │       │       │       │
         Fixed Fence  Fence  Fence  Fixed

Possible horizontal gaps:

Type 1 - Boundary to fence:
- 1→2: size 1
- 1→4: size 3
- 1→6: size 5

Type 2 - Fence to fence:
- 2→4: size 2 (remove nothing, adjacent)
- 2→6: size 4 (remove fence at 4)
- 4→6: size 2 (remove nothing, adjacent)

Type 3 - Boundary to boundary:
- 1→8: size 7 (remove all fences)

Type 4 - Fence to boundary:
- 2→8: size 6 (remove fences at 4,6)
- 4→8: size 4 (remove fence at 6)
- 6→8: size 2 (remove nothing)

All gaps: {1, 2, 3, 4, 5, 6, 7}

 ------------------------------------------------------------
 🔍 Why O(n²) Enumeration?

Question: Why not just find consecutive gaps?
Answer: We can remove NON-CONSECUTIVE fences!

Example: hFences = [2, 3, 4, 5]

If we only consider consecutive:
- Gaps: {1, 1, 1} (all adjacent pairs)

But we can remove fences 3 and 4:
- Creates gap from 2 to 5: size 3
- This is not consecutive in the original array!

Therefore, we must consider ALL pairs:
- All pairs (i, j) where i > j
- This requires O(n²) enumeration
- No way to optimize without losing valid gaps

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: No internal fences
hFences = [], vFences = []
- Only gap: m-1 horizontal, n-1 vertical
- If m-1 == n-1: return (m-1)²
- Otherwise: return -1 ✓

Case 2: Single fence in each dimension
hFences = [5], vFences = [5]
m = 10, n = 10
- Horizontal gaps: {4, 5, 9} (5-1, 10-5, 10-1)
- Vertical gaps: {4, 5, 9}
- Common: {4, 5, 9}
- Maximum: 9
- Result: 81 ✓

Case 3: No common gap
m = 6, n = 7, hFences = [2], vFences = [4]
- Horizontal gaps: {1, 4, 5} (2-1, 6-2, 6-1)
- Vertical gaps: {3, 3, 6} (4-1, 7-4, 7-1)
- Common: {} (empty!)
- Result: -1 ✓

Case 4: Multiple fences, one common gap
hFences = [2, 3, 5], vFences = [2, 4, 6]
m = 7, n = 8
- Many horizontal gaps
- Many vertical gaps
- Find maximum common gap ✓

Case 5: Large result requiring modulo
m = 10^9, n = 10^9, hFences = [], vFences = []
- Gap = 10^9 - 1
- Area = (10^9 - 1)² ≈ 10^18
- Must use modulo ✓

Case 6: All same gaps
hFences = [2, 4, 6], vFences = [2, 4, 6]
m = 8, n = 8
- Identical fence positions
- Many common gaps
- Return maximum ✓

 ------------------------------------------------------------
 🔍 Why Use Long for Multiplication?

Problem: maxValidGap can be up to 10^9 - 1

Without long:
```java
int area = maxValidGap * maxValidGap; // OVERFLOW!
```

With maxValidGap = 10^9:
- maxValidGap * maxValidGap = 10^18
- int max = 2^31 - 1 ≈ 2.1 × 10^9
- Result: overflow, wrong answer!

Correct approach:
```java
long area = (long)maxValidGap * (long)maxValidGap;
return (int)(area % MOD);
```

This ensures:
- Multiplication happens in long (can hold 10^18)
- Then modulo operation
- Then cast to int (safe after modulo)

 ------------------------------------------------------------
 🔍 Complexity Analysis:

Time Complexity:
- Sorting hFences: O(h log h) where h ≤ 600
- Sorting vFences: O(v log v) where v ≤ 600
- Horizontal gap enumeration:
  • Boundary to fence: O(h)
  • Fence to fence: O(h²) - all pairs
  • Fence to boundary: O(h)
  • Total: O(h²)
- Vertical gap checking:
  • Boundary to fence: O(v) × O(1) HashSet lookup
  • Fence to fence: O(v²) × O(1) HashSet lookup
  • Fence to boundary: O(v) × O(1) HashSet lookup
  • Total: O(v²)
- Overall: O(h log h + v log v + h² + v²)
- With h, v ≤ 600: O(600²) ≈ 360,000 operations

Space Complexity:
- horizontalGaps HashSet: O(h²) worst case
- With h ≤ 600: O(360,000) = ~1.4MB
- Sorting: O(log h + log v) recursion
- Overall: O(h²)

Why h² space?
- Maximum number of pairs: h × (h-1) / 2 ≈ h²/2
- Plus boundary gaps: ~h more
- Total unique gaps ≤ h²

⚡ Performance Analysis:
This HashSet intersection approach efficiently handles maximum constraints:
- Fence arrays up to 600 elements each
- Field dimensions up to 10^9 (doesn't affect time complexity!)
- Gap enumeration: O(600²) ≈ 360K operations
- HashSet operations: O(1) average case
- Total execution: <50ms for maximum input
- Space usage: ~1.4MB for HashSet
- The key insights:
  • Field dimensions (m, n) don't affect complexity
  • Only fence count matters (max 600)
  • HashSet enables O(1) intersection checking
  • Must enumerate all pairs (can't optimize to O(n))
- Why this is optimal:
  • Must consider all gap combinations: Ω(n²) lower bound
  • HashSet lookup is optimal for membership testing
  • No way to avoid checking all vertical gaps
- Alternative approaches:
  • Two HashSets + explicit intersection: same complexity, more space
  • Sorting both gap arrays + two pointers: O(n² log n), worse
  • Binary search: same O(n²) but higher constants
- This problem demonstrates:
  • When brute force enumeration is necessary
  • HashSet for efficient set operations
  • Handling large field dimensions as red herrings
  • Proper overflow prevention with long
 ------------------------------------------------------------
*/