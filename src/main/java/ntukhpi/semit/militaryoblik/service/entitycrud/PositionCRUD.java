package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.PositionAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.service.DolghnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionCRUD {

    @Autowired
    DolghnostService dolghnostService;
    public void addPosition(PositionAdapter adapter) {

        Dolghnost dolghnost = new Dolghnost();

        dolghnost.setDolghnName(adapter.getFullName());
        dolghnost.setDolghnShortName(adapter.getShortName());
        //Якщо якись зайде незрозумілий номер категорії (символи або більше 2), то категорія буде 2
        int category;

        try {
            category = Integer.parseInt(adapter.getCategory());
        } catch (IllegalArgumentException e){
            category = 2;
        }
        if (category != 1 && category != 2) {
            category = 2;
        }
        dolghnost.setCategoryEmployees(category);

        dolghnostService.createDolghnost(dolghnost);

    }
}
