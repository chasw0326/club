import React, { SyntheticEvent, useEffect } from 'react';
import { useState, useRef } from 'react';
import { getAPI } from '../../hooks/useFetch';
import './Style/MyPage.scss';
import { userInfo } from '../../type/type';
import person from '../../image/person.svg';
import SettingModal from './Component/SettingModal';
import setting from '../../image/setting.svg';
import MainHeader from '../../SharedComponent/Header';
import MyPageNav from './Component/MyPageNav';

const MyPage = () => {
  const [viewState, setViewState] = useState('');
  const [modalState, setModal] = useState(false);

  const [userInfo, setUserInfo] = useState<userInfo>();

  const changeCategoryState = (e: any) => {
    setViewState(e.target.innerHTML);
  };

  const changeModalState = (e: any) => {
    if (modalState) setModal(false);
    else setModal(true);
  };

  const fetchData = async () => {
    const [status, res] = await getAPI('/api/user');
    setUserInfo({ ...userInfo, ...res });
  };

  const thumbnailChange = (e: SyntheticEvent) => {
    const target = e.target as HTMLInputElement;
    const formData = new FormData();
    formData.append('clubImage', target.files![0]);
    console.log(target.files![0].type);
    // putAPI(formData, 'form', `/api/clubs/image/${clubID}`);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <MainHeader></MainHeader>
      <hr></hr>
      <div className="MyPage-profile">
        <div>
          <img className="MyPage-thumbnail" src={person}></img>
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
              onChange={thumbnailChange}
            ></img>
            edit
          </label>
        </div>

        <div className="MyPage-introFrame">
          <div className="MyPage__text--userName">
            {userInfo?.nickname}
            <img
              className="MyPage-setting"
              src={setting}
              onClick={changeModalState}
            ></img>
          </div>
          <div className="MyPage__text--invite">모임초대</div>
          <div className="MyPage-__text--introduction">
            {userInfo?.introduction}
          </div>
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
