package duke.task;

import org.junit.jupiter.api.Test;

import duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TodoTest {
    @Test
    public void testGetDescription() {
        try {
            String description = "write unit tests";
            Task task = new Todo(description, false);
            assertEquals(description, task.getDescription());
        } catch (DukeException e) {
            fail();
        }
    }

    @Test
    public void init_noName_exceptionThrown() {
        try {
            new Todo("", false);
            fail();
        } catch (DukeException e) {
            assertEquals("The description of a task cannot be empty", e.getMessage());
        }
    }

    @Test
    public void hasIcon_todoIcon_returnsTrue() {
        assertEquals(true, Todo.hasIcon("T"));
    }

    @Test 
    void hasIcon_notTodoIcon_returnsFalse() {
        assertEquals(false, Todo.hasIcon("not T"));
    }

    @Test
    void testMarkAndUnmark() {
        try {
            Task task = new Todo("write unit tests", false);
            assertEquals(false, task.isCompleted());
            task.mark();
            assertEquals(true, task.isCompleted());
            task.unmark();
            assertEquals(false, task.isCompleted());
        } catch (Exception e) {
            fail();
        }
    }
}
