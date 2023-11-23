package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class Category {
    private int id, active;
    private String name;

    public Category(int id, int active, String name) {
        this.id = id;
        this.active = active;
        this.name = name;
    }

    public Category() {
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
