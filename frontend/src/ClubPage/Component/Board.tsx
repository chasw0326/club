import React, { useEffect } from 'react';
import { useState } from 'react';
import thumbnail from '../../image/thumbnail.svg';
import '../Style/clubPage.scss';
import { postAPI } from '../../hooks/useFetch';
import NoticeBoard from './NoticeBoard';

const Information = () => {
  const [clubInformation, setClubInformation] = useState({
    thumbnail: '',
    description: '',
  });

  const [isSigned, setisSigned] = useState(false);

  const signCheck = async () => {
    const data = { username: '팀버너스리' };

    const fData = await postAPI(data, 'json', '/api/signCheck');

    console.log(fData);

    setisSigned(fData.signed);
  };

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
      <hr></hr>
      <div className="ClubPage-signIn">
        {isSigned ? '가입완료' : '가입하기'}
      </div>
    </>
  );
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
