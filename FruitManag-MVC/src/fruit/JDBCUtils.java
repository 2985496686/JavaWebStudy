package fruit;

import java.lang.reflect.Field;
import java.sql.*;

public class JDBCUtils {
    static{
        //注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接对象
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Study2 ?rewriteBatchedStatements=true","root","111111");
    }

    /**
     *
     * @param conn 数据库连接对象
     * @param state 数据库操作对象
     * @param ps 预编译的数据库操作对象
     * @param rs 查询结果集
     */
    //关闭资源
    public static void close(Connection conn ,Statement state, PreparedStatement ps,ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(state != null){
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @description 对所有表都通用的一个查询方法
     * @param clazz 数据表对应的Java类
     * @param sql sql语句
     * @param args 通配符数组
     * @return 将记录转换为一个Java对象返回
     */
    public static<T> T queryForObject(Class<T>clazz , String sql,Object...args){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            //获取数据库连接对象
            conn = getConnection();
            //获取预编译的数据库操作对象
            ps = conn.prepareStatement(sql);
            //给 ？传参
            for(int i = 0; i < args.length; i++){
                ps.setObject(i+1,args[i]);
            }
            //获取查询结果集
            rs = ps.executeQuery();

            if(rs.next()){
                T emp = clazz.newInstance();
                //获取rs的元数据
                ResultSetMetaData rsmd = rs.getMetaData();
                //获取查询结果的列数
                int count = rsmd.getColumnCount();
                for(int i = 1; i <= count; i++){
                    //获取列的列名：rsmd.getColumnName(i),不常用，因为数据库中的列名经常与类中的属性名不相同
                    //String columnName = rsmd.getColumnName(i);

                    //获取列的别名，没有别名获取列名
                    String columnLabel = rsmd.getColumnLabel(i);
                    //通过反射指定emp的属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(emp,rs.getObject(i));
                }
                return emp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            close(conn,null,ps,rs);
        }
        return null;
    }
}