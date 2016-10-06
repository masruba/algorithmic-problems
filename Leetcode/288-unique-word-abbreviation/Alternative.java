/*
An abbreviation of a word follows the form <first letter><number><last letter>. Below are some examples of word abbreviations:

a) it                      --> it    (no abbreviation)

     1
b) d|o|g                   --> d1g

              1    1  1
     1---5----0----5--8
c) i|nternationalizatio|n  --> i18n

              1
     1---5----0
d) l|ocalizatio|n          --> l10n
Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary. A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.

Example: 
Given dictionary = [ "deer", "door", "cake", "card" ]

isUnique("dear") -> false
isUnique("cart") -> true
isUnique("cane") -> false
isUnique("make") -> true
*/

import java.util.*;

class ValidWordAbbr {
    // key, unique
    HashMap<String, List<String>> map;

    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap<>();
        for(String s : dictionary){
            String key = getAbbr(s);
            if(!map.containsKey(key)){
                List<String> l = new ArrayList<>();
                l.add(s);
                map.put(key, l);
            }
            else
              map.get(key).add(s);
        }
    }
    
    public String getAbbr(String s){
        int n = s.length();
        StringBuilder r = new StringBuilder();
        if(n > 0)
          r.append(s.charAt(0));
        if(n > 3)
            r.append(Integer.toString(n-2));
        if(n >= 2)
            r.append(s.charAt(n-1));
        return r.toString();
    }
    public boolean isUnique(String word) {
        String key = getAbbr(word);
        if(!map.containsKey(key))
            return true;
        List<String> original = map.get(key);
        for(String o : original)
            if(word.compareTo(o) != 0)
                return false;
        return true;
    }

    public static void main(String[] args){
      String[] dictionary = {"deer", "door", "cake", "card", "hello"};
      ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
      System.out.println(vwa.isUnique("dear"));
      System.out.println(vwa.isUnique("cart"));
      System.out.println(vwa.isUnique("cane"));
      System.out.println(vwa.isUnique("make"));
      System.out.println(vwa.isUnique("hello"));
    }
}
// Your ValidWordAbbr object will be instantiated and called as such:
// ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
// vwa.isUnique("Word");
// vwa.isUnique("anotherWord");