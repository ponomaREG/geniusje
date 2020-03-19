package com.msulov.geniusje.Levels.Managers;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Crossword_generator {

    public static int WIDTH = 10, HEIGHT = 10;

    private int count_words, current_count;
    private Random random = new Random();
    private HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap;
    private String[][] words = new String[][]{
            {"банан", "банан"},
            {"арбуз", "арбуз"},
            {"манго", "манго"},
            {"огурец", "огурец"},
    };


    public void setCount_Words(int count_words) {
        this.count_words = count_words;
    }

    public int getCount_Words() {
        return this.count_words;
    }

    @Deprecated
    private ArrayList getArrListOfWordsWithLetters() {
        int index_of_word_from_words;
        String word;
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        if (count_words > 0) {
            index_of_word_from_words = random.nextInt(words.length);
            word = words[index_of_word_from_words][0];
            for (String[] word_with_description : words) {
                if (!word_with_description[0].equals(word)) {

                }
            }
        }
        return arrayList;
    }

    @Deprecated
    private int getIndexOfChar(String word,char ch,int last_index){
        return word.indexOf(ch,last_index);
    }


    public HashMap<String,HashMap<String,ArrayList<int[]>>> getHashMap(){
        this.hashmap = new HashMap<>();
        return removeEmptyKeys(getPrepareIndexes(this.hashmap));
    }


    private HashMap<String,HashMap<String,ArrayList<int[]>>> removeEmptyKeys(HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap){
        for(String key:hashmap.keySet().toArray(new String[0])){
            for(String key_in: hashmap.get(key).keySet().toArray(new String[0])) {
                if (hashmap.get(key).get(key_in).isEmpty()) {
                    hashmap.get(key).remove(key_in);
                    continue;
                }
            }
        }
        return hashmap;
    }

    private HashMap<String,HashMap<String,ArrayList<int[]>>> getPrepareIndexes(HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap){
        if(hashmap.isEmpty()){
            String word = words[random.nextInt(words.length)][0];
            hashmap.put(word,new HashMap<String, ArrayList<int[]>>());
            pushIntoHashmapWord(hashmap,word);
            getPrepareIndexes(hashmap);
        }else{
            for(String key:hashmap.keySet().toArray(new String[0])){
                for(String key_in:hashmap.get(key).keySet().toArray(new String[0])) {
                    if (hashmap.size() < this.count_words) {
                        hashmap.put(key_in,new HashMap<String, ArrayList<int[]>>());
                        pushIntoHashmapWord(hashmap, key_in);
                    }
                }
            }
        }


        return hashmap;
    }



    private HashMap<String,HashMap<String,ArrayList<int[]>>> getRandomWordAndSubwords(HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap){
        HashMap<String,HashMap<String,ArrayList<int[]>>> new_hashmap = new HashMap<>();
        String random_key = hashmap.keySet().toArray(new String[0])[this.random.nextInt(hashmap.keySet().size())];
        new_hashmap.put(random_key,hashmap.get(random_key));
        return new_hashmap;
    }



    private HashMap<String,HashMap<String,ArrayList<int[]>>> pushIntoHashmapWord(HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap,String word){
        for(String[] word_in_words:words){
            if(!word_in_words[0].equals(word)){
                if((hashmap.containsKey(word_in_words[0]))){
                    if(hashmap.get(word_in_words[0]).containsKey(word)){
                        int[][] array = hashmap.get(word_in_words[0]).get(word).toArray(new int[0][0]);
                        hashmap.get(word).put(word_in_words[0],new ArrayList<>());
                        for(int[] indexes:array){
                            int[] array_2 = new int[]{indexes[1],indexes[0]};
                            hashmap.get(word).get(word_in_words[0]).add(array_2);
                        }
                    }else{
                        hashmap.get(word).put(word_in_words[0], getIndexesOfSameLetter(word,word_in_words[0]));

                    }

                }else{
                    hashmap.get(word).put(word_in_words[0],getIndexesOfSameLetter(word,word_in_words[0]));
                }
            }
        }
        return hashmap;
    }

    private ArrayList<int[]> getIndexesOfSameLetter(String word, String word_2){
        ArrayList<int[]> indexes = new ArrayList<>();
        for(int i = 0;i<word.toCharArray().length;i++){
            if(word_2.indexOf(word.charAt(i))>-1){
                int[] array = new int[]{i,word_2.indexOf(word.charAt(i))};
                indexes.add(array);
            }
        }
        return indexes;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private HashMap<String,HashMap<String,ArrayList<int[]>>> updateHashmap(HashMap<String,HashMap<String,ArrayList<int[]>>> hashmap){
        for(String key:hashmap.keySet().toArray(new String[0])){
            for(String key_in: hashmap.get(key).keySet().toArray(new String[0])) {
                if (hashmap.get(key).get(key_in).isEmpty()) {
                    hashmap.get(key).remove(key_in);
                    continue;
                }
                int[] current_ind = null;
                if (hashmap.get(key).get(key_in).size() > 1) {
                    int random_n = random.nextInt(hashmap.get(key).get(key_in).size());
                    current_ind = hashmap.get(key).get(key_in).toArray(new int[0][0])[random_n].clone();
                    int[] finalCurrent_ind = current_ind;
                    hashmap.get(key).get(key_in).removeIf(ind -> !((finalCurrent_ind[0] == ind[0]) && (finalCurrent_ind[1] == ind[1])));
                } else {
                    current_ind = hashmap.get(key).get(key_in).toArray(new int[0][0])[0].clone();
                }
                int[] current_ind_in_key = new int[]{current_ind[1], current_ind[0]};
                try {
                    hashmap.get(key_in).get(key).removeIf(ind -> !((current_ind_in_key[0] == ind[0]) && (current_ind_in_key[1] == ind[1])));
                }catch (Exception e){
                    continue;
                }
            }
        }
        return hashmap;
    }
}
