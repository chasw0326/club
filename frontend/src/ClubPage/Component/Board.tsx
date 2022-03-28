import React, { useEffect } from 'react';
import { useState } from 'react';
import thumbnail from '../../image/thumbnail.svg';
import '../Style/clubPage.scss';

const Information = () => {
  const [clubInformation, setClubInformation] = useState({
    thumbnail: '',
    description: '',
  });

  const setInfo = async () => {
    const fData = await fetch(
      `${process.env.REACT_APP_TEST_API}/api/information`
    );
    const res = await fData.json();

    setClubInformation({ ...clubInformation, description: res.description });
  };

  useEffect(() => {
    setInfo();
  }, []);

  return (
    <>
      <div className="ClubPage-middleFrame">
        <img className="ClubPage-thumbnail" src={thumbnail}></img>
        <div className="ClubPage-description">
          좌측 썸네일과 우측 설명은 api로 정보를 받아와야 합니다.
          {clubInformation.description}
        </div>
      </div>
    </>
  );
};

const NoticeBoard = () => {
  const [isStuffOpen, setStuffOpen] = useState(false);
  const [post, setPost] = useState('');
  const data = [
    { title: 'no1' },
    { title: 'no2' },
    { title: 'no3' },
    { title: 'no4' },
  ];

  const onOff = async () => {
    if (isStuffOpen) {
      setStuffOpen(false);
    } else {
      const fData = await fetch(`${process.env.REACT_APP_TEST_API}/api/post`);

      const res = await fData.json();

      console.log(res);

      setPost(res.des);

      setStuffOpen(true);
    }
  };

  if (isStuffOpen) {
    return <div className="ClubPage-postBox">{post}</div>;
  } else {
    return (
      <>
        <div>
          {data.map((val, idx) => {
            return (
              <div className="ClubPage-boardStuff" key={idx} onClick={onOff}>
                {val.title}
              </div>
            );
          })}
        </div>
      </>
    );
  }
};

const PhotoBoard = () => {
  return <div>사진 게시판이다.</div>;
};

const Board = ({ state, setState }: { state: any; setState: any }) => {
  const stateToAPI = { 정보: 'information', 게시판: 'board', 사진: 'photo' };

  useEffect(() => {}, []);

  if (state === '정보') {
    return (
      <>
        <Information></Information>
      </>
    );
  } else if (state === '게시판') {
    return <NoticeBoard></NoticeBoard>;
  } else if (state === '사진') {
    return (
      <>
        <PhotoBoard></PhotoBoard>
      </>
    );
  } else {
    return <div>채팅이다.</div>;
  }
};

export default Board;
