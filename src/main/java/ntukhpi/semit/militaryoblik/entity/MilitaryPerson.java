package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "military_person")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MilitaryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mp_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "prepod_id",unique = true,nullable = false)
    private Prepod prepod;

    //ВОС - військово-облікова спеціальність, строго 6 цифр, може починатися з нуля,
    //тому треба зберігати в строковому вигляді.
    @Column(length = 6,name="vos",nullable = false)
    private String vos;

    //Категорія обліку - 1 або 2 (2 у більшості)
    //Обирається з переліку (фіксований) - 1 або 2
    @Column(name = "categoria_obliky")
    private Integer vCategory;

    //Група обліку - військовозобов''язаний (у більшості) або призивник
    //Обирається з переліку (фіксований) - військовозобов''язаний (у більшості) або призивник
    @Column(name = "grupa_obliky")
    private String vGrupa;

    //Склад у армії, до якого відноситься посада військово-службовця (командир або інженер або технік, тощо)
    //Обирається з переліку (фіксований)
    // командний; інженерно-технічний; інженерний; технічний; юридичний; медичний
    // ще є ветеринарний та оперативно-технічний - не чіпаємо
    //Можна продумати варіант завантаження переліку із файлу зовнішніх налаштувань.
    //Формувати у окремій таблиці БД - складніше і більше зусиль (моя думка)
    //До речі - як і для попередніх полів vCategory та
    @ManyToOne
    @JoinColumn(name = "vsklad_obliky_id")
    private VSklad vSklad;

    //Військове званння.
    //Кожен військовозобовязаний має звання, яке отримав, коли проходив службу.
    //Іх багато - близько 30, але не всі можуть бути потрібні. Я вводив біля 6 з них. Тому
    //пропонується їх зберігати по мірі необхідності. Тобто сформувати базовий перелік,
    //а потім при необхідності вводити нове звання, його зберігати у базі даних
    // у окремій таблиці.
    @ManyToOne
    @JoinColumn(name = "v_zvanie_id")
    private VZvanie vZvanie;

    //Теріторіальний центр комплектування та соціальної підтримки, на обліку у якому стоїть військовозобовязаний
    //Вводиться зі списку, якщо немає - додається новий до окремої таблиці
    @ManyToOne
    @JoinColumn(name = "voenkomat_id",nullable = false)
    private Voenkomat voenkomat;

    //Перебування на спеціальному обліку - по замовчуванню немає
    @Column(name = "reserv")
    @ColumnDefault(value = "немає")
    private String reserv;

    //Придатність
    //Обирається з переліку (фіксований) -  придатний; обмежено-придатний; непридатний
    @Column(name = "v_prydatnist")
    private String vPrydatnist;

    //Сімейний стан  - одружений (по замовченню) або неодружений (можуть бути заміжня та незаміжня,
    // та ще такі, які ми не знаємо). Вводимо як текст, правильність не перевіряти
    @Column(name = "fam_stan")
    private String familyState;

    //Освіта  - повна вища (за замовченням), вища спеціальна, вища неповна, середня
    // та ще такі, які ми не знаємо). Вводимо як текст, правильність не перевіряти
    @Column(name = "edication_level")
    private String educationLevel;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MilitaryPerson ");
        sb.append(id).append(":\n");
        sb.append(prepod);
        sb.append(System.lineSeparator()).append("Дані обліку:").append(System.lineSeparator());
        sb.append(vos).append(System.lineSeparator());
        if (vCategory != null) {
            sb.append(vCategory).append(System.lineSeparator());
        }
        sb.append(vGrupa).append(System.lineSeparator());
        if (vSklad != null) {
            sb.append(vSklad.getSkladName()).append(System.lineSeparator());
        }
        if (vZvanie != null) {
            sb.append(vZvanie.getZvanieName()).append(System.lineSeparator());
        }
        if (voenkomat != null) {
            sb.append(voenkomat.getVoenkomatName()).append(System.lineSeparator());
        }
        sb.append(vPrydatnist).append(System.lineSeparator());
        sb.append(familyState).append(System.lineSeparator());
        sb.append(educationLevel).append(System.lineSeparator());
        return sb.toString();
    }
}
