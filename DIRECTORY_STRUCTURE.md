# 📁 Repository Structure Guide

This document outlines the organization of the DSA Journey repository.

## Root Directory Structure

```
DSA_Journey_A_NEW_DAWN/
├── .github/
│   ├── workflows/
│   │   ├── build.yml              # Java compilation & quality checks
│   │   └── pr-validation.yml      # Pull request validation
│   └── ...
├── .editorconfig                  # Code style configuration
├── .gitignore                     # Git ignore rules
├── Arrays/                        # Array algorithms and problems
├── BinaryTree/                    # Binary tree solutions
├── Binary\ Search/                # Binary search algorithms
├── BitManipulation/               # Bit manipulation techniques
├── DynamicProgramming/            # DP problems (organized by subcategory)
├── Graphs/                        # Graph algorithms
├── Greedy/                        # Greedy algorithms
├── Heaps/                         # Heap and priority queue problems
├── LinkedList/                    # Linked list implementations
├── Math/                          # Mathematical problems
├── Miscellaneous/                 # Mixed problems
├── OOPs_Design/                   # OOP design and simulation
│   └── Simulation/                # System simulation problems
├── PrefixSum/                     # Prefix sum techniques
├── Recursion_Backtracking/        # Recursive and backtracking solutions
├── SlidingWindow_TwoPointers/      # Sliding window and two-pointer problems
├── Stacks_Queues/                 # Stack and queue implementations
├── Strings/                       # String algorithms
├── README.md                      # Main project documentation
├── CONTRIBUTING.md                # Contribution guidelines
└── LICENSE                        # MIT License

```

## Topic Directory Structure

Each topic folder follows this standard structure:

```
Topic/
├── README.md                      # Topic overview and problem list
├── ProblemOne.java               # Solution file
├── ProblemTwo.java               # Solution file
└── Subfolder/ (optional)         # For categorized subtopics
    ├── SubProblem.java
    └── README.md
```

## File Organization Details

### 📊 Arrays/
Contains array manipulation and algorithm problems.
- Two-pointer techniques
- Sliding window
- Array searching and sorting
- Prefix/suffix operations

### 🔤 Strings/
String processing and pattern matching algorithms.
- String traversal
- Character frequency
- Pattern matching
- String transformations

### 🔗 LinkedList/
Linked list data structure implementations.
- Node operations
- Cycle detection
- List reversal and rearrangement
- Intersection problems

### 📚 Stacks_Queues/
Stack and queue implementations and problems.
- LIFO/FIFO operations
- Stack-based problems
- Queue variants
- Monotonic stacks

### 🌳 BinaryTree/
Binary tree traversals and problems.
- Tree traversal (DFS/BFS)
- Tree construction
- Path problems
- Tree properties

### 🌐 Graphs/
Graph algorithms and traversal techniques.
- Graph representations
- DFS/BFS algorithms
- Dijkstra's algorithm
- Union-Find (DSU)
- Kruskal's and Prim's algorithms

### 💡 DynamicProgramming/
Organized with subcategories for different DP patterns:

```
DynamicProgramming/
├── README.md
├── DP_On_Grids/          # 2D grid-based DP
├── DP_On_Strings/        # String DP problems
├── LIS_AND_VARIANTS/     # Longest Increasing Subsequence variants
├── Longest_Common_SubSequence_AND_VARIANTS/  # LCS problems
└── Miscellaneous/        # Other DP problems
```

### 🎭 OOPs_Design/
Object-oriented design and system simulation.

```
OOPs_Design/
├── README.md
└── Simulation/           # System simulation problems
    ├── README.md
    ├── SimpleBankSystem.java
    └── ...
```

### 📝 Math/
Mathematical problems and algorithms.
- Number theory
- Combinatorics
- Geometric problems
- Modular arithmetic

### 🎯 Other Topics
- **Binary Search** - Binary search variants and applications
- **BitManipulation** - Bitwise operations
- **Greedy** - Greedy algorithm problems
- **Heaps** - Heap and priority queue problems
- **PrefixSum** - Prefix sum techniques
- **Recursion_Backtracking** - Recursive and backtracking solutions
- **SlidingWindow_TwoPointers** - Linear optimization techniques
- **Miscellaneous** - Mixed algorithmic challenges

## File Naming Conventions

### Java Files
- **Pattern**: `ProblemNameOrNumber.java`
- **Examples**:
  - `TwoSum.java`
  - `LongestIncreasingSubsequence.java`
  - `3523_UniquePaths.java` (with problem number)

### Documentation Files
- Main README: `README.md` (in root and each topic)
- Contributing guide: `CONTRIBUTING.md`
- This file: `DIRECTORY_STRUCTURE.md`

## Workflow Files

Located in `.github/workflows/`:

### build.yml
- Triggers on: Push to main/develop, PR with Java file changes
- Checks: Java compilation, code quality, documentation
- Java version: 21 LTS

### pr-validation.yml
- Triggers on: Pull request creation/update
- Checks: PR title format, branch naming, file changes, commit messages
- Verifies Java compilation for modified files

## Key Files

| File | Purpose |
|------|---------|
| `.editorconfig` | Consistent code formatting across IDEs |
| `.gitignore` | Git ignore rules for Java projects |
| `README.md` | Main project documentation and overview |
| `CONTRIBUTING.md` | Contribution guidelines and workflow |
| `LICENSE` | MIT License terms |

## Best Practices

### When Adding New Problems

1. **Choose the Right Topic**: Place problem in most relevant category
2. **Follow Naming**: Use descriptive Java class names (PascalCase)
3. **Add Documentation**: Include problem statement and comments
4. **Update README**: Add problem entry to topic README
5. **Test Thoroughly**: Include test cases in main method

### When Creating Subcategories

1. Create a new folder with descriptive name
2. Add `README.md` with subcategory overview
3. Use consistent file naming within subcategory
4. Update parent topic README with link to subcategory

## Navigation Tips

### Finding a Specific Problem
1. Check main `README.md` topics table
2. Navigate to relevant topic folder
3. Check topic's `README.md` for problem list
4. Open the solution file

### Navigating Between Problems
- Topic `README.md` has links to problem solutions
- Related problems are grouped in same folder/subcategory
- Comments in code reference similar problems

---

*This repository is organized for easy navigation and learning. Structure is flexible and can be updated as new topics and problems are added.*

Last Updated: December 16, 2025
