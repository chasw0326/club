import React, { useEffect, useContext } from 'react';
import { store } from '../../hooks/store';
import { getAPI } from '../../hooks/useFetch';
import MainBody from './Component/MainBody';
import MainHeader from '../../SharedComponent/Header';

const MainPage = () => {
  const [globalState, setGlobalState] = useContext(store);

  const fetchData = async () => {
    const [status, res] = await getAPI('/api/user');
    setGlobalState({ ...globalState, nickname: res.nickname });
  };

  useEffect(() => {
    fetchData();
  }, []);
  return (
    <>
      <MainHeader></MainHeader>
      {globalState?.nickname}
      <MainBody></MainBody>
    </>
  );
};

export default MainPage;
