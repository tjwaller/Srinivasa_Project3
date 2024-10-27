
/****************************************************************************************
 * @file  Table.java
 *
 * @author   JOLPS SQUAD
 *
 * compile javac --enable-preview --release 22 *.java
 * run     java --enable-preview TableTest    
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableTest {

    public static void main(String[] args) {
        String[] attribute = {"id", "name", "grade"}; //idk what the attributes are tbh
        String[] domainStrings = {"Integer", "String", "Integer"};
        String[] key = {"id"};

        Class<?>[] domain = convertDomain(domainStrings);
        Table.MapType[] mapTypes = {Table.MapType.NO_MAP, Table.MapType.TREE_MAP, Table.MapType.HASH_MAP, Table.MapType.BPTREE_MAP};
        TupleGenerator tupleGenerator = new TupleGeneratorImpl();

        tupleGenerator.addRelSchema("TestTable", attribute, domainStrings, key, new String[][]{});

        int numberOfTuples = 1000;
        Comparable[][] generatedTuples = tupleGenerator.generate(new int[]{numberOfTuples})[0]; // Assuming TestTable is the first table

        System.out.println("Testing: select(String condition)"); 
        // Iterate over each map type and test the select operation
        for (Table.MapType mapType : mapTypes) {
            System.out.println("\nTesting with map type: " + mapType);
            Table.setMapType(mapType);

            List<Comparable[]> tuples = new ArrayList<>();
            for (Comparable[] tuple : generatedTuples) {
                tuples.add(tuple);
            }
            Table table = new Table("TestTable", attribute, domain, key, tuples);

            String condition = "grade >= 100000";
            testSelectMethod(table, condition);
        }
    }

    /**
     * Tests the select(String condition) method of the Table class.
     * @param table the Table instance to test
     * @param condition the condition for the select method
     */
    private static void testSelectMethod(Table table, String condition) {
        int iterations = 5;
        long totalTime = 0;
        for (int i = 0; i < iterations + 1; i++) {
            long startTime = System.nanoTime();

            table.select(condition);
            long endTime = System.nanoTime();
            if (i > 0) {  
                totalTime += (endTime - startTime);
            }
        }
        long averageTime = totalTime / iterations;
        System.out.println("Average time for select with " + table.getName() + ": " + averageTime + " ns");
    }

    /**
     * Converts string representations of types to their corresponding Class objects.
     * @param domainStrings the string representations of types
     * @return an array of Class<?> corresponding to the domainStrings
     */
    private static Class<?>[] convertDomain(String[] domainStrings) {
        HashMap<String, Class<?>> typeMap = new HashMap<>();
        typeMap.put("Integer", Integer.class);
        typeMap.put("String", String.class);
        typeMap.put("Double", Double.class);

        Class<?>[] domain = new Class[domainStrings.length];
        for (int i = 0; i < domainStrings.length; i++) {
            domain[i] = typeMap.get(domainStrings[i]);
        }
        return domain;
    }
}
