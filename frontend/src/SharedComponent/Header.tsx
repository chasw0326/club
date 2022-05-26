import React, { EventHandler, KeyboardEventHandler, useContext } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import './Style/shared.scss';
import person from '../image/person.svg';
import ClubCreateModal from './ClubCreateModal';
import logoutImg from '../image/logout.svg';

const MainHeader = () => {
  const [inputState, setInputState] = useState('');
  const [modalState, setModalState] = useState(false);
  const navigate = useNavigate();

  const setInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(event.target.value);
  };

  const homeButton = () => {
    if (window.location.pathname.includes('home')) window.location.reload();
    else navigate('/home');
  };

  const logout = () => {
    window.localStorage.removeItem('token');
    navigate('/');
  };

  const modalOnOff = () => {
    if (modalState) setModalState(false);
    else setModalState(true);
  };

  const enter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      navigate(`/result`);
    }
  };

  return (
    <>
      <div className="MainHeader">
        <div className="MainHeader__div--content-wrap">
          <div className="MainHeader-university" onClick={homeButton}>
            CLUB
          </div>
          <input
            className="MainHeader-searchBar"
            type="search"
            placeholder="동아리를 검색하세요"
            onChange={setInput}
            onKeyPress={enter}
          ></input>
          <div className="MainHeader__button--club-create" onClick={modalOnOff}>
            동아리 생성
          </div>
          <img
            className="MainHeader-myPage"
            src={person}
            width="50px"
            height="50px"
            onClick={() => {
              navigate('/mypage');
            }}
          ></img>
          <img
            className="MainHeader-logout"
            width="50px"
            height="50px"
            src={logoutImg}
            onClick={logout}
          ></img>
          <ClubCreateModal
            modalState={modalState}
            setModal={modalOnOff}
          ></ClubCreateModal>
        </div>
      </div>
    </>
  );
};

export default MainHeader;
