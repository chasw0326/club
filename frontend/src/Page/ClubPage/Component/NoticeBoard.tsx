import React, { SyntheticEvent, useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postAPI, getAPI, putAPI, deleteAPI } from '../../../hooks/useFetch';
import { postInfo, commentInfo } from '../../../type/type';
import comment from '../../../image/comment.svg';
import ModifyBoard from './ModifyBoard';
import CommentItem from './CommentItem';
import sad from '../../../image/sad.svg';

const WritePage = ({ postInfo }: { postInfo: any }) => {
  const [inputInfo, setInputInfo] = useState({
    title: '',
    content: '',
  });

  const clubID = window.location.pathname.split('/')[2];

  const inputRef = useRef<any>([]);

  const setInputValue = (e: SyntheticEvent) => {
    const { id, value } = e.target as HTMLInputElement;
    setInputInfo({ ...inputInfo, [id]: value });
  };

  const submitPost = async () => {
    const [status, res] = await postAPI(
      inputInfo,
      'json',
      `/api/post?clubId=${clubID}`
    );

    if (status === 403) {
      alert(res.message);
    }

    window.location.reload();
  };

  useEffect(() => {
    if (postInfo.title === '') {
      console.log(postInfo);
      setInputInfo({
        ...inputInfo,
        title: postInfo?.title,
        content: postInfo?.content,
      });
      inputRef?.current?.forEach((val: any, idx: number) => {
        val.value = postInfo[val.id];
      });
    }
  }, []);

  return (
    <>
      <div className="ClubPage__div--write-wrap">
        <div>
          <input
            id="title"
            className="ClubPage__input--write-title"
            type="text"
            onChange={setInputValue}
            placeholder="제목을 입력하세요"
            ref={(el) => (inputRef.current[0] = el)}
          ></input>
        </div>
        <textarea
          id="content"
          className="ClubPage__textarea--write-area"
          onChange={setInputValue}
          ref={(el) => (inputRef.current[1] = el)}
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
  const [page, setPage] = useState(0);
  const clubID = window.location.pathname.split('/')[2];
  const [postList, setPostList] = useState<postInfo[]>([]);
  const [isPostOpen, setIsPostOpen] = useState(false);
  const fetchData = async () => {
    const [status, res] = await getAPI(
      `/api/post?clubId=${clubID}&page=${page}`
    );

    if (status === 200) {
      setPostList((postList) => res);
    } else {
      alert(res.message);
      navigate(-1);
    }
  };

  const navigate = useNavigate();

  const postClick = (e: SyntheticEvent) => {
    const target = e.target as HTMLSpanElement;
    setPostInfo({
      title: target.dataset.title,
      content: target.dataset.content,
      postId: target.dataset.postid,
      nickname: target.dataset.nickname,
    });
    setCategory('post');
    navigate(`/post/${clubID}/${target.dataset.postid}/${page + 1}`);
  };

  const writeClick = () => {
    setCategory('write');
  };

  const nextPage = () => {
    setPage((page) => page + 1);
  };

  const prevPage = () => {
    setPage((page) => page - 1);
  };

  useEffect(() => {
    fetchData();
  }, [page]);

  return (
    <>
      {postList.length ? (
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
                <React.Fragment key={postData.postId}>
                  <div className="ClubPage-boardStuff">
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
                </React.Fragment>
              );
            })}
          </div>
        </>
      ) : (
        <div className="ClubPage-noResult">
          <img src={sad} width="100px" height="100px"></img>아직 올라온 게시글이
          없어요!
        </div>
      )}
      <div className="NoticeBoard-footer">
        {' '}
        {page ? (
          <span className="NoticeBoard__button--prevPage" onClick={prevPage}>
            이전페이지
          </span>
        ) : null}
        <span className="ClubPage__button--post-button" onClick={writeClick}>
          글쓰기{' '}
        </span>
        {postList.length < 20 ? null : (
          <span className="NoticeBoard__button--nextPage" onClick={nextPage}>
            다음페이지
          </span>
        )}
      </div>
    </>
  );
};

const Post = () => {
  const clubID = window.location.pathname.split('/')[2];
  const postId = window.location.pathname.split('/')[3];
  const page = parseInt(window.location.pathname.split('/')[4]);
  const navigate = useNavigate();
  const [commentList, setCommentList] = useState<commentInfo[]>([]);
  const [postInfo, setPostInfo] = useState<postInfo>();
  const [userNickname, setUserNickname] = useState('');
  const [commentInput, setCommentInput] = useState({
    postId: '',
    content: '',
  });
  const [modifyMode, setModifyMode] = useState(false);

  const commentSubmit = async () => {
    const [status, res] = await postAPI(commentInput, 'json', '/api/comment');

    window.location.reload();
  };

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setCommentInput({ ...commentInput, [id]: value });
  };

  const fetchData = async () => {
    const [statusPost, resPost] = await getAPI(
      `/api/post?clubId=${clubID}&page=${page - 1}`
    );
    const targetPost = resPost.find(
      (val: postInfo) => val.postId.toString() === postId
    );

    setPostInfo({ ...postInfo, ...targetPost });

    const [statusComment, resComment] = await getAPI(
      `/api/comment?postId=${targetPost.postId}`
    );
    setCommentList((commentList) => [
      ...commentList,
      ...resComment.commentData,
    ]);
    setCommentInput({ ...commentInput, postId: targetPost.postId });

    const [statusUser, resUser] = await getAPI('/api/user');

    setUserNickname(resUser.nickname);
  };

  const setModify = () => {
    if (!modifyMode) setModifyMode(true);
  };

  const erasePost = async () => {
    const [status, res] = await deleteAPI(
      `/api/post/${postInfo?.postId}?clubId=${clubID}`
    );
    if (status === 200) {
      alert('글이 삭제 되었습니다.');
      navigate(`/board/${clubID}`);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {modifyMode ? (
        <ModifyBoard postInfo={postInfo!}></ModifyBoard>
      ) : (
        <div className="ClubPage-postBox">
          <div className="ClubPage__div--post-upper">
            <span className="ClubPage__text--title">{postInfo?.title}</span>
            <span className="ClubPage__text--post-writer">
              {postInfo?.nickname}
            </span>
          </div>
          <div className="ClubPage__text--contents">{postInfo?.content}</div>
          <div className="ClubPage__div--post-modify-erase">
            {postInfo?.nickname === userNickname ? (
              <>
                <span
                  className="ClubPage__button--post-modify"
                  onClick={setModify}
                >
                  글수정
                </span>
                &nbsp; &nbsp; &nbsp; &nbsp;
                <span
                  className="ClubPage__button--post-erase"
                  onClick={erasePost}
                >
                  글삭제
                </span>
              </>
            ) : null}
          </div>

          <div className="ClubPage__text--comment">
            <img src={comment} width="20px" height="20px"></img>댓글(
            {commentList?.length})
          </div>
          <hr className="ClubPage__hr--under-comment"></hr>
          <div className="ClubPage__div--all-comment">
            {commentList?.map((val, idx) => {
              return (
                <CommentItem
                  key={val.commentId}
                  commentData={val}
                ></CommentItem>
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
      )}
    </>
  );
};

export { PostList, Post, WritePage };
