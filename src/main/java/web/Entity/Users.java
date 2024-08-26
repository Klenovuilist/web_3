package web.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")

public class Users {

    @Id
    @Column
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password_user")
    private String passwordUser;
}
