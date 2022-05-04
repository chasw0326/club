import React, { useEffect } from 'react';

import InformationBoard from './InformationBoard';
import '../Style/clubPage.scss';
import ManangementBoard from './ManagementBoard';
import NoticeBoard from './NoticeBoard';

const PhotoBoard = () => {
  return <div>사진 게시판이다.</div>;
};

const Board = ({ state, setState }: { state: any; setState: any }) => {
  const stateToAPI = Object.freeze({
    정보: 'information',
    게시판: 'board',
    사진: 'photo',
  });

  useEffect(() => {}, []);

  if (state === '정보') {
    return (
      <>
        <InformationBoard></InformationBoard>
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
    return <ManangementBoard></ManangementBoard>;
  }
};

export default Board;
