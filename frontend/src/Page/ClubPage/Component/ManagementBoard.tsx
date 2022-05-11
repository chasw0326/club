import React, { useEffect, useState } from 'react';
import { getAPI, putAPI } from '../../../hooks/useFetch';
import ClubSetting from './ClubSetting';
import AcceptMember from './AcceptMember';
import MemberManagement from './MemberManagement';

const RightComponent = ({ curCategory }: { curCategory: string }) => {
  if (curCategory === '가입 승인') {
    return <AcceptMember></AcceptMember>;
  } else if (curCategory === '부원 관리') {
    return <MemberManagement></MemberManagement>;
  } else return <ClubSetting></ClubSetting>;
};

const ManangementBoard = () => {
  const [curCategory, setCurCategory] = useState('');

  const setCategory = (e: any) => {
    setCurCategory(e.target.innerHTML);
  };

  return (
    <>
      <div className="ManagementBoard__div--wrap">
        <div className="ManagementBoard__div--category-wrap">
          <div onClick={setCategory}>동아리 설정</div>
          <div onClick={setCategory}>가입 승인</div>
          <div onClick={setCategory}>부원 관리</div>
        </div>
        <RightComponent curCategory={curCategory}></RightComponent>
      </div>
    </>
  );
};

export default ManangementBoard;
