package Heaps;
/*
 🔹 Problem: 2402. Meeting Rooms III
 🔹 Platform: LeetCode
 🔹 Difficulty: Medium
 🔹 Topics: Heap / Priority Queue, Simulation
 🔹 Link: https://leetcode.com/problems/meeting-rooms-iii/

 ------------------------------------------------------------
 📝 Problem Summary:

You are given n rooms (0 to n-1) and a list of meetings.
Each meeting is represented as [start, end) with unique start times.

Rules:
1. Assign meetings to the lowest-numbered available room.
2. If no room is free, delay the meeting until a room becomes available.
3. Delayed meetings keep the same duration.
4. When multiple meetings are waiting, earlier original start time gets priority.

Return the room number that hosted the most meetings.
If there is a tie, return the smallest room number.

 ------------------------------------------------------------
 ⚠️ Constraints:
 • 1 <= n <= 100
 • 1 <= meetings.length <= 10^5
 • 0 <= start < end <= 5 * 10^5
 • All start times are unique

 ------------------------------------------------------------
 💡 Approach (Optimal):

Use two priority queues:
1. freeRooms → min-heap of available room indices
2. busyRooms → min-heap ordered by (endTime, roomIndex)

Steps:
• Sort meetings by start time
• Release rooms whose meetings have ended
• Assign meeting to free room if available
• Otherwise, delay meeting using earliest finishing room
• Track meeting count per room

 ------------------------------------------------------------
 ⏱️ Time Complexity: O(m log n)
 ⏱️ Space Complexity: O(n)

 ------------------------------------------------------------
*/

import java.util.*;

public class MeetingRoomsIII {

    public int mostBooked(int n, int[][] meetings) {

        // Min-heap of free rooms (lowest room number first)
        PriorityQueue<Integer> freeRooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            freeRooms.offer(i);
        }

        // Min-heap of busy rooms: [roomNumber, endTime]
        PriorityQueue<long[]> busyRooms = new PriorityQueue<>(
            (a, b) -> {
                if (a[1] != b[1]) return Long.compare(a[1], b[1]);
                return Long.compare(a[0], b[0]);
            }
        );

        // Count meetings per room
        int[] meetingCount = new int[n];

        // Sort meetings by start time
        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));

        for (int[] meeting : meetings) {
            long start = meeting[0];
            long duration = meeting[1] - meeting[0];

            // Free rooms that have finished before current start
            while (!busyRooms.isEmpty() && busyRooms.peek()[1] <= start) {
                freeRooms.offer((int) busyRooms.poll()[0]);
            }

            if (!freeRooms.isEmpty()) {
                // Assign immediately
                int room = freeRooms.poll();
                meetingCount[room]++;
                busyRooms.offer(new long[]{room, start + duration});
            } else {
                // Delay meeting
                long[] earliest = busyRooms.poll();
                int room = (int) earliest[0];
                long newEndTime = earliest[1] + duration;

                meetingCount[room]++;
                busyRooms.offer(new long[]{room, newEndTime});
            }
        }

        // Find room with maximum meetings
        int bestRoom = 0;
        for (int i = 1; i < n; i++) {
            if (meetingCount[i] > meetingCount[bestRoom]) {
                bestRoom = i;
            }
        }

        return bestRoom;
    }
}
