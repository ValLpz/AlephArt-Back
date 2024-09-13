package com.stella.alephart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stella.alephart.models.Posts;
import com.stella.alephart.services.PostsService;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
	
	@Autowired
	private PostsService postService;
	
	// GET
		@GetMapping
		public List<Posts> getAllPosts() {
			return postService.findAllPosts();
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<Posts> getPostById(@PathVariable("id") Long id) {
			return postService.findPostById(id)
					.map(ResponseEntity::ok)
					.orElse(ResponseEntity.notFound().build());
		}
		
		// POST
		@PostMapping
		public Posts createPost(@RequestBody Posts post) {
			return postService.savePost(post);
		}
		
		// PUT
		@PutMapping("/{id}")
	    public ResponseEntity<Posts> updatePost(@PathVariable Long id, @RequestBody Posts updatedPost) {
	        try {
	            Posts post = postService.updatePost(id, updatedPost);
	            return ResponseEntity.ok(post);
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
		
		// DELETE
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
			return postService.findPostById(id)
					.map(post -> {
						postService.deletePost(id);
						return ResponseEntity.ok().<Void>build();
					})
					.orElse(ResponseEntity.notFound().build());
		}

}