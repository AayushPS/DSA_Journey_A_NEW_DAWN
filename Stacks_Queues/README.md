# Stacks & Queues 📚

## What This Topic Covers
Linear data structures with specific access patterns. This section covers:
- Stack (LIFO) operations and applications
- Queue (FIFO) operations and variations
- Priority queues and deques
- Implementation using arrays and linked lists

## Common Problems & Patterns
- **Balanced Parentheses** - Stack-based validation
- **Expression Evaluation** - Infix, postfix, prefix conversions
- **Monotonic Stack/Queue** - Maintaining order properties
- **Next Greater Element** - Using stack for efficient lookups
- **BFS Implementation** - Queue-based graph traversal
- **Sliding Window Maximum** - Deque-based optimization
- **Min/Max Stack** - Stack with O(1) min/max operations

## Problems Solved

| Problem | Platform | Difficulty | Summary |
|---------|----------|------------|----------|
| [735. Asteroid Collision](./AsteroidCollision.java) | LeetCode | Medium | Monotonic stack implementation to get which asteroids will remain at the end |
| [1047. Remove All Adjacent Duplicates In String](./RemoveAllAdjacentDuplicatesInString.java) | LeetCode | Easy | Use stack to check the adjacent duplicacy and return string with no adjacent duplicates |
| [901. Online Stock Span](./OnlineStockSpan.java) | LeetCode | Monotonic stack used to evaluate th eldest smallest valuation available as span over stocks problem |
| [946. Validate Stack Sequences](./ValidateStackSequences.java) | LeetCode | Medium | Determine whether popped[] can be obtained through a valid series of push + pop operations on an initially empty stack. |
| [20. Valid Parentheses](./ValidParentheses.java) | LeetCode | Easy | Determine if the string is *valid*. A string is valid if: • Open brackets are closed by the same type of bracket  • Open brackets are closed in the correct order  • Every closing bracket has a corresponding open bracket  |
| [1963. Minimum Number of Swaps to Make the String Balanced](./MinimumNumberOfSwapsToMakeTheStringBalanced.java) | LeetCode | Medium | You are given a string `s` of even length, containing exactly n/2 '[' and n/2 ']'. |
 

## Implementation Notes
Solutions are written in **Java** with clean, optimized implementations focusing on:
- Efficient stack and queue operations
- Custom implementation when needed
- Space-time complexity optimization
- Real-world application scenarios

---
*Part of DSA Journey: A New Dawn 🌅*