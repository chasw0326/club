import React from 'react';
import { useState } from 'react';
import back from '../../image/back.svg';

const ModalContent = () => {
  const [modify, setModify] = useState('');

  const [inputInfo, setInputInfo] = useState({
    name: '',
    nickname: '',
    university: '',
    introduction: ' ',
  });
  const [passwordInfo, setPasswordInfo] = useState({
    oldPw: '',
    newPw: '',
    checkPw: '',
  });

  const setModifyState = (e: any) => {
    setModify(e.target.innerHTML);
  };

  const setPasswordValue = (e: any) => {
    const { id, value } = e.target;
    setPasswordInfo({ ...passwordInfo, [id]: value });
  };

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setInputInfo({ ...inputInfo, [id]: value });
  };

  if (modify === '개인정보 수정') {
    return (
      <>
        <div className="MyPage__div--modify">
          <img
            className="MyPage__button--backward"
            src={back}
            onClick={setModifyState}
          ></img>
          <div className="SettingModal__text--title">개인정보 수정</div>
          <div className="SettingModal__text--current-password">이름</div>
          <input
            id="name"
            className="SettingModal__input--box"
            type="text"
            onChange={setInputValue}
          ></input>
          <div className="SettingModal__text--new-password">닉네임</div>
          <input
            id="nickname"
            className="SettingModal__input--box"
            type="text"
            onChange={setInputValue}
          ></input>
          <div className="SettingModal__text--new-password-check">대학교</div>
          <input
            id="university"
            className="SettingModal__input--box"
            type="text"
            onChange={setInputValue}
          ></input>
          <div className="SettingModal__text--new-password-check">자기소개</div>
          <textarea
            id="introduction"
            className="SettingModal__textarea--introduction"
            onChange={setInputValue}
          ></textarea>
          <div className="SettingModal__div--modify-submit">확인</div>
        </div>
      </>
    );
  } else if (modify === '비밀번호 수정') {
    return (
      <>
        <div className="MyPage__div--modify">
          <img
            className="MyPage__button--backward"
            src={back}
            onClick={setModifyState}
          ></img>
          <div className="MyPage__text--title">비밀번호 수정</div>
          <div className="MyPage__text--current-password">현재 비밀번호</div>
          <input
            id="oldPw"
            className="MyPage__input--box"
            type="password"
            onChange={setPasswordValue}
          ></input>
          <div className="MyPage__text--new-password">새 비밀번호</div>
          <input
            id="newPw"
            className="MyPage__input--box"
            type="password"
            onChange={setPasswordValue}
          ></input>
          <div className="MyPage__text--new-password-check">
            새 비밀번호 확인
          </div>
          <input
            id="checkPw"
            className="MyPage__input--box"
            type="password"
            onChange={setPasswordValue}
          ></input>
          <div className="MyPage__div--modify-submit">확인</div>
        </div>
      </>
    );
  } else {
    return (
      <>
        <div className="MyPage__button--password" onClick={setModifyState}>
          비밀번호 수정
        </div>
        <div className="MyPage__button--modify" onClick={setModifyState}>
          개인정보 수정
        </div>
      </>
    );
  }
};

export default ModalContent;
