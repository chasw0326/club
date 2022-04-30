import React, { useEffect } from 'react';
import { useState } from 'react';
import { myClub, myComment, myPost } from '../../../type/type';
import thumbnail from '../../../image/thumbnail.svg';
import { init } from '../../../type/type';

const MyClub = () => {
  const [clubInfo, setClubInfo] = useState<myClub[]>();

  useEffect(() => {
    //api 호출해야하는 부분

    const data: myClub[] = [
      {
        universitiy: '인하대학교',
        club: '네타스',

        description: '네타스입니다',
        address: '하이테크1층',
      },
      {
        universitiy: '인하대학교',
        club: '아이그루스',
        description: '아이그루스입니다',
        address: '하이테크3층',
      },
    ];

    setClubInfo(data);
  }, []);

  return (
    <>
      {clubInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--belong-club">
              <img src={thumbnail}></img>
              <div className="MyPage__div--club-info">
                <div>{val.universitiy}</div>
                <div>{val.club}</div>
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
  const [postInfo, setPostInfo] = useState<myPost[]>();

  useEffect(() => {
    //api 호출해야하는 부분

    const data: myPost[] = [
      {
        nickname: '팀버너스리',
        title: 'html',
        content: 'html의 창시자',
        createdAt: '2021-03-29',
      },
      {
        nickname: '브랜던 아이크',
        title: 'javascript',
        content: 'javascript의 창시자',
        createdAt: '2021-03-29',
      },
    ];

    setPostInfo(data);
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
  const [commentInfo, setCommentInfo] = useState<myComment[]>();

  useEffect(() => {
    //api 호출해야하는 부분

    const data: myComment[] = [
      {
        nickname: '팀버너스리',
        title: 'html',
        content: 'html의 창시자',
        createdAt: '2021-03-29',
      },
      {
        nickname: '브랜던 아이크',
        title: 'javascript',
        content: 'javascript의 창시자',
        createdAt: '2021-03-29',
      },
    ];

    setCommentInfo(data);
  }, []);

  return (
    <>
      {commentInfo?.map((val, idx) => {
        return (
          <>
            <div className="MyPage__div--myPost">
              <div>{val.title}</div>
              <div>{val.content}</div>
              <div>{val.createdAt}</div>
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
