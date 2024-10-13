package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "army_sklad")
@NoArgsConstructor
public class VSklad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "army_sklad_id")
    Long id;

    @Column(length = 30, name="army_sklad_name",nullable = false,unique = true)
    private String skladName;

    //Порядковий номер для представлення у списку, для сортування при відображенні
    @Column(name="show_order",nullable = false,unique = true)
    private Integer numOrderShow;

}
