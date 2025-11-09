/*
───────────────────────────────────────────────────────────────
📘 Problem: 3217. Delete Nodes From Linked List Present in Array
💡 Difficulty: Medium
🧠 Topics: Linked List, HashSet
🔗 Link: https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

Given:
- An array `nums` of integers.
- The head of a singly linked list `head`.

Remove all nodes from the linked list whose values exist in `nums`.

Return the modified linked list’s head.

───────────────────────────────────────────────────────────────
🔒 Constraints:
• 1 <= nums.length <= 10^5  
• 1 <= nums[i], Node.val <= 10^5  
• All elements in nums are unique  
• Linked list length in [1, 10^5]  
• At least one node’s value is **not** in nums
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🥉 Approach 1 — Using HashSet + Dummy Node
───────────────────────────────────────────────────────────────
💡 Idea:
We want to efficiently delete all nodes whose values are in `nums`.

1️⃣ Put all `nums` into a `HashSet` for O(1) membership check.  
2️⃣ Use a dummy node before head to simplify edge cases (like deleting head itself).  
3️⃣ Traverse the list using a pointer `temp`:  
   - If `temp.next.val` exists in the set → skip the node (`temp.next = temp.next.next`)  
   - Else → move forward (`temp = temp.next`)  
4️⃣ Return `dummy.next` as the new head.

🧩 Why a dummy node?
If the head itself needs to be removed, you’d lose the reference otherwise.  
Using a dummy ensures stable pointer control.

🧮 Complexity:
• Time: O(n + m) → traverse list once + build set  
• Space: O(n) → HashSet storage

✅ Optimal solution for constraints.
*/

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        ListNode dummy = new ListNode(0, head);
        ListNode temp = dummy;

        while (temp != null) {
            while (temp.next != null && set.contains(temp.next.val)) {
                temp.next = temp.next.next;
            }
            temp = temp.next;
        }

        return dummy.next;
    }
}

/*
───────────────────────────────────────────────────────────────
🥈 Approach 2 — Using Recursion
───────────────────────────────────────────────────────────────
💡 Idea:
Instead of iterating, recursively rebuild the list:
- Base: if head == null → return null
- Recurse for head.next
- If current node’s value is in set → skip it (return next)
- Else → connect it (head.next = recursive call result)

🧮 Complexity:
• Time: O(n)
• Space: O(n) recursion stack → ⚠️ can cause stack overflow for long lists.

Good conceptually but not practical for 10^5 nodes.
*/

/*
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);
        return helper(head, set);
    }

    private ListNode helper(ListNode node, Set<Integer> set) {
        if (node == null) return null;
        node.next = helper(node.next, set);
        if (set.contains(node.val)) return node.next;
        return node;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🥇 Approach 3 — In-place Filtering (No Dummy Node)
───────────────────────────────────────────────────────────────
💡 Idea:
We can do it without dummy by:
- Removing leading nodes that match set
- Then, iterating through list and skipping matched nodes

⚠️ Slightly trickier due to handling `head` deletions.

🧮 Complexity:
Same O(n) time, O(1) extra space.
Less readable though.
*/

/*
class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        // Remove initial matching nodes
        while (head != null && set.contains(head.val)) {
            head = head.next;
        }

        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (set.contains(curr.next.val)) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }

        return head;
    }
}
*/

/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
Approach 1 (Dummy + Set):  
• Time: O(n + m)  
• Space: O(n)

Approach 2 (Recursive):  
• Time: O(n)  
• Space: O(n) stack

Approach 3 (No Dummy):  
• Time: O(n)  
• Space: O(n)

✅ Final Choice: Approach 1 — clean, optimal, handles all edge cases elegantly.
───────────────────────────────────────────────────────────────
*/

/*
───────────────────────────────────────────────────────────────
🧩 Example Dry Run:
───────────────────────────────────────────────────────────────
nums = [1, 2, 3]
list = [1, 2, 3, 4, 5]

HashSet = {1, 2, 3}

Start dummy → [0] → [1, 2, 3, 4, 5]

Iterate:
temp.next = 1 → in set → skip → now 0→2
temp.next = 2 → in set → skip → now 0→3
temp.next = 3 → in set → skip → now 0→4
temp.next = 4 → not in set → move forward
Result → [4, 5]
───────────────────────────────────────────────────────────────
*/
