import React, { MouseEventHandler, RefAttributes, useEffect } from 'react';
import MainHeader from '../SharedComponent/Header';
import { useState } from 'react';
import Board from './Component/Board';
import { postAPI } from '../hooks/useFetch';

const ClubPage = () => {
  const [isSigned, setisSigned] = useState(false);

  const [category, setCategory] = useState('정보');

  const test = async () => {
    const data = { username: '팀버너스리' };

    const fData = await postAPI(data, 'json', '/api/signCheck');

    console.log(fData);

    setisSigned(fData.signed);
  };

  const selectCategory = (e: any) => {
    setCategory(e.target.innerHTML);
  };

  useEffect(() => {
    test();
  }, []);

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
        <div className="ClubPage-signIn">
          {isSigned ? '가입완료' : '가입하기'}
        </div>
        <span>
          가입하기는 로그인 한 학생의 정보를 담아 post로 요청할 것 같습니다.{' '}
          <br></br>
          그리고 만약 이미 가입한 동아리라면 가입완료라고 출력할 예정입니다.
          가입했는지 아닌지에 대한 정보도 필요할 것 같습니다.
        </span>
        <hr></hr>
      </div>
    </>
  );
};

export default ClubPage;
