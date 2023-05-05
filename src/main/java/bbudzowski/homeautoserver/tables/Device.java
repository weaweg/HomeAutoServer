package bbudzowski.homeautoserver.tables;

public class Device {
    public String id;
    public String name;
    public String location;

    public String toQuery() {
        return String.format("('%s', '%s', '%s')",
                this.id, this.name, this.location);
    }
}
