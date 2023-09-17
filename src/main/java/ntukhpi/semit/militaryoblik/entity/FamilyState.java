package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "family_state")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FamilyState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fam_stan_id")
    Long idFamState;

    @OneToOne
    @JoinColumn(name = "prepod_id",unique = true,nullable = false)
    private Prepod prepod;

    //Сімейний стан  - одружений (по замовченню) або неодружений (можуть бути заміжня та незаміжня,
    // та ще такі, які ми не знаємо). Вводимо як текст, правильність не перевіряти
    @Column(name = "fam_stan",nullable = false)
    private String familyState;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "prepodFamilyState")
    private Set<FamilyMember> family = new LinkedHashSet<>();

    public Set<FamilyMember> getFamily() {
        return Collections.unmodifiableSet(family);
    }

    public void addMember(FamilyMember member) {
        family.add(member);
    }

    public void delMember(FamilyMember member) {
        family.remove(member);
    }

}
