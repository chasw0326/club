import React, { MutableRefObject, RefObject, useEffect, useRef } from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SignUpModal from './Component/signUpModal';
import './Style/login.scss';

const LoginPage = () => {
  const [modalState, setModalState] = useState(false);
  const [idState, setIdState] = useState('');
  const [pwState, setPwState] = useState('');
  const navigate = useNavigate();
  const loginRef = useRef<HTMLDivElement>(null);

  const idInputChange = (e: any) => {
    setIdState(e.target.value);
  };

  const pwInputChange = (e: any) => {
    setPwState(e.target.value);
  };

  const login = async () => {
    const testData = { email: idState, password: pwState };

    const data = await fetch(`${process.env.REACT_APP_TEST_API}/login`, {
      method: 'POST',
      credentials: 'include',
      body: JSON.stringify(testData),
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((res) => res.json());

    const realData = await data;

    if (realData.status === 'ok') {
      navigate('/home');
    } else {
      alert('정보가 올바르지 않습니다.');
    }
  };

  const modalOnOff = () => {
    if (modalState) {
      setModalState(false);
    } else {
      setModalState(true);
    }
  };

  return (
    <>
      <div className="loginPage-loginBox" ref={loginRef}>
        <div className="loginPage-loginBox-logo">CLUB</div>
        <input
          id="idField"
          className="loginPage-loginBox-id"
          type="text"
          placeholder="ID"
          onKeyPress={idInputChange}
        ></input>
        <br></br>
        <input
          className="loginPage-loginBox-pw"
          type="password"
          placeholder="PW"
          onKeyPress={pwInputChange}
        ></input>
        <div className="loginPage-loginBox-signLogin">
          <input
            id="signUp"
            className="loginPage-loginBox-signLogin-signUp"
            value="회원가입"
            type="button"
            onClick={modalOnOff}
          ></input>
          <label
            className="loginPage-loginBox-signLogin-signLabel"
            htmlFor="signUp"
          >
            회원가입
          </label>
          <input
            id="login"
            className="loginPage-loginBox-signLogin-login"
            type="submit"
            onClick={login}
            value="로그인"
          ></input>
          <label
            htmlFor="login"
            className="loginPage-loginBox-signLogin-loginLabel"
          >
            로그인
          </label>
        </div>
      </div>
      {modalState ? (
        <SignUpModal
          modalOnOffA={modalOnOff}
          modalOnOffB={modalOnOff}
        ></SignUpModal>
      ) : null}
    </>
  );
};

export default LoginPage;