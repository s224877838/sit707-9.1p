"""Unit tests for simplified OnTrack task inbox (TDD-friendly, behaviour-focused)."""

from inbox import get_student_tasks


def test_unknown_student_returns_empty_list():
    assert get_student_tasks("unknown") == []


def test_blank_student_id_returns_empty_list():
    assert get_student_tasks("") == []
    assert get_student_tasks("   ") == []


def test_known_student_returns_tasks():
    tasks = get_student_tasks("s224877838")
    assert len(tasks) >= 1
    assert tasks[0]["title"] == "Assignment 1"
    assert tasks[0]["status"] == "submitted"


def test_second_student_has_expected_task():
    tasks = get_student_tasks("s456")
    assert len(tasks) == 1
    assert tasks[0]["title"] == "Portfolio"
