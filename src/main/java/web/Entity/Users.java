package web.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")

public class Users {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password_user")
    private String passwordUser;

    @Column(name = "role_user")
    private String roleUser;

    @Column(name = "data_user")
    private String dataUser;

    @OneToMany(mappedBy = "materals_db", fetch = FetchType.EAGER)
    private List<Materals_db> materals_dbs;
}
