package Graphs;

/*
 🔹 Problem: 2092. Find All People With Secret
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Union-Find, Graph, Sorting by Time
 🔹 Link: https://leetcode.com/problems/find-all-people-with-secret/

 ------------------------------------------------------------
 📝 Problem Statement:

There are n people labeled 0 to n-1.
Person 0 knows a secret and shares it with firstPerson at time 0.

You are given meetings[i] = [xi, yi, timei].
At timei, xi and yi meet and if either knows the secret at that time,
they share it instantly.

People may attend multiple meetings at the same time.
A person who learns the secret at time t can immediately share it
in other meetings also at time t.

Return all people who know the secret after all meetings.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
n = 6
meetings = [[1,2,5],[2,3,8],[1,5,10]]
firstPerson = 1
Output = [0,1,2,3,5]

Example 2:
n = 4
meetings = [[3,1,3],[1,2,2],[0,3,3]]
firstPerson = 3
Output = [0,1,3]

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ n ≤ 100,000
 • 1 ≤ meetings.length ≤ 100,000
 • 1 ≤ time ≤ 100,000
 • Person 0 initially knows the secret

 ------------------------------------------------------------
 📌 Approach Summary:

📍 **Approach 1 (Commented – Graph + Priority Queue)**
   - Treat secret spread like time-aware BFS / Dijkstra.
   - Works but heavier due to PQ and graph traversal.

📍 **Approach 2 (✅ Union-Find by Time – Most Optimal)**

Key Insight:
- Meetings at the same time form temporary connected components.
- If ANYONE in a component knows the secret at that time,
  then EVERYONE in that component learns it.
- But connections must be RESET after each time slot.

Steps:
1. Group meetings by time.
2. For each time:
   - Union all participants of meetings at that time.
   - If a component is NOT connected to person 0,
     reset those nodes (disconnect them).
3. At the end, all nodes connected to 0 know the secret.

This guarantees correct instant propagation within the same time frame.

 ------------------------------------------------------------
 🔹 Approach 1 (Commented – PQ + Graph)
   ⏱️ Time Complexity: O((n + m) log n)
   💾 Space Complexity: O(n + m)
 ------------------------------------------------------------
*/

/*
class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        List<Integer> ans = new ArrayList<>();
        List<int[]>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for(int[] m : meetings){
            graph[m[0]].add(new int[]{m[1], m[2]});
            graph[m[1]].add(new int[]{m[0], m[2]});
        }

        boolean[] known = new boolean[n];
        known[0] = true;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{firstPerson, 0});

        for(int[] e : graph[0]) pq.add(e);

        ans.add(0);

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int person = cur[0], time = cur[1];
            if(known[person]) continue;

            known[person] = true;
            ans.add(person);

            for(int[] e : graph[person]){
                if(e[1] >= time) pq.add(e);
            }
        }
        return ans;
    }
}
*/

/*
 ------------------------------------------------------------
 🔹 Approach 2 (✅ Union-Find by Time – Most Optimal)
   ⏱️ Time Complexity: O(n + m α(n))
   💾 Space Complexity: O(n)

   🧠 Key Insight:
   Connections exist ONLY within the same time frame.
   They must be cleared if not linked to person 0.

   💡 Why it works:
   Ensures instant propagation at same time, and prevents
   invalid propagation across different times.

 ------------------------------------------------------------
*/

import java.util.*;

public class FindAllPeopleWithSecret {

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        // Person 0 and firstPerson share secret at time 0
        parent[firstPerson] = 0;

        // Group meetings by time
        Map<Integer, List<int[]>> timeMap = new HashMap<>();
        for (int[] m : meetings) {
            timeMap.computeIfAbsent(m[2], k -> new ArrayList<>())
                   .add(new int[]{m[0], m[1]});
        }

        // Process times in increasing order
        List<Integer> times = new ArrayList<>(timeMap.keySet());
        Collections.sort(times);

        for (int time : times) {
            List<int[]> list = timeMap.get(time);

            // Union all meetings at this time
            for (int[] m : list) {
                union(m[0], m[1], parent);
            }

            // Reset nodes not connected to 0
            for (int[] m : list) {
                if (find(m[0], parent) != 0) parent[m[0]] = m[0];
                if (find(m[1], parent) != 0) parent[m[1]] = m[1];
            }
        }

        // Collect all people connected to 0
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (find(i, parent) == 0) res.add(i);
        }
        return res;
    }

    private void union(int a, int b, int[] parent) {
        int pa = find(a, parent);
        int pb = find(b, parent);
        if (pa != pb) parent[pb] = pa;
    }

    private int find(int x, int[] parent) {
        if (parent[x] != x)
            parent[x] = find(parent[x], parent);
        return parent[x];
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

meetings = [[1,2,5],[2,3,8],[1,5,10]]
firstPerson = 1

time 5: union(1,2) → connected to 0
time 8: union(2,3) → connected to 0
time10: union(1,5) → connected to 0

Result = [0,1,2,3,5]

 ------------------------------------------------------------
*/

