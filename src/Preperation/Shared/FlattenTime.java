package Preperation.Shared;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FlattenTime {
    public static void main(String [] args){
        String filename = "time.ser";
        if(args.length > 0){
            filename = args[0];
        }
        PersistentTime time = new PersistentTime();
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(time);
            out.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }}