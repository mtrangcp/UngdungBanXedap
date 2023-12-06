package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class Comment {
    private int id, user_id, product_id;
    private String content, time;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comment(int id, int user_id, int product_id, String content, String time) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.content = content;
        this.time = time;
    }

    public Comment() {
    }
}
