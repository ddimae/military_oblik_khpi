package ntukhpi.semit.militaryoblik.main;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.*;
import ntukhpi.semit.militaryoblik.service.*;
import ntukhpi.semit.militaryoblik.utils.Dodatok5Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootTest
public class ExcelReadTest {
    @Autowired
    FakultetRepository fakultetRepository;
    @Autowired
    KafedraRepository kafedraRepository;
    @Autowired
    VoenkomatRepository voenkomatRepository;
    @Autowired
    PrepodRepository prepodRepository;
    @Autowired
    DolghnostRepository dolghnostRepository;
    @Autowired
    KafedraServiceImpl kafedraServiceImpl;
    @Autowired
    DolghnostServiceImpl dolghnostServiceImpl;
    @Autowired
    PrepodServiceImpl prepodServiceImpl;
    @Autowired
    MilitaryPersonServiceImpl militaryPersonServiceImpl;
    @Autowired
    VZvanieServiceImpl vZvanieServiceImpl;

    @Test
    void testReadDodatok5() {
        System.out.println("\nRead from file");
        (new Dodatok5Reader(vZvanieServiceImpl, prepodServiceImpl, militaryPersonServiceImpl)).readExcelFileWithDodatok5();

    }

    @Test
    void testParseFamily() {
        System.out.println("\nParse family sell");
        String sellValue = "";
//      sellValue = " дружина - Лучникова Олена Петрівна, 1993 р.н.";
//        sellValue = " батько - Віктор Петрович, 1940 р.н.";
        sellValue = " дружина - Корольова Наталія Сергіївна, 1977 р.н.; донька - Корольова-Уварова Анна Романівна, 2009 р.н.; донька дружини - Корольова Тамара Юріївна, 2003 р.н.";
//        sellValue = " дружина - Наталія Сергіївна, 1977 р.н.; донька - 2004 р.н.; дочка - 2005 р.н.";
//        sellValue = "дружина - Наталія Петрівна; донька - 2014 р.н.; син - 2009 р.н.";
        Prepod prep = prepodServiceImpl.getPrepodById(35L);
        String[] familyMembersStr = sellValue.split(";");
        Set<FamilyMember> relatives = new LinkedHashSet<>();
        if (familyMembersStr.length > 1) {
            for (int i = 0; i < familyMembersStr.length; i++) {
                FamilyMember familyMember =
                        (new Dodatok5Reader(vZvanieServiceImpl, prepodServiceImpl, militaryPersonServiceImpl)).parseFamilyMember(prep, familyMembersStr[i]);
                System.out.println(familyMember);
                relatives.add(familyMember);
            }
        }
    }

    @Test
    void testParseEducation() {
        System.out.println("\nOsvita");
        String sellValue = "";
//       sellValue = "спец: НТУ \"ХПІ\" у 2007 р., ХА №25542899, фах: обладнання хімічного виробництв і підприємств";
//        sellValue = "маг: НТУ \"ХПІ\" у 2016 р., М16 №831879, фах: хім. техн. переробки полім. та  композ. матеріалів";
//        sellValue = "спец: ІПО ХПІ (Інститут післядипломної освіти НТУ \"ХПІ\") у 2013 р., №401/11, кв: вчитель німецької мови";
//       sellValue = "спец: НТУ \"ХПІ\"";
//        sellValue = "спец: ХДПУ у 1996 р., фах: автомат.ел.механ.системи";
//        sellValue = "спец: ХВУ  у   1995  р., ЛК №440723, фах: балістика";
//        sellValue = "маг: ХВУ у 1999 р., МО №11350286, фах: організація бойового та оперативного забезпечення військ (сил)";
//        sellValue = "спец: ХВУ у 1996 р., ЛЕ №32440, фах: ПЗ ОТ і автоматизованих систем";
//        sellValue = "маг: ХРІ ДУ НАДУ (Харківський регіональний інститут ДУ НАДУ при Президентові України) у 2007 р., фах: державне управління";
        sellValue = "спец: ХВУ у 1996 р., ЛС ВЕ №001111, фах: ПЗ ОТ і автоматизованих систем";
        Prepod prep = prepodServiceImpl.getPrepodById(35L);
//        String[] familyMembersStr = sellValue.split(";");
//        Set<FamilyMember> relatives = new LinkedHashSet<>();
//        if (familyMembersStr.length > 1) {
//            for (int i = 0; i < familyMembersStr.length; i++) {
        Education education =
                (new Dodatok5Reader(vZvanieServiceImpl, prepodServiceImpl, militaryPersonServiceImpl)).parseEducation(prep, sellValue);
//                System.out.println(familyMember);
//                relatives.add(familyMember);
//            }
//        }

    }

    @Test
    void testParseAdress() {
        System.out.println("\nAdress");
        String sellValue = "";
//        sellValue = "м.Харків, вул.Старицького, 13, кв. 22";
//        sellValue = "м. Харків, вул. Динамівська, 3а, гуртожиток";
//        sellValue = "Харківська обл., смт Дергачі, вул.1 травня, 19";
//        sellValue = "Харківська обл., м. Харків, вул Валентинівська б. 13в кв. 28";
//        sellValue = "м.Харків, пров Піщаний б. 1";
        sellValue = "м. Харків, вул. Клапцова 63/71";
        Prepod prep = prepodServiceImpl.getPrepodById(4L);
        PersonalData pd = (new Dodatok5Reader(vZvanieServiceImpl, prepodServiceImpl, militaryPersonServiceImpl)).parsePersonalDate(prep, sellValue,sellValue);


    }

}
