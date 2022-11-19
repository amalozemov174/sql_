package com.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "post")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

public class Post {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NonNull
    private String text;

    @Column
    @NonNull
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;
}
