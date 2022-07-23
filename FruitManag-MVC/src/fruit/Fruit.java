package fruit;

public class Fruit {
    private String fname;
    private  int fprice;
    private int fcount;
    private String fremark;
    private  int fid;
    public Fruit(int fid ,String fname, int fprice, int fcount, String fremark) {
        this.fid = fid;
        this.fname = fname;
        this.fprice = fprice;
        this.fcount = fcount;
        this.fremark = fremark;
    }

    public Fruit(String fname, int fprice, int fcount, String fremark) {
        this.fname = fname;
        this.fprice = fprice;
        this.fcount = fcount;
        this.fremark = fremark;
    }

    public Fruit(){}

    public String getFname() {
        return fname;
    }

    public int getFprice() {
        return fprice;
    }

    public int getFid() {
        return fid;
    }

    public int getFcount() {
        return fcount;
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

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }
}
