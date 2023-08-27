package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "prepod_stepen")
@Getter
@Setter
@NoArgsConstructor
public class Stepen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stepen_id")
    Long id;

    @Column(length = 40, name="stepen_name")
    private String stepenName;
    @Column(length = 80, name="stepen_long")
    private String stepenLong;
    @Column(name="okp_id_nstep")
    private int okpIdNaukStepen;}