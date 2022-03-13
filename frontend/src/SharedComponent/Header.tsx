import React, { EventHandler, KeyboardEventHandler } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './Style/header.scss';
import person from '../image/person.svg';

const MainHeader = ({ fetchData }: { fetchData: any }) => {
  const [inputState, setInputState] = useState('');
  const navigate = useNavigate();
  const locations = useLocation();

  const setInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(event.target.value);
  };

  const enter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      navigate(`/result`);
    }
  };

  return (
    <>
      <div className="MainHeader">
        <div className="MainHeader-university">대학교명</div>
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
        ></img>
      </div>
    </>
  );
};

export default MainHeader;
