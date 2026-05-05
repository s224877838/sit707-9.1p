package com.sit707.inbox;

/**
 * A task row shown in the student inbox (id, title, workflow status).
 */
public record Task(int id, String title, String status) {}
