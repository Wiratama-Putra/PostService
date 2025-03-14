package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.PostDTO;
import org.acme.model.Post;
import org.acme.service.PostService;

import java.util.List;

@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @POST
    public Response createPost(PostDTO dto) {
        return Response.ok(postService.createPost(dto)).build();
    }

    @POST
    @Path("publish/{id}")
    @RolesAllowed({"Admin"})
    public Response publishPost(@PathParam("id") Long id) {
        return Response.ok(postService.publishPost(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePost(@PathParam("id") Long id, PostDTO dto) {
        return Response.ok(postService.updatePost(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePost(@PathParam("id") Long id) {
        postService.deletePost(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getPost(@PathParam("id") Long id) {
        return Response.ok(postService.getPost(id)).build();
    }

    @GET
    public List<Post> getPostsByTag(@QueryParam("tag") String tagLabel) {
        if (tagLabel != null) {
            return postService.searchPostsByTag(tagLabel);
        }
        return Post.listAll();
    }
}
