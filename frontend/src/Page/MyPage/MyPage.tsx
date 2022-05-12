import React, { useEffect } from 'react';
import { useState, useRef } from 'react';
import './Style/MyPage.scss';
import person from '../../image/person.svg';
import SettingModal from './Component/SettingModal';
import setting from '../../image/setting.svg';
import MainHeader from '../../SharedComponent/Header';
import MyPageNav from './Component/MyPageNav';

const MyPage = () => {
  const [viewState, setViewState] = useState('');
  const [modalState, setModal] = useState(false);

  const changeCategoryState = (e: any) => {
    setViewState(e.target.innerHTML);
  };

  const changeModalState = (e: any) => {
    if (modalState) setModal(false);
    else setModal(true);
  };

  return (
    <>
      <MainHeader></MainHeader>
      <hr></hr>
      <div className="MyPage-profile">
        <img className="MyPage-thumbnail" src={person}></img>
        <div className="MyPage-introFrame">
          <div className="MyPage__text--userName">
            username
            <img
              className="MyPage-setting"
              src={setting}
              onClick={changeModalState}
            ></img>
          </div>
          <div className="MyPage__text--invite">모임초대</div>
          <div className="MyPage-__text--introduction">introduction</div>
        </div>
      </div>
      <hr></hr>
      <div className="MyPage-category">
        <div className="MyPage-category-club" onClick={changeCategoryState}>
          가입한 모임
        </div>
        <div className="MyPage-category-post" onClick={changeCategoryState}>
          작성한 글
        </div>
        <div className="MyPage-category-comment" onClick={changeCategoryState}>
          작성한 댓글
        </div>
      </div>
      <hr></hr>
      <MyPageNav viewState={viewState}></MyPageNav>
      <SettingModal
        modalState={modalState}
        setModal={changeModalState}
      ></SettingModal>
    </>
  );
};

export default MyPage;
