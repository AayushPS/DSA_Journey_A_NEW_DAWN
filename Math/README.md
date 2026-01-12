# Math for DSA 🔢

## What This Topic Covers
This section focuses on math-based techniques essential for competitive programming and DSA:
- Number theory fundamentals
- Combinatorics and counting principles
- Modular arithmetic techniques
- Fast exponentiation & matrix math
- Factorization and sieve methods
- Math-based optimization patterns

## Common Problems & Patterns

### Number Theory
- GCD / LCM
- Prime generation (sieve)
- Modular arithmetic
- Modular inverse
- Factorization patterns
- Diophantine equations
- CRT

### Combinatorics
- nCr patterns
- Permutations & combinations
- Pascal’s identity
- Inclusion–exclusion
- Catalan-type counts

### Optimized Math Techniques
- Binary exponentiation
- Matrix exponentiation
- Bit manipulation math
- Digit-based math (digit DP foundation)
- Prefix-sum/XOR math tricks

## Problems Solved

| Problem | Platform | Difficulty | Summary |
|---------|----------|------------|----------|
| [1513. Number of Substrings With Only 1s](./NumberOfSubstringsWithOnly1s.java) | LeetCode | Medium | Return the number of substrings that consist ONLY of character '1' |
| [1015. Smallest Integer Divisible by K](./SmallestIntegerDivisibleByK.java) | LeetCode | Medium | return the length of the smallest positive integer n such that: n is divisible by k, n consists only of the digit '1'. If no such integer exists, return -1. |
| [3623. Count Number of Trapezoids I](./CountNumberOfTrapezoidsI.java) | LeetCode | Medium | Count all **unique horizontal trapezoids** formed by selecting any 4 distinct points. |
| [3625. Count Number of Trapezoids II](./CountNumberOfTrapezoidsII.java) | LeetCode | Hard | Return the number of unique trapezoids formed by choosing any 4 distinct points. |
| [1523. Count Odd Numbers in an Interval Range](./CountOddNumbersInAnIntervalRange.java) | LeetCode | Easy | Given two integers `low` and `high`, return how many **odd numbers** exist in the inclusive range `[low, high]`. | 
| [1925. Count Square Sum Triples](./CountSquareSumTriples.java) | LeetCode | Easy | A *square triple* (a, b, c) satisfies: a² + b² = c²;  1 ≤ a, b, c ≤ n . Return how many such ordered triples exist. |
| [3577. Count the Number of Computer Unlocking Permutations](./CountTheNumberOfComputerUnlockingPermutations.java) | LeetCode | Medium | A permutation P of [0…n−1] is a valid unlocking order if: • P[0] = 0  • For every index t>0, computer P[t] has some earlier position u<t such that: P[u] < P[t] AND complexity[P[u]] < complexity[P[t]] |
| [2147. Number of Ways to Divide a Long Corridor](./NumberOfWaysToDivideALongCorridor.java) | LeetCode | Hard | Count the segments and then see the available partitions possible |
| [2110. Number of Smooth Descent Periods of a Stock](./NumberOfSmoothDescentPeriodsOfAStock.java) | LeetCode | Medium | Count continuous decrement of value 1 in array |
| [1390. Four Divisors](./FourDivisors.java) | LeetCode | Medium | Sum of divisors of integers who have only 4 divisors | 
| [1266. Minimum Time Visiting All Points](./MinTimeVisitAllPoints.java) | LeetCode | Easy | use max(diff at both axis) to find shortest dist, usefull many places | 


## Implementation Notes
Solutions are written in **Java** with focus on:
- Modular-safe arithmetic
- Efficient precomputation (sieve, factorial arrays)
- Fast exponentiation patterns
- Space/time-optimized formula derivations

---
*Part of DSA Journey: A New Dawn 🌅*
