import React from 'react';
import { useState } from 'react';
import comment from '../../../image/comment.svg';

const NoticeBoard = () => {
  const [isStuffOpen, setStuffOpen] = useState(false);
  const [writeMode, setWriteMode] = useState(false);
  const [postList, setPostList] = useState({
    postData: [
      { postID: 543212, postTitle: '공지사항', postSubmitter: '운영자' },
      {
        postID: 543211,
        postTitle: '모던 자바스크립트',
        postSubmitter: '브랜든 아이크',
      },
      { postID: 543210, postTitle: 'HTML 웹', postSubmitter: '팀 버너스리' },
    ],
  });
  const [post, setPost] = useState({
    title: '',
    content: '',
    comment: [
      { nickname: '익명의 순록', content: '나는 익명의 순록이야!' },
      { nickname: '익명의 바다사자', content: '나는 익명의 바다사자야!' },
    ],
  });

  const writeOnOff = () => {
    if (writeMode) setWriteMode(false);
    else setWriteMode(true);
  };

  const onOff = async (e: any) => {
    if (isStuffOpen) {
      setStuffOpen(false);
    } else {
      const fData = await fetch(`${process.env.REACT_APP_TEST_API}/api/post`);

      const res = await fData.json();

      setPost({ ...post, content: res.content, title: e.target.innerHTML });

      setStuffOpen(true);
    }
  };

  if (isStuffOpen) {
    return (
      <div className="ClubPage-postBox">
        <div className="ClubPage__text--title">{post.title}</div>
        <div className="ClubPage__text--contents">{post.content}</div>
        <div className="ClubPage__text--comment">
          <img src={comment} width="20px" height="20px"></img>댓글(
          {post.comment?.length})
        </div>
        <hr className="ClubPage__hr--under-comment"></hr>
        <div className="ClubPage__div--all-comment">
          {post.comment?.map((val, idx) => {
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
          <textarea className="ClubPage__textarea--submit-comment"></textarea>
          <div className="ClubPage__button--submit">등록</div>
        </div>
      </div>
    );
  } else if (writeMode) {
    return (
      <>
        <div className="ClubPage__div--write-wrap">
          <div>
            제목 :{' '}
            <input className="ClubPage__input--write-title" type="text"></input>
          </div>
          <textarea className="ClubPage__textarea--write-area"></textarea>
          <div className="ClubPage__button--submit-post">작성완료</div>
        </div>
      </>
    );
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
          {postList.postData?.map((postData, idx) => {
            return (
              <>
                <div className="ClubPage-boardStuff" key={idx}>
                  <span className="ClubPage__span--post-ID">
                    {postData.postID}
                  </span>
                  <span className="ClubPage__span--post-Title" onClick={onOff}>
                    {postData.postTitle}
                  </span>
                  <span className="ClubPage__span--post-Submitter">
                    {postData.postSubmitter}
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
