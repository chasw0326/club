import React, { KeyboardEventHandler } from 'react';
import '../Style/header.scss';
import person from '../../image/person.svg';

const MainHeader = ({ test }: { test: string }) => {
  const enter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') alert('ㅁㅁ');
  };

  return (
    <>
      <div className="MainHeader">
        <div className="MainHeader-university">대학교명</div>
        <input
          className="MainHeader-searchBar"
          type="search"
          placeholder="동아리를 검색하세요"
          onKeyPress={enter}
        ></input>
        <img
          className="MainHeader-myPage"
          src={person}
          width="50px"
          height="50px"
        ></img>
      </div>
    </>
  );
};

export default MainHeader;
