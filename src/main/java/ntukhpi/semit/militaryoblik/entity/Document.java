package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pasports")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdata_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "prepod_id",nullable = false)
    private Prepod prepod;

    // Тип документу
    // Додаток 5 потребує інформації про:
    // - паспорт громадянина України (!!! - на обліку можуть стояти лише громадяни України - !!!)
    // - закордонний паспорт
    // - посвідчення особи офіцера
    // Обирається з переліку (фіксований)
    // Правільність вводу не контролюємо: FF9834239 - проблема тих, хто вводить
    @Column(name = "docType", length = 30, nullable = false)
    private String docType;

    //Серія та номер документу.
    //Паспорт громадянина України - старий зразок МК229850, IDCard 009559678.
    //ще зустрів ДУЖЕ старий - радянський в обліковій картці XIV-ВЛ 954687 - не розглядаємо, на уточнення!!!
    // - закордонний паспорт FR444328
    // - посвідчення особи офіцера KK #732843
    //тобто користувач має ввести або 2 букви та 6 цифр, або 9 цифр ИД картки - це контролювати
    @Column(name = "doc_number", length = 10, nullable = false)
    private String docNumber;

    //дата видачі документа
    //Семантичну коректність дати не контролюємо. Мається на увазі перевірка наявності 16 років
    //на момент отримання паспорту, 21 року на момент отримання посвідчення офіцера, тощо.
    @Column(name = "data_vyd", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataVyd;

    //ким виданий документ
    // Для ІД карток вказується цифрова назва сервісного центру
    // Для всіх інших це буде текстове поле, яке постійно треба вводити руцями.
    // Тому довжину обмежувати не слід, та щось вигадувати для збереження значень теж.
    // Принаймні так було у Приватбанку
    @Column(name = "kto_vyd", nullable = false)
    private String ktoVyd;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(docType).append(": ").append(docNumber);
        sb.append(", виданий ").append(ktoVyd).append("  ").append(dataVyd);
        return sb.toString();
    }
}
