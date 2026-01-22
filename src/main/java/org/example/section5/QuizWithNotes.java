package org.example.section5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizWithNotes {

    private Map<Integer, String> idToNameMap;

    private static long numberOfInstances = 0;

    public QuizWithNotes() {
        this.idToNameMap = new HashMap<>();
        numberOfInstances++;
     }

    public List<String> getAllNames() {
        int count = idToNameMap.size();
        List<String> allNames = new ArrayList<>();

        allNames.addAll(idToNameMap.values());

        return allNames;
   }

}

/*
* 1. idToNameMap reference is a class level member and is thus stored
* on the heap. It is thus shared by threads
*
* 2. numberOfInstances is on the heap due to being static and is thus
* shared by threads
*
* 3. The count var is a prim type allocated on the stack.
* Is therefore only accessible by the thread currently executing the
* getAllNames method.
*
* 4. allNames reference is located on the stack. But its object
* is located on the heap.
* */
