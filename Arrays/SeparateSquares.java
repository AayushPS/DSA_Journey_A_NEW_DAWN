/*
 🔹 Problem: 3453. Separate Squares I
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Array, Binary Search, Geometry, Sorting, Line Sweep
 🔹 Link: https://leetcode.com/problems/separate-squares-i/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a 2D integer array squares. Each squares[i] = [xi, yi, li] 
represents the coordinates of the bottom-left point and the side length of a 
square parallel to the x-axis.

Find the minimum y-coordinate value of a horizontal line such that the total 
area of the squares above the line equals the total area of the squares below 
the line.

Answers within 10^-5 of the actual answer will be accepted.

Note: Squares may overlap. Overlapping areas should be counted multiple times.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: squares = [[0,0,1],[2,2,1]]
Output: 1.00000
Explanation:
Any horizontal line between y = 1 and y = 2 will have 1 square unit above it 
and 1 square unit below it. The lowest option is 1.

Example 2:
Input: squares = [[0,0,2],[1,1,1]]
Output: 1.16667
Explanation:
The areas are:
• Below the line: 7/6 * 2 (Red) + 1/6 (Blue) = 15/6 = 2.5.
• Above the line: 5/6 * 2 (Red) + 5/6 (Blue) = 15/6 = 2.5.
Since the areas above and below the line are equal, the output is 7/6 = 1.16667.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= squares.length <= 5 * 10^4
 • squares[i] = [xi, yi, li]
 • squares[i].length == 3
 • 0 <= xi, yi <= 10^9
 • 1 <= li <= 10^9
 • The total area of all the squares will not exceed 10^12

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find y-coordinate where area below = area above (each = totalArea/2).

📍 **Approach 1 (Binary Search on Y-coordinate):**
   - Calculate total area of all squares
   - Binary search on y-coordinate from minY to maxY
   - For each candidate y, calculate area below the line
   - If area below ≥ half total area, search lower; else search higher
   - Converge until precision threshold (10^-5) reached
   - Time: O(n log(maxY-minY) / ε), Space: O(1)
   - Straightforward but requires many iterations

📍 **Approach 2 (Sweep Line with Events - Optimized):**
   - Key insight: Answer lies at a y-coordinate where active squares change
   - Create events: square starts (bottom edge) and ends (top edge)
   - Sort events by y-coordinate
   - Sweep upward, tracking total width of active squares at each y
   - Calculate cumulative area below current height
   - When cumulative area reaches totalArea/2, compute exact y
   - Time: O(n log n) for sorting, Space: O(n) for events
   - More efficient, exact calculation within event ranges

 ------------------------------------------------------------
 🔹 Approach 1 (Commented - Binary Search on Y-coordinate)
   ⏱️ Time Complexity: O(n × log((maxY-minY)/ε)) - binary search with area calculation
   💾 Space Complexity: O(1) - only variables
   
   🧠 **Key Insight:**
   The area below line y is monotonically increasing as y increases.
   This monotonicity allows binary search to find the splitting point.
   
   💡 **Why it works:**
   - Total area is fixed (sum of all square areas)
   - As y increases, area below increases monotonically
   - Binary search finds minimum y where area below ≥ totalArea/2
   - For each candidate y, calculate area below:
     • For each square, if y ≥ bottom, add min(sideLength, y-bottom) × sideLength
   - Precision: iterate until |hi - lo| < 10^-5
   - Simple to implement, works for all cases
 ------------------------------------------------------------

// class Solution {
//     public double separateSquares(int[][] squares) {
//         int maxY = 0, minY = Integer.MAX_VALUE;
//         double totalArea = 0;
//         
//         // Find bounds and calculate total area
//         for(int[] square : squares) {
//             minY = Math.min(minY, square[1]);
//             maxY = Math.max(maxY, square[1] + square[2]);
//             totalArea += (double)square[2] * square[2];
//         }
//         
//         double halfArea = totalArea / 2.0;
//         double lo = minY, hi = maxY;
//         double eps = 1e-5;
//         
//         // Binary search for the splitting y-coordinate
//         while(Math.abs(hi - lo) > eps) {
//             double mid = (hi + lo) / 2;
//             if(divides(mid, halfArea, squares)) {
//                 hi = mid;
//             } else {
//                 lo = mid;
//             }
//         }
//         
//         return hi;
//     }
//     
//     // Check if horizontal line at y divides area equally
//     private boolean divides(double y, double halfArea, int[][] squares) {
//         double areaUnder = 0;
//         
//         for(int[] square : squares) {
//             if(y >= square[1]) {
//                 // Square is at least partially below line
//                 double heightBelow = Math.min(square[2], y - square[1]);
//                 areaUnder += heightBelow * square[2];
//             }
//         }
//         
//         return areaUnder >= halfArea;
//     }
// }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Sweep Line with Events - Most Optimal)
   ⏱️ Time Complexity: O(n log n) - sorting events
   💾 Space Complexity: O(n) - storing events
   
   🧠 **Key Insight:**
   The answer must lie at a y-coordinate where the configuration of active 
   squares changes (i.e., at square boundaries). Use sweep line to process 
   these critical points efficiently.
   
   💡 **Why it works:**
   - Create events for each square's bottom (start) and top (end) edges
   - Sort events by y-coordinate
   - Sweep upward, maintaining total width of active squares
   - Between events, the width is constant, so area accumulates linearly
   - When accumulated area reaches totalArea/2, calculate exact y:
     • y = prevHeight + (targetArea - currentArea) / currentWidth
   - This gives exact answer within the current event interval
   
   🎯 **Event-based calculation:**
   - Event types: +1 (square starts), -1 (square ends)
   - coveredWidth = sum of widths of all active squares
   - For each event at height y:
     1. Calculate area added since last event: coveredWidth × (y - prevHeight)
     2. Check if adding this area crosses the halfway point
     3. If yes, interpolate exact y-coordinate within interval
     4. If no, update coveredWidth and continue
   
   🔑 **Why more efficient:**
   - Only processes O(n) events instead of O(log(range/ε)) iterations
   - Each event processed in O(1) time
   - Sorting dominates: O(n log n)
   - No repeated area calculations for entire array
   - Exact calculation, no epsilon convergence needed
 ------------------------------------------------------------
*/

