# 🚀 Getting Started Guide

Welcome to **DSA Journey: A New Dawn**! This guide will help you quickly get up and running.

## ⚡ Quick Start (5 Minutes)

### 1. Clone the Repository
```bash
git clone https://github.com/AayushPS/DSA_Journey_A_NEW_DAWN.git
cd DSA_Journey_A_NEW_DAWN
```

### 2. Choose Your Topic
```bash
cd Arrays  # or any topic folder
```

### 3. Run a Solution
```bash
javac TwoSum.java
java TwoSum
```

That's it! You're running DSA solutions. 🎉

## 📚 Repository Overview

This repository contains **93+ DSA problem solutions** organized into **17 topics**:

- **Arrays**: Array manipulation and algorithms
- **Strings**: String processing and pattern matching
- **LinkedList**: Node-based data structures
- **Stacks & Queues**: LIFO/FIFO implementations
- **Graphs**: Graph algorithms and traversals
- **Dynamic Programming**: DP patterns and optimizations
- **And 11 more topics!**

## 🛠️ System Requirements

| Requirement | Version | Installation |
|:-----------:|:-------:|:-------------:|
| Java | 21 LTS+ | [Download JDK](https://www.oracle.com/java/technologies/downloads/) |
| Git | Any | [Download Git](https://git-scm.com) |
| IDE | Optional | IntelliJ / VS Code / Eclipse |

### Verify Installation

```bash
# Check Java
java -version
# Should show: java version "21.x.x" or higher

# Check Git
git --version
# Should show: git version 2.x.x or higher
```

## 📖 Usage Guide

### Finding a Problem

**Method 1: Using the Main README**
1. Open `README.md` in repository root
2. Check the "Topics & Coverage" table
3. Click on your desired topic
4. Look for the problem in the topic's README

**Method 2: Using Directory Structure**
1. Browse the folder structure
2. Open topic folder (e.g., `Arrays/`)
3. Check `README.md` for problem list
4. Open solution file

**Method 3: Search**
```bash
# Find problems by name
find . -name "*TwoSum*" -type f

# Find all problems in a topic
ls Arrays/*.java | head -10

# Search for pattern in code
grep -r "Approach:" --include="*.java"
```

### Running Solutions

**Command Line:**
```bash
cd Arrays
javac TwoSum.java
java TwoSum
```

**Using IDE (IntelliJ IDEA):**
1. Open project in IntelliJ
2. Navigate to desired file
3. Right-click → Run
4. Or press `Shift + F10`

**Using IDE (VS Code):**
1. Install Extension: Extension Pack for Java (Microsoft)
2. Open file in editor
3. Click "Run" button at top-right
4. Or press `Ctrl + Shift + D` → select Java

## 🎯 Learning Path

### Beginner: Start Here
1. **Arrays** - Fundamentals of data structures
2. **Strings** - String manipulation basics
3. **LinkedList** - Understanding pointers and references

### Intermediate: Build Foundation
4. **Stacks & Queues** - Important data structures
5. **Binary Search** - Search optimization
6. **Recursion & Backtracking** - Problem-solving techniques

### Advanced: Master Complex Topics
7. **Dynamic Programming** - Optimization technique
8. **Graphs** - Network and connectivity problems
9. **Advanced Structures** - Fenwick Trees, Segment Trees

### Challenge Phase
10. **Greedy Algorithms** - Local optimization
11. **Bit Manipulation** - Binary operations
12. **Math & Number Theory** - Advanced algorithms

## 📝 Understanding a Solution

Each solution file contains:

### 1. **Problem Header**
```java
/*
 * Problem: 1. Two Sum
 * Platform: LeetCode
 * Difficulty: Easy
 * Link: https://leetcode.com/problems/two-sum/
 * Problem Statement: [Description]
 */
```

### 2. **Approach Explanation**
```java
/**
 * Approach:
 * - Use HashMap to store number and index
 * - For each number, check if complement exists
 * - Return indices when found
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
```

### 3. **Implementation**
```java
public int[] twoSum(int[] nums, int target) {
    // Clean, optimized code
}
```

### 4. **Test Cases**
```java
public static void main(String[] args) {
    // Multiple test cases with expected outputs
}
```

## 💡 Key Concepts

### Time Complexity
- **O(1)**: Constant time (best)
- **O(log n)**: Logarithmic
- **O(n)**: Linear
- **O(n log n)**: Linearithmic (sorting)
- **O(n²)**: Quadratic
- **O(2ⁿ)**: Exponential (worst)

### Space Complexity
- **O(1)**: Constant extra space
- **O(n)**: Linear with input size
- **O(log n)**: Logarithmic (recursion depth)

### Common Patterns
1. **Two Pointer** - Efficient array traversal
2. **Sliding Window** - Subarray problems
3. **HashMap** - Fast lookup and frequency count
4. **Recursion** - Tree and graph traversal
5. **DP** - Overlapping subproblems optimization

## 🔍 Exploring Topics

### Arrays (19 Problems)
```bash
cd Arrays
cat README.md  # See all problems
javac AdjacentIncreasingSubarrays.java
java AdjacentIncreasingSubarrays
```

### Dynamic Programming (25 Problems)
```bash
cd DynamicProgramming
ls  # See subcategories

cd DP_On_Grids
javac UniquePaths.java
java UniquePaths
```

### Graphs (4 Problems)
```bash
cd Graphs
# Implementations of:
# - DFS/BFS
# - Dijkstra's Algorithm
# - Kruskal's Algorithm
# - Prim's Algorithm
# - Disjoint Set Union (DSU)
```

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| `README.md` | Main overview and progress tracker |
| `CONTRIBUTING.md` | How to contribute new solutions |
| `CODE_STYLE_GUIDE.md` | Coding standards and best practices |
| `DIRECTORY_STRUCTURE.md` | Repository organization |
| `GETTING_STARTED.md` | This file - quick start guide |

## 🤝 Contributing

### Want to Add Your Solution?

1. **Read** `CONTRIBUTING.md` for detailed guidelines
2. **Follow** `CODE_STYLE_GUIDE.md` for code standards
3. **Update** relevant `README.md` with your problem
4. **Test** your solution thoroughly
5. **Submit** a Pull Request

### Types of Contributions
- New problem solutions
- Performance optimizations
- Bug fixes
- Documentation improvements
- Code clarity enhancements

## ❓ FAQ

**Q: Which Java version should I use?**
A: Java 21 LTS or higher. Download from [oracle.com](https://www.oracle.com/java/technologies/downloads/)

**Q: Do I need an IDE?**
A: No, you can use command line. IDE is helpful for debugging though.

**Q: How do I run tests?**
A: Each Java file has a `main` method with test cases. Just run the file.

**Q: Can I contribute?**
A: Absolutely! See `CONTRIBUTING.md` for guidelines.

**Q: Where can I find problem links?**
A: Check the `README.md` in each topic folder for LeetCode/GeeksforGeeks links.

**Q: How should I approach learning?**
A: Start with Arrays and Strings, then move to more complex topics. Consistency over speed!

**Q: How do I report issues?**
A: Open a GitHub issue with detailed description and we'll help!

## 🎯 Learning Tips

1. **Understand Before Coding**: Read problem, understand approach before looking at code
2. **Try First**: Attempt solution yourself before checking implementation
3. **Analyze Complexity**: Always calculate time and space complexity
4. **Practice Variations**: Solve similar problems to reinforce concepts
5. **Trace Through**: Manually trace execution with sample inputs
6. **Optimize**: Find multiple approaches and compare efficiency
7. **Review Others**: Learn from different solution approaches

## 📊 Progress Tracking

Keep track of your learning:

```
✅ Arrays (10/19 completed)
✅ Strings (1/2 completed)
🔄 LinkedList (3/7 in progress)
⏳ Dynamic Programming (not started)
```

## 🔗 Useful Resources

### Learning Platforms
- [LeetCode](https://leetcode.com) - Problem source
- [GeeksforGeeks](https://www.geeksforgeeks.org) - Concept tutorials
- [HackerRank](https://www.hackerrank.com) - Interactive learning
- [InterviewBit](https://www.interviewbit.com) - Interview preparation

### Complexity Analysis
- [Big O Cheatsheet](https://www.bigocheatsheet.com/)
- [Big O Notation](https://en.wikipedia.org/wiki/Big_O_notation)

### Java Resources
- [Java Documentation](https://docs.oracle.com/javase/docs/)
- [Collections Framework](https://docs.oracle.com/javase/tutorial/collections/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

## 🚀 Next Steps

1. **Clone the repository** (see Quick Start)
2. **Choose a topic** that interests you
3. **Read the problem README** to understand concepts
4. **Run an example solution** to see the pattern
5. **Attempt the problems** yourself first
6. **Compare your solution** with provided solution
7. **Analyze the complexity** and learn the approach
8. **Move to the next problem** and repeat!

## 💬 Feedback & Questions

- **Found a bug?** Open an issue on GitHub
- **Have suggestions?** Create a discussion or issue
- **Want to contribute?** Follow `CONTRIBUTING.md`
- **Need help?** Check `README.md` for contact info

---

<div align="center">

## 🎓 Happy Learning! 🎓

**Remember**: *"The journey of a thousand algorithms begins with a single array."* 🌟

*Made with ❤️ for DSA Enthusiasts*

</div>

---

**Quick Links:**
- [Main Repository](https://github.com/AayushPS/DSA_Journey_A_NEW_DAWN)
- [Contributing Guidelines](./CONTRIBUTING.md)
- [Code Style Guide](./CODE_STYLE_GUIDE.md)
- [Directory Structure](./DIRECTORY_STRUCTURE.md)
- [Main README](./README.md)

Last Updated: December 16, 2025
