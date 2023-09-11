package ntukhpi.semit.militaryoblik;

import javafx.application.Application;
import ntukhpi.semit.militaryoblik.service.Test;
import ntukhpi.semit.militaryoblik.utils.DataPreparer;
import ntukhpi.semit.militaryoblik.utils.DataWriteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class MilitaryAccounting {

    public static void main(String[] args) throws IOException {
        // for run demo console with menu
//        ConfigurableApplicationContext context = SpringApplication.run(MilitaryAccounting.class, args);
//        runConsole();
//        context.close();

        //for run javaFX app
        Application.launch(MilitaryOblikKhPIMain.class, args);
    }

//    public static void runConsole() throws IOException {
//        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
//        System.setErr(new java.io.PrintStream(System.err, true, StandardCharsets.UTF_8));
//        DataPreparer dataPreparer = new DataPreparer();
//        DataWriteService dataWriteService = new DataWriteService(dataPreparer, d05DataCollectService);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
//        String message = "\n1. для генерації excel таблиці відсортованій за ім’ям" +
//                "\n2. для генерації excel таблиці відсортованій за ТЦК" +
//                "\n3. для генерації excel таблиці з тільки зазначеним ТЦК" +
//                "\n4. для генерації word документа\n0. для виходу з додатку:";
//        System.out.println(message);
//        String inputString = reader.readLine();
//        int result;
//        result = getResult(inputString);
//        while (result != 0) {
//            if (result == 1) {
//                dataWriteService.writeDataToExcel("name");
//            } else if (result == 2) {
//                dataWriteService.writeDataToExcel("tck");
//            } else if (result == 3) {
//                System.out.println("Введіть назву ТЦК");
//                String tckName = reader.readLine();
//                dataWriteService.writeDataToExcelOnTCKName(tckName);
//            } else if (result == 4) {
//                dataWriteService.writeDataToWord();
//            }
//            System.out.println(message);
//            inputString = reader.readLine();
//            result = getResult(inputString);
//        }
//        reader.close();
//    }

//    private static int getResult(String inputString) {
//        int result;
//        if (Character.isDigit(inputString.charAt(0))) {
//            result = Integer.parseInt(inputString);
//        } else {
//            result = 5465465;
//            System.out.println("Введіть тільки цифрове значення");
//        }
//        return result;
//    }
}