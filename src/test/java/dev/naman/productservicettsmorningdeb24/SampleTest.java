package dev.naman.productservicettsmorningdeb24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    /*
    TestCase – A test case is nothing but a method...Refer ProductControllerTest and FakeStoreProductServiceTest
    For writing test, we have 3 ways  Arrange, Act, Assert
    We can use multiple assertions inside the test
     */

    @Test
    void testAddAndMulFunctionality() {
        //Step 1: Arrange -> Arrange the input params
        //add(a,b) = a + b
        int a = 1, b = 2;
        //Step 2: Act -> Call the method
        //we didn't have add method  as add(a,b) and we have to hard coded the value.
        //So, explicitly do it by add a + b and store in reponseForAdd
        int responseForAdd = a + b;
        int responseForMul = a * b;
        int expectedOutputForAdd = 3;
        int expectedOutputForMul = 2;
        //Step 3: Assert -> Validate the expected output is equal to the actual output
        //assert responseForAdd == expectedOutput;

        //For assert, we have to use assertion library
        assertEquals(expectedOutputForAdd, responseForAdd);//expected output, actual output
        assertEquals(expectedOutputForMul, responseForMul);//expected output, actual output

    }
}