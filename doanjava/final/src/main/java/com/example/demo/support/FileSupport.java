package com.example.demo.support;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSupport {
    public static boolean checkFileExist(String location){
        try{
            Path path = Paths.get(location);

            if(Files.exists(path) == true)
                return true;
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }
    public static void delete_file(String location)
    {
        File file = new File(location);
        if(file.delete() == true){

        }
        else {
            //System.out.println("failure");
        }
    }
}
