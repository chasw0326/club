import React, { useEffect } from 'react';
import { useAsync } from '../../hooks/useFetch';
import ResultBody from './Component/ResultBody';
import MainHeader from '../../SharedComponent/Header';
import { fetchState } from '../../type/type';
import ClubItem from '../../SharedComponent/ClubItem';
const ResultPage = () => {
  const abc = async () => {
    const rData = await fetch('http://localhost:3001/api/search');

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
      <ResultBody clubItem={res?.clubInfo}></ResultBody>
    </>
  );
};

export default ResultPage;
