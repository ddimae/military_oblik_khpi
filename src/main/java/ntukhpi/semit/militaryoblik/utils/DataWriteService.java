package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.utils.DataPreparer;
import ntukhpi.semit.militaryoblik.utils.ExcelWriter;
import ntukhpi.semit.militaryoblik.utils.WordWriter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataWriteService {

    public void writeDataToExcel(String sortType) {
        DataPreparer dataPreparer = new DataPreparer();

        List<D05Adapter> sortedAdapters = dataPreparer.sortD5AdapterByUAAlphabet(generateTestData(), sortType);
        String[][] workingData = dataPreparer.listToArray(sortedAdapters);

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.writeExcel(workingData);
    }

    public void writeDataToExcelOnTCKName(String tckName) {
        DataPreparer dataPreparer = new DataPreparer();

        List<D05Adapter> sortedAdapters = generateTestData();
        List<D05Adapter> currentTCKAdapter = sortedAdapters.stream()
                .filter(d05Adapter -> d05Adapter.getColumn13()
                        .equals(tckName))
                .toList();

        String[][] workingData = dataPreparer.listToArray(currentTCKAdapter);

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.writeExcel(workingData);
    }

    public void writeDataToWord() {
        WordWriter wordReader = new WordWriter();
        wordReader.readAndWriteFile();
    }

    //тестові данні, будуть прибрані коли буде реалізована робота з БД
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
