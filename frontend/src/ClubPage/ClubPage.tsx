import React, { MouseEventHandler, RefAttributes, useEffect } from 'react';
import MainHeader from '../SharedComponent/Header';
import { useState } from 'react';
import Board from './Component/Board';

const ClubPage = () => {
  const [category, setCategory] = useState('정보');

  const selectCategory = (e: any) => {
    setCategory(e.target.innerHTML);
  };

  return (
    <>
      <div className="ClubPage">
        <MainHeader></MainHeader>
        <hr></hr>
        <div className="ClubPage-navigateBar">
          <div
            className="ClubPage-navigateBar-information"
            onClick={selectCategory}
          >
            정보
          </div>
          <div className="ClubPage-navigateBar-board" onClick={selectCategory}>
            게시판
          </div>
          <div className="ClubPage-navigateBar-photo" onClick={selectCategory}>
            사진
          </div>
          <div className="ClubPage-navigateBar-chat" onClick={selectCategory}>
            채팅
          </div>
        </div>
        <hr className="horizon"></hr>
        <Board state={category} setState={setCategory}></Board>
        <hr></hr>
      </div>
    </>
  );
};

export default ClubPage;
