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
  const [isMaster, setIsMaster] = useState('false');
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
    const clubID = window.location.pathname.split('/')[2];
    const [status, res] = await getAPI(`/api/clubs/${clubID}/is-master`);
    setIsMaster(res.result);
    setGlobalState({ ...globalState, nickname: resUser.nickname });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="ClubPage">
        <MainHeader></MainHeader>
        <div className="ClubPage-navigateBar">
          <div
            className={
              category === 'information'
                ? 'ClubPage-navigateBar-information-active'
                : 'ClubPage-navigateBar-information'
            }
            onClick={selectCategory}
            id="information"
          >
            정보
          </div>
          <div
            id="board"
            className={
              category === 'board'
                ? 'ClubPage-navigateBar-information-active'
                : 'ClubPage-navigateBar-information'
            }
            onClick={selectCategory}
          >
            게시판
          </div>
          <div
            id="photo"
            className={
              category === 'photo'
                ? 'ClubPage-navigateBar-information-active'
                : 'ClubPage-navigateBar-information'
            }
            onClick={selectCategory}
          >
            사진
          </div>
          {isMaster ? (
            <>
              <div
                id="management"
                className={
                  category === 'management'
                    ? 'ClubPage-navigateBar-information-active'
                    : 'ClubPage-navigateBar-information'
                }
                onClick={selectCategory}
              >
                관리
              </div>
            </>
          ) : null}
        </div>
        <hr className="MainBody-horizon"></hr>
        <Board state={category} setCategory={setCategory}></Board>
      </div>
    </>
  );
};

export default ClubPage;