import java.util.*;

public class SeparateSquares {
    public double separateSquares(int[][] squares) {
        long totalArea = 0;
        List<int[]> events = new ArrayList<>();
        
        // Create events for square boundaries
        for(int[] sq : squares) {
            int y = sq[1];      // Bottom y-coordinate
            int l = sq[2];      // Side length
            totalArea += (long)l * l;
            
            // Start event: square begins at y (add width)
            events.add(new int[]{y, l, 1});
            
            // End event: square ends at y+l (remove width)
            events.add(new int[]{y + l, l, -1});
        }
        
        // Sort events by y-coordinate
        events.sort(Comparator.comparingInt(a -> a[0]));
        
        double coveredWidth = 0;    // Total width of active squares
        double currArea = 0;         // Cumulative area below current height
        double prevHeight = 0;       // Previous event height
        
        // Process events in ascending y order
        for(int[] e : events) {
            int y = e[0];          // Current event height
            int l = e[1];          // Side length of square in event
            int delta = e[2];      // +1 for start, -1 for end
            
            // Calculate area added between previous and current event
            int diff = y - (int)prevHeight;
            double area = coveredWidth * diff;
            
            // Check if adding this area crosses the halfway point
            if(2 * (currArea + area) >= totalArea) {
                // Answer lies in the interval [prevHeight, y]
                // Interpolate exact y-coordinate
                return prevHeight + (totalArea - 2.0 * currArea) / (2.0 * coveredWidth);
            }
            
            // Update covered width (add or remove square width)
            coveredWidth += delta * l;
            
            // Update cumulative area
            currArea += area;
            
            // Update previous height for next iteration
            prevHeight = y;
        }
        
        return 0.0; // Should never reach here given problem constraints
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: squares = [[0,0,2],[1,1,1]]

Step 1: Calculate total area and create events
- Square 1: y=0, l=2 → area = 4
  Events: [0, 2, 1] (start), [2, 2, -1] (end)
- Square 2: y=1, l=1 → area = 1
  Events: [1, 1, 1] (start), [2, 1, -1] (end)
- totalArea = 5

Step 2: Sort events by y-coordinate
events = [[0,2,1], [1,1,1], [2,2,-1], [2,1,-1]]

Step 3: Process events

Event 1: [0, 2, 1] (y=0, square 1 starts)
- diff = 0 - 0 = 0
- area = 0 × 0 = 0
- 2×(0+0) = 0 < 5 (not halfway yet)
- coveredWidth = 0 + 1×2 = 2
- currArea = 0 + 0 = 0
- prevHeight = 0

Event 2: [1, 1, 1] (y=1, square 2 starts)
- diff = 1 - 0 = 1
- area = 2 × 1 = 2
- 2×(0+2) = 4 < 5 (not halfway yet)
- coveredWidth = 2 + 1×1 = 3
- currArea = 0 + 2 = 2
- prevHeight = 1

Event 3: [2, 2, -1] (y=2, square 1 ends)
- diff = 2 - 1 = 1
- area = 3 × 1 = 3
- 2×(2+3) = 10 ≥ 5 ✓ (crossed halfway!)
- Answer = prevHeight + (totalArea - 2×currArea) / (2×coveredWidth)
         = 1 + (5 - 2×2) / (2×3)
         = 1 + 1/6
         = 1.16667 ✅

Result: 1.16667

Visual representation:
y=2  ┌─────┐ ┌───┐
     │     │ │   │
y=1  │  1  │ └───┘ <- Line at y=1.16667
     │     │   2
y=0  └─────┘

Below line: (7/6)×2 + 1/6 = 15/6 = 2.5
Above line: (5/6)×2 + 5/6 = 15/6 = 2.5

 ------------------------------------------------------------
 🔍 Understanding the Interpolation Formula:

When we detect that the answer lies in interval [prevHeight, currentY]:

We know:
- currArea = area below prevHeight
- coveredWidth = total width of active squares in interval
- targetArea = totalArea / 2

Linear interpolation:
Area at height h = currArea + coveredWidth × (h - prevHeight)

Set area = targetArea:
currArea + coveredWidth × (h - prevHeight) = totalArea / 2

Solve for h:
coveredWidth × (h - prevHeight) = totalArea/2 - currArea
h - prevHeight = (totalArea/2 - currArea) / coveredWidth
h = prevHeight + (totalArea - 2×currArea) / (2×coveredWidth)

This is the exact formula used in the code! ✓

 ------------------------------------------------------------
 🔍 Why Events at Square Boundaries?

Key observation: Between square boundaries, the configuration is constant.

Example with 3 squares:
y=5  ─────── Event: square 3 ends
     ███████ (squares 1, 2, 3 active)
y=3  ─────── Event: square 2 ends
     █████   (squares 1, 3 active)
y=2  ─────── Event: square 3 starts
     ███     (squares 1, 2 active)
y=1  ─────── Event: square 2 starts
     █       (square 1 active)
y=0  ─────── Event: square 1 starts

Between events, active squares don't change, so:
- Width is constant
- Area grows linearly with height
- We can calculate exact crossing point within interval

This is why sweep line is efficient and exact!

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Single square
squares = [[0,0,10]]
- totalArea = 100
- Events: [0,10,1], [10,10,-1]
- Answer: y = 5 (middle of square) ✓

Case 2: Non-overlapping squares at different heights
squares = [[0,0,1], [0,10,1]]
- totalArea = 2
- Events: [0,1,1], [1,1,-1], [10,1,1], [11,1,-1]
- Answer: y = 1 (after first square) ✓

Case 3: Completely overlapping squares
squares = [[0,0,2], [0,0,2]]
- totalArea = 8 (counted twice!)
- Events: [0,2,1], [0,2,1], [2,2,-1], [2,2,-1]
- Answer: y = 1 (middle height) ✓

Case 4: Partially overlapping squares
squares = [[0,0,3], [1,1,2]]
- Multiple configurations as we sweep
- Answer calculated when area reaches half ✓

Case 5: Answer at exact square boundary
squares = [[0,0,2], [0,2,2]]
- Answer exactly at y=2 where squares meet ✓

Case 6: Large coordinates
squares = [[0,0,1000000000]]
- Binary search would need many iterations
- Sweep line handles in O(n log n) regardless of coordinate size ✓

 ------------------------------------------------------------
 🔍 Comparison of Approaches:

Binary Search Approach:
✅ Simple to understand and implement
✅ Works for any precision requirement
❌ O(n × log(range/ε)) time - many iterations for large ranges
❌ Requires epsilon tuning
❌ Recalculates areas repeatedly

Sweep Line Approach:
✅ O(n log n) time - independent of coordinate range
✅ Exact calculation (no epsilon needed)
✅ Each square processed only twice (start/end)
✅ Efficient event-based computation
❌ Slightly more complex to implement
❌ Requires sorting

For this problem:
- Coordinates up to 10^9
- Binary search would need ~log(10^9/10^-5) ≈ 47 iterations × n squares
- Sweep line needs O(n log n) event processing
- For n=50,000: sweep line is ~100x faster!

⚡ Performance Analysis:
The sweep line approach efficiently handles maximum constraints:
- Up to 50,000 squares
- Coordinates up to 10^9
- Total area up to 10^12
- Events created: 2n = 100,000
- Sorting: O(n log n) ≈ 100,000 × 17 = 1.7M operations
- Event processing: O(n) = 100,000 operations
- Total: ~1.8M operations vs ~2.35B for binary search!
- Space: O(n) for events list (400KB for 50K squares)
- Precision: Exact calculation using double (15-16 significant digits)
- No epsilon convergence needed
- The sweep line technique demonstrates:
  • Event-based processing for geometric problems
  • How sorting can enable linear-time processing
  • Exact vs approximate solutions
  • Space-time tradeoffs (O(n) space for O(log n) → O(1) factor improvement)
- This pattern appears in many problems:
  • Interval scheduling
  • Rectangle area calculation
  • Skyline problem
  • Meeting rooms
  • Range queries with updates
 ------------------------------------------------------------
*/