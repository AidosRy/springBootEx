package kz.iitu.swd.group34.FirstSpringBootIITU.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int yearOfAddmission;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Courses> course;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Groups> group;


}
