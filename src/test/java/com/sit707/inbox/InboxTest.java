package com.sit707.inbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Behaviour specification for the simplified OnTrack <strong>task inbox</strong> function
 * (list tasks for a given student ID), aligned with SIT707 Pass Task: TDD and CI.
 *
 * <p>These tests encode the client requirement: predictable inbox per ID, empty (safe) inbox for
 * bad input, rows with title and status, trimming, and isolation between students.
 */
@DisplayName("OnTrack task inbox — requirement-driven behaviour (TDD)")
class InboxTest {

    private static final String DEMO_STUDENT_ID = "s224877838";
    private static final String OTHER_STUDENT_ID = "s456";

    @Test
    @DisplayName(
            "Given unknown, blank, whitespace-only, or null student ID, when resolving the inbox, "
                    + "then return an empty list (safe default, no crash).")
    void invalidStudentIds_returnEmptyInbox() {
        assertTrue(Inbox.getStudentTasks("no-such-student").isEmpty());
        assertTrue(Inbox.getStudentTasks("").isEmpty());
        assertTrue(Inbox.getStudentTasks("   ").isEmpty());
        assertTrue(Inbox.getStudentTasks("\t").isEmpty());
        assertTrue(Inbox.getStudentTasks(null).isEmpty());
    }

    @Test
    @DisplayName(
            "Given a registered student ID, when they view the inbox, "
                    + "then OnTrack returns a non-empty list and each row has title and status.")
    void registeredStudent_seesTasksWithTitleAndStatus() {
        List<Task> inbox = Inbox.getStudentTasks(DEMO_STUDENT_ID);
        assertNotNull(inbox);
        assertFalse(inbox.isEmpty());
        for (Task row : inbox) {
            assertFalse(row.title().isBlank(), "title must be visible in inbox");
            assertFalse(row.status().isBlank(), "status must be visible in inbox");
        }
    }

    @Test
    @DisplayName(
            "Given a student ID with leading or trailing spaces, when viewing the inbox, "
                    + "then OnTrack matches the same student as when the ID is trimmed.")
    void studentIdIsTrimmedBeforeLookup() {
        List<Task> normalised = Inbox.getStudentTasks(DEMO_STUDENT_ID);
        List<Task> padded = Inbox.getStudentTasks("  " + DEMO_STUDENT_ID + "  ");
        assertEquals(normalised.size(), padded.size());
        assertEquals(normalised.get(0).title(), padded.get(0).title());
        assertEquals(normalised.get(0).status(), padded.get(0).status());
    }

    @Test
    @DisplayName(
            "Given one student’s ID, when loading the inbox, "
                    + "then tasks that belong only to another demo student do not appear.")
    void inboxDoesNotLeakOtherStudentsTasks() {
        List<Task> myInbox = Inbox.getStudentTasks(DEMO_STUDENT_ID);
        assertTrue(
                myInbox.stream().noneMatch(t -> "Portfolio".equals(t.title())),
                "Portfolio is demo data for the other student only");
    }

    @Test
    @DisplayName(
            "Given a different registered student, when they view the inbox, "
                    + "then they only see their own task list (demo: one portfolio item).")
    void otherRegisteredStudent_seesOnlyTheirTasks() {
        List<Task> inbox = Inbox.getStudentTasks(OTHER_STUDENT_ID);
        assertEquals(2, inbox.size());
        assertEquals("Portfolio", inbox.get(0).title());
        assertEquals("graded", inbox.get(0).status());
    }
}
