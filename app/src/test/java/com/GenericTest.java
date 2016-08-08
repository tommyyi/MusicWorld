package com;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/12.
 */
public class GenericTest
{
    @Test
    public void create() throws Exception
    {
        Generic<String,Integer,Boolean> generic= new Generic<>("string",100,Boolean.TRUE);
        print(generic);

        ArrayList<Generic<String,Integer,Boolean>> genericList = new ArrayList<>();
        genericList.add(new Generic<String, Integer, Boolean>("",1,Boolean.TRUE));

        List<String> stringList = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("");

        Map<String,String> stringStringMap=new HashMap<>();
        stringStringMap.put("tomy","35");
        String age=stringStringMap.get("tomy");
        System.out.println(age);
        test();
        test(0);
    }

    public void test()
    {

    }

    public void test(int index)
    {

    }

    public <T> T print()
    {
        T t=null;
        return t;
    }

    public <T> T  print(T generic)
    {
        return generic;
    }
}
