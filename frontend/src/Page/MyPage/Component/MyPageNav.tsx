import React, { SyntheticEvent, useEffect } from 'react';
import { useState } from 'react';
import { getAPI } from '../../../hooks/useFetch';
import { myClub, myComment, myPost } from '../../../type/type';
import next from '../../../image/next.svg';
import prev from '../../../image/prev.svg';
const MyClub = () => {
  const [clubInfo, setClubInfo] = useState<myClub[]>([]);

  const fetchData = async () => {
    const [status, res] = await getAPI('/api/users/joined-club');
    setClubInfo((clubInfo) => [...clubInfo, ...res]);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {clubInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--belong-club">
              <img src={val.imageUrl}></img>
              <div className="MyPage__div--club-info">
                <div>{val.university}</div>
                <div>{val.name}</div>
                <div>{val.description}</div>
                <div className="MyPage__text--club-address">{val.address}</div>
              </div>
            </div>
          </>
        );
      })}
    </>
  );
};

const MyPost = () => {
  const [postInfo, setPostInfo] = useState<myPost[]>([]);
  const [page, setPage] = useState(0);
  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/user/posts?page=${page}`);
    setPostInfo((postInfo) => res);
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
      {postInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--myPost">
              <div>{val.nickname}</div>
              <div>{val.title}</div>
              <div>{val.createdAt.split('T')[0]}</div>
            </div>
          </>
        );
      })}
      <div className="MyPage-footer">
        {' '}
        {page ? (
          <span className="NoticeBoard__button--prevPage" onClick={prevPage}>
            <img src={prev} width="40px" height="40px"></img>
            이전
          </span>
        ) : null}
        {postInfo.length < 20 ? null : (
          <span className="NoticeBoard__button--nextPage" onClick={nextPage}>
            다음
            <img src={next} width="40px" height="40px"></img>
          </span>
        )}
      </div>
    </>
  );
};

const MyComment = () => {
  const [commentInfo, setCommentInfo] = useState<myComment[]>([]);
  const [page, setPage] = useState(0);
  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/user/comments?page=${page}`);
    setCommentInfo((commentInfo) => res);
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
      {commentInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--myPost">
              <div>{val?.postTitle}</div>
              <div>{val?.commentData.content}</div>
              <div>{val?.commentData.createdAt.split('T')[0]}</div>
            </div>
          </>
        );
      })}
      <div className="MyPage-footer">
        {' '}
        {page ? (
          <span className="NoticeBoard__button--prevPage" onClick={prevPage}>
            <img src={prev} width="40px" height="40px"></img>
            이전
          </span>
        ) : null}
        {commentInfo.length < 20 ? null : (
          <span className="NoticeBoard__button--nextPage" onClick={nextPage}>
            다음
            <img src={next} width="40px" height="40px"></img>
          </span>
        )}
      </div>
    </>
  );
};

const MyPageNav = ({ viewState }: { viewState: string }) => {
  if (viewState === '가입한 모임') {
    return <MyClub></MyClub>;
  } else if (viewState === '작성한 글') {
    return <MyPost></MyPost>;
  } else if (viewState === '작성한 댓글') {
    return <MyComment></MyComment>;
  }

  return <div></div>;
};

export default MyPageNav;
