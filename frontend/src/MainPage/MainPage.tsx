import React, { useEffect } from 'react';
import { useAsync } from '../hooks/useFetch';
import MainBody from './Component/MainBody';
import MainHeader from '../SharedComponent/Header';
import { fetchState } from '../type/type';
import { useLocation } from 'react-router-dom';
const MainPage = () => {
  const abc = async () => {
    const rData = await fetch('http://localhost:3001/api');

    const data = await rData.json();

    return data;
  };

  const [state, fetchData] = useAsync(abc, []);

  const { data: res } = state as fetchState;

  useEffect(() => {}, []);

  console.log('render');

  return (
    <>
      <MainHeader></MainHeader>
      <MainBody clubItem={res?.clubInfo}></MainBody>
    </>
  );
};

export default MainPage;
