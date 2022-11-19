package com.hibernate;

import org.hibernate.Session;

public class InstDAO {

    private Session session;

    public InstDAO(Session session) {
        this.session = session;
    }

    public void saveUserToDb(User user) {
        session.save(user);
    }

    public void savePostToDb(Post post) {
        session.save(post);
    }

    public void saveCommentToDb(Comment comment) {
        session.save(comment);
    }
}
