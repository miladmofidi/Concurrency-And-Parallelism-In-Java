package org.example.service.blocking_approach;


import org.example.util.DataSet;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.CommonUtil.delay;
import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public class StringTransformExample_BlockingApproach
{

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        log("names : "+ names);

        names.forEach((name)->{
            String newValue = addNameLengthTransform(name);
            resultList.add(newValue);
        });
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
