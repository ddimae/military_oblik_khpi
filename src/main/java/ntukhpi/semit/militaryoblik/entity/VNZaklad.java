package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;

@Entity
@Table(name = "vnz")
@Getter
@Setter
@NoArgsConstructor
public class VNZaklad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vnz_id")
    private Long id;

    @Column(name="vnz_name",nullable = false,unique = true)
    private String vnzName;

    @Column(length = 10, name="vnz_short_name",unique = true)
    private String vnzShortName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VNZaklad vnZaklad = (VNZaklad) o;

        return vnzName.equals(vnZaklad.vnzName);
    }

    @Override
    public int hashCode() {
        return vnzName.hashCode();
    }

    @Override
    public String toString() {
        return vnzShortName + " (" + vnzName + ")";
    }

    public void setAbbreviation(String abbreviation) {
        this.vnzShortName = abbreviation;
    }
}
