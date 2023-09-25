package ntukhpi.semit.militaryoblik.utils;

import javafx.collections.ObservableList;
import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.service.D05DataCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataWriteService {

    private final DataPreparer dataPreparer;
    private final D05DataCollectService d05DataCollectService;

    private final ExcelWriter excelWriter;

    @Autowired
    public DataWriteService(DataPreparer dataPreparer, D05DataCollectService d05DataCollectService, ExcelWriter excelWriter) {
        this.dataPreparer = dataPreparer;
        this.d05DataCollectService = d05DataCollectService;
        this.excelWriter = excelWriter;
    }

    // Запис данних з бд до файлу додатку 5.
    // Аргументи: тип сортування по імені "name" по ТЦК "tck",
    // та список id miliratiPersonIds які потрібно вивести.
    public void writeDataToExcel(String sortType, List<Long> miliratiPersonIds) {
        Map<String, List<D05Adapter>> sortedAdapters = dataPreparer.sortD5AdapterByUAAlphabet(d05DataCollectService.collectD05Adapter(miliratiPersonIds), sortType);
        List<String[][]> workingDatas = new ArrayList<>();
        for (List<D05Adapter> adapters : sortedAdapters.values()) {
            String[][] workingData = dataPreparer.listToArray(adapters);
            workingDatas.add(workingData);
        }

        excelWriter.writeExcel(workingDatas, null);
    }

    public void writeDataToExcelBase(ObservableList<ReservistAdapter> reservistsList, File file) {
        List<Long> miliratiPersonIds = reservistsList.stream().map(ReservistAdapter::getMilitaryPersonId).toList();
        Map<String, List<D05Adapter>> sortedAdapters = dataPreparer.sortD5AdapterByUAAlphabet(d05DataCollectService.collectD05Adapter(miliratiPersonIds), "name");
        List<String[][]> workingDatas = new ArrayList<>();
        for (List<D05Adapter> adapters : sortedAdapters.values()) {
            String[][] workingData = dataPreparer.listToArray(adapters);
            workingDatas.add(workingData);
        }

        excelWriter.writeExcel(workingDatas, file);
    }

    //ToDo check to delete
//    public void writeDataToExcelOnTCKName(String tckName, List<Long> miliratiPersonIds) {
//
//        List<D05Adapter> sortedAdapters = d05DataCollectService.collectD05Adapter(miliratiPersonIds);
//        List<D05Adapter> currentTCKAdapter = sortedAdapters.stream()
//                .filter(d05Adapter -> d05Adapter.getTerCentr()
//                        .equals(tckName))
//                .toList();
//
//        String[][] workingData = dataPreparer.listToArray(currentTCKAdapter);
//
//        ExcelWriter excelWriter = new ExcelWriter();
//        excelWriter.writeExcel(workingData);
//    }

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
