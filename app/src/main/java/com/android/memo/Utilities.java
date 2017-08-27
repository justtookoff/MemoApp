package com.android.memo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Donghwee on 2017-08-27.
 */

public class Utilities {
    public static final String EXTRAS_NOTE_FILENAME = "EXTRAS_NOTE_FILENAME";
    public static final String FILE_EXTENSION = ".bin";

    public static boolean savePost(Context context, Memo memo){
        String fileName = String.valueOf(memo.getDateTime()) + FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try{
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(memo);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false; //tell the user something went wrong
        }

        return true;
    }

    @Nullable
    public static ArrayList<Memo> getAllSavedPosts(Context context) {
        ArrayList<Memo> memos = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> postFiles = new ArrayList<>();

        //add .bin files to the noteFiles list
        for(String file : filesDir.list()) {
            if(file.endsWith(FILE_EXTENSION)) {
                postFiles.add(file);
            }
        }

        //read objects and add to list of notes
        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < postFiles.size(); i++) {
            try{
                fis = context.openFileInput(postFiles.get(i));
                ois = new ObjectInputStream(fis);

                memos.add((Memo) ois.readObject());
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return memos;
    }

    /**
     * Loads a note file by its name
     * @param context Application's context
     * @param fileName Name of the note file
     * @return A Note object, null if something goes wrong!
     */
    public static Memo getNoteByFileName(Context context, String fileName) {

        File file = new File(context.getFilesDir(), fileName);
        if(file.exists() && !file.isDirectory()) { //check if file actually exist

            Log.v("UTILITIES", "File exist = " + fileName);

            FileInputStream fis;
            ObjectInputStream ois;

            try { //load the file
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                Memo memo = (Memo) ois.readObject();
                fis.close();
                ois.close();

                return memo;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }

    public static boolean deleteFile(Context context, String fileName) {
        File dirFiles = context.getFilesDir();
        File file = new File(dirFiles, fileName);

        if(file.exists() && !file.isDirectory()) {
            return file.delete();
        }

        return false;
    }
}
