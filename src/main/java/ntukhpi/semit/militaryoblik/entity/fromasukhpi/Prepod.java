package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Entity
@Table(name = "prepod")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Prepod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prepod_id")
    Long id;

    @Column(length = 40)
    private String fam;
    @Column(length = 30)
    private String imya;
    @Column(length = 30)
    private String otch;

    //В БД АСУ УПД ХПИ можно получить дані про посаду, яку обіймає співробітник
    //Тобто фіксується поточна (або остання посада) співробітника
    // Пара dolghnost + kafedra (або пара полів в таблиці dolghn_id + kid) визначають, яку посаду та на якій кафедрі
    // працює співробітник.
    // Для обліку не вистачає інформації про накази про призначення на посаду,
    // а також про звільнення з посади, тому що деякий час інформація про таких співробітників зберігається у 2 відділі.
    //Через те, що це клас "незмінний", створений клас CurrentDoljnostInfo, який буде зберігати дані про накази
    //про призначення та звільнення
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid")
    private Kafedra kafedra;

    @ManyToOne
    @JoinColumn(name = "dolghn_id")
    private Dolghnost dolghnost;

    //Вчене звання - це доцент, професор, старший науковий співробітник, академік, тощо
    @ManyToOne
    @JoinColumn(name = "zvanie_id")
    private Zvanie zvanie;

    //Науковий ступінь - кандидат технічних наук, доктор технічних наук
    @ManyToOne
    @JoinColumn(name = "stepen_id")
    private Stepen stepen;

    @Column
    private String email;

}
