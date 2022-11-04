package com.testTask.ninja;

public class Main {

    public static void main(String[] args) {
        OsUpdates.getSystemUpdates().forEach(System.out::println);
    }
}
