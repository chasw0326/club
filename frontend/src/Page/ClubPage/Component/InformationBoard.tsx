import React, { useState, useEffect } from 'react';
import { postAPI, getAPI } from '../../../hooks/useFetch';
import { clubInfo } from '../../../type/type';
import thumbnail from '../../../image/thumbnail.svg';
const InformationBoard = () => {
  const [clubInformation, setClubInformation] = useState<clubInfo>();

  const clubID = window.location.pathname.split('/')[2];

  const [isSigned, setisSigned] = useState(false);
  const [signWait, setSignWait] = useState(false);
  const signCheck = async () => {
    const [statusSign, signRes] = await getAPI('/api/users/joined-club');

    const [statusWait, waitRes] = await getAPI('/api/users/not-joined-club');

    const joinedClubID = signRes.map((val: any) => val.id);
    const waitClubID = waitRes.map((val: any) => val.id);
    if (joinedClubID.includes(parseInt(clubID))) setisSigned(true);
    else if (waitClubID.includes(parseInt(clubID))) setSignWait(true);
  };

  const requestSign = async () => {
    const [status, res] = await postAPI(
      {},
      'json',
      `/api/clubs/${clubID}/member`
    );
  };

  const setInfo = async () => {
    const [status, res] = await getAPI(`/api/clubs/${clubID}`);
    setClubInformation({ ...clubInformation, ...res });
  };

  useEffect(() => {
    signCheck();
    setInfo();
  }, []);

  return (
    <>
      <div className="ClubPage-middleFrame">
        <img
          className="ClubPage-thumbnail"
          src={clubInformation?.imageUrl}
        ></img>
        <div className="ClubPage-description">
          {clubInformation?.description}
        </div>
      </div>
      <hr></hr>
      {isSigned ? (
        <div className="ClubPage-sign-done">가입완료</div>
      ) : signWait ? (
        <div className="ClubPage-sign-wait" onClick={requestSign}>
          가입대기 중
        </div>
      ) : (
        <div className="ClubPage-signIn" onClick={requestSign}>
          가입하기
        </div>
      )}
    </>
  );
};

export default InformationBoard;
