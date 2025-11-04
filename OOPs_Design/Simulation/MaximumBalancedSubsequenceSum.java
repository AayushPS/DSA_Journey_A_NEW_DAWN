/*
───────────────────────────────────────────────────────────────
📘 Problem: 3318. Find X-Sum of All K-Long Subarrays I
💡 Difficulty: Easy
🧠 Topics: Sliding Window, HashMap, Priority Queue, Frequency Counting
🔗 Link: https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-i/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are given an array `nums` of `n` integers and two integers `k` and `x`.

The **x-sum** of an array is calculated by:
1️⃣ Counting occurrences of all elements.
2️⃣ Keeping only the **top x most frequent** elements.
   - If two elements have the same frequency, the **larger value** is considered more frequent.
3️⃣ The x-sum is the sum of the resulting array after applying these rules.

You must return an array `answer` of length `n - k + 1`,
where `answer[i]` is the x-sum of the subarray `nums[i..i + k - 1]`.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ n == nums.length ≤ 50  
• 1 ≤ nums[i] ≤ 50  
• 1 ≤ x ≤ k ≤ n
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach — Sliding Window + Frequency Counting + Priority Queue
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

We need to compute the X-sum for every contiguous subarray of length `k`.

For each subarray:
1️⃣ Count the frequency of each number.
2️⃣ Keep only the top `x` elements — based on:
   • Higher frequency first.
   • If tie → higher value preferred.
3️⃣ Compute `sum = Σ(freq * num)` for these top elements.

To slide the window efficiently:
- Maintain a frequency array `freq[51]` (since nums[i] ≤ 50).
- Use a `PriorityQueue` (min-heap) of pairs [frequency, value],
  sorted by frequency first, then value.

At each step:
- Remove frequency of outgoing element.
- Add frequency of new incoming element.
- Rebuild heap for the new window and compute new sum.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time: O(n * (m log x)) → where m = 50 (bounded)
  ≈ O(n * log x) due to small constant range.
• Space: O(51) → constant extra space.

───────────────────────────────────────────────────────────────
✅ Works efficiently due to small constraints.
───────────────────────────────────────────────────────────────
*/

import java.util.*;

class Solution {
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] freq = new int[51];

        // Step 1️⃣ Initialize frequency for first window of size k
        for (int i = 0; i < k; i++) {
            freq[nums[i]]++;
        }

        // Step 2️⃣ Define comparator for PriorityQueue (min-heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                // Sort by frequency, then by value
                int c = a[0] - b[0];
                if (c == 0) c = a[1] - b[1];
                return c;
            }
        });

        // Step 3️⃣ Compute x-sum for the first window
        for (int i = 0; i < 51; i++) {
            if (freq[i] != 0) {
                pq.add(new int[]{freq[i], i});
                if (pq.size() > x) pq.poll(); // keep only top x
            }
        }

        int sum = 0;
        for (int[] el : pq) sum += el[0] * el[1];

        int[] res = new int[n - k + 1];
        res[0] = sum;
        int idx = 1;

        // Step 4️⃣ Slide the window across the array
        for (int j = k; j < n; j++) {
            int out = nums[j - k];
            int in = nums[j];

            // Update frequencies
            freq[out]--;
            freq[in]++;

            // Rebuild the heap for the new window
            pq.clear();
            for (int i = 0; i < 51; i++) {
                if (freq[i] != 0) {
                    pq.add(new int[]{freq[i], i});
                    if (pq.size() > x) pq.poll();
                }
            }

            // Compute x-sum for current window
            sum = 0;
            for (int[] el : pq) sum += el[0] * el[1];
            res[idx++] = sum;
        }

        return res;
    }
}


/*
───────────────────────────────────────────────────────────────
🧮 Example Walkthrough:
───────────────────────────────────────────────────────────────
Input:
nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

Window 1: [1,1,2,2,3,4]
  - freq(1)=2, freq(2)=2, freq(3)=1, freq(4)=1
  - Top 2 → {1,2}
  - Sum = (2*1) + (2*2) = 6

Window 2: [1,2,2,3,4,2]
  - freq(2)=3, freq(4)=1, freq(3)=1, freq(1)=1
  - Top 2 → {2,4}
  - Sum = (3*2) + (1*4) = 10

Window 3: [2,2,3,4,2,3]
  - freq(2)=3, freq(3)=2, freq(4)=1
  - Top 2 → {2,3}
  - Sum = (3*2) + (2*3) = 12

Output: [6, 10, 12]
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
• Time:  O(n * log x)
• Space: O(51) ≈ O(1)
───────────────────────────────────────────────────────────────
✅ Optimal for small constraints (n ≤ 50, nums[i] ≤ 50)
───────────────────────────────────────────────────────────────
*/
