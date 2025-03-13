package org.acme.Service;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.acme.DTO.PostDTO;
import org.acme.Model.Post;
import org.acme.Model.Tag;
import org.acme.Repository.PostRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class PostService {

    @Inject
    JsonWebToken jwt;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createPost(PostDTO dto) {
        Post post = new Post();
        post.title = dto.title;
        post.content = dto.content;
        post.status = "draft";
        post.tags = getTagsFromLabels(dto.tags);
        postRepository.persist(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long id, PostDTO dto) {
        Post post = postRepository.findById(id);
        if (post == null) throw new NotFoundException("Post not found");

        post.title = dto.title;
        post.content = dto.content;
        post.tags = getTagsFromLabels(dto.tags);
        return post;
    }

    @Transactional
    public Post publishPost(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) throw new NotFoundException("Post not found");

        post.status = "publish";
        post.publishDate = LocalDateTime.now();
        return post;
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post getPost(Long id) {
        return postRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Post not found"));
    }

    @Transactional
    public List<Post> searchPostsByTag(String tagLabel) {
        return postRepository.findByTag(tagLabel);
    }

    private Set<Tag> getTagsFromLabels(List<String> labels) {
        Set<Tag> tags = new HashSet<>();
        for (String label : labels) {
            Tag tag = Tag.find("label", label).firstResult();
            if (tag == null) {
                tag = new Tag();
                tag.label = label;
                tag.persist();
            }
            tags.add(tag);
        }
        return tags;
    }

}
