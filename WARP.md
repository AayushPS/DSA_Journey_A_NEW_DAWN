# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Repository Overview

This is a personal Data Structures & Algorithms learning repository tracking solutions to competitive programming problems, primarily from LeetCode. All implementations are in **Java** and organized by topic/pattern.

## Development Commands

### Compiling and Running Java Solutions

```bash
# Navigate to topic folder
cd Arrays  # or DynamicProgramming, Greedy, Strings, OOPs_Design/Simulation, etc.

# Compile a solution
javac ProblemName.java

# Run the compiled solution
java ProblemName
```

### Testing Specific Solutions

```bash
# Compile and run in one command
javac AdjacentIncreasingSubarrays.java && java Solution
```

There are **no automated test frameworks** - each solution includes test cases in its `main()` method. Run the Java file directly to execute tests.

## Code Structure

### File Organization

```
DSA_Journey_A_NEW_DAWN/
├── Arrays/                  # Array-based problems
├── Strings/                 # String manipulation
├── DynamicProgramming/      # DP solutions
├── Greedy/                  # Greedy algorithms
├── LinkedList/              # Linked list operations
├── BinaryTree/              # Tree problems
├── Graphs/                  # Graph algorithms
├── OOPs_Design/Simulation/  # Object-oriented design and system simulation
└── [13 other topic folders]
```

Each topic folder contains:
- `README.md` - Topic overview and problem list
- `ProblemName.java` - Individual solution files

### Solution File Format

Every Java solution follows a strict, comprehensive documentation pattern:

```java
/*
 🔹 Problem: [Number]. [Problem Title]
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy/Medium/Hard
 🔹 Topics: [Tags]
 🔹 Link: [URL]
 
 ------------------------------------------------------------
 🔸 Problem Statement:
 [Full problem description]
 
 🔸 Examples:
 [Input/Output examples]
 
 🔸 Constraints:
 [Problem constraints]
 
 ------------------------------------------------------------
 🔹 APPROACHES
 ------------------------------------------------------------
 
 🔸 Approach 1: [Description]
 [Detailed explanation with code if multiple approaches exist]
 
 🔸 Approach 2: [Better approach if exists]
 [Detailed explanation]
 
 ✅ Active Code (Final Optimal Implementation)
*/

class Solution {
    // Implementation
}

/*
 🔹 Complexity:
   Time  → O(...)
   Space → O(...)
   
 🔹 Summary:
 [Key insights and reasoning]
*/
```

## Coding Standards

### Java Conventions
- **Class names**: `Solution` (standard LeetCode convention)
- **Variable naming**: Descriptive, camelCase (`currRun`, `prevRun`, `maxSum`)
- **Comments**: Comprehensive block comments with emoji markers (🔹, 🔸, ✅)
- **Complexity analysis**: Always included at top and bottom of file

### Documentation Requirements
When adding new solutions:
1. Include complete problem statement with constraints
2. Provide multiple approaches when applicable (with explanations)
3. Mark the final optimal implementation with `✅ Active Code`
4. Include time/space complexity analysis
5. Add examples with explanations
6. Include intuition/summary section

### Code Quality
- Optimize for both **time and space complexity**
- Prefer clean, readable implementations over overly clever code
- Use meaningful variable names (avoid single letters except in mathematical contexts)
- Include test cases in the main method

## Architecture Notes

### Problem Categorization System
Problems are organized by algorithmic patterns and data structures rather than strict problem type. Some solutions may appear in multiple categories (e.g., Dynamic Programming may overlap with Greedy).

### Solution Evolution Pattern
Many files document multiple approaches, showing the evolution from brute force → optimized → space-optimized. The "Active Code" marker indicates the final, optimal solution to implement.

### Progress Tracking
- Total problems, difficulty breakdown, and recent activity are tracked in the root `README.md`
- Each topic folder's `README.md` contains specific problem lists for that category
- Update the root README when adding new solutions

## Common Patterns

### Finding Problems
```bash
# Search for specific problem by number
find . -name "*.java" | xargs grep "Problem: 3349"

# Find all medium difficulty problems
grep -r "Difficulty: Medium" --include="*.java"

# List all solved problems in a topic
ls -la Arrays/*.java
```

### Working with Solutions

When reviewing or modifying code:
- Read the full problem statement in comments first
- Check if multiple approaches are documented
- Verify the complexity analysis matches the implementation
- Test with the provided examples before adding new test cases

### Adding New Solutions

1. Navigate to the appropriate topic folder
2. Create `ProblemName.java` following the documented format
3. Include all required sections (problem statement, approaches, complexity)
4. Add test cases in the main method
5. Update the topic's `README.md` problem count
6. Update root `README.md` progress tracker and recent activity

## Notes

- This is a **learning repository** - solutions prioritize clarity and understanding over code golf
- Multiple approaches are often included to demonstrate algorithmic progression
- No IDE-specific configurations - works with any Java development environment
- No build automation (Maven/Gradle) - direct `javac`/`java` usage
