/*
 🔹 Problem: 3606. Coupon Code Validator
 🔹 Platform: LeetCode
 🔹 Difficulty: Easy
 🔹 Topics: String Processing, Filtering, Sorting, Validation
 🔹 Link: https://leetcode.com/problems/coupon-code-validator/

 ------------------------------------------------------------
 📝 Problem Statement:

Given arrays:
 - code[i]: coupon identifier string  
 - businessLine[i]: business category  
 - isActive[i]: boolean indicating whether coupon is active

A coupon is valid iff:
  1. code[i] is non-empty AND contains only:
        - letters (a–z, A–Z)
        - digits  (0–9)
        - underscore "_"  
  2. businessLine[i] ∈ {"electronics", "grocery", "pharmacy", "restaurant"}
  3. isActive[i] == true

Return all valid coupon codes sorted:
   1. By businessLine in this exact order:
         electronics → grocery → pharmacy → restaurant
   2. Then by lexicographical order within each category.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input:
  code = ["SAVE20","","PHARMA5","SAVE@20"]
  businessLine = ["restaurant","grocery","pharmacy","restaurant"]
  isActive = [true,true,true,true]

Output: ["PHARMA5","SAVE20"]

Example 2:
Input:
  code = ["GROCERY15","ELECTRONICS_50","DISCOUNT10"]
  businessLine = ["grocery","electronics","invalid"]
  isActive = [false,true,true]

Output: ["ELECTRONICS_50"]

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ n ≤ 100  
 • code[i] length ≤ 100  
 • businessLine[i] length ≤ 100  
 • Characters are ASCII printable  
 • isActive[i] ∈ {true, false}

 ------------------------------------------------------------
 📌 Approach Summary:

✅ **Goal:** Filter, validate, and output sorted coupon codes.

📍 **Approach 1 (Category Bucketing + Custom Validation — Most Optimal)**

Steps:
1. Validate code:
     - must be non-empty
     - must contain only alphanumeric or '_'  
2. Validate business line:
     - must belong to the allowed 4 categories  
3. Validate active status:
     - must be true  
4. Store into separate lists for each category.
5. Sort each list lexicographically.
6. Concatenate in required category priority order.

Why optimal?
- n ≤ 100 → trivial cost
- Sorting small lists is efficient
- Clean direct filtering logic

Time Complexity: O(n log n) due to sorting  
Space Complexity: O(n)

 ------------------------------------------------------------
 🔹 Approach 1 (✅ Category Bucketing + String Validation — Most Optimal)
   - Time Complexity: O(n log n)
   - Space Complexity: O(n)

   🧠 Key Insight:
     Pre-sorting by category order is easiest via separate buckets.

   💡 Why it works:
     The required category ordering is fixed; sorting inside buckets is straightforward.

 ------------------------------------------------------------
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CouponCodeValidator {

    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {

        List<String> result = new ArrayList<>();
        List<String> electronics = new ArrayList<>();
        List<String> grocery     = new ArrayList<>();
        List<String> pharmacy    = new ArrayList<>();
        List<String> restaurant  = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {

            String c = code[i];

            // Reject empty or invalid code
            if (c.isEmpty() || !isValidCode(c)) continue;

            String b = businessLine[i];
            boolean active = isActive[i];

            if (!active) continue; // must be active

            switch (b) {
                case "electronics": electronics.add(c); break;
                case "grocery":     grocery.add(c);     break;
                case "pharmacy":    pharmacy.add(c);    break;
                case "restaurant":  restaurant.add(c);  break;
                default: continue; // invalid business line
            }
        }

        // Sort each category lexicographically
        Collections.sort(electronics);
        Collections.sort(grocery);
        Collections.sort(pharmacy);
        Collections.sort(restaurant);

        // Append categories in required order
        result.addAll(electronics);
        result.addAll(grocery);
        result.addAll(pharmacy);
        result.addAll(restaurant);

        return result;
    }

    private boolean isValidCode(String s) {
        for (char ch : s.toCharArray()) {
            if (!(Character.isLetterOrDigit(ch) || ch == '_')) {
                return false;
            }
        }
        return true;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run:

code = ["SAVE20","","PHARMA5","SAVE@20"]
businessLine = ["restaurant","grocery","pharmacy","restaurant"]
isActive = [true,true,true,true]

Valid:
  "SAVE20" → restaurant
  "PHARMA5" → pharmacy

Sorted:
  pharmacy → ["PHARMA5"]
  restaurant → ["SAVE20"]

Final output = ["PHARMA5","SAVE20"]

 ------------------------------------------------------------
*/
