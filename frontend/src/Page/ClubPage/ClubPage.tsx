import React, { MouseEventHandler, RefAttributes, useEffect } from 'react';
import MainHeader from '../../SharedComponent/Header';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Board from './Component/Board';

const BOARDSTATE: any = Object.freeze({
  정보: 'information',
  게시판: 'board',
  사진: 'photo',
  관리: 'management',
});

const ClubPage = () => {
  const [category, setCategory] = useState('정보');
  const clubID = window.location.pathname.split('/')[2];
  const navigate = useNavigate();

  const selectCategory = (e: React.SyntheticEvent) => {
    const target = e.target as HTMLDivElement;
    console.log(target.innerHTML);
    setCategory(target.innerHTML);
    navigate(`/${BOARDSTATE[target.innerHTML]}/${clubID}`);
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
            관리
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
