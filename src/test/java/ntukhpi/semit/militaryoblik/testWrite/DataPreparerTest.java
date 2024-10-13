package ntukhpi.semit.militaryoblik.testWrite;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.utils.DataPreparer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataPreparerTest {

    private final DataPreparer dataPreparer = new DataPreparer();

    @Test
    void testSortD5AdapterByUAAlphabetByName() {
        Map<String, List<D05Adapter>> result = dataPreparer.sortD5AdapterByUAAlphabet(generateTestDataMap(), "name");
        assertEquals("Андрій", result.get("Одеський ТЦК").get(0).getPib());
        assertEquals("Іван", result.get("Житомирський ТЦК").get(0).getPib());
        assertEquals("Мстислав", result.get("Одеський ТЦК").get(1).getPib());
        assertEquals("Юрій", result.get("Кропивницький ТЦК").get(0).getPib());
        assertEquals("Яков", result.get("Житомирський ТЦК").get(1).getPib());
    }

    @Test
    void testSortD5AdapterByUAAlphabetByTCK() {
        Map<String, List<D05Adapter>> result = dataPreparer.sortD5AdapterByUAAlphabet(generateTestDataMap(), "tck");
        assertEquals("Андрій", result.get("Одеський ТЦК").get(0).getPib());
        assertEquals("Іван", result.get("Житомирський ТЦК").get(0).getPib());
        assertEquals("Мстислав", result.get("Одеський ТЦК").get(1).getPib());
        assertEquals("Юрій", result.get("Кропивницький ТЦК").get(0).getPib());
        assertEquals("Яков", result.get("Житомирський ТЦК").get(1).getPib());
    }

    @Test
    void testLisToArray() {
        String[][] result = dataPreparer.listToArray(generateTestDataList());
        assertEquals("Іван", result[0][2]);
        assertEquals("Андрій", result[1][2]);
        assertEquals("Мстислав", result[2][2]);
        assertEquals("Яков", result[3][2]);
        assertEquals("Юрій", result[4][2]);
    }

    public Map<String, List<D05Adapter>> generateTestDataMap() {
        Map<String, List<D05Adapter>> map = new HashMap<>();
        List<D05Adapter> adapters1 = new ArrayList<>();
        List<D05Adapter> adapters2 = new ArrayList<>();
        List<D05Adapter> adapters3 = new ArrayList<>();
        D05Adapter d05Adapter1 = new D05Adapter();
        d05Adapter1.setPib("Іван");
        d05Adapter1.setTerCentr("Житомирський ТЦК");
        adapters1.add(d05Adapter1);

        D05Adapter d05Adapter2 = new D05Adapter();
        d05Adapter2.setPib("Андрій");
        d05Adapter2.setTerCentr("Одеський ТЦК");
        adapters2.add(d05Adapter2);

        D05Adapter d05Adapter3 = new D05Adapter();
        d05Adapter3.setPib("Мстислав");
        d05Adapter3.setTerCentr("Одеський ТЦК");
        adapters2.add(d05Adapter3);

        D05Adapter d05Adapter4 = new D05Adapter();
        d05Adapter4.setPib("Яков");
        d05Adapter4.setTerCentr("Житомирський ТЦК");
        adapters1.add(d05Adapter4);

        D05Adapter d05Adapter5 = new D05Adapter();
        d05Adapter5.setPib("Юрій");
        d05Adapter5.setTerCentr("Кропивницький ТЦК");
        adapters3.add(d05Adapter5);
        map.put(d05Adapter1.getTerCentr(), adapters1);
        map.put(d05Adapter2.getTerCentr(), adapters2);
        map.put(d05Adapter5.getTerCentr(), adapters3);

        return map;
    }

    public List<D05Adapter> generateTestDataList() {
        List<D05Adapter> adapters = new ArrayList<>();
        D05Adapter d05Adapter1 = new D05Adapter();
        d05Adapter1.setPib("Іван");
        d05Adapter1.setTerCentr("Жмеринський ТЦК");
        adapters.add(d05Adapter1);

        D05Adapter d05Adapter2 = new D05Adapter();
        d05Adapter2.setPib("Андрій");
        d05Adapter2.setTerCentr("Одеський ТЦК");
        adapters.add(d05Adapter2);

        D05Adapter d05Adapter3 = new D05Adapter();
        d05Adapter3.setPib("Мстислав");
        d05Adapter3.setTerCentr("Одеський ТЦК");
        adapters.add(d05Adapter3);

        D05Adapter d05Adapter4 = new D05Adapter();
        d05Adapter4.setPib("Яков");
        d05Adapter4.setTerCentr("Житомирський ТЦК");
        adapters.add(d05Adapter4);

        D05Adapter d05Adapter5 = new D05Adapter();
        d05Adapter5.setPib("Юрій");
        d05Adapter5.setTerCentr("Кропивницький ТЦК");
        adapters.add(d05Adapter5);

        return adapters;
    }
}
