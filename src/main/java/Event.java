public class Event extends Task{
  protected String from;
  protected String to;

  public Event(String name, String from, String to) throws DukeException {
    super(name);
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (from: %s to: %s)", super.toString(), from, to);
  }
}
