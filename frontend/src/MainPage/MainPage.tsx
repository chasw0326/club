import React, { useEffect } from 'react';
import { useAsync } from '../hooks/useFetch';
import MainBody from './Component/MainBody';
import MainHeader from '../SharedComponent/Header';
import { fetchState } from '../type/type';
import Category from './Component/Category';

const MainPage = () => {
  useEffect(() => {}, []);

  return (
    <>
      <MainHeader></MainHeader>
      <MainBody></MainBody>
    </>
  );
};

export default MainPage;
