import React, { useEffect } from 'react';
import { useState } from 'react';
import { getAPI } from '../../../hooks/useFetch';
import { myClub, myComment, myPost } from '../../../type/type';
import thumbnail from '../../../image/thumbnail.svg';
import { init } from '../../../type/type';

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
              <img src={thumbnail}></img>
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

  const fetchData = async () => {
    const [status, res] = await getAPI('/api/user/posts');
    setPostInfo((postInfo) => [...postInfo, ...res]);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {postInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--myPost">
              <div>{val.nickname}</div>
              <div>{val.title}</div>
              <div>{val.createdAt}</div>
            </div>
          </>
        );
      })}
    </>
  );
};

const MyComment = () => {
  const [commentInfo, setCommentInfo] = useState<myComment[]>([]);

  const fetchData = async () => {
    const [status, res] = await getAPI('/api/user/comments');
    setCommentInfo((commentInfo) => [...commentInfo, ...res]);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      {commentInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--myPost">
              <div>{val.postTitle}</div>
              <div>{val.commentData.content}</div>
              <div>{val.commentData.createdAt}</div>
            </div>
          </>
        );
      })}
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
