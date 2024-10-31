
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableTest {

    public static void main(String[] args) {
        String[] attribute = {"id", "name", "grade"};
        String[] domainStrings = {"Integer", "String", "Integer"};
        String[] key = {"id"};
        Class<?>[] domain = convertDomain(domainStrings);

        Table.MapType[] mapTypes = {
            Table.MapType.NO_MAP, 
            Table.MapType.HASH_MAP, 
            Table.MapType.TREE_MAP, 
            Table.MapType.BPTREE_MAP
        };
        
        TupleGenerator tupleGenerator = new TupleGeneratorImpl();

        // Define the schema for the tables
        tupleGenerator.addRelSchema("TestTable1", attribute, domainStrings, key, new String[][]{});
        tupleGenerator.addRelSchema("TestTable2", attribute, domainStrings, key, new String[][]{});

        int numberOfTuples = 1000;
        Comparable[][] generatedTuples1 = tupleGenerator.generate(new int[]{numberOfTuples})[0];
        Comparable[][] generatedTuples2 = tupleGenerator.generate(new int[]{numberOfTuples})[0];

        // Populate the tables with generated tuples for each map type
        for (Table.MapType mapType : mapTypes) {
            System.out.println("\nTesting with map type: " + mapType);
            Table.setMapType(mapType);

            List<Comparable[]> tuples1 = new ArrayList<>();
            for (Comparable[] tuple : generatedTuples1) {
                tuples1.add(tuple);
            }
            List<Comparable[]> tuples2 = new ArrayList<>();
            for (Comparable[] tuple : generatedTuples2) {
                tuples2.add(tuple);
            }

            Table table1 = new Table("TestTable1", attribute, domain, key, tuples1);
            Table table2 = new Table("TestTable2", attribute, domain, key, tuples2);

            // Define a condition for the select operation
            String condition = "grade >= 100000";

            // Define a contition for the join operation
            String conditionJoin = "id == id";

            // Test the select method and log the performance
            System.out.println("\nTesting: select(String condition)");
            testSelectMethod(table1, condition);

            // Test the join method and log the performance
            System.out.println("\nTesting: join(Table table2)");
            testJoinMethod(table1, table2);

            System.out.println("\nTesting: theta(Table table2)");
            testThetaJoinMethod(table1, table2, conditionJoin);

            // Test the select by key method and log the performance
            System.out.println("\nTesting: select(KeyType keyVal)");
            Integer testKeyVal = 1; // Example key to use in select by key
            testSelectByKeyMethod(table1, testKeyVal);

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
     * Tests the select(KeyType keyVal) method of the Table class.
     * @param table the Table instance to test
     * @param intKeyVal the Integer key value for testing select by key
     */
    private static void testSelectByKeyMethod(Table table, Integer intKeyVal) {
        int iterations = 5;
        long totalTime = 0;

        // Convert Integer to KeyType (or cast as necessary for KeyType compatibility)
        KeyType keyVal = createKeyType(intKeyVal);

        for (int i = 0; i < iterations + 1; i++) {
            long startTime = System.nanoTime();
            table.select(keyVal);
            long endTime = System.nanoTime();
            if (i > 0) {
                totalTime += (endTime - startTime);
            }
        }
        long averageTime = totalTime / iterations;
        System.out.println("Average time for select by key with key " + intKeyVal + " on " + table.getName() + ": " + averageTime + " ns");
    }

      /**
     * Helper method to create a KeyType from an Integer.
     * Adjust this method if KeyType has a different constructor or if a cast is required.
     * @param intKeyVal the integer value to convert to KeyType
     * @return a KeyType instance representing the integer key value
     */
    private static KeyType createKeyType(Integer intKeyVal) {
        // Replace this with appropriate conversion or casting if necessary
        return new KeyType(intKeyVal);  // Assuming KeyType has a constructor taking Integer
    }
        

    /**
     * Tests the join(Table table2) method of the Table class.
     * @param table1 the first Table instance to join
     * @param table2 the second Table instance to join with
     */
    
    private static void testJoinMethod(Table table1, Table table2) {
        int iterations = 5;
        long totalTime = 0;
        for (int i = 0; i < iterations + 1; i++) {
            long startTime = System.nanoTime();
            table1.join(table2);
            long endTime = System.nanoTime();
            if (i > 0) {
                totalTime += (endTime - startTime);
            }
        }
        long averageTime = totalTime / iterations;
        System.out.println("Average time for join with " + table1.getName() + " and " + table2.getName() + ": " + averageTime + " ns");
    }

    private static void testThetaJoinMethod(Table table1, Table table2, String condition) {
        int iterations = 5;
        long totalTime = 0;
        for (int i = 0; i < iterations + 1; i++) {
            long startTime = System.nanoTime();
            table1.join(condition, table2);
            long endTime = System.nanoTime();
            if (i > 0) {
                totalTime += (endTime - startTime);
            }
        }
        long averageTime = totalTime / iterations;
        System.out.println("Average time for join with " + table1.getName() + " and " + table2.getName() + ": " + averageTime + " ns");
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


