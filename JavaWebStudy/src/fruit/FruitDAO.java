package fruit;

import java.sql.Connection;
import java.util.List;

public class FruitDAO {

    public  static void insert(Connection conn,Fruit fruit){
        String sql = "insert into fruit (fname,fprice,fcount,fremark) value(?,?,?,?)";
        BaseDAO.update(conn,sql,fruit.getFname(),fruit.getFprice(),fruit.getCount(),fruit.getFremark());
    }

    public static void update(Connection conn,String fieldName,Object value){
        String sql = "update fruit set ? = ?";
        BaseDAO.update(conn,sql,fieldName,value);
    }

    public static Fruit getFruitByName(Connection conn,String fname){
        String sql = "select fname,fprice,fcount,fremark from fruit where fname = ?";
        return BaseDAO.getObject(conn,Fruit.class,sql,fname);
    }

    public static List<Fruit> getAll(Connection conn){
        String sql = "select fname,fprice,fcount,fremark from fruit";
        return BaseDAO.getForList(conn,Fruit.class,sql);
    }
}
