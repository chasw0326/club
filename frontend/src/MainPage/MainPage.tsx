import React, { useEffect } from 'react';
import { useAsync } from '../hooks/useFetch';
import MainBody from './Component/Body';
import MainHeader from './Component/Header';
import { fetchState } from '../type/type';
const MainPage = () => {
  const abc = async () => {
    const rData = await fetch('http://localhost:3001/api?abc=푸들');

    const data = await rData.json();

    return data;
  };

  const [state, fetchData] = useAsync(abc, []);

  const { data: res } = state as fetchState;

  useEffect(() => {}, []);

  return (
    <>
      <MainHeader test="This is props fgrom MainPage."></MainHeader>
      <MainBody clubItem={res?.clubInfo}></MainBody>
    </>
  );
};

export default MainPage;
