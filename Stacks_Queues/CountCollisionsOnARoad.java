package Stacks_Queues;

/*
 🔹 Problem: 2211. Count Collisions on a Road
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Simulation, Greedy, String Processing
 🔹 Link: https://leetcode.com/problems/count-collisions-on-a-road/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given a string `directions` where:
 • 'L' → car moving left
 • 'R' → car moving right
 • 'S' → stationary car

Rules:
 • R → L collision = +2 collisions  
 • R → S collision = +1  
 • S → L collision = +1  
 • After colliding, cars stop moving forever.

Return total collisions.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: "RLRSLL"
Output: 5

Example 2:
Input: "LLRR"
Output: 0

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ directions.length ≤ 10⁵  
 • characters ∈ {L, R, S}

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Count how many collisions happen as cars stop when they hit another car.

📍 **Approach 1 (Stack Simulation — Suboptimal but Valid):**
   - Use a stack to track moving cars and simulate every collision.
   - Full simulation of R…L, R…S, and L…S interactions.
   - Correct but overly complicated and slower than needed.

📍 **Approach 2 (Count R then L collisions — Incorrect in several patterns):**
   - Attempts to count R-blocked and L-blocked collisions separately.
   - Overcounts in some mixed sequences.
   - Not reliable for the problem.

📍 **Approach 3 (Trim Ends + Count Non-S — Most Optimal):**
   - Leading 'L's never collide (they move freely left).
   - Trailing 'R's never collide (they move freely right).
   - Everything in the middle becomes blocked.
   - Every non-'S' in the central segment must collide at some point.
   - Count those characters for total collisions.

   **Time Complexity:** O(n)  
   **Space Complexity:** O(1)  
   **This is the chosen final approach.**

 ------------------------------------------------------------
 🔹 Approach 1 (Commented — Stack Simulation)
   ⏱️ Time: O(n)
   💾 Space: O(n)

   🧠 Key Insight:
   Explicitly model collisions using a stack of active cars.

   💡 Why it works:
   Simulates the definition directly but is unnecessarily complex.

-------------------------------------------------------------

/*
class Solution {
    public int countCollisions(String directions) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        int ans = 0;
        for(char c: directions.toCharArray()){
            boolean destroyed = false;
            if(!stack.isEmpty()){
                char peeked = stack.peekLast();
                if(peeked=='S'){
                    if(c=='L'){
                        destroyed = true;
                        ans++;
                    }
                }else if(peeked=='R'){
                    if(c=='L'){
                        stack.pollLast();
                        c='S';
                        ans+=2;
                        peeked = stack.isEmpty() ? '\0' : stack.peekLast(); 
                    }
                    if(c=='S'){
                        while(!stack.isEmpty() && stack.peekLast()=='R'){
                            stack.pollLast();
                            ans++;
                        }
                    }
                }
            }
            if(!destroyed){
                stack.addLast(c);
            }
        }
        return ans;
    }
}


 ------------------------------------------------------------
 🔹 Approach 2 (Commented — R/L Counters, Not Fully Correct)
   ⏱️ Time: O(n)
   💾 Space: O(1)

   🧠 Key Insight:
   Count collisions based on sequences of R and L separately.

   💡 Why it fails:
   Overcounts mixed R-S-L and R-L-S patterns, misses chained stops.

-------------------------------------------------------------


class Solution {
    public int countCollisions(String directions) {
        int r = 0, l = 0, ans = 0, n = directions.length();
        for(int i = 0; i < n; i++){
            if(directions.charAt(i) == 'R') r++;
            else { ans += r; r = 0; }
        }
        for(int i = n - 1; i >= 0; i--){
            if(directions.charAt(i) == 'L') l++;
            else { ans += l; l = 0; }
        }
        return ans;
    }
}
*/
/*
 ------------------------------------------------------------
 🔹 Approach 3 (✅ Trim Ends + Count Remaining Non-S — Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(1)

   🧠 Key Insight:
   The only cars that **never** collide are:
     ➜ prefix of 'L'  
     ➜ suffix of 'R'

   All other cars are trapped and must collide.

   💡 Why it works:
   After trimming both ends, every 'L' or 'R' in the middle will eventually hit
   something stationary or opposite-moving.

 ------------------------------------------------------------
*/

public class CountCollisionsOnARoad {

    public int countCollisions(String directions) {
        int n = directions.length();
        int l = 0, r = n - 1;

        // skip leading L-cars (they move freely left)
        while (l < n && directions.charAt(l) == 'L') l++;

        // skip trailing R-cars (they move freely right)
        while (r >= 0 && directions.charAt(r) == 'R') r--;

        int ans = 0;

        // all cars inside [l, r] will collide except stationary cars
        for (int i = l; i <= r; i++) {
            if (directions.charAt(i) != 'S') ans++;
        }

        return ans;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

directions = "RLRSLL"

Trim:
- none at left (starts with R)
- none at right (ends with L)

Middle segment = entire string

Characters ≠ 'S' = R,L,R,L,L → 5

Answer = 5

 ------------------------------------------------------------
*/
