package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "prepod_dolghnost")
public class Dolghnost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dolghn_id")
    Long id;

    @Column(length = 40, name="dolghn_name",nullable = false)
    private String dolghnName;

    @Column(length = 15, name="dolghn_shot_name")
    private String dolghnShortName;

    @Column(name="kat_sotr")
    @ColumnDefault(value="1")
    private int categoryEmployees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dolghnost dolghnost = (Dolghnost) o;

        return dolghnName.equals(dolghnost.dolghnName);
    }

    @Override
    public int hashCode() {
        return dolghnName.hashCode();
    }

    @Override
    public String toString() {
        return dolghnName;
    }
}
