import React, { FunctionComponent, MouseEventHandler } from 'react';
import { useState } from 'react';
import { SignUp } from '../../../type/type';
import cancel from '../../../image/cancel.svg';
import { postAPI } from '../../../hooks/useFetch';

const SignUpModal = ({
  modalOnOffA,
  modalOnOffB,
}: {
  modalOnOffA: MouseEventHandler;
  modalOnOffB: Function;
}) => {
  const initialState: SignUp = {
    email: '',
    password: '',
    name: '',
    nickname: '',
    university: '',
    profile: null,
    introduction: '',
  };

  const isValidity = (id: string, pw: string) => {
    const specialID = id.match(/[~!\#$%<>^&*\()\-=+_\’ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi);
    const special = pw.match(/[~!@\#$%<>^&*\()\-=+_\’]/gi);
    const alpha = pw.match(/[a-zA-Z]/gi);
    const digit = pw.match(/[0-9]/gi);

    if (specialID) {
      alert('사용할 수 없는 ID 입니다. 다시 입력해주세요');
      return false;
    }

    if (!(special && alpha && digit) || !(pw.length >= 8 && pw.length <= 20)) {
      alert('사용할 수 없는 비밀번호 입니다. 다시 입력해주세요');
      return false;
    }

    if (passwordCheck !== signInfo.password) {
      alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요');
      return false;
    }

    return true;
  };

  const [signInfo, setSignInfo] = useState(initialState);
  const [passwordCheck, setPasswordCheck] = useState('');

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setSignInfo({ ...signInfo, [event.target.id]: event.target.files[0] });
    } else setSignInfo({ ...signInfo, [event.target.id]: event.target.value });
  };

  const setPwCheck = (event: any) => {
    setPasswordCheck((passwordCheck) => event.target.value);
  };

  const upload = async () => {
    if (isValidity(signInfo.email, signInfo.password)) {
      const [status, res] = await postAPI(signInfo, 'json', '/auth/signup');

      if (status === 200) {
        alert('등록이 완료되었습니다');
        modalOnOffB();
      } else {
        alert(res.message);
      }
    }
  };

  return (
    <>
      <div className="loginPage-signUpModal">
        <div className="SignUpModal__text--logo">CLUB</div>
        <img
          src={cancel}
          className="MyPage__button--cancel"
          onClick={modalOnOffA}
        ></img>
        <div className="SignUpModal__text"> 아이디</div>
        <input
          id="email"
          type="text"
          className="SignUpModal__Input"
          onChange={setInput}
        ></input>
        <div className="SignUpModal__text">비밀번호</div>
        <input
          id="password"
          type="password"
          className="SignUpModal__Input"
          onChange={setInput}
        ></input>
        <div className="SignUpModal__text">비밀번호 확인</div>
        <input
          id="password-check"
          type="password"
          className="SignUpModal__Input"
          onChange={setPwCheck}
        ></input>
        <div className="SignUpModal__text">이름</div>
        <input
          id="name"
          type="text"
          className="SignUpModal__Input"
          onChange={setInput}
        ></input>
        <div className="SignUpModal__text">닉네임</div>
        <input
          id="nickname"
          className="SignUpModal__Input"
          type="text"
          onChange={setInput}
        ></input>
        <div className="SignUpModal__text">대학교</div>
        <input
          id="university"
          type="text"
          className="SignUpModal__Input"
          onChange={setInput}
        ></input>
        <div className="SignUpModal__text">자기소개</div>
        <textarea
          id="introduction"
          className="SignUpModal__textarea--introduction"
          onChange={setInput}
        ></textarea>
        <div className="SignUpModal__text">프로필</div>
        <input
          id="profile"
          type="file"
          className="SignUpModal__Input--profile"
          onChange={setInput}
        ></input>
        <label htmlFor="profile" className="SignUpModal__label--profile">
          이미지 업로드
        </label>
        <div className="SignUpModal__button--submit" onClick={upload}>
          가입하기
        </div>
      </div>
      <div className="blur"></div>
    </>
  );
};

export default SignUpModal;
