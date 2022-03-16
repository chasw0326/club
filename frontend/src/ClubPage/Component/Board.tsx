import React from 'react';
import { Statement } from 'typescript';
import thumbnail from '../../image/thumbnail.svg';

const Board = ({ state, setState }: { state: any; setState: any }) => {
  if (state === '정보') {
    return (
      <>
        <div className="ClubPage-middleFrame">
          <img className="ClubPage-thumbnail" src={thumbnail}></img>
          <div className="ClubPage-description">
            좌측 썸네일과 우측 설명은 api로 정보를 받아와야 합니다.
          </div>
        </div>
      </>
    );
  } else if (state === '게시판') {
    return <div>게시판이다.</div>;
  } else if (state === '사진') {
    return <div>사진이다.</div>;
  } else {
    return <div>채팅이다.</div>;
  }
};

export default Board;
