/*
 🔹 Problem: 3350. Adjacent Increasing Subarrays Detection II
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Approach: Iterate once through the array while tracking current and previous increasing run lengths.
   For each element, update maximum possible k as either currRun/2 or min(currRun, prevRun).
   This ensures detection of the largest k forming two adjacent increasing subarrays.
 🔹 Time Complexity: O(n)
 🔹 Space Complexity: O(1)
*/

import java.util.List;

class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        int max = 1, prevRun = 1, currRun = 1;
        for (int i = 1; i < n; i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                currRun++;
                max = Math.max(max, currRun / 2);
                max = Math.max(max, Math.min(currRun, prevRun));
            } else {
                prevRun = currRun;
                currRun = 1;
            }
        }
        return max;
    }
}