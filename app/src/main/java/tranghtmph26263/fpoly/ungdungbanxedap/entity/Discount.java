package tranghtmph26263.fpoly.ungdungbanxedap.entity;

public class Discount {
    private int id, value;
    private String code_name, detail, start_date, expiration_date;

    public Discount() {
    }

    public Discount(int id, int value, String code_name, String detail, String start_date, String expiration_date) {
        this.id = id;
        this.value = value;
        this.code_name = code_name;
        this.detail = detail;
        this.start_date = start_date;
        this.expiration_date = expiration_date;
    }
    public Discount( int value, String code_name, String detail, String start_date, String expiration_date) {
        this.value = value;
        this.code_name = code_name;
        this.detail = detail;
        this.start_date = start_date;
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", value=" + value +
                ", code_name='" + code_name + '\'' +
                ", detail='" + detail + '\'' +
                ", start_date='" + start_date + '\'' +
                ", expiration_date='" + expiration_date + '\'' +
                '}';
    }
}
