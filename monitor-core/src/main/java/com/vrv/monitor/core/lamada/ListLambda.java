package com.vrv.monitor.core.lamada;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ListLambda {

    public static void main(String[] args) {
        List<CtbQuestionSuitableGrade> list = new ArrayList<>();
        CtbQuestionSuitableGrade item1 = new CtbQuestionSuitableGrade();
        item1.setQuestionId(1);
        item1.setGradeTerm(9);

        CtbQuestionSuitableGrade item2 = new CtbQuestionSuitableGrade();
        item2.setQuestionId(1);
        item2.setGradeTerm(10);

        CtbQuestionSuitableGrade item3 = new CtbQuestionSuitableGrade();
        item3.setQuestionId(2);
        item3.setGradeTerm(9);

        CtbQuestionSuitableGrade item4 = new CtbQuestionSuitableGrade();
        item4.setQuestionId(3);
        item4.setGradeTerm(20);

        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

//        Map<Integer,List<CtbQuestionSuitableGrade>> gradeMap = list.stream().collect(Collectors.groupingBy(CtbQuestionSuitableGrade::getQuestionId));

        Map<Integer, List<Integer>> gradeMap = list.stream().
                collect(Collectors.groupingBy(CtbQuestionSuitableGrade::getQuestionId, Collectors.mapping(CtbQuestionSuitableGrade::getGradeTerm, Collectors.toList())));

        System.out.println("end it");
//        Map<Integer,Object> map = new HashMap<>();
//        gradeMap.forEach((i,l)->(map.put(i,l.stream().map(g -> g.getGradeTerm()).collect(toList())));

    }
}