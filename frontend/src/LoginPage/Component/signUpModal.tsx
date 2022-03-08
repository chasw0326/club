import React from 'react';

const SignUpModal = ({ modalState }: { modalState: any }) => {
  return (
    <>
      <div className="loginPage-signUpModal">
        <div className="loginPage-signUpModal-logo">회원가입</div>
        아이디<input id="email" type="text"></input>
        비밀번호<input id="password" type="password"></input>
        이름<input id="name" type="text"></input>
        닉네임<input id="username" type="text"></input>
        대학교<input id="university " type="text"></input>
        프로필<input id="profile" type="file"></input>
        자기소개<textarea></textarea>
        <input type="button" value="등록" onClick={modalState}></input>
        <input type="button" value="닫기" onClick={modalState}></input>
      </div>
      <div className="blur"></div>
    </>
  );
};

export default SignUpModal;
