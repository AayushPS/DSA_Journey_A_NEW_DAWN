package Heaps;
/*
 🔹 Problem: 2054. Two Best Non-Overlapping Events
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Greedy, Sorting, Priority Queue
 🔹 Link: https://leetcode.com/problems/two-best-non-overlapping-events/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a list of events, where each event has:
[startTime, endTime, value].

You may attend at most two non-overlapping events.
Events are inclusive on both ends, so if one event ends at time t,
the next event must start at t + 1 or later.

Return the maximum sum of values obtainable.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [[1,3,2],[4,5,2],[2,4,3]]
Output: 4

Example 2:
Input: [[1,3,2],[4,5,2],[1,5,5]]
Output: 5

Example 3:
Input: [[1,5,3],[1,5,1],[6,6,5]]
Output: 8

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 <= events.length <= 100000
 • 1 <= startTime <= endTime <= 1e9
 • 1 <= value <= 1e6

 ------------------------------------------------------------
 📌 Approach Summary:

✅ Goal:
Pick at most two non-overlapping events maximizing total value.

📍 Approach 1: Recursion + DP + Binary Search
- Sort events by start time
- Try take / skip recursively
- Use binary search to find next valid event
- Works but too heavy

📍 Approach 2 (✅ Optimized): Greedy + Priority Queue
- Sort events by start time
- Use a min-heap by end time
- Track best value of completed events
- Combine current event with best past event

Why optimal:
- Time: O(n log n)
- Space: O(n)
- Single pass after sorting
 ------------------------------------------------------------
 🔹 Approach 1 (Commented - DP + Binary Search)
   ⏱️ Time Complexity: O(n log n)
   💾 Space Complexity: O(n)
 ------------------------------------------------------------

 // class Solution {
 //     public int maxTwoEvents(int[][] events) {
 //         int n = events.length;
 //         Arrays.sort(events, (a, b) -> a[0] - b[0]);
 //         int[][] dp = new int[n][2];
 //         for (int[] row : dp) Arrays.fill(row, -1);
 //         return dfs(0, 0, events, dp);
 //     }
 //
 //     private int dfs(int i, int count, int[][] events, int[][] dp) {
 //         if (i == events.length || count == 2) return 0;
 //         if (dp[i][count] != -1) return dp[i][count];
 //
 //         int end = events[i][1];
 //         int next = i + 1;
 //         while (next < events.length && events[next][0] <= end) next++;
 //
 //         int take = events[i][2] + dfs(next, count + 1, events, dp);
 //         int skip = dfs(i + 1, count, events, dp);
 //
 //         return dp[i][count] = Math.max(take, skip);
 //     }
 // }

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Greedy + Priority Queue - Most Optimal)
   ⏱️ Time Complexity: O(n log n)
   💾 Space Complexity: O(n)
 ------------------------------------------------------------
*/

import java.util.*;

public class TwoBestNonOverlappingEvents {

    public int maxTwoEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> a[0] - b[0]);

        PriorityQueue<int[]> pq =
            new PriorityQueue<>(Comparator.comparingInt(e -> e[1]));

        int maxValSoFar = 0;
        int answer = 0;

        for (int[] event : events) {

            while (!pq.isEmpty() && pq.peek()[1] < event[0]) {
                maxValSoFar = Math.max(maxValSoFar, pq.poll()[2]);
            }

            answer = Math.max(answer, maxValSoFar + event[2]);
            pq.add(event);
        }

        return answer;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

events = [[1,3,2],[4,5,2],[2,4,3]]

Sorted by start:
[1,3,2], [2,4,3], [4,5,2]

Best combination:
[1,3,2] + [4,5,2] = 4 ✅
 ------------------------------------------------------------
*/
