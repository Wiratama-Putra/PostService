package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Post;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    @Inject
    EntityManager em;

    public List<Post> findByTag(String tagLabel) {
        return em.createQuery("SELECT p FROM Post p JOIN p.tags t WHERE t.label = :tag", Post.class)
                .setParameter("tag", tagLabel)
                .getResultList();
    }
}
