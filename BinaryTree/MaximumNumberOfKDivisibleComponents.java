package BinaryTree;

import java.util.ArrayList;
import java.util.List;

/*
 🔹 Problem: 2872. Maximum Number of K-Divisible Components
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Tree DP, DFS, Modular Arithmetic
 🔹 Link: https://leetcode.com/problems/maximum-number-of-k-divisible-components/

 ------------------------------------------------------------
 🔸 Problem:

Given an undirected tree with n nodes and an integer array values,  
the task is to remove any set of edges such that every resulting  
connected component has a total node-value sum divisible by k.

Return the maximum possible number of connected components.

 ------------------------------------------------------------
 🔸 Examples:

Example 1:
n = 5  
edges = [[0,2],[1,2],[1,3],[2,4]]  
values = [1,8,1,4,4], k = 6  
Output: 2

Example 2:
n = 7  
edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]]  
values = [3,0,6,1,5,2,1], k = 3  
Output: 3

 ------------------------------------------------------------
 🔸 Constraints:

• 1 <= n <= 3×10⁴  
• edges.length = n − 1  
• 0 <= values[i] <= 1e9  
• 1 <= k <= 1e9  
• The total sum of all node values is divisible by k  
• edges describes a valid tree  

 ------------------------------------------------------------
 🔹 Approach 1: Leaf-Stripping Using Accumulated Sums (Commented)

This method processes the tree by peeling leaves iteratively:

• Initialize all leaves (nodes with degree 1) in a queue.  
• Maintain subtree sums for each node.  
• When a leaf is removed:
      – If its accumulated sum is divisible by k, it becomes a separate component.
      – Otherwise, its accumulated value is passed up to its parent.
• Remove the leaf from the tree, reduce degrees, and enqueue new leaves.

Characteristics:
• Each edge is removed exactly once.  
• Subtree sums propagate upward when they cannot form divisible components.  

Complexity:
• Time:  O(n)  
• Space: O(n)

 ------------------------------------------------------------

/*
public class MaximumNumberOfKDivisibleComponents {
    private static class Node {
        int idx;
        Set<Integer> edges = new HashSet<>();
        Node(int idx) { this.idx = idx; }
        void add(int e) { edges.add(e); }
    }

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        if (n == 1)
            return values[0] % k == 0 ? 1 : 0;

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node(i);

        for (int[] e : edges) {
            nodes[e[0]].add(e[1]);
            nodes[e[1]].add(e[0]);
        }

        Deque<Node> queue = new ArrayDeque<>();
        for (Node node : nodes)
            if (node.edges.size() == 1)
                queue.addLast(node);

        long[] sums = new long[n];
        for (int i = 0; i < n; i++)
            sums[i] = values[i];

        int count = 0;

        while (!queue.isEmpty()) {
            Node leaf = queue.pollFirst();
            int idx = leaf.idx;
            long val = sums[idx];

            if (val % k == 0) {
                count++;
            } else {
                for (int parent : leaf.edges)
                    sums[parent] += val;
            }

            for (int parent : leaf.edges) {
                nodes[parent].edges.remove(idx);
                if (nodes[parent].edges.size() == 1)
                    queue.addLast(nodes[parent]);
            }
        }

        return count;
    }
}
*/




/*
 ------------------------------------------------------------
 🔹 Approach 2: DFS Tree DP (Final Working Code)

This method performs a DFS to compute subtree sums:

• Root the tree at an arbitrary node (0).  
• For each node:
      – Accumulate its value with sums returned from children.
• If a subtree sum is divisible by k:
      – Count it as a separate component.
      – Return 0 upward to avoid merging it with the parent.
• Otherwise:
      – Return the subtotal to be merged into the parent’s computation.

This ensures each subtree divisible by k forms an independent component.

Complexity:
• Time:  O(n)  
• Space: O(n)

 ------------------------------------------------------------
*/

public class MaximumNumberOfKDivisibleComponents {
    private int count = 0;

    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] graph =new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        dfs(0, -1, graph, values, k);
        return count;
    }

    private long dfs(int node, int parent, List<Integer>[] graph, int[] values, int k) {
        long subtotal = values[node];

        for (int child : graph[node]) {
            if (child != parent)
                subtotal += dfs(child, node, graph, values, k);
        }

        if (subtotal % k == 0) {
            count++;
            return 0;
        }

        return subtotal;
    }
}

/*
 ------------------------------------------------------------
 🔹 End of File
 ------------------------------------------------------------
*/
