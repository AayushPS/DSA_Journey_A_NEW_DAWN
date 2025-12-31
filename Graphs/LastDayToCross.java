package Graphs;
/*
 🔹 Problem: 1970. Last Day Where You Can Still Cross
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Array, Binary Search, Depth-First Search, Breadth-First Search, Union Find, Matrix
 🔹 Link: https://leetcode.com/problems/last-day-where-you-can-still-cross/

 ------------------------------------------------------------
 📝 Problem Statement:

There is a 1-based binary matrix where 0 represents land and 1 represents water. 
You are given integers row and col representing the number of rows and columns in 
the matrix, respectively.

Initially on day 0, the entire matrix is land. However, each day a new cell becomes 
flooded with water. You are given a 1-based 2D array cells, where cells[i] = [ri, ci] 
represents that on the ith day, the cell on the rith row and cith column (1-based 
coordinates) will be covered with water (i.e., changed to 1).

You want to find the last day that it is possible to walk from the top to the bottom 
by only walking on land cells. You can start from any cell in the top row and end at 
any cell in the bottom row. You can only travel in the four cardinal directions 
(left, right, up, and down).

Return the last day where it is possible to walk from the top to the bottom by only 
walking on land cells.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
Output: 2
Explanation: The above image depicts how the matrix changes each day starting from day 0.
The last day where it is possible to cross from top to bottom is on day 2.

Example 2:
Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
Output: 1
Explanation: The above image depicts how the matrix changes each day starting from day 0.
The last day where it is possible to cross from top to bottom is on day 1.

Example 3:
Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
Output: 3
Explanation: The above image depicts how the matrix changes each day starting from day 0.
The last day where it is possible to cross from top to bottom is on day 3.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 <= row, col <= 2 * 10^4
 • 4 <= row * col <= 2 * 10^4
 • cells.length == row * col
 • 1 <= ri <= row
 • 1 <= ci <= col
 • All the values of cells are unique

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Find the last day when a path exists from top row to bottom row on land cells.

📍 **Approach 1 (Binary Search + BFS/DFS):**
   - Binary search on the answer (day number)
   - For each mid-day, simulate grid state and run BFS/DFS
   - Check if path exists from any top cell to any bottom cell
   - Time complexity: O(row * col * log(row * col)) per search
   - Straightforward but potentially slow for large grids

📍 **Approach 2 (Union Find - Reverse Time Optimized):**
   - Think in reverse: start with all water, gradually add land
   - Use Union-Find (DSU) to track connected components
   - Create virtual nodes for "top row" and "bottom row"
   - Work backwards through cells array, converting water to land
   - Connect adjacent land cells using union operations
   - When top and bottom virtual nodes become connected, that's the answer
   - Elegant O(row * col * α(n)) solution where α is inverse Ackermann function

/*
 ------------------------------------------------------------
 🔹 Approach (✅ Union Find - Reverse Time with Virtual Nodes)
   ⏱️ Time Complexity: O(row * col * α(n)) - where α is inverse Ackermann, nearly O(1)
   💾 Space Complexity: O(row * col) - DSU parent and rank arrays + grid matrix
   
   🧠 **Key Insight:**
   Instead of going forward in time (hard to know when path breaks), go backwards!
   Start with all water and add land cells in reverse order. The moment a path 
   forms from top to bottom is the answer. Using DSU with virtual nodes makes 
   connectivity checks efficient.
   
   💡 **Why it works:**
   - Virtual top node (index = size) connects to all cells in row 0
   - Virtual bottom node (index = size+1) connects to all cells in row (row-1)
   - As we add land cells backwards, we union them with adjacent land cells
   - DSU efficiently tracks which cells are connected
   - When find(top) == find(bottom), a complete path exists
   - The first day (going backwards) when this happens is our answer
   - Path compression and union by rank ensure near-constant time operations
   
   🎯 **Algorithm Steps:**
   1. Initialize grid with all water (all cells true)
   2. Create DSU with size + 2 nodes (grid cells + 2 virtual nodes)
   3. Iterate cells array backwards (last day to first day)
   4. For each cell, convert it to land (false)
   5. Union with top virtual node if in first row
   6. Union with bottom virtual node if in last row
   7. Union with all adjacent land cells in 4 directions
   8. Check if top and bottom virtual nodes are connected
   9. If connected, return current day index
 ------------------------------------------------------------
*/

