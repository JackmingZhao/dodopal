package com.dodopal.card.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Test {
    
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        logger.info("aaa");
        int parm = 12;
        int[] testArray = {2, 5, 9, 13, 17, 20, 45, 57, 89};

        int retInt = -1;
        int low = 0;
        int high = testArray.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = testArray[mid];
            if (midVal < parm) {
                low = mid + 1;
            } else if (midVal > parm) {
                high = mid - 1;
            } else {
                retInt = mid;
                break;
            }
        }
        System.out.println(retInt);
    }
}
