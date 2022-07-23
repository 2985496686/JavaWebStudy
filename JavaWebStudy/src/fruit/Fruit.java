package fruit;

public class Fruit {
    private String fname;
    private  int fprice;
    private int count;
    private String fremark;

    public Fruit(String fname, int fprice, int count, String fremark) {
        this.fname = fname;
        this.fprice = fprice;
        this.count = count;
        this.fremark = fremark;
    }

    public String getFname() {
        return fname;
    }

    public int getFprice() {
        return fprice;
    }

    public int getCount() {
        return count;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFprice(int fprice) {
        this.fprice = fprice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }
}
