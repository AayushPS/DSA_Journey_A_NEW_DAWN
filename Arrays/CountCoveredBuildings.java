/*
 🔹 Problem: 3531. Count Covered Buildings
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Geometry, Hashing, Preprocessing
 🔹 Link: https://leetcode.com/problems/count-covered-buildings/

 ------------------------------------------------------------
 📝 Problem Statement:

You're given an n×n grid city and a list of building coordinates.

A building at (x, y) is **covered** if there exists:
  • at least one building strictly above  it   (same x, smaller y)
  • at least one building strictly below  it   (same x, larger y)
  • at least one building strictly left   of it (same y, smaller x)
  • at least one building strictly right  of it (same y, larger x)

Return the number of covered buildings.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]
Output: 1

Example 2:
Input: n = 3, buildings = [[1,1],[1,2],[2,1],[2,2]]
Output: 0

Example 3:
Input: n = 5, buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]
Output: 1

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ n ≤ 100,000  
 • 1 ≤ buildings.length ≤ 100,000  
 • All coordinates are unique  

 ------------------------------------------------------------
 📌 Approach Summary:

A building (x, y) is covered if:
  - In its row:     min_x < x < max_x
  - In its column:  min_y < y < max_y

We preprocess min/max for every row and every column.

📍 **Approach 1 (TreeMap-based — Correct but Suboptimal, O(n log n))**
   - Store building positions in TreeSets for each row/column.
   - Check lower() and higher() to determine existence of neighbors.
   - Works, but slower.

📍 **Approach 2 (Array min/max preprocessing — Most Optimal)**
   - For each row y:
         maxL[y] = smallest x
         maxR[y] = largest  x
   - For each column x:
         maxD[x] = smallest y
         maxU[x] = largest  y

   A building is covered if:
        maxL[y] < x < maxR[y]
        maxD[x] < y < maxU[x]

   **Time Complexity:** O(n)  
   **Space Complexity:** O(n)  
   **This is the final chosen approach.**

 ------------------------------------------------------------
 🔹 Approach 1 (Commented — TreeMap-based, O(n log n))
   ⏱️ Time: O(n log n)
   💾 Space: O(n)

   🧠 Key Insight:
      Use TreeSet to get immediate left/right/up/down neighbors.

   💡 Why not optimal:
      log factor unnecessary; can be simplified to O(n).

-------------------------------------------------------------

/*
class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, TreeSet<Integer>> row = new HashMap<>();
        Map<Integer, TreeSet<Integer>> col = new HashMap<>();

        for (int[] b : buildings) {
            int x = b[0], y = b[1];
            row.computeIfAbsent(y, k -> new TreeSet<>()).add(x);
            col.computeIfAbsent(x, k -> new TreeSet<>()).add(y);
        }

        int cnt = 0;
        for (int[] b : buildings) {
            int x = b[0], y = b[1];

            boolean left  = row.get(y).lower(x)  != null;
            boolean right = row.get(y).higher(x) != null;
            boolean down  = col.get(x).lower(y)  != null;
            boolean up    = col.get(x).higher(y) != null;

            if (left && right && up && down) cnt++;
        }
        return cnt;
    }
}
*/
/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Array Min/Max Preprocessing — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
      A building is covered iff it is strictly inside the bounding box
      of its row AND its column.

   💡 Why it works:
      We only need global min/max in each row/column, not full ordering.

 ------------------------------------------------------------
*/

import java.util.Arrays;

public class CountCoveredBuildings {

    public int countCoveredBuildings(int n, int[][] buildings) {

        int[] minRowX = new int[n + 1];
        int[] maxRowX = new int[n + 1];
        int[] minColY = new int[n + 1];
        int[] maxColY = new int[n + 1];

        // Initialize min arrays
        Arrays.fill(minRowX, Integer.MAX_VALUE);
        Arrays.fill(minColY, Integer.MAX_VALUE);

        // Process buildings
        for (int[] b : buildings) {
            int x = b[0];
            int y = b[1];

            minRowX[y] = Math.min(minRowX[y], x);
            maxRowX[y] = Math.max(maxRowX[y], x);

            minColY[x] = Math.min(minColY[x], y);
            maxColY[x] = Math.max(maxColY[x], y);
        }

        int count = 0;
        for (int[] b : buildings) {
            int x = b[0];
            int y = b[1];

            if (minRowX[y] < x && x < maxRowX[y] &&
                minColY[x] < y && y < maxColY[x]) {
                count++;
            }
        }

        return count;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]):

Row 2: minX = 1, maxX = 3
Col 2: minY = 1, maxY = 3

Check (2,2):
  1 < 2 < 3  AND  1 < 2 < 3 → covered

Answer = 1

 ------------------------------------------------------------
*/
