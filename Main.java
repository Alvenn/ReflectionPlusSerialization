package com.gmail.alvenn89;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IllegalAccessException{
        SomeClass object = new SomeClass();

        try {
            object.getClass().getMethod("serialization", String.class).invoke(object, "A.txt");
        }
        catch (NoSuchMethodException e){
            System.out.println(e);
        }
        catch (InvocationTargetException e){
            System.out.println(e);
        }
    }
}
