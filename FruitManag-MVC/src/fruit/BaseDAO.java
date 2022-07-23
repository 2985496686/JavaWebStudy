package fruit;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {
    /**
     *
     * @param conn 数据库连接对象
     * @param sql sql语句
     * @param args 通配符对应的实际参数
     */
    public static void update(Connection conn,String sql,Object ...args){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //给通配符传参
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行sql语句
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                //执行出错时回滚事务
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            JDBCUtils.close(null,null,ps,null);
        }
    }

    /**
     *
     * @param conn 数据库连接对象
     * @param clazz 表所对应的Class对象
     * @param sql  sql语句
     * @param args 通配符对应的实际参数列表
     * @param <T> 泛型，需要传入表所对应的类型
     * @return 将查询结果以list集合的形式返回
     */
    public static <T> List<T> getForList(Connection conn,Class<T> clazz,String sql,Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            //通配符传参
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行sql语句
            rs = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while(rs.next()){
                //获取rs的元数据
                ResultSetMetaData rsmd = rs.getMetaData();
                //通过反射实例化一个对象，对应一条记录
                T object = clazz.newInstance();
                //获取列数
                int count = rsmd.getColumnCount();
                for (int i = 0; i < count; i++) {
                    //获取列的别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //通过反射获取类的属性，并且设置对象的该属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(object,rs.getObject(i+1));
                }
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.close(null,null,ps,rs);
        }
        return null;
    }
    public static <T> T getObject(Connection conn,Class<T> clazz,String sql,Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            //通配符传参
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //执行sql语句
            rs = ps.executeQuery();
            if(rs.next()){
                //获取rs的元数据
                ResultSetMetaData rsmd = rs.getMetaData();
                //通过反射实例化一个对象，对应一条记录
                T object = clazz.newInstance();
                //获取列数
                int count = rsmd.getColumnCount();
                for (int i = 0; i < count; i++) {
                    //获取列的别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //通过反射获取类的属性，并且设置对象的该属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(object,rs.getObject(i+1));
                }
                return object;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.close(null,null,ps,rs);
        }
        return null;
    }
    public static Object getValue(Connection conn,String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            //通配符传参
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行sql语句
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,ps,null);
        }
        return 0;
    }

}
