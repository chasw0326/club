import React, { KeyboardEventHandler } from 'react';

const MainHeader = ({ test }: { test: string }) => {
  const enter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') alert('ㅁㅁ');
  };

  return (
    <>
      <span className="MainHeader-University">대학교명</span>
      <input
        className="MainHeader-SearchBar"
        type="search"
        placeholder="동아리를 검색하세요"
        onKeyPress={enter}
      ></input>
    </>
  );
};

export default MainHeader;
