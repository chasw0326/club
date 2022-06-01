import React, { MutableRefObject, RefObject, useEffect, useRef } from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import SignUpModal from './Component/SignUpModal';
import { postAPI } from '../../hooks/useFetch';
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

  const enterLogin = (e: any) => {
    if (e.key === 'Enter') login();
  };

  const login = async () => {
    const loginInformation = { email: idState, password: pwState };

    const [responseStatus, res] = await postAPI(
      loginInformation,
      'login',
      '/auth/signin'
    );

    if (responseStatus === 200) {
      const localStorage = window.localStorage;
      localStorage.setItem('token', res);
      navigate('/home');
    } else {
      alert(res.message);
    }
  };

  const modalOnOff = () => {
    if (modalState) {
      setModalState(false);
    } else {
      setModalState(true);
    }
  };

  useEffect(() => {
    if (window.localStorage.getItem('token')) navigate('/home');
  }, []);

  return (
    <>
      <div className="loginPage-loginBox" ref={loginRef}>
        <div className="loginPage-loginBox-logo">CLUB</div>
        <input
          id="idField"
          className="loginPage-loginBox-id"
          type="text"
          placeholder="ID"
          onChange={idInputChange}
          onKeyPress={enterLogin}
        ></input>
        <br></br>
        <input
          className="loginPage-loginBox-pw"
          type="password"
          placeholder="PW"
          onChange={pwInputChange}
          onKeyPress={enterLogin}
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
