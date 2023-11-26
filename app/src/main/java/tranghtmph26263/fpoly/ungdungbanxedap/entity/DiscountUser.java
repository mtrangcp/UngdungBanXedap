package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class DiscountUser {
    private int id, discount_id, user_id, status;

    public DiscountUser() {
    }

    public DiscountUser(int id, int discount_id, int user_id, int status) {
        this.id = id;
        this.discount_id = discount_id;
        this.user_id = user_id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DiscountUser{" +
                "id=" + id +
                ", product_id=" + discount_id +
                ", user_id=" + user_id +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
