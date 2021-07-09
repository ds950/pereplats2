package com.firstprog.pereplats.domain;

public class Inicial {

    public static String getInitials(String str) {
/*        String[] tempArray = str.split(" ");
        String rezult = tempArray[0] + " ";
        for (int i = 0; i < tempArray.length; i++) {*/

        String rezult  = str.trim().substring(0, 1).toUpperCase() + ".";
        //}
        return rezult.trim();
    }

}


