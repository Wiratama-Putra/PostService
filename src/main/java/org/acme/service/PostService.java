package org.acme.service;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.PostDTO;
import org.acme.model.Post;
import org.acme.model.Tag;
import org.acme.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    private static final String DRAFT = "draft";
    private static final String PUBLISHED = "publish";

    @Transactional
    public Post createPost(PostDTO dto) {
        Post post = new Post();
        post.title = dto.title;
        post.content = dto.content;
        post.status = DRAFT;
        post.tags = getTagsFromLabels(dto.tags);
        postRepository.persist(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long id, PostDTO dto) {
        Post post = postRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        post.title = dto.title;
        post.content = dto.content;
        post.tags = getTagsFromLabels(dto.tags);
        postRepository.persist(post); // Pastikan perubahan tersimpan
        return post;
    }

    @Transactional
    public Post publishPost(Long id) {
        Post post = postRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        post.status = PUBLISHED;
        post.publishDate = LocalDateTime.now();
        postRepository.persist(post); // Pastikan perubahan tersimpan
        return post;
    }

    @Transactional
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Post not found");
        }
    }

    public Post getPost(Long id) {
        return postRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    @Transactional
    public List<Post> searchPostsByTag(String tagLabel) {
        return postRepository.findByTag(tagLabel);
    }

    private Set<Tag> getTagsFromLabels(List<String> labels) {
        Set<Tag> tags = new HashSet<>();
        if (labels == null || labels.isEmpty()) {
            return tags;
        }

        for (String label : labels) {
            Tag tag = Optional.ofNullable((Tag) Tag.find("label", label).firstResult()) // Explicit cast
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.label = label;
                        newTag.persist();
                        return newTag;
                    });

            tags.add(tag);
        }
        return tags;
    }


}
