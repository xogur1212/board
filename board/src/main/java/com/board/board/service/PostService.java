package com.board.board.service;

import com.board.board.model.Post;
import com.board.board.model.dto.Result;
import com.board.board.repository.PostRepository;
import com.board.board.model.dto.ResultCode;
import com.board.board.service.specification.PostSpecification;
import com.board.board.util.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPostService {


    private final PostRepository postRepository;


    @Override
    public Post findPostWithPostSeq(Long postSeq) {

        Optional<Post> optionalPost = postRepository.findByPostSeq(postSeq);
        Post post = null;
        if (!optionalPost.isPresent()) {
            post = null;
        } else {
            post = optionalPost.get();
        }

        return post;
    }

    @Override
    public List<Post> findList(Map<String,Object> paramMap) {


        String postName = null;
        Specification<Post> spec = null;
        if(paramMap.get("postName") != null){
            postName = paramMap.get("postName").toString();
            spec = Specification.where(PostSpecification.likePostName(postName));

        }
        List<Post> userList = postRepository.findAll(spec);
        /* 이름 관련 검색 */
        /*  */


//        String postName = paramMap.get("postName").toString();
//        Specification<Post> spec = Specification.where(PostSpecification.likePostName(postName))
//                .or(PostSpecification.likePostContent(keyword));
        /* 내용 관련 검색 */
        return userList;

    }

    @Override
    public Result savePost(Post post) {


        if (post.getPostName() == null || post.getPostContent() == null || post.getInsertUserSeq() == null) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }
        Post savePost = new Post();
        savePost.setPostName(post.getPostName());
        savePost.setPostContent(post.getPostContent());
        Timestamp timestamp = null;

        if (post.getInsertUserSeq() != null) {
            timestamp = new Timestamp(System.currentTimeMillis());
            savePost.setInsertUserSeq(post.getInsertUserSeq());
            savePost.setInsertTime(timestamp);
        }
        //TODO 실패에 관련된 코드 추가 해야 됨
        postRepository.save(savePost);
        return ResultUtil.makeResult(ResultCode.SUCCESS);

    }

    @Override
    @Transactional
    public Result updatePost(Post post) {

        if (post.getUpdateUserSeq() == null) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }
        Optional<Post> optionalUpdatePost = postRepository.findByPostSeq(post.getPostSeq());
        if (!optionalUpdatePost.isPresent()) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }
        Post updatePost = optionalUpdatePost.get();
        updatePost.setPostName(post.getPostName());
        updatePost.setPostContent(post.getPostContent());
        Timestamp timestamp = null;

        if (post.getInsertUserSeq() != null) {
            timestamp = new Timestamp(System.currentTimeMillis());
            updatePost.setInsertUserSeq(post.getInsertUserSeq());
            updatePost.setInsertTime(timestamp);
        }
        if (post.getUpdateUserSeq() != null) {
            timestamp = new Timestamp(System.currentTimeMillis());
            updatePost.setUpdateUserSeq(post.getUpdateUserSeq());
            updatePost.setUpdateTime(timestamp);
        }
        //TODO 실패에 관련된 코드 추가 해야 됨
        postRepository.save(updatePost);
        return ResultUtil.makeResult(ResultCode.SUCCESS);

    }

    @Override
    public Result deletePost(Long postSeq) {
        if (postSeq == null) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }
        Optional<Post> optionalPost = postRepository.findByPostSeq(postSeq);
        if (!optionalPost.isPresent()) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }
        Long count = postRepository.deleteByPostSeq(postSeq);
        if (count == 0) {
            return ResultUtil.makeResult(ResultCode.FAIL);
        }

        return ResultUtil.makeResult(ResultCode.SUCCESS);
    }

    @Override
    public List<Post> findAll() {
        List<Post> postList = postRepository.findAll();
        return postList;
    }



    @Override
    public List<Post> findWithPostName(Map<String, Object> paramMap) {

        int start = 0;
        int length = 0;
        int count = 0;

        return null;
    }


}
