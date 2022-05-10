import React, {
  MouseEventHandler,
  RefAttributes,
  useEffect,
  useContext,
} from 'react';
import MainHeader from '../../SharedComponent/Header';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { store } from '../../hooks/store';
import { getAPI } from '../../hooks/useFetch';
import Board from './Component/Board';

const BOARDSTATE: any = Object.freeze({
  정보: 'information',
  게시판: 'board',
  사진: 'photo',
  관리: 'management',
});

const ClubPage = () => {
  const [category, setCategory] = useState('정보');
  const clubID = window.location.pathname.split('/')[2];
  const navigate = useNavigate();
  const [globalState, setGlobalState] = useContext(store);
  const selectCategory = (e: React.SyntheticEvent) => {
    const target = e.target as HTMLDivElement;
    setCategory(target.id);
    navigate(`/${target.id}/${clubID}`);
  };

  const fetchData = async () => {
    const [statusUser, resUser] = await getAPI('/api/user');

    setGlobalState({ ...globalState, nickname: resUser.nickname });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="ClubPage">
        <MainHeader></MainHeader>
        <hr></hr>
        <div className="ClubPage-navigateBar">
          <div
            className="ClubPage-navigateBar-information"
            onClick={selectCategory}
            id="information"
          >
            정보
          </div>
          <div
            id="board"
            className="ClubPage-navigateBar-board"
            onClick={selectCategory}
          >
            게시판
          </div>
          <div
            id="photo"
            className="ClubPage-navigateBar-photo"
            onClick={selectCategory}
          >
            사진
          </div>
          <div
            id="management"
            className="ClubPage-navigateBar-chat"
            onClick={selectCategory}
          >
            관리
          </div>
        </div>
        <hr className="horizon"></hr>
        <Board state={category} setCategory={setCategory}></Board>
        <hr></hr>
      </div>
    </>
  );
};

export default ClubPage;
