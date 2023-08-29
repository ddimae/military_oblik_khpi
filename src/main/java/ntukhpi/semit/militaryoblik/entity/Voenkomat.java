package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Entity
@Table(name = "voenkomat")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Voenkomat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voenkamat_id")
    Long id;

    @Column(length = 50,name="voenkomat_name")
    private String voenkomatName;

}
