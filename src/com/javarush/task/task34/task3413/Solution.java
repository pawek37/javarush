package com.javarush.task.task34.task3413;

/* 
Кеш на основании SoftReference
*/

public class Solution {
    public static void main(String[] args) {
        SoftCache cache = new SoftCache();
        System.out.println("feature");
        System.out.println("feature");
        System.out.println("feature");
        System.out.println("feature");
        System.out.println("feature");
        System.out.println("feature1");
        System.out.println("feature2");
        for (long i = 0; i < 2_500_000; i++) {
            System.out.println("test");
            System.out.println("main branch");
            System.out.println("test");
            cache.put(i, new AnyObject(i, null, null));
        }
    }
}