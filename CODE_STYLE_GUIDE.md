# 💻 Code Style Guide

This document outlines the coding standards and best practices for the DSA Journey repository.

## 🎯 Principles

- **Clarity**: Code should be easy to understand
- **Consistency**: Uniform style throughout the repository
- **Efficiency**: Focus on both time and space complexity
- **Documentation**: Comprehensive comments and explanations
- **Testing**: Include meaningful test cases

## Java Code Standards

### File Structure

```java
/*
 * Problem: [Problem Number and Name]
 * Platform: LeetCode / GeeksforGeeks / HackerRank
 * Difficulty: Easy / Medium / Hard
 * Link: [Problem URL]
 * 
 * Problem Statement:
 * [Clear, concise problem description]
 */

import java.util.*;

public class ProblemSolutionName {
    
    // Solution methods
    
    public static void main(String[] args) {
        // Test cases
    }
}
```

### Naming Conventions

#### Classes
- **Pattern**: PascalCase
- **Prefix**: Descriptive noun
- **Examples**:
  ```java
  public class TwoSum { }
  public class LongestIncreasingSubsequence { }
  public class BinarySearchSolution { }
  ```

#### Methods
- **Pattern**: camelCase
- **Prefix**: Action verb
- **Examples**:
  ```java
  public int findMaximum(int[] arr) { }
  public boolean isValid(String s) { }
  public List<Integer> getSortedArray(int[] nums) { }
  ```

#### Variables
- **Pattern**: camelCase
- **Descriptive**: Meaningful names
- **Avoid**: Single letters (except loop counters: i, j, k)
- **Examples**:
  ```java
  int[] resultArray;
  int maxLength;
  Map<Integer, Integer> frequencyMap;
  
  // Good for loops
  for (int i = 0; i < n; i++) { }
  
  // Avoid: single letter for non-loop variables
  int x;        // ❌ Bad
  int count;    // ✅ Good
  ```

#### Constants
- **Pattern**: UPPER_SNAKE_CASE
- **Examples**:
  ```java
  private static final int MAX_SIZE = 1000;
  private static final String DEFAULT_SEPARATOR = ",";
  ```

### Indentation & Formatting

- **Indentation**: 4 spaces (configured in `.editorconfig`)
- **Line Length**: Maximum 120 characters
- **Braces**: Opening brace on same line (Java convention)

```java
public void exampleMethod() {
    // Proper indentation with 4 spaces
    if (condition) {
        // Method body
    }
}
```

### Comments & Documentation

#### File Header
```java
/*
 * Problem: 1. Two Sum
 * Platform: LeetCode
 * Difficulty: Easy
 * Link: https://leetcode.com/problems/two-sum/
 * 
 * Problem Statement:
 * Given an array of integers nums and an integer target, 
 * return the indices of the two numbers that add up to target.
 */
```

#### Method Documentation
```java
/**
 * Finds two numbers in an array that sum to target.
 * 
 * Approach:
 * - Use HashMap to store number and its index
 * - For each number, check if (target - number) exists in map
 * - Return indices when pair is found
 * 
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(n) - HashMap storage
 * 
 * @param nums The input array of integers
 * @param target The target sum
 * @return Array of two indices [i, j] where nums[i] + nums[j] == target
 */
public int[] twoSum(int[] nums, int target) {
    // Implementation
}
```

#### Inline Comments
```java
// Track visited nodes for cycle detection
Set<Integer> visited = new HashSet<>();

// Use two pointers for O(1) extra space
int left = 0, right = n - 1;
```

### Complexity Analysis Template

Include in comments before method implementation:

```java
/*
 * Time Complexity: O(n log n)
 * - Sorting takes O(n log n)
 * - Binary search loop takes O(log n) per element
 * - Total: O(n log n)
 * 
 * Space Complexity: O(1)
 * - No extra space besides output array
 * - Sorting is done in-place
 */
```

## Code Structure Best Practices

### Variable Declaration
```java
// ✅ Good: Close to usage, descriptive names
if (arr[i] > 0) {
    int positiveCount = countPositives(arr);
    result.add(positiveCount);
}

// ❌ Avoid: Far from usage, unclear names
int pc;
// ... 20 lines of code ...
pc = countPositives(arr);
```

### Method Length
- Keep methods focused and concise
- Ideal: 20-50 lines
- Maximum: 100 lines (consider refactoring)
- Extract complex logic into helper methods

### Helper Methods
```java
public class Solution {
    public int solve(int[] nums) {
        // Main logic using helper methods
        int max = findMaximum(nums);
        int min = findMinimum(nums);
        return max - min;
    }
    
    private int findMaximum(int[] nums) {
        // Helper method logic
    }
    
    private int findMinimum(int[] nums) {
        // Helper method logic
    }
}
```

