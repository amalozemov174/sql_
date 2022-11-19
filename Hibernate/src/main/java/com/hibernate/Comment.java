package com.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Table(name = "comment")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

public class Comment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NonNull
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @NonNull
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;

    @Column
    @NonNull
    private Date createdAt;

}
