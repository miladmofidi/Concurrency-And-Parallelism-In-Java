package org.example.service.parallel_approach;


import org.example.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/2/2023
 */
class ParallelStreamExampleTest
{
    ParallelStreamExample parallelStreamExample = new ParallelStreamExample();

    @Test
    void stringTransform()
    {
        //given
        List<String> inputList = DataSet.namesList();
        //when
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(inputList);
        timeTaken();
        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));

    }

    @ParameterizedTest
    @ValueSource(booleans = {false,true})
    void stringTransform_1(boolean isParallel)
    {
        //given
        List<String> inputList = DataSet.namesList();
        //when
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform_1(inputList, isParallel);
        timeTaken();
        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(name.contains("-")));

    }
    @Test
    void string_toLowerCase()
    {
        //given
        List<String> inputList = DataSet.namesList();
        //when
        startTimer();
        List<String> resultList = parallelStreamExample.string_toLowerCase(inputList);
        timeTaken();
        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> assertTrue(isAllLowerCase(name)));

    }

    //checking string is in just lower case
    public boolean isAllLowerCase(String word) {
        if ( word == null ) return false; // this just to avoid NPE
        return word.toLowerCase().equals(word);
    }
}