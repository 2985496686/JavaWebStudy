package json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonTest  {
    public static void main(String[] args) {
        Gson gson = new Gson();
        //将普通的Java对象转换为json
        People people1 = new People("张三",21);
        System.out.println(people1);
        String p1Json = gson.toJson(people1);
        System.out.println(p1Json);

        //将json字符串转换为普通的java对
        People people = gson.fromJson(p1Json, people1.getClass());
        System.out.println(people);


        //将list对象转换为json(跟上面使用方法一样)
        List<People> list = new ArrayList<>();
        list.add(new People("张三",19));
        list.add(new People("lisi",21));
        String listJson = gson.toJson(list);
        System.out.println(listJson);

        //将json字符串转换为Java的list(使用泛型的)对象
        //方式一：List<People> list2 = gson.fromJson(listJson, new PeopleMapType().getType());
        List<People> list2 = gson.fromJson(listJson, new TypeToken<List<People>>() {}.getType());
        System.out.println(list2);
        People people2 = list2.get(1);



        //将Map集合转换为json字符串
        Map<Integer,People> map = new TreeMap<>();
        map.put(1,new People("zhangsan",19));
        map.put(2,new People("lisi",21));
        System.out.println(map);
        String mapJson = gson.toJson(map);
        System.out.println(mapJson);

        //将json字符串传为为Map集合
        Map<Integer,People> map2 = gson.fromJson(mapJson, new TypeToken<Map<Integer, People>>() {}.getType());
        People people3 = map2.get(1);
        System.out.println(people3);
        map2.forEach((k,v)->{
            System.out.println(k + "   " + v);
        });
    }
}
