package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataPreparerTest {

    private final DataPreparer dataPreparer = new DataPreparer();

    @Test
    void testSortD5AdapterByUAAlphabetByName() {
        List<D05Adapter> result =  dataPreparer.sortD5AdapterByUAAlphabet(generateTestData(), "name");
        assertEquals("Андрій", result.get(0).getColumn03());
        assertEquals("Іван", result.get(1).getColumn03());
        assertEquals("Мстислав", result.get(2).getColumn03());
        assertEquals("Юрій", result.get(3).getColumn03());
        assertEquals("Яков", result.get(4).getColumn03());
    }

    @Test
    void testSortD5AdapterByUAAlphabetByTCK() {
        List<D05Adapter> result =  dataPreparer.sortD5AdapterByUAAlphabet(generateTestData(), "tck");
        assertEquals("Житомирський ТЦК", result.get(0).getColumn13());
        assertEquals("Жмеринський ТЦК", result.get(1).getColumn13());
        assertEquals("Кропивницький ТЦК", result.get(2).getColumn13());
        assertEquals("Одеський ТЦК", result.get(3).getColumn13());
        assertEquals("Одеський ТЦК", result.get(4).getColumn13());
    }

    @Test
    void testLisToArray() {
        String[][] result = dataPreparer.listToArray(generateTestData());
        assertEquals("Іван", result[0][2]);
        assertEquals("Андрій", result[1][2]);
        assertEquals("Мстислав", result[2][2]);
        assertEquals("Яков", result[3][2]);
        assertEquals("Юрій", result[4][2]);
    }

    public List<D05Adapter> generateTestData() {
        List<D05Adapter> adapters = new ArrayList<>();
        D05Adapter d05Adapter1 = new D05Adapter();
        d05Adapter1.setVirtualValues(1);
        d05Adapter1.setColumn03("Іван");
        d05Adapter1.setColumn13("Жмеринський ТЦК");
        adapters.add(d05Adapter1);

        D05Adapter d05Adapter2 = new D05Adapter();
        d05Adapter2.setVirtualValues(2);
        d05Adapter2.setColumn03("Андрій");
        d05Adapter2.setColumn13("Одеський ТЦК");
        adapters.add(d05Adapter2);

        D05Adapter d05Adapter3 = new D05Adapter();
        d05Adapter3.setVirtualValues(3);
        d05Adapter3.setColumn03("Мстислав");
        d05Adapter3.setColumn13("Одеський ТЦК");
        adapters.add(d05Adapter3);

        D05Adapter d05Adapter4 = new D05Adapter();
        d05Adapter4.setVirtualValues(4);
        d05Adapter4.setColumn03("Яков");
        d05Adapter4.setColumn13("Житомирський ТЦК");
        adapters.add(d05Adapter4);

        D05Adapter d05Adapter5 = new D05Adapter();
        d05Adapter5.setVirtualValues(2);
        d05Adapter5.setColumn03("Юрій");
        d05Adapter5.setColumn13("Кропивницький ТЦК");
        adapters.add(d05Adapter5);

        return adapters;
    }
}