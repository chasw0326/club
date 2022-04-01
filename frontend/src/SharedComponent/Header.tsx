import React, { EventHandler, KeyboardEventHandler } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './Style/shared.scss';
import person from '../image/person.svg';
import cancel from '../image/cancel.svg';

const ClubCreateModal = ({
  modalState,
  setModal,
}: {
  modalState: boolean;
  setModal: any;
}) => {
  if (modalState) {
    return (
      <>
        <div className="MainHeader__div--club-create-modal">
          <img
            src={cancel}
            className="MyPage__button--cancel"
            onClick={setModal}
          ></img>
          <div className="MainHeader__div--modify">
            <div className="MyPage__text--title">동아리 생성</div>
            <div className="MyPage__text--current-password">동아리명</div>
            <input
              id="name"
              className="MyPage__input--box"
              type="password"
            ></input>
            <div className="MyPage__text--new-password">동아리 위치</div>
            <input
              id="nickname"
              className="MyPage__input--box"
              type="password"
            ></input>
            <div className="MyPage__text--new-password-check">동아리 소개</div>
            <input
              id="university"
              className="MyPage__input--box"
              type="password"
            ></input>
            <div className="MyPage__text--new-password-check">카테고리</div>
            <input
              id="introduction"
              className="MyPage__input--box"
              type="password"
            ></input>
            <div className="MyPage__text--new-password-check">
              동아리 대표 이미지
            </div>
            <input
              id="introduction"
              className="MyPage__input--file"
              type="file"
            ></input>
            <div className="MyPage__div--modify-submit">확인</div>
          </div>
        </div>

        <div className="blur"></div>
      </>
    );
  } else return null;
};

const MainHeader = () => {
  const [inputState, setInputState] = useState('');
  const [modalState, setModalState] = useState(false);
  const navigate = useNavigate();
  const locations = useLocation();

  const setInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(event.target.value);
  };

  const modalOnOff = () => {
    if (modalState) setModalState(false);
    else setModalState(true);
  };

  const enter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      navigate(`/result`);
    }
  };

  return (
    <>
      <div className="MainHeader">
        <div className="MainHeader-university">대학교명(API)</div>
        <input
          className="MainHeader-searchBar"
          type="search"
          placeholder="동아리를 검색하세요"
          onChange={setInput}
          onKeyPress={enter}
        ></input>
        <img
          className="MainHeader-myPage"
          src={person}
          width="50px"
          height="50px"
          onClick={() => {
            navigate('/mypage');
          }}
        ></img>
        <div className="MainHeader__button--club-create" onClick={modalOnOff}>
          동아리 생성
        </div>
        <ClubCreateModal
          modalState={modalState}
          setModal={modalOnOff}
        ></ClubCreateModal>
      </div>
    </>
  );
};

export default MainHeader;
