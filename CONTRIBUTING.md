# Contributing to DSA Journey: A New Dawn 🌅

Thank you for your interest in contributing! This document provides guidelines and instructions for contributing to this repository.

## 🎯 Types of Contributions

We welcome contributions in the following areas:

### 1. **Bug Reports & Fixes** 🐛
- Report incorrect solutions or logic errors
- Provide fixes for identified bugs
- Include test cases that demonstrate the issue

### 2. **Performance Optimizations** ⚡
- Suggest more efficient algorithms
- Improve time/space complexity
- Provide benchmarks showing improvements

### 3. **New Problem Solutions** 🆕
- Add solutions to problems not yet covered
- Ensure they fit into appropriate topic categories
- Include comprehensive comments and test cases

### 4. **Documentation Improvements** 📝
- Enhance problem explanations
- Improve README files
- Add or clarify comments in code

### 5. **Code Quality Enhancements** ✨
- Refactor existing solutions for clarity
- Improve variable naming
- Add missing edge case handling

## 📋 Before You Start

1. **Check existing issues and PRs** to avoid duplicate work
2. **Discuss large changes** by opening an issue first
3. **Ensure you understand** the problem and solution
4. **Test thoroughly** before submitting

## 🔧 Development Setup

### Prerequisites
```bash
# Install Java 21 or higher
java -version

# Verify git installation
git --version
```

### Clone & Setup
```bash
# Fork the repository on GitHub
# Clone your fork
git clone https://github.com/YOUR_USERNAME/DSA_Journey_A_NEW_DAWN.git
cd DSA_Journey_A_NEW_DAWN

# Add upstream remote
git remote add upstream https://github.com/AayushPS/DSA_Journey_A_NEW_DAWN.git
```

## 📝 Contribution Workflow

### Step 1: Create a Feature Branch
```bash
# Update your local main branch
git checkout main
git pull upstream main

# Create a feature branch
git checkout -b feature/add-unique-paths-solution
# or for bug fixes
git checkout -b fix/incorrect-complexity-analysis
```

### Step 2: Make Your Changes

#### For New Problem Solutions:
```java
/*
 * Problem: [Problem Number/Name]
 * Platform: LeetCode / GeeksforGeeks / HackerRank
 * Difficulty: Easy / Medium / Hard
 * Link: [Problem Link]
 * 
 * Problem Statement:
 * [Clear description of what needs to be solved]
 */

public class SolutionClassName {
    
    /**
     * Main solution method
     * 
     * Approach:
     * - [Describe your approach]
     * - [Key insights]
     * - [Why this works]
     * 
     * Time Complexity: O(n log n) - [explanation]
     * Space Complexity: O(n) - [explanation]
     */
    public static [returnType] solve([parameters]) {
        // Your implementation
        return result;
    }
    
    public static void main(String[] args) {
        // Test Case 1
        // Expected: [expected output]
        // Result: [actual output]
        
        // Test Case 2
        // ...
    }
}
```

#### For Bug Fixes:
- Include a comment explaining the bug
- Show the incorrect behavior
- Demonstrate the fix
- Add test cases

#### For Optimizations:
- Provide before/after complexity analysis
- Include performance comparison
- Document alternative approaches

### Step 3: Follow Code Standards

✅ **DO:**
- Use clear, descriptive variable names
- Write comprehensive comments
- Include complexity analysis
- Add multiple test cases
- Follow Java naming conventions (camelCase for methods, UPPER_CASE for constants)
- Use meaningful commit messages

❌ **DON'T:**
- Use single-letter variable names (except loop counters)
- Omit comments or explanations
- Mix multiple unrelated changes
- Leave debug code or commented-out lines
- Use non-standard formatting

### Step 4: Commit Your Changes

```bash
# Stage your changes
git add .

# Commit with clear, descriptive message
git commit -m "Add solution for Problem 123: Problem Name"

# Examples of good commit messages:
# "Add optimized solution for Two Sum problem"
# "Fix time complexity analysis in Binary Search"
# "Improve code clarity in Merge Sort implementation"
# "Add edge case handling in LinkedList problem"

# Push to your fork
git push origin feature/add-unique-paths-solution
```

### Step 5: Create a Pull Request

1. Go to the original repository on GitHub
2. Click "New Pull Request"
3. Select your fork and branch
4. Fill in the PR template with:
   - **Title**: Clear, concise description
   - **Description**: What changes were made and why
   - **Related Issues**: Link to any related issues
   - **Type**: Bug Fix / New Feature / Optimization / Documentation
   - **Testing**: How you tested your changes

#### PR Template Example:
```markdown
## Description
Added solution for LeetCode Problem 123: Unique Paths

## Type of Change
- [x] New Problem Solution
- [ ] Bug Fix
- [ ] Performance Optimization
- [ ] Documentation Update

## Problem Details
- **Problem**: Unique Paths
- **Platform**: LeetCode
- **Difficulty**: Medium
- **Link**: [link]

## Solution Approach
Explain your approach here...

## Complexity Analysis
- Time: O(m*n)
- Space: O(m*n)

## Testing
- [x] Tested with provided examples
- [x] Tested with edge cases
- [x] All test cases pass

## Checklist
- [x] Code follows style guidelines
- [x] Added/updated comments
- [x] Added test cases
- [x] Updated relevant README
- [x] No breaking changes
```

## ✅ Review Process

1. **Automated Checks**: Your code will be checked for style and basic issues
2. **Code Review**: Maintainers will review your changes
3. **Feedback**: You may be asked to make adjustments
4. **Approval**: Once approved, your PR will be merged

### Common Feedback Areas:
- **Clarity**: Is the code easy to understand?
- **Correctness**: Does the solution work for all cases?
- **Efficiency**: Can it be optimized further?
- **Documentation**: Are comments and explanations clear?

## 📚 File Organization

### Topic Folder Structure
```
TopicName/
├── README.md                 # Topic overview and problem list
├── Problem1Name.java         # Problem solution
├── Problem2Name.java
└── Subfolder/                # For categorized problems
    └── SubProblem.java
```

### README Requirements for Topics
- Topic description
- Common patterns and algorithms
- Problems list with links and difficulty
- Implementation notes

## 🔄 Updating from Upstream

```bash
# Fetch latest changes
git fetch upstream

# Rebase your branch
git rebase upstream/main

# If conflicts, resolve them, then:
git rebase --continue

# Force push to your fork
git push origin feature-branch --force-with-lease
```

## ❓ Questions or Need Help?

- **Open an Issue**: For bugs, features, or questions
- **Discussions**: For general discussions
- **Email**: Reach out to the maintainer
- **PR Comments**: Ask questions in your PR

## 📋 Code Review Checklist

Before submitting your PR, ensure:

- [ ] Code compiles without errors
- [ ] All test cases pass
- [ ] Added meaningful test cases
- [ ] Following code standards
- [ ] Comments are clear and comprehensive
- [ ] Complexity analysis is accurate
- [ ] README updated if needed
- [ ] No unrelated changes included
- [ ] Commit messages are clear
- [ ] No sensitive information is included

## 🎓 Learning Resources

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Big O Notation](https://en.wikipedia.org/wiki/Big_O_notation)
- [Git Best Practices](https://git-scm.com/book/en/v2)
- [How to Write Good Commit Messages](https://chris.beams.io/posts/git-commit/)

## 📄 License

By contributing, you agree that your contributions will be licensed under the MIT License.

## 🙏 Thank You!

Your contributions help make this a better learning resource for everyone. Thank you for taking the time to contribute!

---

<div align="center">

### Happy Contributing! 🚀

*Made with ❤️ by the DSA Journey community*

</div>