## Import Statements

- **Order**: Standard library → java.util → java.lang (implicit)
- **Avoid**: Wildcard imports (use specific imports)

```java
// ✅ Good
import java.util.*;
import java.util.HashMap;
import java.util.List;

// ❌ Avoid
import java.*;  // Wildcard imports
```

## Collections Usage

### List Operations
```java
// Use ArrayList for general purpose
List<Integer> list = new ArrayList<>();

// Use LinkedList for frequent insertions/deletions
List<Integer> linkedList = new LinkedList<>();

// Use Arrays.asList for fixed-size lists
List<String> fixed = Arrays.asList("a", "b", "c");
```

### Map Operations
```java
// HashMap for fast lookups
Map<Integer, String> map = new HashMap<>();

// TreeMap for sorted order
Map<Integer, String> sorted = new TreeMap<>();

// LinkedHashMap for insertion order
Map<Integer, String> ordered = new LinkedHashMap<>();
```

### Set Operations
```java
// HashSet for uniqueness
Set<Integer> set = new HashSet<>();

// TreeSet for sorted order
Set<Integer> sorted = new TreeSet<>();

// LinkedHashSet for insertion order
Set<Integer> ordered = new LinkedHashSet<>();
```

## Common Patterns

### Edge Case Checking
```java
public void solve(int[] arr) {
    // Check for null and empty
    if (arr == null || arr.length == 0) {
        return;
    }
    
    // Main logic
}
```

### Two Pointer Pattern
```java
int left = 0, right = n - 1;
while (left < right) {
    // Process
    if (condition) {
        left++;
    } else {
        right--;
    }
}
```

### Sliding Window Pattern
```java
int left = 0;
int windowSum = 0;
for (int right = 0; right < n; right++) {
    windowSum += arr[right];
    
    while (windowSum > target && left < right) {
        windowSum -= arr[left];
        left++;
    }
    
    // Process current window
}
```

## Testing Best Practices

### Test Cases Structure
```java
public static void main(String[] args) {
    // Test Case 1: Normal case
    int[] nums = {2, 7, 11, 15};
    int target = 9;
    int[] result = twoSum(nums, target);
    System.out.println("Test 1: " + Arrays.toString(result));
    // Expected: [0, 1]
    
    // Test Case 2: Edge case - minimal input
    int[] nums2 = {2, 7};
    int target2 = 9;
    result = twoSum(nums2, target2);
    System.out.println("Test 2: " + Arrays.toString(result));
    // Expected: [0, 1]
    
    // Test Case 3: Large numbers
    int[] nums3 = {1000000, 2000000};
    int target3 = 3000000;
    result = twoSum(nums3, target3);
    System.out.println("Test 3: " + Arrays.toString(result));
    // Expected: [0, 1]
}
```

## 🚫 Avoid These

| Pattern | Avoid | Use Instead |
|---------|-------|-------------|
| Single letter vars | `int x` | `int count` |
| Magic numbers | `if (i > 100)` | `if (i > MAX_SIZE)` |
| Nested loops > 3 | Deep nesting | Extract methods |
| Huge methods | >100 lines | Break into smaller methods |
| Exception swallowing | `catch(E e) {}` | `catch(E e) { log/handle }` |
| No null checks | Direct dereference | Check for null |

## ✅ Do These

| Practice | Benefit |
|----------|---------|
| Add comments | Helps future readers |
| Include test cases | Validates correctness |
| Analyze complexity | Ensures efficiency |
| Use meaningful names | Improves readability |
| Handle edge cases | Prevents crashes |
| Keep methods small | Easier to debug |

## Checking Your Code

Before committing:

- [ ] All methods have JavaDoc comments
- [ ] Complexity analysis included
- [ ] Test cases pass
- [ ] No unused imports
- [ ] No hardcoded values (use constants)
- [ ] Variable names are descriptive
- [ ] Code formatted consistently
- [ ] Maximum line length 120 characters
- [ ] No trailing whitespace
- [ ] Proper indentation (4 spaces)

## Tools & IDE Configuration

### VS Code Settings
```json
{
    "java.format.settings.url": "GoogleStyle",
    "java.format.onSave": true,
    "[java]": {
        "editor.defaultFormatter": "redhat.java",
        "editor.formatOnSave": true
    }
}
```

### IntelliJ IDEA
- Import Code Style: Google Java Style
- Enable Code Inspections: Full
- Format on Save: Enabled

---

**Remember**: Code is read much more often than it's written. Write code for humans first, machines second.

Last Updated: December 16, 2025
