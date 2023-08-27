package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

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
    @JoinColumn(name = "prepod_id",unique = true)
    private Prepod prepod;

    //Категорія обліку - 1 або 2 (2 у більшості)
    //Обирається з переліку (фіксований) - 1 або 2
    @Column(name = "categoria_obliky")
    private int vCategory;

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
    @Column(name = "sklad_obliky")
    private int vSklad;

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
    @JoinColumn(name = "voenkomat_id")
    private Voenkomat voenkomat;

    //Придатність
    //Обирається з переліку (фіксований) -  придатний; обмежено-придатний; непридатний
    @Column(name = "v_prydatnist")
    private String vPrydatnist;







}
