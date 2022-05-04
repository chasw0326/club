import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { postAPI, getAPI } from '../../../hooks/useFetch';
import { postInfo, commentInfo } from '../../../type/type';
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

const NoticeBoard = () => {
  const navigate = useNavigate();
  const clubID = window.location.pathname.split('/')[2];
  const [isStuffOpen, setStuffOpen] = useState(false);
  const [writeMode, setWriteMode] = useState(false);
  const [postList, setPostList] = useState<postInfo[]>([]);
  const [post, setPost] = useState({
    title: '',
    content: '',
  });
  const [commentList, setCommentList] = useState<commentInfo[]>([]);

  const [commentInput, setCommentInput] = useState({
    postId: '',
    content: '',
  });

  // const [postList, setPostList] = useState({
  //   postData: [
  //     { postID: 543212, postTitle: '공지사항', postSubmitter: '운영자' },
  //     {
  //       postID: 543211,
  //       postTitle: '모던 자바스크립트',
  //       postSubmitter: '브랜든 아이크',
  //     },
  //     { postID: 543210, postTitle: 'HTML 웹', postSubmitter: '팀 버너스리' },
  //   ],
  // });

  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/post?clubId=${clubID}`);

    setPostList((postList) => [...postList, ...res]);
  };

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setCommentInput({ ...commentInput, [id]: value });
  };

  const writeOnOff = () => {
    if (writeMode) setWriteMode(false);
    else setWriteMode(true);
  };

  const onOff = async (e: any) => {
    if (isStuffOpen) {
      setStuffOpen(false);
    } else {
      const fData = await fetch(`${process.env.REACT_APP_TEST_API}/api/post`);

      const tempPostId = e.target.dataset.postid;

      const [status, res] = await getAPI(`/api/comment?postId=${tempPostId}`);

      setCommentList((commentList) => [...commentList, ...res.commentData]);
      setPost({
        ...post,
        title: e.target.dataset.title,
        content: e.target.dataset.content,
      });
      setStuffOpen(true);
      setCommentInput({ ...commentInput, postId: tempPostId });
      navigate(`/board/${clubID}/${tempPostId}`);
    }
  };

  const commentSubmit = async () => {
    const [status, res] = await postAPI(commentInput, 'json', '/api/comment');
    console.log(status);
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (isStuffOpen) {
    return (
      <div className="ClubPage-postBox">
        <div className="ClubPage__text--title">{post.title}</div>
        <div className="ClubPage__text--contents">{post.content}</div>
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
  } else if (writeMode) {
    return <WritePage></WritePage>;
  } else {
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
                    onClick={onOff}
                    data-postid={postData.postId}
                    data-content={postData.content}
                    data-title={postData.title}
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
        <div className="ClubPage__button--post-button" onClick={writeOnOff}>
          글쓰기
        </div>
      </>
    );
  }
};

export default NoticeBoard;