public class LastDayToCross {
    private static final int[][] DIRS = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public int latestDayToCross(int row, int col, int[][] cells) {
        // Initialize all cells as water (true = water, false = land)
        boolean[][] mat = new boolean[row][col];
        for(int[] cell : cells) {
            mat[cell[0]-1][cell[1]-1] = true;
        }
        
        // Create DSU with grid cells + 2 virtual nodes (top and bottom)
        int size = row * col;
        int top = size;          // Virtual node representing top row
        int bottom = size + 1;   // Virtual node representing bottom row
        DSU dsu = new DSU(size + 2);
        
        // Traverse backwards through cells array (reverse time)
        for(int day = cells.length - 1; day >= 0; day--) {
            int r = cells[day][0] - 1;  // Convert to 0-indexed
            int c = cells[day][1] - 1;
            
            // Make this cell land
            mat[r][c] = false;
            int idx1 = r * col + c;  // Convert 2D position to 1D index
            
            // Connect to top virtual node if in first row
            if(r == 0) {
                dsu.union(idx1, top);
            }
            
            // Connect to bottom virtual node if in last row
            if(r == row - 1) {
                dsu.union(idx1, bottom);
            }
            
            // Connect to adjacent land cells in 4 directions
            for(int[] dir : DIRS) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                // Check bounds and if neighbor is land
                if(nr >= 0 && nr < row && nc >= 0 && nc < col && !mat[nr][nc]) {
                    int idx2 = nr * col + nc;
                    dsu.union(idx1, idx2);
                }
            }
            
            // Check if top and bottom virtual nodes are connected
            if(dsu.find(top) == dsu.find(bottom)) {
                return day;  // This is the last day we can cross
            }
        }
        
        return 0;  // Should never reach here given constraints
    }
    
    // Disjoint Set Union (Union-Find) data structure
    private static class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // Initially, each node is its own parent
                rank[i] = 0;
            }
        }

        // Find with path compression
        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]); // Path compression optimization
            return parent[x];
        }

        // Union by rank
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px == py) return;  // Already in same set

            // Union by rank: attach smaller tree under larger tree
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
}

/*
 ------------------------------------------------------------
 🔍 Example Dry Run:

Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]

Initial Setup:
- Grid (all water): [[true, true], [true, true]]
- DSU size: 4 cells + 2 virtual nodes = 6
- Virtual top node: index 4
- Virtual bottom node: index 5

Cell indexing (2D to 1D):
[0,0] → 0, [0,1] → 1
[1,0] → 2, [1,1] → 3

Backward iteration:

Day 3 (cells[3] = [2,2]):
- Convert cell [1,1] to land (index 3)
- r=1 (last row) → union(3, 5) [bottom virtual]
- Check neighbors: all water, no unions
- find(4) ≠ find(5) → no path yet

Day 2 (cells[2] = [1,2]):
- Convert cell [0,1] to land (index 1)
- r=0 (first row) → union(1, 4) [top virtual]
- Check neighbors: [1,1] is land → union(1, 3)
- Now: top(4)→1→3→bottom(5)
- find(4) == find(5) ✓ → PATH EXISTS!
- Return day = 2

Result: 2 ✅

Explanation:
After day 2, grid looks like:
[[water, land],
 [water, land]]

Path exists: top → [0,1] → [1,1] → bottom

💡 Note: The reverse time approach is brilliant because it converts the problem
from "when does connectivity break" (hard) to "when does connectivity form" (easy).

⚡ Performance Analysis:
This Union-Find solution efficiently handles the maximum constraints:
- Grid size up to 2*10^4 × 2*10^4 (400 million cells theoretical, but product limited to 2*10^4)
- With path compression and union by rank, each operation is nearly O(1)
- Total complexity: O(row * col * α(n)) where α is inverse Ackermann (< 5 for practical inputs)
- Much faster than binary search + BFS which would be O(row * col * log(row * col) * row * col)
- Scales extremely well even for maximum constraints
 ------------------------------------------------------------
*/