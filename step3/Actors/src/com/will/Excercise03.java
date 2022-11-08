package com.will;


import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Excercise03 {
    public static void main(String[] args) throws Exception{
        try {
        	if(args.length < 2 || args.length > 3) {
        		System.out.println("the paramethers should be 2 or 3: filepath, actorA and/or actorB");
                return;
        	}
        	String path = args[0];
        	Reader reader = Files.newBufferedReader(Paths.get(path));
            Type collectionType = new TypeToken<Collection<Movie>>(){}.getType();
            List<Movie> movies = new Gson().fromJson(reader, collectionType);
        	String actorA = args[1];
        	String actorB = "";
            if(args.length == 2) {
        		actorB = "Kevin Bacon";
        	} else {
        		actorB = args[2];
        	}
            boolean find = false;
        	for(int i = 0; i < movies.size(); i++) {
    			String[] casts = movies.get(i).getCast();
    			if(casts != null && casts.length > 0 ) {
    				if(find01(casts, actorA, actorB)) {
    					find = true;
    					System.out.println("1");
    					break;
    				}
    			}
    		}
        	if(!find) {
        		for(int i = 0; i < movies.size(); i++) {
        			String[] casts = movies.get(i).getCast();
        			if(casts != null && casts.length > 0 ) {
        				if(find02(casts, actorA)) {
        					for(int j = 0; j < casts.length; j++) {
        						String[] casts2 = movies.get(i).getCast();
        						if(casts2 != null && casts2.length > 0 ) {
        							for(int k=0; k < casts2.length; k++) {
            							if(find01(casts2, casts[j], actorB)) {
            								find = true;
            								System.out.println("2");
            		    					break;
            							}
            						}
        						}
        					}
        				}
        			}
        		}
        	}
        	if(!find) {
        		System.out.println(actorA + " did not star in a movie in the data provided.");
        	}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean find01(String[] casts, String actorA, String actorB) {
    	boolean findA = false;
    	boolean findB = false;
    	for(int i = 0; i < casts.length; i++) {
    		if(actorA.equals(casts[i])){
    			findA = true;
    		}
    		if(actorB.equals(casts[i])){
    			findB = true;
    		}
    	}
    	if(findA && findB) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static boolean find02(String[] casts, String actorA) {
    	boolean findA = false;
    	for(int i = 0; i < casts.length; i++) {
    		if(actorA.equals(casts[i])){
    			findA = true;
    		}
    	}
    	if(findA) {
    		return true;
    	} else {
    		return false;
    	}
    }
}

