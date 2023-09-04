package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionKharkiv;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person_data")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdata_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "prepod_id",unique = true,nullable = false)
    private Prepod prepod;



    //адреса
    //Страна - одне обовязкове поле в адресі (так будемо робити!)
    //якщо пусте - встановлювати "Україна"
    //В БД АСУ УНП ХПі є довідник країн, країну користувач буде обирати з переліку
    @ManyToOne
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;

    //Також в БД АСУ УНП ХПі є довідник областей та довідник районів Харкова
    // Якщо країна Україна, то це поле oblastUA треба заповнювати, якщо ні - залишаємо пустим (null).
    // Список областей достатньо странний...
    // Там є Харків, Севастополь, Краснодарський край та інші області РФ... Це поточне наповнення, зміниться наповнення,
    // зміниться і наше відображення у переліку.
    @ManyToOne
    @JoinColumn(name = "oblast_id")
    private RegionUkraine oblastUA;

    @Column(name = "post_index",length = 10)
    private String postIndex;
    @Column(name="city_name",length = 30, nullable = false)
    private String city;
    @Column(name="adress", nullable = false)
    private String rowAddress;

    //Район Харькова.... Мутное поле.... По идее более важным является данные про то, в каком военкомате находиться человек....
    //Решение - оставлять пустым (null). Считаем, чтоо поле на перспективу.
    @ManyToOne
    @JoinColumn(name = "region_kh_id")
    private RegionKharkiv regionKh;

    //===============================
    // Адреса фактична
    //===============================
    @ManyToOne
    @JoinColumn(name = "country_id_fact")
    private Country country_fact;

    @ManyToOne
    @JoinColumn(name = "oblast_id_fact")
    private RegionUkraine factOblastUA;

    @Column(name = "post_index_fact",length = 10)
    private String factPostIndex;
    @Column(name="city_name_fact",length = 30)
    private String factCity;
    @Column(name="adress_fact")
    private String factRowAddress;
    @ManyToOne
    @JoinColumn(name = "region_kh_id_fact")
    private RegionKharkiv factRegionKh;

    //телефони
    //Храним в символьном виде, но при вводе проверяем маску - +СтранаОператорНомер
    //+380951203066
    //Также в базе есть "неполные" номера (0951203066 или 380951203066) и городские (7076845).
    //Неполные мобильные принимать и дописывать нужные цифры и "+"
    //Если городской, то дописывать +38057
    //При вводе нового сотрудника или редактировании существующего контролировать наличие хотя бы одного номера
    @Column(name = "phone_main",length = 13,nullable = false)
    private String phoneMain;
    @Column(name = "phone_dop",length = 13)
    private String phoneDop;





}
