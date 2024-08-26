package web.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "materals_db")


public class Materals_db {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "limit_strength")
    private int limitStrength;

    @Column(name = "materials")
    private String materials;

    @OneToMany(mappedBy = "materals_db", fetch = FetchType.LAZY)
    private List<Moments_db> moments_db;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

//    @OneToOne
//    private Moments_db moments_db = new Moments_db();



}
