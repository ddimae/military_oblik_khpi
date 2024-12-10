package ntukhpi.semit.militaryoblik.utils;

import javafx.collections.ObservableList;
import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.adapters.EIAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.utils.D5.D5DataCollectService;
import ntukhpi.semit.militaryoblik.utils.D5.D5DataPreparer;
import ntukhpi.semit.militaryoblik.utils.D5.D5ExcelWriter;
import ntukhpi.semit.militaryoblik.utils.P2.P2WordWriter;
import ntukhpi.semit.militaryoblik.utils.exportimport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataWriteReadService {

    private final D5DataPreparer d5DataPreparer;
    private final D5DataCollectService d5DataCollectService;
    private final EIDataCollectService eiDataCollectService;
    private final EIDataSaveService eiDataSaveService;

    private final D5ExcelWriter d5ExcelWriter;
    private final P2WordWriter P2WordWriter;
    private final EIExcelWriter eiExcelWriter;

    private final EIExcelReader eiExcelReader;

    @Autowired
    public DataWriteReadService(D5DataPreparer d5DataPreparer,
                                D5DataCollectService d5DataCollectService,
                                D5ExcelWriter d5ExcelWriter,
                                P2WordWriter P2WordWriter,
                                EIExcelWriter eiExcelWriter,
                                EIExcelReader eiExcelReader,
                                EIDataCollectService eiDataCollectService,
                                EIDataSaveService eiDataSaveService) {
        this.d5DataPreparer = d5DataPreparer;
        this.d5DataCollectService = d5DataCollectService;
        this.d5ExcelWriter = d5ExcelWriter;
        this.P2WordWriter = P2WordWriter;
        this.eiExcelWriter = eiExcelWriter;
        this.eiExcelReader = eiExcelReader;
        this.eiDataCollectService = eiDataCollectService;
        this.eiDataSaveService = eiDataSaveService;
    }

    // Запис данних з бд до файлу додатку 5.
    // Аргументи: тип сортування по імені "name" по ТЦК "tck",
    // та список id miliratiPersonIds які потрібно вивести.
    public void writeDataToExcel(String sortType, List<Long> miliratiPersonIds) {
        Map<String, List<D05Adapter>> sortedAdapters = d5DataPreparer.sortD5AdapterByUAAlphabet(d5DataCollectService.collectD05Adapter(miliratiPersonIds), sortType);
        List<String[][]> workingDatas = new ArrayList<>();
        for (List<D05Adapter> adapters : sortedAdapters.values()) {
            String[][] workingData = d5DataPreparer.listToArray(adapters);
            workingDatas.add(workingData);
        }

        d5ExcelWriter.writeExcel(workingDatas, null);
    }

    public String writeDataToExcelBase(ObservableList<ReservistAdapter> reservistsList, File file) {
        List<Long> miliratiPersonIds = reservistsList.stream().map(ReservistAdapter::getMilitaryPersonId).toList();
        Map<String, List<D05Adapter>> sortedAdapters = d5DataPreparer.sortD5AdapterByUAAlphabet(d5DataCollectService.collectD05Adapter(miliratiPersonIds), "name");
        List<String[][]> workingDatas = new ArrayList<>();
        for (List<D05Adapter> adapters : sortedAdapters.values()) {
            String[][] workingData = d5DataPreparer.listToArray(adapters);
            workingDatas.add(workingData);
        }

        return d5ExcelWriter.writeExcel(workingDatas, file);
    }

    public String writeExportDataToExcelBase(ReservistAdapter reservist, File file) throws Exception {
        Long prepodId = reservist.getId();

        EIAdapter exportAdapter = eiDataCollectService.collectData(prepodId);

        eiExcelWriter.writeExcel(EIDataPreparer.exportAdapterToDataList(exportAdapter), eiDataCollectService.getDropdownAdapter().toList(), file);

        return file.getPath();
    }

    public EIAdapter readImportDataFromExcel(File file) throws Exception {
        System.out.println("Типи клітинок ІМПОРТА=======================");
        String[] importData = eiExcelReader.readImportExcel(file);
        System.out.println("Типи клітинок ЕКСПОРТА=======================");
        String[] exportData = eiExcelReader.readExportExcel(file);

        System.out.println("Значення клітинок ІМПОРТА==============================================");
        EIAdapter importAdapter = EIDataPreparer.dataToImportAdapter(importData);
        System.out.println("Значення клітинок ЕКСПОРТА==============================================");
        EIAdapter exportAdapter = EIDataPreparer.dataToImportAdapter(exportData);

        Long prepodId = eiDataCollectService.getPrepodIdByEIAdapter(exportAdapter);

        EIAdapter sourceAdapter = eiDataCollectService.collectData(prepodId);
        EIAdapter mergedAdapter = EIDataPreparer.mergeEIAdapters(sourceAdapter, importAdapter);

        return eiDataSaveService.update(mergedAdapter) ? mergedAdapter : null;
    }

    public String writeDataToWord(Long reservistId, File file) {
        return P2WordWriter.fillFormP2(reservistId, file);
    }
}
