package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

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

}
