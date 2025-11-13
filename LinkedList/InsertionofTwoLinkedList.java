/*
───────────────────────────────────────────────────────────────
📘 Problem: 160. Intersection of Two Linked Lists
💡 Difficulty: Easy
🧠 Topics: Linked List, Two Pointers
🔗 Link: https://leetcode.com/problems/intersection-of-two-linked-lists/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

Given the heads of two singly linked lists `headA` and `headB`, return the node
where the two lists intersect.  
If the lists do **not** intersect, return `null`.

Intersection means the two lists share **the same node reference**, not just value.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 ≤ m, n ≤ 3 × 10⁴  
• 1 ≤ Node.val ≤ 10⁵  
• No cycles in the lists  
• intersectVal = 0 if no intersection  
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach — Two-Pointer Sync (Best O(m+n) / O(1) Solution)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

We use the famous **pointer switching technique**:

1️⃣ Create two pointers:
    - `a` starting at headA
    - `b` starting at headB

2️⃣ Move both forward one step at a time.

3️⃣ When a pointer reaches the end:
    → redirect it to the **other** list’s head.

4️⃣ After at most `m + n` steps:
    - Either both meet at the intersection node.
    - Or both become null (no intersection).

Why this works:
- Both pointers travel exactly `m + n` steps.
- If there's an intersection, they sync up exactly at that node.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• Time:  O(m + n)  
• Space: O(1)  
───────────────────────────────────────────────────────────────
🔥 Cleanest, simplest, and optimal solution.
───────────────────────────────────────────────────────────────
*/


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        // If either list is empty → no intersection
        if (headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        // Both will traverse m+n length and meet at intersection or at null
        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }

        return a; // either intersection node or null
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Example Visualization:
───────────────────────────────────────────────────────────────
ListA: 4 → 1 → 8 → 4 → 5
ListB:     5 → 6 → 1 → 8 → 4 → 5

Pointers travel:
A: A1 → A2 → A3 → ... → null → B1 → B2 → ...
B: B1 → B2 → B3 → ... → null → A1 → A2 → ...

Eventually both pointers land on the SAME node (8).

───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Time:  O(m + n)
Space: O(1)

✔ Best possible solution  
✔ Uses no extra space  
✔ Elegant pointer sync trick  
───────────────────────────────────────────────────────────────
*/
