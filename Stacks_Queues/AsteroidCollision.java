package Stacks_Queues;
/*
 🔹 Problem: 735. Asteroid Collision
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Stack, Simulation
 🔹 Link: https://leetcode.com/problems/asteroid-collision/

 ------------------------------------------------------------
 📝 Problem Statement:

You are given an array `asteroids`, where:
 - `abs(asteroids[i])` = size of asteroid  
 - `sign(asteroids[i])` = direction  
       * positive → moving right  
       * negative → moving left  

All asteroids move at the same speed.

Collision Rules:
 • Asteroids moving in the same direction never meet.
 • When a right-moving asteroid meets a left-moving asteroid:
      - Smaller one explodes  
      - If equal size → both explode  
      - Larger one survives and continues moving  

Return the state of asteroids after all collisions.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input: [5,10,-5]
Output: [5,10]

Example 2:
Input: [8,-8]
Output: []

Example 3:
Input: [10,2,-5]
Output: [10]

Example 4:
Input: [3,5,-6,2,-1,4]
Output: [-6,2,4]

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 2 ≤ asteroids.length ≤ 10⁴  
 • -1000 ≤ asteroids[i] ≤ 1000  
 • asteroids[i] ≠ 0

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Simulate asteroid movement and collisions efficiently.

📍 **Approach 1 (Stack Simulation - Most Optimal):**
   - Use a stack to maintain asteroids moving right.
   - For each left-moving asteroid, repeatedly check for collisions with stack top.
   - Resolve collisions based on size comparison.
   - Only append asteroid when it survives all potential collisions.

   **Why optimal?**
   - Each asteroid enters and leaves the stack at most once.
   - No need for repeated array shifts or brute-force simulations.

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Stack Simulation - Most Optimal)
   - Time Complexity: O(n)
   - Space Complexity: O(n)

   🧠 Key Insight:
   Collisions only occur when a right-moving asteroid meets a left-moving one.
   A stack naturally models the "frontier" of right-moving asteroids.

   💡 Why it works:
   - Linear processing with constant-time collision handling.
   - Perfectly matches the sequence-based collision mechanics.
 ------------------------------------------------------------
*/

import java.util.ArrayDeque;

public class AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int asteroid : asteroids) {
            boolean destroyed = false;

            // Collision only if last > 0 and current < 0
            while (!stack.isEmpty() && asteroid < 0 && stack.peekLast() > 0) {
                int top = stack.peekLast();

                if (top < -asteroid) {
                    stack.pollLast(); // right asteroid explodes
                } else if (top == -asteroid) {
                    stack.pollLast(); // both explode
                    destroyed = true;
                    break;
                } else {
                    destroyed = true; // left asteroid explodes
                    break;
                }
            }

            if (!destroyed) {
                stack.addLast(asteroid);
            }
        }

        return stack.stream().mapToInt(Integer::intValue).toArray();
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

Input: [10,2,-5]

Stack = []

Push 10 → [10]
Push 2  → [10,2]
Next = -5

Collision with 2:
   2 < 5 → pop → [10]
Collision with 10:
   10 > 5 → -5 destroyed

Final stack → [10]

 ------------------------------------------------------------
*/
