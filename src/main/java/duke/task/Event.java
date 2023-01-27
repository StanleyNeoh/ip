package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.storage.serializer.Serializer;
import duke.storage.serializer.TaskDeserializer;
import duke.storage.serializer.TaskSerializer;

/**
 * Represents an event with a start timing and an end timing
 */
public class Event extends Task {
    private static final String ICON = "E";
    private static final String DESCRIPTION_KEY = "description";
    private static final String COMPLETED_KEY = "completed";
    private static final String FROM_KEY = "from";
    private static final String TO_KEY = "to";
    private static final DateTimeFormatter RECEIVE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy kkmm");
    private static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern("dd-MMM-uuuu,EEE,hh:mma");

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Initialises an Event task.
     *
     * @param description Description of event.
     * @param completed Whether the event has been completed.
     * @param from Start time of event.
     * @param to End time of event.
     * @throws DukeException
     */
    public Event(String description, boolean completed, String from, String to) throws DukeException {
        super(description, completed);
        try {
            this.from = LocalDateTime.parse(from, RECEIVE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new DukeException("Could not parse 'from' as date time");
        }
        try {
            this.to = LocalDateTime.parse(to, RECEIVE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new DukeException("Could not parse 'to' as date time");
        }
    }

    /**
     * Returns whether {@code icon} belongs to a Event Task.
     *
     * @param icon Icon to be checked.
     * @return whether the {@code icon} belongs to a Event Task.
     */
    public static boolean hasIcon(String icon) {
        return icon.equals(ICON);
    }

    public static boolean canDeserialize(Serializer serializer) {
        return hasIcon(serializer.get(CATEGORY_KEY).toString());
    }

    public static TaskDeserializer getDeserializer() {
        return (TaskSerializer serializer) -> {
            String description = serializer.get(DESCRIPTION_KEY).toString();
            boolean completed = Boolean.parseBoolean(serializer.get(COMPLETED_KEY).toString());
            String from = serializer.get(FROM_KEY).toString();
            String to = serializer.get(TO_KEY).toString();
            return new Event(description, completed, from, to);
        };
    }

    @Override
    public String serialize() {
        Serializer ts = new TaskSerializer();
        ts.add(CATEGORY_KEY, ICON);
        ts.add(DESCRIPTION_KEY, description);
        ts.add(COMPLETED_KEY, completed);
        ts.add(FROM_KEY, from.format(RECEIVE_FORMAT));
        ts.add(TO_KEY, to.format(RECEIVE_FORMAT));
        return ts.toString();
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", ICON, super.toString(), from.format(PRINT_FORMAT),
                to.format(PRINT_FORMAT));
    }
}
