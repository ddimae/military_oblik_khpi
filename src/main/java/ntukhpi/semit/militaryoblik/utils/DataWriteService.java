package ntukhpi.semit.militaryoblik.utils;

import javafx.collections.ObservableList;
import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.adapters.ExportAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.utils.D5.D5DataCollectService;
import ntukhpi.semit.militaryoblik.utils.D5.D5DataPreparer;
import ntukhpi.semit.militaryoblik.utils.D5.D5ExcelWriter;
import ntukhpi.semit.militaryoblik.utils.P2.P2WordWriter;
import ntukhpi.semit.militaryoblik.utils.exportimport.EIDataCollectService;
import ntukhpi.semit.militaryoblik.utils.exportimport.EIExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataWriteService {

    private final D5DataPreparer d5DataPreparer;
    private final D5DataCollectService d5DataCollectService;
    private final EIDataCollectService eiDataCollectService;

    private final D5ExcelWriter d5ExcelWriter;
    private final P2WordWriter P2WordWriter;
    private final EIExcelWriter eiExcelWriter;

    @Autowired
    public DataWriteService(D5DataPreparer d5DataPreparer,
                            D5DataCollectService d5DataCollectService,
                            D5ExcelWriter d5ExcelWriter,
                            P2WordWriter P2WordWriter,
                            EIExcelWriter eiExcelWriter,
                            EIDataCollectService eiDataCollectService) {
        this.d5DataPreparer = d5DataPreparer;
        this.d5DataCollectService = d5DataCollectService;
        this.d5ExcelWriter = d5ExcelWriter;
        this.P2WordWriter = P2WordWriter;
        this.eiExcelWriter = eiExcelWriter;
        this.eiDataCollectService = eiDataCollectService;
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

    public String writeExportDataToExcelBase(ReservistAdapter reservist, File file) {
        Long prepodId = reservist.getId();
        List<String[]> workingDatas = new ArrayList<>();

        try {
            ExportAdapter exportAdapter = eiDataCollectService.collectData(prepodId);
            String[] generalInfo = exportAdapter.getGeneralInfoAsStringArray();

            workingDatas.add(generalInfo);

            return eiExcelWriter.writeExcel(workingDatas, file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "error";
        }
    }

    public String writeDataToWord(Long reservistId, File file) {
        return P2WordWriter.fillFormP2(reservistId, file);
    }
}
