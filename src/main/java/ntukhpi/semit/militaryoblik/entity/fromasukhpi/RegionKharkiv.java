package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rn_kharkov")
@Getter
@Setter
@NoArgsConstructor
public class RegionKharkiv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rn_id")
    private Long id;

    @Column(length = 40, name="rn_name",nullable = false)
    private String countryName;

}
