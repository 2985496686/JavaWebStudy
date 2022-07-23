package fruit;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            FruitDAO fruitDAO = new FruitDAO();
            long pageCount = fruitDAO.getPageCount(conn,"");
            System.out.println(pageCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
