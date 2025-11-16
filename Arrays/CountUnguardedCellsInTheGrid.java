/*
───────────────────────────────────────────────────────────────
📘 Problem: 2257. Count Unguarded Cells in the Grid
💡 Difficulty: Medium
🧠 Topics: Matrix, Simulation, Directional Traversal
🔗 Link: https://leetcode.com/problems/count-unguarded-cells-in-the-grid/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are given two integers m and n representing a 0-indexed m x n grid.
You are also given two 2D integer arrays guards and walls where:
- guards[i] = [rowi, coli] represents the position of the ith guard
- walls[j] = [rowj, colj] represents the position of the jth wall

A guard can see every cell in the four cardinal directions (north, east, south, or west)
starting from their position unless obstructed by a wall or another guard.
A cell is guarded if there is at least one guard that can see it.

Return the number of unoccupied cells that are not guarded.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 <= m, n <= 10^5
• 2 <= m * n <= 10^5
• 1 <= guards.length, walls.length <= 5 * 10^4
• 2 <= guards.length + walls.length <= m * n
• All the positions in guards and walls are unique
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 1 — Matrix Simulation with Directional Traversal
───────────────────────────────────────────────────────────────
💡 Idea:
Simulate each guard's vision by traversing in four directions until hitting a wall or guard.

Algorithm:
1. Mark all walls and guards as "blocked" positions
2. For each guard, simulate vision in 4 directions (up, down, left, right):
   - Keep moving in each direction until:
     * Out of grid bounds, OR
     * Hits a blocked cell (wall or guard)
   - Mark all cells visited during this traversal as "guarded"
3. Count all unoccupied, unguarded cells

Why it works:
- Guards can see infinitely far until obstructed
- We mark all cells visible to each guard
- Final count excludes: guards, walls, and guarded cells

🧩 Edge cases:
- Guards on boundaries (vision still extends in valid directions)
- Walls blocking guard vision completely
- Empty cells near guards

🧮 Complexity:
• Time: O(m * n + G * (m + n)) where G = number of guards
  - Grid initialization: O(m * n)
  - Each guard scans in 4 directions with worst case O(m + n)
• Space: O(m * n) for visited/blocked matrices

✅ Clean, intuitive, and efficient for given constraints.
*/

public class CountUnguardedCellsInTheGrid {
    private static final int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        boolean[][] visited = new boolean[m][n]; // tracks guarded cells
        boolean[][] blocked = new boolean[m][n]; // tracks walls + guards
        
        // Mark blocked positions (walls and guards)
        for (int[] w : walls) blocked[w[0]][w[1]] = true;
        for (int[] g : guards) blocked[g[0]][g[1]] = true;
        
        // Simulate each guard's vision
        for (int[] g : guards) {
            int x = g[0], y = g[1];
            visited[x][y] = true; // guard position is guarded
            
            // Explore all 4 directions
            for (int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];
                
                // Keep moving in this direction until blocked or out of bounds
                while (nx >= 0 && nx < m && ny >= 0 && ny < n && !blocked[nx][ny]) {
                    visited[nx][ny] = true;
                    nx += d[0];
                    ny += d[1];
                }
            }
        }
        
        // Count unguarded, unoccupied cells
        int unguarded = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && !blocked[i][j]) {
                    unguarded++;
                }
            }
        }
        
        return unguarded;
    }
}

/*
───────────────────────────────────────────────────────────────
🧮 Example Walkthrough:
───────────────────────────────────────────────────────────────
Example 1:
m=4, n=6, guards=[[0,0],[1,1],[2,3]], walls=[[0,1],[2,2],[1,4]]

Grid visualization after simulation:
  0  1  2  3  4  5
0 G  W  G  G  G  G   (Guard at (0,0) sees row 0 left→right, blocked at (0,1))
1 G  G  G  G  W  G   (Guard at (1,1) sees all 4 directions)
2 G  G  W  G  G  G   (Guard at (2,3) sees all 4 directions, blocked at (2,2))
3 G  G  U  G  G  U   (U = unguarded cells)

Unguarded cells: (3,2), (3,5)
Total: 7 unguarded cells

Example 2:
m=3, n=3, guards=[[1,1]], walls=[[0,1],[1,0],[2,1],[1,2]]

Grid:
  0  1  2
0 G  W  G   (Guard at (1,1) blocked in all directions)
1 W  G  W   
2 G  W  U   (U = unguarded)

Unguarded: (2,2)
Total: 4 unguarded cells
───────────────────────────────────────────────────────────────
*/

