import React from 'react';

const NavigateBar = () => {
  return (
    <>
      <hr></hr>
      <div className="ClubPage-navigateBar">
        <div className="ClubPage-navigateBar-information">정보</div>
        <div className="ClubPage-navigateBar-board">게시판</div>
        <div className="ClubPage-navigateBar-photo">사진</div>
        <div className="ClubPage-navigateBar-chat">채팅</div>
      </div>
      <hr className="horizon"></hr>
    </>
  );
};

export default NavigateBar;
