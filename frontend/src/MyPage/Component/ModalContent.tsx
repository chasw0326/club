import React from 'react';
import { useState } from 'react';

const ModalContent = () => {
  const [modify, setModify] = useState('');
  const setModifyState = (e: any) => {
    setModify(e.target.innerHTML);
  };

  if (modify === '개인정보 수정') {
    return (
      <>
        <div>개인정보 수정 input</div>
      </>
    );
  } else if (modify === '비밀번호 수정') {
    return (
      <>
        <div>비밀번호 수정 input</div>
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
