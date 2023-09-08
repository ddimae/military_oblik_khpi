package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "army_zvanie")
@NoArgsConstructor
public class VZvanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "army_zvanie_id")
    Long id;

    @Column(length = 30, name="army_zvanie_name",nullable = false,unique = true)
    private String zvanieName;

    //Порядковий номер для представлення у списку, для сортування при відображенні
    @Column(name="show_order",nullable = false,unique = true)
    private Integer numOrderShow;

    //Код, до якого відноситься склад: і
    @Column(name="kod_skladu",nullable = false)
    private Integer kodSkladu;

}