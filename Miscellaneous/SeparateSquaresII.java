/*
 🔹 Problem: 3454. Separate Squares II
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Array, Geometry, Sorting, Line Sweep, Computational Geometry
 🔹 Link: https://leetcode.com/problems/separate-squares-ii/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a 2D integer array squares. Each squares[i] = [xi, yi, li] 
represents the coordinates of the bottom-left point and the side length of a 
square parallel to the x-axis.

Find the minimum y-coordinate value of a horizontal line such that the total 
area covered by squares above the line equals the total area covered by squares 
below the line.

Answers within 10^-5 of the actual answer will be accepted.

Note: Squares may overlap. Overlapping areas should be counted only once in 
this version.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: squares = [[0,0,1],[2,2,1]]
Output: 1.00000
Explanation:
Any horizontal line between y = 1 and y = 2 results in an equal split, with 
1 square unit above and 1 square unit below. The minimum y-value is 1.

Example 2:
Input: squares = [[0,0,2],[1,1,1]]
Output: 1.00000
Explanation:
Since the blue square overlaps with the red square, it will not be counted 
again. Thus, the line y = 1 splits the squares into two equal parts.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= squares.length <= 5 * 10^4
 • squares[i] = [xi, yi, li]
 • squares[i].length == 3
 • 0 <= xi, yi <= 10^9
 • 1 <= li <= 10^9
 • The total area of all the squares will not exceed 10^15

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find y-coordinate where covered area below = covered area above (unions).

🔑 **Key Difference from Problem I:**
   - Problem I: Count overlapping areas multiple times (simple width sum)
   - Problem II: Count overlapping areas only once (requires interval union)

📍 **Approach (2D Sweep Line with Interval Union):**
   - Create events for each square's bottom and top edges
   - Sort events by y-coordinate
   - Between each pair of consecutive y-coordinates (a "strip"):
     • Maintain list of active x-intervals
     • Calculate union width of overlapping x-intervals
     • Strip area = union_width × height
   - First pass: Calculate all strip areas and total covered area
   - Second pass: Find y where accumulated area = totalArea/2
   - Time: O(n² log n) worst case, Space: O(n)

 ------------------------------------------------------------
 🔹 Key Challenges in Problem II:

Challenge 1: Handling Overlapping Squares
- Simple width sum overcounts overlapping regions
- Need interval union algorithm for x-coordinates

Challenge 2: 2D Sweep Line
- Sweep vertically (y-axis) to process strips
- For each strip, calculate horizontal union width

Challenge 3: Exact Interpolation
- Answer may lie within a strip
- Use linear interpolation: y = bottomY + missingArea/width

/*
 ------------------------------------------------------------
 🔹 Approach (✅ 2D Sweep Line with Interval Union - Optimal)
   ⏱️ Time Complexity: O(n² log n) - n events, each processes O(n) intervals
   💾 Space Complexity: O(n) - events list + active intervals
   
   🧠 **Key Insight:**
   Treat this as a 2D geometry problem:
   1. Vertical sweep: process horizontal strips between y-coordinates
   2. Horizontal union: for each strip, compute union of x-intervals
   3. Strip area = union_width × strip_height
   4. Accumulate until reaching half of total area
   
   💡 **Why it works:**
   - Between consecutive y-coordinates, the x-configuration is constant
   - Within each strip, area grows linearly with height
   - Union width accounts for overlaps (counts each region once)
   - Two-pass algorithm:
     • Pass 1: Build all strips, calculate total area
     • Pass 2: Find splitting point with linear interpolation
   
   🎯 **Interval Union Algorithm:**
   - Sort intervals by start position
   - Merge overlapping/touching intervals
   - Sum lengths of merged intervals
   - This gives total width covered (counting overlaps once)
   
   📐 **Strip Processing:**
   Each strip represents a horizontal band where:
   - Bottom y = previous event y-coordinate
   - Height = current event y - previous event y
   - Width = union of all active x-intervals
   - Area = width × height
 ------------------------------------------------------------
*/

import java.util.*;

