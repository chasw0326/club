import { sign } from 'crypto';
import { type } from 'os';
import React, { FunctionComponent, MouseEventHandler } from 'react';
import { useState } from 'react';
import { SignUp } from '../../type/type';
import { uploadController } from '../utils/uploadController';
import { UsePostAPI } from '../../hooks/useFetch';

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
    introduce: '',
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

    return true;
  };

  const [signInfo, setSignInfo] = useState(initialState);

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setSignInfo({ ...signInfo, [event.target.id]: event.target.files[0] });
    } else setSignInfo({ ...signInfo, [event.target.id]: event.target.value });
  };

  const upload = async () => {
    if (isValidity(signInfo.email, signInfo.password)) {
      console.log(
        await UsePostAPI(uploadController(signInfo), 'form', '/signUp')
      );
      alert('등록이 완료되었습니다');
      modalOnOffB();
    }
  };

  return (
    <>
      <div className="loginPage-signUpModal">
        <div className="loginPage-signUpModal-logo">회원가입</div>
        아이디
        <input id="email" type="text" onChange={setInput}></input>
        비밀번호
        <input id="password" type="password" onChange={setInput}></input>
        이름<input id="name" type="text" onChange={setInput}></input>
        닉네임<input id="username" type="text" onChange={setInput}></input>
        대학교<input id="university " type="text" onChange={setInput}></input>
        프로필<input id="profile" type="file" onChange={setInput}></input>
        자기소개<textarea id="introduce" onChange={setInput}></textarea>
        <input type="button" value="등록" onClick={upload}></input>
        <input type="button" value="닫기" onClick={modalOnOffA}></input>
      </div>
      <div className="blur"></div>
    </>
  );
};

export default SignUpModal;
