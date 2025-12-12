/*
 🔹 Problem: 3433. Count Mentions Per User
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Simulation, Parsing, Online/Offline Tracking
 🔹 Link: https://leetcode.com/problems/count-mentions-per-user/

 ------------------------------------------------------------
 📝 Problem Statement:

You have `numberOfUsers` users (IDs 0..n−1), all initially ONLINE.

You are given `events[i]` of two types:

1. **MESSAGE timestamp mentions_string**
   - Mentions users based on:
     • "idX idY idZ ..." → mentions specific IDs  
     • "ALL" → mentions all users (online or offline)  
     • "HERE" → mentions only users currently ONLINE
   - Each mention increments counter; duplicates count.

2. **OFFLINE timestamp id**
   - User `id` goes offline for EXACTLY 60 units.
   - User automatically becomes online again at (timestamp + 60).

**Important rule:**  
If a user goes offline or comes online *exactly at the same timestamp as a message*,  
the status change happens *before processing the message*.

Return `mentions[i]` = total mentions of user i across all MESSAGE events.

 ------------------------------------------------------------
 📊 Examples:

Example 1:
Input:
  numberOfUsers = 2  
  events = [
    ["MESSAGE","10","id1 id0"],
    ["OFFLINE","11","0"],
    ["MESSAGE","71","HERE"]
  ]

Output: [2, 2]

Example 2:
Input:
  numberOfUsers = 2  
  events = [
    ["MESSAGE","10","id1 id0"],
    ["OFFLINE","11","0"],
    ["MESSAGE","12","ALL"]
  ]

Output: [2, 2]

Example 3:
Input:
  numberOfUsers = 2  
  events = [
    ["OFFLINE","10","0"],
    ["MESSAGE","12","HERE"]
  ]

Output: [0, 1]

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 ≤ numberOfUsers ≤ 100  
 • 1 ≤ events.length ≤ 100  
 • OFFLINE user is always online at that moment  
 • Mention count per message ≤ 100  
 • timestamps ≤ 1e5  

 ------------------------------------------------------------
 📌 Approach Summary:

We simulate the system while tracking:

📍 For each user:  
   - All timestamps when the user was *mentioned*  
   - All timestamps when the user went *offline*

📍 MESSAGE handling:
   - If "ALL": mention everyone  
   - If "HERE": mention only those currently ONLINE  
   - If "idX ...": parse IDs and mention them individually  

📍 OFFLINE handling:
   - Mark the user as offline from t to t+60  
   - All events at that same timestamp see this updated status  

📍 POST-PROCESSING:
   - For each user:
       For each offline window [t, t+60):
           Any "HERE" mention during that window must be removed
           (because HERE should not count offline users)

This allows precise correction of mentions.

Time complexity is small enough since n ≤ 100 and events ≤ 100.

 ------------------------------------------------------------
 🔹 Approach 1 (Simulation + Offline Interval Correction — Most Optimal)
   - Time Complexity: O(E²) (acceptable for E ≤ 100)
   - Space Complexity: O(E)

   🧠 Key Insight:
     "HERE" depends on online users; offline intervals override mentions.
     Therefore track all mentions, then retroactively subtract invalid ones.

   💡 Why it works:
     Small constraints allow full simulation + exact post correction.

 ------------------------------------------------------------
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class CountMentionsPerUser {

    public int[] countMentions(int numberOfUsers, List<List<String>> events) {

        int[] mentions = new int[numberOfUsers];

        // map[user] -> all timestamps where user was mentioned
        Map<Integer, TreeSet<Integer>> mentionTimes = new HashMap<>();

        // offline periods: offlineStartTimes[user] = list of start times
        Map<Integer, List<Integer>> offlineStart = new HashMap<>();

        for (int i = 0; i < numberOfUsers; i++) {
            mentionTimes.put(i, new TreeSet<>());
            offlineStart.put(i, new ArrayList<>());
        }

        // Track which users are currently ONLINE
        boolean[] online = new boolean[numberOfUsers];
        Arrays.fill(online, true);

        // Preprocess: events must be evaluated in given order,
        // but offline/online state changes take priority if timestamps match.
        // We will simulate carefully:
        for (List<String> e : events) {

            String type = e.get(0);
            int timestamp = Integer.parseInt(e.get(1));
            String third = e.get(2);

            if (type.equals("OFFLINE")) {
                // User goes offline from timestamp to timestamp+60
                int id = Integer.parseInt(third);
                online[id] = false;
                offlineStart.get(id).add(timestamp);
            }
            else { // MESSAGE
                String msg = third;

                if (msg.equals("ALL")) {
                    // Count mention for ALL users
                    for (int u = 0; u < numberOfUsers; u++) {
                        mentions[u]++;
                        mentionTimes.get(u).add(timestamp);
                    }
                }
                else if (msg.equals("HERE")) {
                    // Count mention only for ONLINE users
                    for (int u = 0; u < numberOfUsers; u++) {
                        if (online[u]) {
                            mentions[u]++;
                            mentionTimes.get(u).add(timestamp);
                        }
                    }
                }
                else {
                    // Parse "idX idY ..."
                    String[] parts = msg.split(" ");
                    for (String s : parts) {
                        int id = Integer.parseInt(s.substring(2));
                        mentions[id]++;
                        mentionTimes.get(id).add(timestamp);
                    }
                }
            }

            // AFTER handling the event, restore users who auto-return online
            for (int u = 0; u < numberOfUsers; u++) {
                List<Integer> offs = offlineStart.get(u);
                if (!offs.isEmpty()) {
                    int lastOff = offs.get(offs.size() - 1);
                    if (timestamp >= lastOff + 60) {
                        online[u] = true;
                    }
                }
            }
        }

        // POST-CORRECTION: Remove invalid HERE-mentions done while user was offline
        for (int user = 0; user < numberOfUsers; user++) {
            for (int off : offlineStart.get(user)) {
                int back = off + 60;

                // Mentions during [off, back)
                for (int t : mentionTimes.get(user).subSet(off, back)) {
                    // check if this mention was from a HERE message
                    for (List<String> e : events) {
                        if (e.get(0).equals("MESSAGE")
                                && Integer.parseInt(e.get(1)) == t
                                && e.get(2).equals("HERE")) {
                            mentions[user]--;
                            break;
                        }
                    }
                }
            }
        }

        return mentions;
    }
}

/*
 ------------------------------------------------------------
 🔹 Example Dry Run (Example 1):

Events:
10: MESSAGE id1 id0 → both +1
11: OFFLINE 0 → offline interval [11,71)
71: MESSAGE HERE → only user 1 is online → user1 +1

Final = [2,2]

 ------------------------------------------------------------
*/
