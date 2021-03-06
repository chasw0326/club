import React, { useState, useEffect, SyntheticEvent } from 'react';
import { postAPI, getAPI, deleteAPI, putAPI } from '../../../hooks/useFetch';
import { clubInfo } from '../../../type/type';
import thumbnail from '../../../image/thumbnail.svg';
import { Navigate, useNavigate } from 'react-router-dom';
import setting from '../../../image/setting.svg';
const InformationBoard = () => {
  const [clubInformation, setClubInformation] = useState<clubInfo>();
  const clubID = window.location.pathname.split('/')[2];
  const [isSigned, setisSigned] = useState(false);
  const [signWait, setSignWait] = useState(false);
  const [isMaster, setIsMaster] = useState(false);
  const navigate = useNavigate();
  const signCheck = async () => {
    const [statusSign, signRes] = await getAPI('/api/users/joined-club');
    const [statusWait, waitRes] = await getAPI('/api/users/not-joined-club');
    const [statusMaster, masterRes] = await getAPI(
      `/api/clubs/${clubID}/is-master`
    );

    const joinedClubID = signRes.map((val: any) => val.id);
    const waitClubID = waitRes.map((val: any) => val.id);
    if (joinedClubID.includes(parseInt(clubID))) setisSigned(true);
    else if (waitClubID.includes(parseInt(clubID))) setSignWait(true);

    setIsMaster(masterRes.result);
  };

  const requestSign = async () => {
    const [status, res] = await postAPI(
      {},
      'json',
      `/api/clubs/${clubID}/member`
    );

    if (status === 200) {
      alert('가입 신청이 완료되었습니다.');
      window.location.reload();
    }
  };

  const setInfo = async () => {
    const [status, res] = await getAPI(`/api/clubs/${clubID}`);
    setClubInformation({ ...clubInformation, ...res });
  };

  const quitClub = async () => {
    const [status, res] = await deleteAPI(`/api/clubs/${clubID}/member`);
    if (status === 200) {
      alert('동아리 탈퇴가 완료되었습니다.');
      navigate(`/home`);
    }
  };

  useEffect(() => {
    signCheck();
    setInfo();
  }, []);

  const thumbnailChange = async (e: SyntheticEvent) => {
    const target = e.target as HTMLInputElement;
    const formData = new FormData();
    formData.append('clubImage', target.files![0]);
    const [status, res] = await putAPI(
      formData,
      'form',
      `/api/clubs/image/${clubID}`
    );

    if (status === 200) window.location.reload();
  };

  return (
    <>
      <div className="ClubPage-middleFrame">
        <div>
          <img
            className="ClubPage-thumbnail"
            src={
              clubInformation?.imageUrl ? clubInformation.imageUrl : thumbnail
            }
          ></img>

          {isMaster ? (
            <>
              <input
                type="file"
                id="thumbnail"
                className="Information__file--edit-thumbnail"
                onChange={thumbnailChange}
              ></input>
              <label
                htmlFor="thumbnail"
                className="Information__button--edit-button"
              >
                <img
                  className="Information__img--edit-button"
                  src={setting}
                ></img>
                edit
              </label>
            </>
          ) : null}
        </div>
        <div className="Information__div--rightComponent">
          <div className="Information__div--club-introduction">동아리 소개</div>
          <div className="ClubPage-description">
            {clubInformation?.description}
          </div>
          {isSigned ? (
            <div className="ClubPage-sign-done" onClick={quitClub}>
              탈퇴하기
            </div>
          ) : signWait ? (
            <div className="ClubPage-sign-wait">가입대기 중</div>
          ) : (
            <div className="ClubPage-signIn" onClick={requestSign}>
              가입하기
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default InformationBoard;
