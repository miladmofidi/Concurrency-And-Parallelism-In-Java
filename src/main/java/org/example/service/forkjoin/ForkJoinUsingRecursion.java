package org.example.service.forkjoin;


import org.example.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static org.example.util.CommonUtil.delay;
import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>>
{
    List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList)
    {
        this.inputList = inputList;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        forkJoinPool.invoke(forkJoinUsingRecursion);

        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    //compute method is a where we should do fork and join operations
    @Override
    protected List<String> compute()
    {
        //we need a condition to break the process, since we are splitting the list to the left and right part at the end we will have a list with size 1 so this is where it means the splitting has completed and enough
        if (inputList.size() <= 1){
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name-> resultList.add(addNameLengthTransform(name)));
            return resultList;
        }
        //at the first we should split a input data to the chunks
        int midPoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
        //update input list to other remained part of its (right side of its)
        inputList = inputList.subList(midPoint,inputList.size());
        //Recursive call the compute method to divide the updated input list into right part of list
        List<String> rightResult = compute(); //recursive call compute method to divide the list in 2 part again
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
