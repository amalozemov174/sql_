package com.hibernate;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "my_user")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column
    @NonNull
    private Date createdAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
