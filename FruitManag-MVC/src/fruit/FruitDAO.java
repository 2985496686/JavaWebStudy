package fruit;

import java.sql.Connection;
import java.util.List;

public class FruitDAO {

    public  void insert(Connection conn, Fruit fruit){
        String sql = "insert into fruit (fname,fprice,fcount,fremark) value(?,?,?,?)";
        BaseDAO.update(conn,sql,fruit.getFname(),fruit.getFprice(),fruit.getFcount(),fruit.getFremark());
    }

    public void update(Connection conn,Fruit fruit){
        String sql = "update fruit set fname = ? , fprice=?, fcount=?,fremark=? where fid=?";
        BaseDAO.update(conn,sql,fruit.getFname(),fruit.getFprice(),fruit.getFcount(),fruit.getFremark(),fruit.getFid());
    }

    public Fruit getFruitById(Connection conn, int fid){
        String sql = "select fid,fname,fprice,fcount,fremark from fruit where fid = ?";
        return BaseDAO.getObject(conn, Fruit.class,sql,fid);
    }

    public List<Fruit> getAll(Connection conn,int pageNo,String keyword){
        String sql = "select fid,fname,fprice,fcount,fremark from fruit where fname like ? or fremark like ? limit ?,5;";
        return BaseDAO.getForList(conn, Fruit.class,sql,"%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
    }

    public void delFruitById(Connection conn,int fid){
        String sql = "delete from fruit where fid = ?";
        BaseDAO.update(conn,sql,fid);
    }

    public long getPageCount(Connection conn,String keyword){
        String sql = "select count(*) from fruit where fname like ? or fremark like ?";
        Long pageCount= (Long)BaseDAO.getValue(conn, sql,"%"+keyword+"%","%"+keyword+"%");
        return  (pageCount+4)/5;
    }

}
