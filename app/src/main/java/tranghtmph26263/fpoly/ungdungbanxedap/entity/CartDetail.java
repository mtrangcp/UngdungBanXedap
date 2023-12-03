package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class CartDetail {
    private int id, user_id, product_id;
    private int price, quantity;

    public CartDetail() {
    }

    public CartDetail(int id, int user_id, int product_id, int price, int quantity) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
    }

    public CartDetail(String objectString) {
        // Phân tích chuỗi và đặt giá trị cho các trường
        String[] parts = objectString.split(", ");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            switch (key) {
                case "id":
                    this.id = Integer.parseInt(value);
                    break;
                case "user_id":
                    this.user_id = Integer.parseInt(value);
                    break;
                case "product_id":
                    this.product_id = Integer.parseInt(value);
                    break;
                case "price":
                    this.price = Integer.parseInt(value);
                    break;
                case "quantity":
                    this.quantity = Integer.parseInt(value);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
