package com.board.board.controller;


import com.board.board.model.Post;
import com.board.board.model.dto.Result;
import com.board.board.model.dto.ResultCode;
import com.board.board.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final IPostService postService;


    /**
     * @param intPostSeq
     * @return
     */
    @GetMapping("/{intPostSeq}")
    public ResponseEntity<?> findPostWithPostSeq(@PathVariable int intPostSeq) {
        Long postSeq = Long.valueOf(intPostSeq);
        Post post = postService.findPostWithPostSeq(postSeq);

        return ResponseEntity.ok(post);
    }

    /**
     *
     * @param paramMap
     * 1. 전체 조회
     *  {}
     * 2. 조건 조회
     *  {
     *      "postName" : ""
     *  }
     *      or
     *  {
     *      "postContent : ""
     *  }
     *      or
     *  {
     *      "postName" : ""
     *      "postContent" : ""
     *  }
     *
     *
     * @return
     *  검색조건 like 문과 or 문의 조합으로 조회
     */
    @PostMapping("")
    public ResponseEntity<?> findPost(@RequestBody Map<String, Object> paramMap) {

        List<Post> postList = null;
        if (paramMap.isEmpty() || paramMap == null) {
            postList = postService.findAll();
        }
        else {
            postList =postService.findList(paramMap);
        }

        return ResponseEntity.ok(postList);
    }

    /**
     * 글 등록
     * {
     * "postName":"글1번",
     * "postContent":"글내용",
     * "insertUserSeq":1
     * }
     *
     * @param post
     * @return 실패시 {
     * "code": -1,
     * "message": "fail"
     * }
     * 성공시{
     * "code": 1,
     * "message": success"
     * }
     */
    @PutMapping("")
    public ResponseEntity<?> savePost(@RequestBody Post post) {
        Result result = postService.savePost(post);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);

    }

    /**
     * 글 수정
     * <p>
     * {
     * "postSeq": "1",
     * "postName":"글1번",
     * "postContent":"글내용",
     * "insertUserSeq":1
     * }
     *
     * @param post
     * @return 실패시 {
     * "code": -1,
     * "message": "fail"
     * }
     * 성공시 {
     * "code": 1,
     * "message": success"
     * }
     */
    @PatchMapping("")
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        Result result = postService.updatePost(post);

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);

    }

    /**
     * @param intPostSeq
     * @return 실패시 {
     * "code": -1,
     * "message": "fail"
     * }
     * 성공시 {
     * "code": 1,
     * "message": success"
     * }
     */
    @DeleteMapping("/{intPostSeq}")
    public ResponseEntity<?> deletePost(@PathVariable int intPostSeq) {
        Long postSeq = Long.valueOf(intPostSeq);
        Result result = postService.deletePost(postSeq);

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }


}
