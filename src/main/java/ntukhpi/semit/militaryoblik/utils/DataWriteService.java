package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.service.D05DataCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataWriteService {

    private final DataPreparer dataPreparer;
    private final D05DataCollectService d05DataCollectService;

    @Autowired
    public DataWriteService(DataPreparer dataPreparer, D05DataCollectService d05DataCollectService) {
        this.dataPreparer = dataPreparer;
        this.d05DataCollectService = d05DataCollectService;
    }

    public void writeDataToExcel(String sortType, List<Integer> miliratiPersonIds) {
        List<D05Adapter> sortedAdapters = dataPreparer.sortD5AdapterByUAAlphabet(d05DataCollectService.collectD05Adapter(miliratiPersonIds), sortType);
        String[][] workingData = dataPreparer.listToArray(sortedAdapters);

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.writeExcel(workingData);
    }

    public void writeDataToExcelOnTCKName(String tckName, List<Integer> miliratiPersonIds) {

        List<D05Adapter> sortedAdapters = d05DataCollectService.collectD05Adapter(miliratiPersonIds);
        List<D05Adapter> currentTCKAdapter = sortedAdapters.stream()
                .filter(d05Adapter -> d05Adapter.getTerCentr()
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
