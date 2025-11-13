/*
───────────────────────────────────────────────────────────────
📘 Problem: 1472. Design Browser History
💡 Difficulty: Medium
🧠 Topics: Linked List, Stack, Design
🔗 Link: https://leetcode.com/problems/design-browser-history/
───────────────────────────────────────────────────────────────
🧩 Problem Statement:

You are implementing a simple Browser History system.

You begin at the `homepage`, and can perform three operations:

1️⃣ visit(url)
      - Navigate to a new page.
      - All forward history is cleared.

2️⃣ back(steps)
      - Move back up to `steps` times (stop if no more back history).

3️⃣ forward(steps)
      - Move forward up to `steps` times (stop if no more forward history).

Implement the following class:

    BrowserHistory(homepage)
    void visit(String url)
    String back(int steps)
    String forward(int steps)

───────────────────────────────────────────────────────────────
🔒 Constraints:
• At most 5000 operations  
• URL length ≤ 20  
• Only lowercase letters and '.'  
───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🥇 Approach — Doubly Linked List (Optimal & Clean)
───────────────────────────────────────────────────────────────
💡 Idea Breakdown:

Simulate browser history using a **doubly-linked list**:

- Each node stores:
      url, prev, next

- `history` pointer always points to the **current page**.

Operations:

1️⃣ visit(url)
    - Create new node next to `history`.
    - Clear forward history by overwriting `history.next`.
    - Move to new node.

2️⃣ back(steps)
    - Move `steps` times to `.prev` (stop early if needed).

3️⃣ forward(steps)
    - Move `steps` times to `.next` (stop early if needed).

Simple, clean, and perfectly matches browser behavior.

───────────────────────────────────────────────────────────────
🧮 Complexity:
• visit():    O(1)  
• back():     O(steps) ≤ O(n) worst-case  
• forward():  O(steps)  
• Space:      O(n) for history nodes

───────────────────────────────────────────────────────────────
🔥 This is the recommended design pattern for such problems.
───────────────────────────────────────────────────────────────
*/

class BrowserHistory {

    // Doubly Linked List Node
    private static class DLL {
        String url;
        DLL next, prev;

        DLL(String url) {
            this.url = url;
        }

        DLL(String url, DLL prev) {
            this.url = url;
            this.prev = prev;
        }
    }

    private DLL history;  // current page

    // Initialize with homepage
    public BrowserHistory(String homepage) {
        history = new DLL(homepage);
    }
    
    // ───────────────────────────────────────────────────────────────
    // Visit new URL → Clear forward history and append node
    // ───────────────────────────────────────────────────────────────
    public void visit(String url) {
        DLL newVisit = new DLL(url, history);

        // When visiting a new page, forward history is wiped
        history.next = newVisit;

        history = history.next;
    }
    
    // ───────────────────────────────────────────────────────────────
    // Go back in history by up to 'steps'
    // ───────────────────────────────────────────────────────────────
    public String back(int steps) {
        while (steps > 0 && history.prev != null) {
            history = history.prev;
            steps--;
        }
        return history.url;
    }
    
    // ───────────────────────────────────────────────────────────────
    // Go forward in history by up to 'steps'
    // ───────────────────────────────────────────────────────────────
    public String forward(int steps) {
        while (steps > 0 && history.next != null) {
            history = history.next;
            steps--;
        }
        return history.url;
    }
}


/*
───────────────────────────────────────────────────────────────
🧩 Example Execution:
───────────────────────────────────────────────────────────────

BrowserHistory("leetcode.com")
visit("google.com")
visit("facebook.com")
visit("youtube.com")
back(1)      → "facebook.com"
back(1)      → "google.com"
forward(1)   → "facebook.com"
visit("linkedin.com")
forward(2)   → stays on "linkedin.com"
back(2)      → "google.com"
back(7)      → "leetcode.com"

───────────────────────────────────────────────────────────────
*/


/*
───────────────────────────────────────────────────────────────
🧮 Final Complexity Summary:
───────────────────────────────────────────────────────────────
visit:    O(1)
back:     O(steps)
forward:  O(steps)
Space:    O(total history nodes)

✔ Clean
✔ Efficient
✔ Realistic browser simulation
───────────────────────────────────────────────────────────────
*/
