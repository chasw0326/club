import React, { useEffect, useState } from 'react';
import { getAPI, putAPI } from '../../../hooks/useFetch';
import ClubSetting from './ClubSetting';
import AcceptMember from './AcceptMember';
import MemberManagement from './MemberManagement';
import DeleteClub from './deleteClub';

const RightComponent = ({ curCategory }: { curCategory: string }) => {
  if (curCategory === '가입 승인') {
    return <AcceptMember></AcceptMember>;
  } else if (curCategory === '부원 관리') {
    return <MemberManagement></MemberManagement>;
  } else if (curCategory === '동아리 삭제') {
    return <DeleteClub></DeleteClub>;
  } else return <ClubSetting></ClubSetting>;
};

const ManangementBoard = () => {
  const [curCategory, setCurCategory] = useState('동아리 설정');
  const clubID = window.location.pathname.split('/')[2];
  const setCategory = (e: any) => {
    setCurCategory(e.target.innerHTML);
  };

  return (
    <>
      <div className="ManagementBoard">
        <div className="ManagementBoard__div--wrap">
          <div className="ManagementBoard__div--category-wrap">
            <div
              className={
                curCategory === '동아리 설정'
                  ? 'ManagementBoard__div--setting-category-selected'
                  : 'ManagementBoard__div--setting-category'
              }
              onClick={setCategory}
            >
              동아리 설정
            </div>
            <div
              className={
                curCategory === '가입 승인'
                  ? 'ManagementBoard__div--setting-category-selected'
                  : 'ManagementBoard__div--setting-category'
              }
              onClick={setCategory}
            >
              가입 승인
            </div>
            <div
              className={
                curCategory === '부원 관리'
                  ? 'ManagementBoard__div--setting-category-selected'
                  : 'ManagementBoard__div--setting-category'
              }
              onClick={setCategory}
            >
              부원 관리
            </div>
            <div
              className={
                curCategory === '동아리 삭제'
                  ? 'ManagementBoard__div--setting-category-selected'
                  : 'ManagementBoard__div--setting-category'
              }
              onClick={setCategory}
            >
              동아리 삭제
            </div>
          </div>
          <div className="vertical-line"></div>
          <RightComponent curCategory={curCategory}></RightComponent>
        </div>
      </div>
    </>
  );
};

export default ManangementBoard;
