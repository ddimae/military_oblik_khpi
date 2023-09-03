package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class MilitaryOblikInfo {
    // Класс создан для того, чтобы в будущем ПРИ НЕОБХОДИМОСТИ
    // реализовать наполнение данных, которые поступают об учете военнообязанных из военкоматов
    // ПОКА НЕ ТРОГАЕМ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mil_data_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "mp_id",unique = true,nullable = false)
    private MilitaryPerson militaryPerson;


}