public class SeparateSquaresII {
    
    // Helper class to represent X-intervals (horizontal ranges)
    private static class Interval implements Comparable<Interval> {
        int start, end;
        
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public int compareTo(Interval other) {
            if(this.start != other.start) {
                return Integer.compare(this.start, other.start);
            }
            return Integer.compare(this.end, other.end);
        }
        
        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return start == interval.start && end == interval.end;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
    
    // Helper class for Sweep Line events
    private static class Event implements Comparable<Event> {
        int y;          // Y-coordinate of event
        int type;       // 1 for square start (bottom), -1 for end (top)
        int xStart;     // Left x-coordinate of square
        int xEnd;       // Right x-coordinate of square
        
        Event(int y, int type, int xStart, int xEnd) {
            this.y = y;
            this.type = type;
            this.xStart = xStart;
            this.xEnd = xEnd;
        }
        
        @Override
        public int compareTo(Event other) {
            return Integer.compare(this.y, other.y);
        }
    }
    
    public double separateSquares(int[][] squares) {
        // Create sweep line events
        List<Event> sweepEvents = new ArrayList<>();
        for(int[] sq : squares) {
            int x = sq[0];
            int y = sq[1];
            int l = sq[2];
            
            // Start event: square begins at y
            sweepEvents.add(new Event(y, 1, x, x + l));
            
            // End event: square ends at y+l
            sweepEvents.add(new Event(y + l, -1, x, x + l));
        }
        
        // Sort events by y-coordinate
        Collections.sort(sweepEvents);
        
        // Track active x-intervals and processed strips
        List<Interval> activeIntervals = new ArrayList<>();
        List<double[]> processedStrips = new ArrayList<>();
        
        double totalArea = 0;
        int prevY = sweepEvents.get(0).y;
        
        // First pass: Process all strips and calculate total area
        for(Event event : sweepEvents) {
            // Process the strip between previous y and current y
            if(event.y > prevY) {
                double unionWidth = getUnionWidth(activeIntervals);
                double height = (double)(event.y - prevY);
                
                if(unionWidth > 0) {
                    // Store strip: [bottomY, height, width]
                    processedStrips.add(new double[]{prevY, height, unionWidth});
                    totalArea += height * unionWidth;
                }
            }
            
            // Update active intervals based on event type
            Interval currentInterval = new Interval(event.xStart, event.xEnd);
            if(event.type == 1) {
                // Square starts: add interval
                activeIntervals.add(currentInterval);
            } else {
                // Square ends: remove interval
                activeIntervals.remove(currentInterval);
            }
            
            prevY = event.y;
        }
        
        // Second pass: Find the y-coordinate that splits area equally
        double targetArea = totalArea / 2.0;
        double accumulatedArea = 0;
        
        for(double[] strip : processedStrips) {
            double bottomY = strip[0];
            double height = strip[1];
            double width = strip[2];
            double stripArea = height * width;
            
            // Check if target area is reached within this strip
            if(accumulatedArea + stripArea >= targetArea) {
                double missingArea = targetArea - accumulatedArea;
                // Linear interpolation within strip
                return bottomY + (missingArea / width);
            }
            
            accumulatedArea += stripArea;
        }
        
        return 0.0; // Should not reach here given problem constraints
    }
    
