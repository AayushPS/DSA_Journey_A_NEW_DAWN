/*
 🔹 Problem: 2043. Simple Bank System
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: OOP, Simulation, System Design, Validation
 🔹 Link: https://leetcode.com/problems/simple-bank-system/

 ------------------------------------------------------------
 🔸 Problem Statement:

You are to implement a simple banking system to process transactions.

There are `n` accounts (1-indexed).  
The initial balance of each account is stored in a 0-indexed integer array `balance`.

You must support:
  🟢 deposit(account, money)
  🔴 withdraw(account, money)
  🟡 transfer(account1, account2, money)

A transaction is valid if:
  • The involved account number(s) exist.
  • The withdrawn or transferred amount ≤ current balance.

If valid → perform the operation and return `true`, else `false`.

 ------------------------------------------------------------
 🔸 Example:

Input:
["Bank", "withdraw", "transfer", "deposit", "transfer", "withdraw"]
[[[10, 100, 20, 50, 30]], [3, 10], [5, 1, 20], [5, 20], [3, 4, 15], [10, 50]]

Output:
[null, true, true, true, false, false]

Explanation:
Bank bank = new Bank([10,100,20,50,30]);
bank.withdraw(3,10);   ✅ true
bank.transfer(5,1,20); ✅ true
bank.deposit(5,20);    ✅ true
bank.transfer(3,4,15); ❌ false
bank.withdraw(10,50);  ❌ false

 ------------------------------------------------------------
 🔸 Constraints:
 • 1 <= n, account, account1, account2 <= 10^5
 • 0 <= balance[i], money <= 10^12
 • Up to 10^4 calls for each operation

 ------------------------------------------------------------
 🔹 Approach:

✅ Model each account with direct indexing (`balance[account-1]`)  
✅ Always validate account numbers before operation  
✅ Maintain O(1) per operation  
✅ Use long to avoid overflow  
✅ Ensure atomic rollback during invalid transfer  

 ------------------------------------------------------------
 🔹 Time Complexity:
   - deposit, withdraw, transfer → O(1)
 🔹 Space Complexity:
   - O(N) for storing balances
 ------------------------------------------------------------
*/

class Bank {
    private long[] balance;

    public Bank(long[] balance) {
        this.balance = balance;
    }

    // 🔸 Transfer money between accounts
    public boolean transfer(int account1, int account2, long money) {
        if (notValidAcc(account1) || notValidAcc(account2)) return false;
        if (!withdraw(account1, money)) return false;
        if (!deposit(account2, money)) {
            // rollback if deposit fails
            deposit(account1, money);
            return false;
        }
        return true;
    }

    // 🔸 Deposit money into account
    public boolean deposit(int account, long money) {
        if (notValidAcc(account)) return false;
        long oldBal = balance[account - 1];
        long newBal = oldBal + money;
        // overflow safety check
        if (newBal < oldBal) return false;
        balance[account - 1] = newBal;
        return true;
    }

    // 🔸 Withdraw money from account
    public boolean withdraw(int account, long money) {
        if (notValidAcc(account)) return false;
        long oldBal = balance[account - 1];
        if (oldBal < money) return false;
        balance[account - 1] = oldBal - money;
        return true;
    }

    // 🔸 Helper: Validate account number
    private boolean notValidAcc(int acc) {
        return acc < 1 || acc > balance.length;
    }
}

/**
 * Example usage:
 * Bank bank = new Bank(new long[]{10,100,20,50,30});
 * bank.withdraw(3,10);     // true
 * bank.transfer(5,1,20);   // true
 * bank.deposit(5,20);      // true
 * bank.transfer(3,4,15);   // false
 * bank.withdraw(10,50);    // false
 */
