import React, { SyntheticEvent, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { postAPI, getAPI } from '../../../hooks/useFetch';
import { postInfo, commentInfo } from '../../../type/type';
import { store } from '../../../hooks/store';
import comment from '../../../image/comment.svg';

const WritePage = () => {
  const [inputInfo, setInputInfo] = useState({
    title: '',
    content: '',
  });

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setInputInfo({ ...inputInfo, [id]: value });
  };

  const clubID = window.location.pathname.split('/')[2];

  const submitPost = async () => {
    const [status, res] = await postAPI(inputInfo, 'json', '/api/post');

    if (status === 403) {
      alert(res.message);
    }
  };

  return (
    <>
      <div className="ClubPage__div--write-wrap">
        <div>
          제목 :{' '}
          <input
            id="title"
            className="ClubPage__input--write-title"
            type="text"
            onChange={setInputValue}
          ></input>
        </div>
        <textarea
          id="content"
          className="ClubPage__textarea--write-area"
          onChange={setInputValue}
        ></textarea>
        <div className="ClubPage__button--submit-post" onClick={submitPost}>
          작성완료
        </div>
      </div>
    </>
  );
};

const PostList = ({
  setPostInfo,
  setCategory,
}: {
  setPostInfo: any;
  setCategory: any;
}) => {
  const clubID = window.location.pathname.split('/')[2];
  const [postList, setPostList] = useState<postInfo[]>([]);
  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/post?clubId=${clubID}`);
    setPostList((postList) => [...postList, ...res]);
  };

  const postClick = (e: SyntheticEvent) => {
    const target = e.target as HTMLSpanElement;
    setPostInfo({
      title: target.dataset.title,
      content: target.dataset.content,
      postId: target.dataset.postid,
      nickname: target.dataset.nickname,
    });
    setCategory('post');
  };

  const writeClick = () => {
    setCategory('write');
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="ClubPage-postBox">
        <div className="ClubPage__div--index">
          <span className="ClubPage__span--index-ID">번호</span>
          <span className="ClubPage__span--index-Title">제목</span>
          <span className="ClubPage__span--index-Submitter">작성자</span>
        </div>
        <hr className="ClubPage__hr--index"></hr>
        {postList?.map((postData, idx) => {
          return (
            <>
              <div className="ClubPage-boardStuff" key={idx}>
                <span className="ClubPage__span--post-ID">
                  {postData.postId}
                </span>
                <span
                  className="ClubPage__span--post-Title"
                  onClick={postClick}
                  data-postid={postData.postId}
                  data-content={postData.content}
                  data-title={postData.title}
                  data-nickname={postData.nickname}
                >
                  {postData.title}
                </span>
                <span className="ClubPage__span--post-Submitter">
                  {postData.nickname}
                </span>
              </div>
              <hr className="ClubPage__hr--index"></hr>
            </>
          );
        })}
      </div>
      <div className="ClubPage__button--post-button" onClick={writeClick}>
        글쓰기
      </div>
    </>
  );
};

const Post = ({ postInfo }: { postInfo: any }) => {
  const clubID = window.location.pathname.split('/')[2];
  const navigate = useNavigate();
  const [commentList, setCommentList] = useState<commentInfo[]>([]);
  const [globalState, setGlobalState] = useContext(store);
  const [commentInput, setCommentInput] = useState({
    postId: '',
    content: '',
  });

  const commentSubmit = async () => {
    const [status, res] = await postAPI(commentInput, 'json', '/api/comment');
    console.log(status);
  };

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setCommentInput({ ...commentInput, [id]: value });
  };

  const fetchData = async () => {
    const fData = await fetch(`${process.env.REACT_APP_TEST_API}/api/post`);
    const [status, res] = await getAPI(
      `/api/comment?postId=${postInfo.postId}`
    );
    setCommentList((commentList) => [...commentList, ...res.commentData]);
    setCommentInput({ ...commentInput, postId: postInfo.postId });
    navigate(`/post/${clubID}/${postInfo.postId}`);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="ClubPage-postBox">
      <div className="ClubPage__text--title">
        {postInfo?.title}
        <span className="ClubPage__text--post-writer">
          {postInfo?.nickname}
        </span>
      </div>
      <div className="ClubPage__text--contents">{postInfo?.content}</div>
      {postInfo?.nickname === globalState.nickname ? (
        <div className="ClubPage__button--post-modify">글수정</div>
      ) : null}

      <div className="ClubPage__text--comment">
        <img src={comment} width="20px" height="20px"></img>댓글(
        {commentList?.length})
      </div>
      <hr className="ClubPage__hr--under-comment"></hr>
      <div className="ClubPage__div--all-comment">
        {commentList?.map((val, idx) => {
          return (
            <div className="ClubPage__div--comment-frame" key={idx}>
              <div className="ClubPage__text--comment-nickname">
                {val.nickname}
              </div>
              <div className="ClubPage__text--comment-content">
                {val.content}
              </div>
            </div>
          );
        })}
      </div>

      <div className="ClubPage__div--submit-frame">
        <textarea
          className="ClubPage__textarea--submit-comment"
          id="content"
          onChange={setInputValue}
        ></textarea>
        <div className="ClubPage__button--submit" onClick={commentSubmit}>
          등록
        </div>
      </div>
    </div>
  );
};

export { PostList, Post, WritePage };
