import React, { useEffect, useContext } from 'react';
import { store } from '../../hooks/store';
import { getAPI } from '../../hooks/useFetch';
import MainBody from './Component/MainBody';
import MainHeader from '../../SharedComponent/Header';

const MainPage = () => {
  // const fetchData = async () => {
  //   const [status, res] = await getAPI('/api/user');
  //   setGlobalState({ ...globalState, nickname: res.nickname });
  // };

  // useEffect(() => {
  //   fetchData();
  // }, []);
  return (
    <>
      <MainHeader></MainHeader>
      <MainBody></MainBody>
    </>
  );
};

export default MainPage;
