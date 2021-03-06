import React, { useContext, useState } from 'react';
import { store } from '../../../hooks/store';
import { putAPI, deleteAPI } from '../../../hooks/useFetch';
import { commentInfo } from '../../../type/type';

const CommentItem = ({ commentData }: { commentData: commentInfo }) => {
  const [globalState, setGlobalState] = useContext(store);
  const [commentModifyMode, setCommentModifyMode] = useState(false);
  const clubID = window.location.pathname.split('/')[2];
  const setModifyMode = () => {
    if (commentModifyMode) setCommentModifyMode(false);
    else setCommentModifyMode(true);
  };

  const [commentInput, setCommentInput] = useState({
    content: '',
  });

  const modifySubmit = () => {
    setModifyMode();
    const modifyData = { content: commentInput.content };
    console.log(commentInput.content);
    putAPI(modifyData, 'json', `/api/comment/${commentData.commentId}`);
    window.location.reload();
  };

  const eraseComment = async () => {
    const [status, res] = await deleteAPI(
      `/api/comment/${commentData.commentId}?clubId=${clubID}`
    );
    window.location.reload();
  };

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setCommentInput({ ...commentInput, [id]: value });
  };

  return (
    <>
      {commentModifyMode ? (
        <div className="ClubPage__div--submit-frame">
          <textarea
            className="ClubPage__textarea--submit-comment"
            id="content"
            onChange={setInputValue}
          ></textarea>
          <div className="ClubPage__button--submit" onClick={modifySubmit}>
            수정
          </div>
        </div>
      ) : (
        <div
          className="ClubPage__div--comment-frame"
          key={commentData.commentId}
        >
          <div className="ClubPage__text--comment-nickname">
            <span>{commentData?.nickname}</span>
            <span className="ClubPage__div--modify-delete">
              {' '}
              {commentData?.nickname === globalState.nickname ? (
                <>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span
                    className="ClubPage__text--comment-modify"
                    onClick={setModifyMode}
                  >
                    수정
                  </span>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span
                    className="ClubPage__text--comment-delete"
                    onClick={eraseComment}
                  >
                    삭제
                  </span>
                </>
              ) : null}
            </span>
          </div>

          <div
            className="ClubPage__text--comment-content"
            key={commentData.commentId}
          >
            {commentData?.content}
          </div>
        </div>
      )}
    </>
  );
};

export default CommentItem;
