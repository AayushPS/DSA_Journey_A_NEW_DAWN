/*
───────────────────────────────────────────────────────────────
📘 Problem: 3234. Count the Number of Substrings With Dominant Ones
💡 Difficulty: Medium
🧠 Topics: Strings, Math, Two-pointers, Sqrt decomposition
🔗 Link: https://leetcode.com/problems/count-the-number-of-substrings-with-dominant-ones/
───────────────────────────────────────────────────────────────

You are given a binary string s.

Return the number of substrings with dominant ones.

A substring has dominant ones if:
    (#ones in substring) >= (#zeros in substring)^2

Constraints:
• 1 ≤ s.length ≤ 4·10^4
• s consists only of '0' and '1'

───────────────────────────────────────────────────────────────
🧠 High-level intuition (why sqrt appears)

Let z = number of zeros in a substring.
The constraint is ones ≥ z^2.

If z is large (z > sqrt(n)), then z^2 > n so the inequality is impossible
(since ones ≤ n). Therefore we only need to consider z up to ~sqrt(n).

Strategy:
1. Precompute positions of zeros (Z) and n = s.length().
2. Handle z = 0 separately (pure-one substrings) — count contiguous-one blocks.
3. For z from 1 to K = floor(sqrt(n)):
   - Iterate over windows of z consecutive zeros using Z indices.
   - For each window determine:
       prevZero (index before this window) and nextZero (index after this window)
       possible start positions: (prevZero+1) .. leftZero
       possible end   positions: rightZero .. (nextZero - 1)
     For each possible start s, compute minimal end e_min = max(rightZero, s + (z^2 + z - 1))
     Add count = max(0, nextZero - e_min) for that start.
   - Efficient because z is small (≤ sqrt(n)), so total work ≈ n·sqrt(n)
4. Sum up all counts. Use long during accumulation, but final answer fits in int.

This is a standard sqrt-decomposition trick for substring counting problems.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time:  O(n · sqrt(n)) — K ≈ sqrt(n)
• Space: O(n) (positions of zeros)
───────────────────────────────────────────────────────────────
*/

import java.util.*;

public class CountSubstringsWithDominantOnes {

    public int numberOfSubstrings(String s) {
        int n = s.length();
        // Collect positions of zeros
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') zeros.add(i);
        }

        long ans = 0L;

        // Handle z = 0 (substrings with zero zeros => all ones)
        // Count contiguous blocks of ones
        for (int i = 0; i < n; ) {
            if (s.charAt(i) == '0') { i++; continue; }
            int j = i;
            while (j < n && s.charAt(j) == '1') j++;
            long len = j - i;
            ans += len * (len + 1) / 2; // number of all-ones substrings in this block
            i = j;
        }

        // If there are no zeros at all, we are done
        if (zeros.isEmpty()) {
            return (int) ans;
        }

        // Max zeros to consider
        int K = (int) Math.sqrt(n) + 1;

        int t = zeros.size();

        // For z = 1 .. K
        for (int z = 1; z <= K; z++) {
            if (z > t) break; // cannot have more zeros than exist

            // For each window of z consecutive zeros indexed by i..i+z-1 in zeros list
            for (int i = 0; i + z - 1 < t; i++) {
                int leftZero = zeros.get(i);                 // index of first zero in window
                int rightZero = zeros.get(i + z - 1);        // index of last zero in window

                int prevZero = (i == 0) ? -1 : zeros.get(i - 1);            // index before window
                int nextZero = (i + z < t) ? zeros.get(i + z) : n;          // index after window

                // start can range from (prevZero+1) .. leftZero  (inclusive)
                int startL = prevZero + 1;
                int startR = leftZero;

                // end can range from rightZero .. nextZero-1 (inclusive)
                int endL = rightZero;
                int endR = nextZero - 1;

                // minimal total length required: ones + zeros >= z^2 + z
                // Since ones = length - z  ==> length >= z^2 + z
                // For given start s, minimal end e must satisfy:
                // e >= s + (z^2 + z - 1)
                int T = z * z + z - 1;

                // iterate possible start positions (startR - startL + 1 steps)
                // total work across all windows and z <= K remains ~ O(n*K)
                for (int sStart = startL; sStart <= startR; sStart++) {
                    int minEndRequired = Math.max(endL, sStart + T);
                    if (minEndRequired <= endR) {
                        ans += (endR - minEndRequired + 1);
                    }
                }
            }
        }

        return (int) ans;
    }
}


/*
───────────────────────────────────────────────────────────────
🥈 Alternative Approach 2 — Brute force with pruning (commented)
───────────────────────────────────────────────────────────────
Idea:
- Precompute prefix ones.
- For each left index L, try increasing R and compute zeros, ones quickly.
- Use pruning: if zeros^2 grows too big, skip ranges etc.

This approach is simpler to implement but worst-case O(n^2) and may TLE on n = 4e4.

(Kept here for reference; not recommended for worst-case inputs.)
*/

/*
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int[] prefOnes = new int[n+1];
        for (int i = 0; i < n; i++) prefOnes[i+1] = prefOnes[i] + (s.charAt(i)=='1'?1:0);
        long ans = 0L;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int ones = prefOnes[j+1] - prefOnes[i];
                int len = j - i + 1;
                int zeros = len - ones;
                if (ones >= zeros * zeros) ans++;
            }
        }
        return (int) ans;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥉 Alternative Approach 3 — Sliding windows by zero count + prefix-ones binary search
───────────────────────────────────────────────────────────────
Idea:
- For each z up to K, for each i window of zeros compute start and end ranges.
- Instead of iterating every start s, you can binary search for the smallest s where s+T > right bound, or find ranges analytically, and add arithmetic sums.
- This reduces some inner-loop work at the cost of more careful indexing.
- The implementation above uses a simple loop over starts (still O(n·K)) which is simpler and fast enough.

(Left commented for clarity.)
*/

/*
class Solution {
    public int numberOfSubstrings(String s) {
        // Implementation variant with binary search / prefix sums
        // (omitted to avoid redundancy — use the main optimal method above)
        return 0;
    }
}
*/

 
/*
───────────────────────────────────────────────────────────────
🧩 Example walkthrough (tiny ASCII-like diagram)

s = "101101"   (n=6)
zeros positions Z = [1, 3]
ones-only substrings add their counts first.

K = floor(sqrt(6)) + 1 = 3

z = 1 windows:
 - window i=0: leftZero=1, rightZero=1, prev=-1, next=3
   start ∈ [0..1], end ∈ [1..2], T = 1*1 +1 -1 = 1
   For s=0: minEnd = max(1, 0+1)=1 -> ends {1,2} -> 2
   For s=1: minEnd = max(1,1+1)=2 -> ends {2} -> 1
   -> contributes 3

 - window i=1: leftZero=3,rightZero=3, prev=1,next=6
   start ∈ [2..3], end ∈ [3..5], T=1
   s=2 -> minEnd = max(3,2+1)=3 -> ends {3,4,5} -> 3
   s=3 -> minEnd = max(3,3+1)=4 -> ends {4,5} -> 2
   -> contributes 5

z = 2 windows: (if exist) ...
Sum up + ones-only substrings → final answer 16 (per example).

───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
- Time:  O(n · sqrt(n))
- Space: O(n)
───────────────────────────────────────────────────────────────
*/
