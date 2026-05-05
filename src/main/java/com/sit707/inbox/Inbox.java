package com.sit707.inbox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simplified OnTrack-style task inbox: list tasks for a student ID.
 *
 * <p>Student: Levin Joseph Poovakulath — ID s224877838.
 */
public final class Inbox {

    private static final Map<String, List<Task>> TASKS_BY_STUDENT;

    static {
        Map<String, List<Task>> m = new HashMap<>();
        m.put(
                "s224877838",
                List.of(
                        new Task(1, "Assignment 1", "submitted"),
                        new Task(2, "Lab 3", "submitted")));
        m.put("s456", List.of(new Task(3, "Portfolio", "graded")));
        TASKS_BY_STUDENT = Map.copyOf(m);
    }

    private Inbox() {}

    /**
     * Returns tasks for the given student ID. Unknown or blank IDs yield an empty list.
     */
    public static List<Task> getStudentTasks(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return List.of();
        }
        String key = studentId.trim();
        List<Task> tasks = TASKS_BY_STUDENT.get(key);
        if (tasks == null) {
            return List.of();
        }
        return tasks.stream()
                .map(t -> new Task(t.id(), t.title(), t.status()))
                .collect(Collectors.toUnmodifiableList());
    }
}
