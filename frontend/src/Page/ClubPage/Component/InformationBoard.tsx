import React, { useState, useEffect } from 'react';
import { postAPI } from '../../../hooks/useFetch';
import thumbnail from '../../../image/thumbnail.svg';
const InformationBoard = () => {
  const [clubInformation, setClubInformation] = useState({
    thumbnail: '',
    description: '',
  });

  const [isSigned, setisSigned] = useState(false);

  const signCheck = async () => {
    const data = { username: '팀버너스리' };

    const fData = await postAPI(data, 'json', '/api/signCheck');

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

export default InformationBoard;
