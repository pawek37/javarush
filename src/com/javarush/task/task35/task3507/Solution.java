package com.javarush.task.task35.task3507;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/

public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal>animalSet = new HashSet<>();
        File directory = new File(pathToAnimals);
        File [] files = directory.listFiles();
        if (files==null)return animalSet;
        MyClassLoader mcl = new MyClassLoader();
        for (File file : files){
            if (file.isFile() && file.getName().endsWith(".class")){
                try {
                    Class<?> clazz = mcl.findClass(file.getAbsolutePath());
                    if (Animal.class.isAssignableFrom(clazz)){
                        for (Constructor<?> constructor : clazz.getConstructors()){
                            if (constructor.getParameterCount()==0){
                                Animal animal = (Animal) constructor.newInstance();
                                animalSet.add(animal);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                }
            }
        }


        return null;
    }
     static class MyClassLoader extends ClassLoader{

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[]byteCode = Files.readAllBytes(Paths.get(name));
                return defineClass(null,byteCode,0, byteCode.length);
            } catch (IOException e) {
                throw new ClassNotFoundException();
            }
        }
    }
}
