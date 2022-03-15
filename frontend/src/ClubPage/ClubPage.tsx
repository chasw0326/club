import React from 'react';
import MainHeader from '../SharedComponent/Header';
import NavigateBar from './Component/NavigateBar';
import thumbnail from '../image/thumbnail.svg';

const ClubPage = () => {
  return (
    <>
      <div className="ClubPage">
        <MainHeader></MainHeader>
        <NavigateBar></NavigateBar>
        <div className="ClubPage-middleFrame">
          <img className="ClubPage-thumbnail" src={thumbnail}></img>
          <div className="ClubPage-description">
            좌측 썸네일과 우측 설명은 api로 정보를 받아와야 합니다.
          </div>
        </div>
        <hr></hr>
        <div className="ClubPage-signIn">가입하기</div>
        <span>
          가입하기는 로그인 한 학생의 정보를 담아 post로 요청할 것 같습니다.
        </span>
        <hr></hr>
      </div>
    </>
  );
};

export default ClubPage;
