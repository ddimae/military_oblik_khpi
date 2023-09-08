package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "prepod_zvanie")
@NoArgsConstructor
public class Zvanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zvanie_id")
    Long id;

    @Column(length = 40, name="zvanie_name",nullable = false,unique = true)
    private String zvanieName;

    @Column(name="okp_id_uzvan",nullable = false,unique = true)
    private Integer okpIdUchZvan;

    @Override
    public String toString() {
        return zvanieName;
    }

}