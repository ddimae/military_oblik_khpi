package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.*;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Entity
//Унікальним робимо ПІБ+кафедра.
//Тобто приймаємо за аксіому, що однофамільці на одній кафедрі не працюють
@Table(name = "prepod", uniqueConstraints = @UniqueConstraint(columnNames = {"fam", "imya", "otch", "kid"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prepod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prepod_id")
    Long id;

    @Column(length = 40, nullable = false)
    private String fam;
    @Column(length = 30, nullable = false)
    private String imya;
    @Column(length = 30, nullable = false)
    private String otch;

    //дата рождения
    @Column(name = "data_rozd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dr;

    //В БД АСУ УПД ХПИ можно получить дані про посаду, яку обіймає співробітник
    //Тобто фіксується поточна (або остання посада) співробітника
    // Пара dolghnost + kafedra (або пара полів в таблиці dolghn_id + kid) визначають, яку посаду та на якій кафедрі
    // працює співробітник.
    // Для обліку не вистачає інформації про накази про призначення на посаду,
    // а також про звільнення з посади, тому що деякий час інформація про таких співробітників зберігається у 2 відділі.
    //Через те, що це клас "незмінний", створений клас CurrentDoljnostInfo, який буде зберігати дані про накази
    //про призначення та звільнення
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid", nullable = false)
    private Kafedra kafedra;

    @ManyToOne
    @JoinColumn(name = "dolghn_id", nullable = false)
    private Dolghnost dolghnost;

    //Вчене звання - це доцент, професор, старший науковий співробітник, академік, тощо
    //Якщо не вибрано, то записувати null
    @ManyToOne
    @JoinColumn(name = "zvanie_id")
    private Zvanie zvanie;

    //Науковий ступінь - кандидат технічних наук, доктор технічних наук
    //Якщо не вибрано, то записувати null
    @ManyToOne
    @JoinColumn(name = "stepen_id")
    private Stepen stepen;

    @Column
    private String email;

    //Склад родини -
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepod")
    private Set<FamilyMember> family = new LinkedHashSet<>();
    // Перелік вузів, які були закінчені
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepod")
    private Set<Education> educationList = new LinkedHashSet<>();
    ;

    // Дані про навчання в аспірантурі (адюнктурі) та докторантурі
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepod")
    private Set<EducationPostgraduate> educationPostList = new LinkedHashSet<>();

    // Документи
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepod")
    private Set<Document> documents = new LinkedHashSet<>();

    //Дані про накази

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "prepod")
    private CurrentDoljnostInfo posadaNakazy;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "prepod")
    private PersonalData contacts;


    public Prepod(String fam, String imya, String otch,
                  Kafedra kafedra, Dolghnost dolghnost, Zvanie zvanie, Stepen stepen, String email) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
        this.zvanie = zvanie;
        this.stepen = stepen;
        this.email = email;
    }

    public Prepod(String fam, String imya, String otch, Kafedra kafedra) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
    }

    public Prepod(String fam, String imya, String otch, Kafedra kafedra, Dolghnost dolghnost) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
    }

    public Prepod(String fam, String imya, String otch, LocalDate dr,
                  Kafedra kafedra, Dolghnost dolghnost,
                  Stepen stepen, Zvanie zvanie, String email) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.dr = dr;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
        this.stepen = stepen;
        this.zvanie = zvanie;
        this.email = email;
    }

    //Для складу родини
    public Set<FamilyMember> getFamily() {
        return Collections.unmodifiableSet(family);
    }

    public void addMember(FamilyMember member) {
        family.add(member);
    }

    public void delMember(FamilyMember member) {
        family.remove(member);
    }

    public void delFamily() { family.clear(); }

    //Для переліку вузів, що закінчені
    public Set<Education> getEducationList() {
        return Collections.unmodifiableSet(educationList);
    }

    public void addEducation(Education education) {
        educationList.add(education);
    }

    public void delEducation(Education education) {
        educationList.remove(education);
    }

    public void delEducationList() {
        educationList.clear();
    }

    //Для даних про навчання в аспірантурі (адюнктурі) та докторантурі
    public Set<EducationPostgraduate> getEducationPostList() {
        return Collections.unmodifiableSet(educationPostList);
    }

    public void addEducationPost(EducationPostgraduate education) {
        educationPostList.add(education);
    }

    public void delEducationPost(EducationPostgraduate education) {
        educationPostList.remove(education);
    }

    public void delEducationPostList() {
        educationPostList.clear();
    }

    //Для даних про документи
    public Set<Document> getDocuments() {
        return Collections.unmodifiableSet(documents);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void delDocument(Document document) {
        documents.remove(document);
    }

    public void delDocuments() { documents.clear(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prepod prepod = (Prepod) o;

        if (!fam.equals(prepod.fam)) return false;
        if (!imya.equals(prepod.imya)) return false;
        if (!otch.equals(prepod.otch)) return false;
        return kafedra.equals(prepod.kafedra);
    }

    @Override
    public int hashCode() {
        int result = fam.hashCode();
        result = 31 * result + imya.hashCode();
        result = 31 * result + otch.hashCode();
        result = 31 * result + kafedra.hashCode();
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Prepod ");
        sb.append(id);
        sb.append(": ").append(fam).append(" ").append(imya).append(" ").append(otch);
        sb.append(" (").append(dr.format(DateTimeFormatter.ISO_LOCAL_DATE));
        sb.append(", ").append(kafedra.getKabr());
        sb.append(", ").append(dolghnost.getDolghnName());
        if (zvanie != null) sb.append(", ").append(zvanie.getZvanieName());
        if (stepen != null) sb.append(", ").append(stepen);
        sb.append(") ").append(System.lineSeparator());
        sb.append("Family: ");
        if (family == null) {
            sb.append("немає даних").append(System.lineSeparator());
        } else {
            for (FamilyMember fm : family) {
                sb.append(System.lineSeparator()).append(fm);
            }
        }
        sb.append(System.lineSeparator());
        sb.append("Education: ");
        if (educationList == null) {
            sb.append("немає даних").append(System.lineSeparator());
        } else {
            for (Education edu : educationList) {
                sb.append(System.lineSeparator()).append(edu);
            }
        }
        sb.append(System.lineSeparator());
        sb.append("PostGraduate Education: ");
        if (educationPostList == null) {
            sb.append("немає даних").append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        sb.append("Documents: ");
        if (documents == null) {
            sb.append("немає даних").append(System.lineSeparator());
        } else {
            for (Document doc : documents) {
                sb.append(doc);
            }
        }
        sb.append(System.lineSeparator());
        if (posadaNakazy != null)
            sb.append("Призначений: наказ #").append(posadaNakazy.getNumNakazStart())
                    .append(" від ").append(posadaNakazy.getDateStart());
        sb.append(System.lineSeparator());
        sb.append("Contacts: ");
        if (contacts != null)
            sb.append(contacts);
        return sb.toString();
    }
}
