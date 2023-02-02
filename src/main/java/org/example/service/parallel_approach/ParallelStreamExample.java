package org.example.service.parallel_approach;

import org.example.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.util.CommonUtil.delay;
import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.example.util.LoggerUtil.log;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/2/2023
 */
public class ParallelStreamExample
{
    public static void main(String[] args)
    {
        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        List<String> namesList = DataSet.namesList();
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(namesList);
        log("result list: " + resultList);
        timeTaken();
    }

    public List<String> stringTransform(List<String> nameList)
    {
        return nameList
                //.stream() //stream method do streaming in blocking way
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    //dynamically switch between sequential and parallel approach
    public List<String> stringTransform_1(List<String> nameList, boolean isParallel)
    {
        Stream<String> nameListStream = nameList.stream();
        if (isParallel)
        {
            nameListStream.parallel();
        }
        return nameListStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    private String addNameLengthTransform(String name)
    {
        delay(500);
        return name.length() + " - " + name;
    }

    //assigment

    public List<String> string_toLowerCase(List<String> nameList)
    {
        return nameList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
