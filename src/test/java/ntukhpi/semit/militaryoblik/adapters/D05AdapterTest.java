package ntukhpi.semit.militaryoblik.adapters;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class D05AdapterTest {

    @Test
    void testSetValues() {
        D05Adapter d05data = new D05Adapter();
        d05data.setVirtualValues(23);
        System.out.println(d05data);
    }

    @Test
    void createListData() {
        int numEmployee = (new Random()).nextInt(81)+20;
        List<D05Adapter> dataList = new ArrayList<>(numEmployee);
        for (int i=0;i<numEmployee;i++){
            D05Adapter d05data = new D05Adapter();
            d05data.setVirtualValues(i+1);
            dataList.add(d05data);
        }
        assertEquals(numEmployee,dataList.size());
        for (D05Adapter d05: dataList) {
            System.out.println(d05.toString());
        }

    }


}