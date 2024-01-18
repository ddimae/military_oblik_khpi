package ntukhpi.semit.militaryoblik.utils;

import javafx.collections.ObservableList;
import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
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
    private final WordWriter wordWriter;

    @Autowired
    public DataWriteService(DataPreparer dataPreparer, D05DataCollectService d05DataCollectService, ExcelWriter excelWriter, WordWriter wordWriter) {
        this.dataPreparer = dataPreparer;
        this.d05DataCollectService = d05DataCollectService;
        this.excelWriter = excelWriter;
        this.wordWriter = wordWriter;
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

    public String writeDataToExcelBase(ObservableList<ReservistAdapter> reservistsList, File file) {
        List<Long> miliratiPersonIds = reservistsList.stream().map(ReservistAdapter::getMilitaryPersonId).toList();
        Map<String, List<D05Adapter>> sortedAdapters = dataPreparer.sortD5AdapterByUAAlphabet(d05DataCollectService.collectD05Adapter(miliratiPersonIds), "name");
        List<String[][]> workingDatas = new ArrayList<>();
        for (List<D05Adapter> adapters : sortedAdapters.values()) {
            String[][] workingData = dataPreparer.listToArray(adapters);
            workingDatas.add(workingData);
        }

        return excelWriter.writeExcel(workingDatas, file);
    }

    public String writeDataToWord(Long reservistId, File file) {
        return wordWriter.fillDodatok2(reservistId, file);
    }
}
