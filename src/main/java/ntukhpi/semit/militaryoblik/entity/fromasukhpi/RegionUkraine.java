package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "obl_u")
@Getter
@Setter
@NoArgsConstructor
public class RegionUkraine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obl")
    private Long id;

    @Column(length = 40, name="oname",nullable = false)
    private String countryName;

}
