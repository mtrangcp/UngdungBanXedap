package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class Bill {
    int id, discount_id, user_id, temp_price, real_price,status;
    String address, user_fullname, created_date, phone,detail;

    public Bill() {
    }

    public Bill(int id, int discount_id, int user_id, int temp_price, int real_price, int status, String address, String user_fullname, String created_date, String phone, String detail) {
        this.id = id;
        this.discount_id = discount_id;
        this.user_id = user_id;
        this.temp_price = temp_price;
        this.real_price = real_price;
        this.status = status;
        this.address = address;
        this.user_fullname = user_fullname;
        this.created_date = created_date;
        this.phone = phone;
        this.detail = detail;
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

    public int getTemp_price() {
        return temp_price;
    }

    public void setTemp_price(int temp_price) {
        this.temp_price = temp_price;
    }

    public int getReal_price() {
        return real_price;
    }

    public void setReal_price(int real_price) {
        this.real_price = real_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
