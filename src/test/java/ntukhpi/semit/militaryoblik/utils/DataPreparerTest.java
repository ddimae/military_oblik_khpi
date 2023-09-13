package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataPreparerTest {

    private final DataPreparer dataPreparer = new DataPreparer();

    @Test
    void testSortD5AdapterByUAAlphabetByName() {
        List<D05Adapter> result =  dataPreparer.sortD5AdapterByUAAlphabet(generateTestData(), "name");
        assertEquals("Андрій", result.get(0).getPib());
        assertEquals("Іван", result.get(1).getPib());
        assertEquals("Мстислав", result.get(2).getPib());
        assertEquals("Юрій", result.get(3).getPib());
        assertEquals("Яков", result.get(4).getPib());
    }

    @Test
    void testSortD5AdapterByUAAlphabetByTCK() {
        List<D05Adapter> result =  dataPreparer.sortD5AdapterByUAAlphabet(generateTestData(), "tck");
        assertEquals("Житомирський ТЦК", result.get(0).getTerCentr());
        assertEquals("Жмеринський ТЦК", result.get(1).getTerCentr());
        assertEquals("Кропивницький ТЦК", result.get(2).getTerCentr());
        assertEquals("Одеський ТЦК", result.get(3).getTerCentr());
        assertEquals("Одеський ТЦК", result.get(4).getTerCentr());
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
        d05Adapter1.setPib("Іван");
        d05Adapter1.setTerCentr("Жмеринський ТЦК");
        adapters.add(d05Adapter1);

        D05Adapter d05Adapter2 = new D05Adapter();
        d05Adapter2.setVirtualValues(2);
        d05Adapter2.setPib("Андрій");
        d05Adapter2.setTerCentr("Одеський ТЦК");
        adapters.add(d05Adapter2);

        D05Adapter d05Adapter3 = new D05Adapter();
        d05Adapter3.setVirtualValues(3);
        d05Adapter3.setPib("Мстислав");
        d05Adapter3.setTerCentr("Одеський ТЦК");
        adapters.add(d05Adapter3);

        D05Adapter d05Adapter4 = new D05Adapter();
        d05Adapter4.setVirtualValues(4);
        d05Adapter4.setPib("Яков");
        d05Adapter4.setTerCentr("Житомирський ТЦК");
        adapters.add(d05Adapter4);

        D05Adapter d05Adapter5 = new D05Adapter();
        d05Adapter5.setVirtualValues(2);
        d05Adapter5.setPib("Юрій");
        d05Adapter5.setTerCentr("Кропивницький ТЦК");
        adapters.add(d05Adapter5);

        return adapters;
    }
}