package com.juliar.pal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AndreiM on 1/1/2017.
 */
public class PrimitivesMap {
    private static final Map<String, String> map;
    static {
        map = new HashMap<>();
        map.put("fileOpen", "sys_file_open");
        map.put("printLine", "sys_print_line");
        map.put("fileWrite", "sys_file_write");
        map.put("print", "sys_print");
        map.put("available_memory", "sys_available_memory");
    }


    public static String getFunction(String name){
        String current = map.get(name);
        if(current == null) return name;
        return current;
    }

}
