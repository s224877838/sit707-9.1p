"""
Simplified OnTrack-style task inbox: list submitted tasks for a student ID.
"""

from __future__ import annotations

# Demo in-memory store (no database).
_TASKS_BY_STUDENT: dict[str, list[dict[str, str | int]]] = {
    "s123": [
        {"id": 1, "title": "Assignment 1", "status": "submitted"},
        {"id": 2, "title": "Lab 3", "status": "submitted"},
    ],
    "s456": [
        {"id": 3, "title": "Portfolio", "status": "graded"},
    ],
}


def get_student_tasks(student_id: str) -> list[dict[str, str | int]]:
    """
    Return tasks for the given student ID.

    Unknown or blank IDs return an empty list.
    """
    if not student_id or not str(student_id).strip():
        return []
    key = str(student_id).strip()
    tasks = _TASKS_BY_STUDENT.get(key)
    if tasks is None:
        return []
    return [dict(t) for t in tasks]
