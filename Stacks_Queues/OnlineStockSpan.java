package Stacks_Queues;

import java.util.ArrayDeque;

/*
 🔹 Problem: 901. Online Stock Span
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Stack, Monotonic Stack, Design
 🔹 Link: https://leetcode.com/problems/online-stock-span/

 ------------------------------------------------------------
 📝 Problem Statement:

Design a class `StockSpanner` that collects daily stock prices and returns the
**span** of the price for each day.

The span of today’s price is the maximum number of consecutive previous days
(starting from today and moving backward) where the price was **less than or equal**
to today’s price.

Example:
If previous prices are [7,2,1,2] and today's price is 2 → span = 4  
If previous prices are [7,34,1,2] and today's price is 8 → span = 3  

Implement:
 - `StockSpanner()` → initializes the object  
 - `int next(int price)` → returns span for today's price  

 ------------------------------------------------------------
 📊 Examples:

Input:
["StockSpanner","next","next","next","next","next","next","next"]
[[],[100],[80],[60],[70],[60],[75],[85]]

Output:
[null,1,1,1,2,1,4,6]

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ price ≤ 100000  
 • Up to 10000 calls to next()

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Efficiently compute span in O(1) amortized time per query.

📍 **Approach 1 (Monotonic Stack - Most Optimal):**
   - Use a stack storing pairs: `[price, span]`.
   - For each new price:
       * Pop all prices ≤ current (merge their spans).
       * Push the new price with total accumulated span.
   - This ensures each element is pushed and popped at most once.

   **Time:** O(n) amortized over all calls  
   **Space:** O(n)

   **Why optimal?**
   - Avoids recomputing spans from scratch.
   - Monotonic decreasing stack compresses past data efficiently.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Monotonic Stack - Most Optimal)
   - Time Complexity: O(n) amortized
   - Space Complexity: O(n)

   🧠 Key Insight:
   When today’s price is higher than past days, those past days cannot
   affect future spans → safe to merge and discard them.

   💡 Why it works:
   Stack stores strictly decreasing prices, ensuring constant amortized work.

 ------------------------------------------------------------
*/

public class OnlineStockSpan {

    ArrayDeque<int[]> stack = new ArrayDeque<>();

    public OnlineStockSpan() {}

    public int next(int price) {
        int span = 1;

        // Merge all previous prices <= current price
        while (!stack.isEmpty() && stack.peekLast()[0] <= price) {
            span += stack.pollLast()[1];
        }

        // Push current price with its resolved span
        stack.addLast(new int[]{price, span});
        return span;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input sequence: next(100), next(80), next(60), next(70), next(60), next(75), next(85)

Stack progression (price, span):
next(100) → [(100,1)]
next(80)  → [(100,1),(80,1)]
next(60)  → [(100,1),(80,1),(60,1)]
next(70)  → pop 60 → span=2 → [(100,1),(80,1),(70,2)]
next(60)  → [(100,1),(80,1),(70,2),(60,1)]
next(75)  → pop 60, pop 70 → span=4 → [(100,1),(80,1),(75,4)]
next(85)  → pop 75, pop 80 → span=6 → [(100,1),(85,6)]

Final outputs: [1,1,1,2,1,4,6]
 ------------------------------------------------------------
*/

