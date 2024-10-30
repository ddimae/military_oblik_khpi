package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.service.DolghnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionCRUD {

    @Autowired
    DolghnostService dolghnostService;
    public void addCathedra(String fullName, String shortName, String categoryString) {

        Dolghnost dolghnost = new Dolghnost();

        dolghnost.setDolghnName(fullName);
        dolghnost.setDolghnShortName(shortName);
        //Якщо якись зайде незрозумілий номер категорії (символи або більше 2), то категорія буде 2
        int category;
        try {
            category = Integer.parseInt(categoryString);
        } catch (IllegalArgumentException e){
            category = 2;
        }
        if (category !=1 || category !=2) {
            category = 2;
        }
        dolghnost.setCategoryEmployees(category);

        dolghnostService.createDolghnost(dolghnost);

    }
}
