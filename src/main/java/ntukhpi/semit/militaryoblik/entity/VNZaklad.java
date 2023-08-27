package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name="vnz_name")
    private String vnzName;

    @Column(length = 10, name="vnz_short_name")
    private String vnzShortName;

}
