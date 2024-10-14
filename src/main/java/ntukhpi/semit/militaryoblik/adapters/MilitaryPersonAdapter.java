package ntukhpi.semit.militaryoblik.adapters;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryPersonAdapter implements IBaseAdapter {
    private Long id;
    private String vos;
    private String vCategory;
    private String vGrupa;
    private String vSklad;
    private String vZvanie;
    private String voenkomat;
    private String reserv;
    private String vPrydatnist;
    private String familyState;
    private String educationLevel;
}
