package DynamicProgramming.KnapSack_and_Variants;
/*
 🔹 Problem: 3562. Maximum Profit from Trading Stocks with Discounts
 🔹 Platform: LeetCode
 🔹 Difficulty: Hard
 🔹 Topics: Tree DP, Knapsack DP, DFS
 🔹 Link: https://leetcode.com/problems/maximum-profit-from-trading-stocks-with-discounts/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given:
 • n employees arranged in a hierarchy tree (employee 1 is CEO).
 • present[i]: price to buy stock of employee i today.
 • future[i]: price to sell stock of employee i tomorrow.
 • hierarchy edges defining boss → employee.
 • a limited investment budget.

Rules:
 • Each stock can be bought at most once.
 • If a boss buys their stock, direct subordinates get a 50% discount
   (floor division) on their buy price.
 • Profit from selling cannot be reinvested.
 • Goal: maximize total profit without exceeding budget.

 ------------------------------------------------------------
 📊 Examples:

Example:
n = 2
present = [1,2]
future  = [4,3]
hierarchy = [[1,2]]
budget = 3
Output = 5

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ n ≤ 160
 • 1 ≤ present[i], future[i] ≤ 50
 • 1 ≤ budget ≤ 160
 • hierarchy forms a tree rooted at employee 1

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Maximize profit under budget with hierarchical discount dependency.

📍 **Approach (Tree DP + Knapsack DP — Most Optimal)**

Key Observations:
 • Employee discount depends on whether their boss buys.
 • Hierarchy is a tree → natural DFS DP.
 • Budget is small (≤160) → knapsack DP feasible.

DP Definition:
For each node u:
   dp[b][0] → max profit using b budget, if u's parent did NOT buy
   dp[b][1] → max profit using b budget, if u's parent DID buy

Steps:
1. DFS from root (employee 1).
2. Merge children DP using knapsack convolution.
3. Decide:
   - Not buy u
   - Buy u at full price
   - Buy u at discounted price (if allowed)
4. Return dp table to parent.

Final Answer:
   dp_root[budget][0]

 ------------------------------------------------------------
 🔹 Approach (✅ Tree DP + Knapsack — Most Optimal)
   - Time Complexity: O(n × budget²)
   - Space Complexity: O(n × budget)

   🧠 Key Insight:
      Discount dependency converts problem into a 2-state DP per node.

   💡 Why it works:
      Small constraints allow full knapsack DP per subtree.

 ------------------------------------------------------------
*/
import java.util.ArrayList;
import java.util.List;

public class MaximumProfitFromTradingStocksWithDiscounts {

    List<Integer>[] tree;
    int[] buyPrice, sellPrice;
    int budget;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {

        this.buyPrice = present;
        this.sellPrice = future;
        this.budget = budget;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();

        for (int[] h : hierarchy) {
            int boss = h[0] - 1;
            int emp  = h[1] - 1;
            tree[boss].add(emp);
        }

        int[][] dp = dfs(0);
        return dp[budget][0];
    }

    // Returns dp[b][0/1] for subtree rooted at u
    private int[][] dfs(int u) {

        int[][] dp = new int[budget + 1][2];

        // Merge children
        for (int v : tree[u]) {
            int[][] child = dfs(v);
            int[][] next = new int[budget + 1][2];

            for (int b = 0; b <= budget; b++) {
                for (int cb = 0; cb <= b; cb++) {
                    next[b][0] = Math.max(next[b][0],
                            dp[b - cb][0] + child[cb][0]);
                    next[b][1] = Math.max(next[b][1],
                            dp[b - cb][1] + child[cb][1]);
                }
            }
            dp = next;
        }

        int[][] ans = new int[budget + 1][2];

        for (int b = 0; b <= budget; b++) {

            // Parent did NOT buy → u has no discount
            ans[b][0] = dp[b][0];
            if (b >= buyPrice[u]) {
                ans[b][0] = Math.max(
                        ans[b][0],
                        sellPrice[u] - buyPrice[u] + dp[b - buyPrice[u]][1]
                );
            }

            // Parent DID buy → u gets discount
            int discounted = buyPrice[u] / 2;
            ans[b][1] = dp[b][0];
            if (b >= discounted) {
                ans[b][1] = Math.max(
                        ans[b][1],
                        sellPrice[u] - discounted + dp[b - discounted][1]
                );
            }
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (Example 4):

Hierarchy: 1 → 2 → 3
present = [5,2,3]
future  = [8,5,6]
budget = 7

Employee 1 buys → unlocks discount for 2
Employee 2 buys → unlocks discount for 3
Total cost = 5 + 1 + 1 = 7
Total profit = 3 + 4 + 5 = 12

 ------------------------------------------------------------
*/
