package com.gmail.alvenn89;


import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class SomeClass  {
    @Save int anInt;
    @Save String string;
    @Save long aLong;

    public SomeClass() {
        this.anInt = 503;
        this.string = "someCls str";
        this.aLong = 1953l;
    }

    public void serialization(String fileName) throws IllegalAccessException{
        ArrayList<String> list = new ArrayList<String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            if (field.isAnnotationPresent(Save.class)){
                list.add(field.getName() + "=" + field.get(this));
            }
        }
         File file = new File(fileName);
        try{
           if (!file.exists()){
               file.createNewFile();
           }
        }
        catch (IOException e){
           System.out.println("Error in output file");
        }

        try (PrintWriter printWriter = new PrintWriter(file)){
            for (int i = 0; i < list.size() ; i++) {
            printWriter.println(list.get(i));
            }

        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }

    public void deserialization(String fileName) throws IllegalAccessException{
        Field[] fields = this.getClass().getDeclaredFields();
        ArrayList<String> list = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String temp="";
                for (;(temp = bufferedReader.readLine()) != null; ){
                list.add(temp);
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
            catch (IOException e){
                System.out.println("IO exception");
            }

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Save.class)) {
                    for (int i = 0; i < list.size(); i++) {
                        String[] pair = list.get(i).split("=");

                        if (field.getName().equals(pair[0])) {
                            if (field.getType() == int.class) {
                                field.setInt(this, Integer.parseInt(pair[1]));
                            }
                            if (field.getType() == long.class) {
                                field.setLong(this, Long.parseLong(pair[1]));
                            }
                            if (field.getType() == String.class) {
                                field.set(this, pair[1]);
                            }
                        }
                    }
                }
            }
        }
        else System.out.println("Input file not exists");
    }

    @Override
    public String toString() {
        return "anInt is " + anInt + ", string is '" + string + ", aLong is " + aLong + '}';

    }
}
