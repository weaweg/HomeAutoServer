package bbudzowski.homeautoserver.tables;

public class Device {
    public String id;
    public String ip;
    public String name;
    public String location;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s')",
                this.id, this.ip, this.name, this.location);
    }
}