    /**
     * Calculate union width of overlapping intervals.
     * This is the key difference from Problem I.
     * 
     * Algorithm: Interval Merging
     * 1. Sort intervals by start position
     * 2. Merge overlapping intervals
     * 3. Sum lengths of merged intervals
     * 
     * Time: O(k log k) where k = number of active intervals
     */
    private double getUnionWidth(List<Interval> intervals) {
        if(intervals.isEmpty()) return 0;
        
        // Create sorted copy to avoid modifying original list
        List<Interval> sorted = new ArrayList<>(intervals);
        Collections.sort(sorted);
        
        double unionLength = 0;
        double currentEnd = -1e18; // Negative infinity
        
        for(Interval iv : sorted) {
            if(iv.start >= currentEnd) {
                // Disjoint interval: no overlap with previous
                unionLength += (iv.end - iv.start);
                currentEnd = iv.end;
            } else if(iv.end > currentEnd) {
                // Overlapping interval: only count non-overlapping part
                unionLength += (iv.end - currentEnd);
                currentEnd = iv.end;
            }
            // If iv.end <= currentEnd: completely contained, skip
        }
        
        return unionLength;
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: squares = [[0,0,2],[1,1,1]]

Step 1: Create events
Square 1: [0,0,2]
  - Start: Event(y=0, type=1, x=[0,2])
  - End: Event(y=2, type=-1, x=[0,2])
Square 2: [1,1,1]
  - Start: Event(y=1, type=1, x=[1,2])
  - End: Event(y=2, type=-1, x=[1,2])

Events sorted: [
  Event(0, 1, [0,2]),
  Event(1, 1, [1,2]),
  Event(2, -1, [0,2]),
  Event(2, -1, [1,2])
]

Step 2: First pass - process strips

Event 1: y=0 (Square 1 starts)
- No previous strip (first event)
- Add interval [0,2] to activeIntervals
- prevY = 0

Event 2: y=1 (Square 2 starts)
- Process strip [y=0 to y=1]:
  - Active intervals: [[0,2]]
  - Union width: 2
  - Height: 1
  - Area: 2 × 1 = 2
  - Store strip: [0, 1, 2]
  - totalArea = 2
- Add interval [1,2] to activeIntervals
- activeIntervals: [[0,2], [1,2]]
- prevY = 1

Event 3: y=2 (Square 1 ends)
- Process strip [y=1 to y=2]:
  - Active intervals: [[0,2], [1,2]]
  - Union width calculation:
    • Sort: [[0,2], [1,2]]
    • Interval [0,2]: currentEnd=-∞, add 2, currentEnd=2
    • Interval [1,2]: start=1 < currentEnd=2, end=2 ≤ currentEnd=2, skip
    • Union width = 2 (overlap counted once!)
  - Height: 1
  - Area: 2 × 1 = 2
  - Store strip: [1, 1, 2]
  - totalArea = 2 + 2 = 4
- Remove interval [0,2] from activeIntervals
- prevY = 2

Event 4: y=2 (Square 2 ends)
- No strip (same y as previous)
- Remove interval [1,2]
- activeIntervals: []

Step 3: Second pass - find split point

Strips: [[0, 1, 2], [1, 1, 2]]
targetArea = 4 / 2 = 2
accumulatedArea = 0

Strip 1: [0, 1, 2]
- bottomY=0, height=1, width=2
- stripArea = 1 × 2 = 2
- accumulatedArea + stripArea = 0 + 2 = 2 ≥ 2 ✓
- missingArea = 2 - 0 = 2
- Answer = 0 + (2 / 2) = 1.0 ✅

Result: 1.00000

Visual representation:
y=2  ┌─────┐
     │  1  │
y=1  │─────┤ <- Line at y=1.0
     │  1  │└───┘
     │     │  2
y=0  └─────┘

Below line: 2 units (red square bottom half)
Above line: 2 units (red square top half, blue completely inside)

 ------------------------------------------------------------
 🔍 Understanding Interval Union:

Example: intervals = [[0,2], [1,3], [5,7]]

After sorting: [[0,2], [1,3], [5,7]]

Processing:
1. [0,2]: currentEnd=-∞, disjoint
   - Add length: 2-0 = 2
   - currentEnd = 2
   - unionLength = 2

2. [1,3]: start=1 < currentEnd=2, overlapping
   - Add non-overlapping part: 3-2 = 1
   - currentEnd = 3
   - unionLength = 2 + 1 = 3

3. [5,7]: start=5 ≥ currentEnd=3, disjoint
   - Add length: 7-5 = 2
   - currentEnd = 7
   - unionLength = 3 + 2 = 5

Total union width = 5

Visual:
[0────2]
  [1────3]
         [5──7]
└─────┘  └──┘
   3   +  2  = 5 ✓

 ------------------------------------------------------------
 🔍 Key Differences: Problem I vs Problem II

Problem I (Multiple Counting):
- Simple width calculation: sum of all square widths
- No overlap handling needed
- O(n) per strip

Problem II (Union Counting):
- Complex width calculation: union of intervals
- Must merge overlapping intervals
- O(k log k) per strip where k = active squares

Example showing difference:
Squares: [[0,0,2], [1,0,2]] at same height

Problem I:
- Width = 2 + 2 = 4 (count overlap twice)
- Area = 4 × height

Problem II:
- Intervals: [0,2], [1,3]
- Union: [0,3] 
- Width = 3 (count overlap once)
- Area = 3 × height

 ------------------------------------------------------------
 🔍 Edge Cases:

Case 1: Non-overlapping squares
squares = [[0,0,1], [5,0,1]]
- Two separate intervals: [0,1] and [5,6]
- Union width = 1 + 1 = 2
- Works correctly ✓

Case 2: Completely overlapping squares
squares = [[0,0,2], [0,0,2]]
- Same interval [0,2] twice
- Union width = 2 (counted once)
- Area = 4 (not 8) ✓

Case 3: Partially overlapping squares
squares = [[0,0,3], [2,0,3]]
- Intervals: [0,3], [2,5]
- Union: [0,5]
- Width = 5 (not 6) ✓

Case 4: Three-way overlap
squares = [[0,0,3], [1,0,3], [2,0,3]]
- Intervals: [0,3], [1,4], [2,5]
- Union: [0,5]
- Width = 5 ✓

Case 5: Nested squares
squares = [[0,0,10], [2,2,6]]
- At y=2: intervals [0,10], [2,8]
- Union: [0,10] (inner completely contained)
- Width = 10 ✓

Case 6: Answer at exact boundary
squares = [[0,0,2], [0,2,2]]
- Strip 1: y=[0,2], area=4
- Strip 2: y=[2,4], area=4
- Answer = 2.0 (at boundary) ✓

 ------------------------------------------------------------
 🔍 Complexity Analysis Detailed:

Time Complexity Breakdown:
1. Event creation: O(n) - two events per square
2. Event sorting: O(n log n)
3. First pass (strip processing):
   - n events processed
   - Each event: O(k) to add/remove interval
   - Each strip: O(k log k) for union calculation
   - Worst case: all squares overlap → k=n
   - Total: O(n² log n)
4. Second pass: O(n) - linear scan through strips
Overall: O(n² log n)

Space Complexity:
- Events list: O(n)
- Active intervals: O(n) worst case
- Processed strips: O(n)
- Total: O(n)

Optimization possibilities:
- Segment tree for interval operations: O(n log² n)
- Coordinate compression: reduce constants
- Early termination in second pass: already implemented

Practical performance:
- For n=50,000 with sparse squares: ~O(n log n)
- For n=50,000 with dense overlap: ~O(n² log n)
- Average case much better than worst case

⚡ Performance Analysis:
The 2D sweep line approach handles maximum constraints:
- Up to 50,000 squares
- Coordinates up to 10^9
- Total area up to 10^15
- Events: 100,000 (2 per square)
- Worst case complexity: O(n² log n)
  • n=50,000: ~50,000² × 17 ≈ 42 billion operations
  • With modern CPUs: ~10-20 seconds
- Average case (sparse): O(n log n)
  • n=50,000: ~50,000 × 17 ≈ 850K operations
  • Execution: <100ms
- The key algorithmic insights:
  • 2D problem → vertical sweep + horizontal union
  • Overlaps → interval merging algorithm
  • Exact answer → linear interpolation within strips
- This demonstrates advanced computational geometry:
  • Sweep line with complex predicates
  • Interval union (basis for many GIS algorithms)
  • Proper handling of floating point precision
  • Two-pass algorithm for efficiency
- Real-world applications:
  • GIS: calculating covered area with overlapping regions
  • Computer graphics: visible surface determination
  • Circuit design: routing area calculation
  • Urban planning: building coverage analysis
 ------------------------------------------------------------
*/