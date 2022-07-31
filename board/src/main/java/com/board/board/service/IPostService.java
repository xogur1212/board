package com.board.board.service;

import com.board.board.model.Post;
import com.board.board.model.dto.Result;
import com.board.board.model.dto.ResultCode;

import java.util.List;
import java.util.Map;

public interface IPostService {


    public Post findPostWithPostSeq(Long postSeq);

    public List<Post> findList(Map<String,Object> paramMap);

    public Result savePost(Post post);

    public Result updatePost(Post updatePost);

    public Result deletePost(Long postSeq);

    public List<Post> findAll();

    public List<Post> findWithPostName(Map<String,Object> paramMap);



}
