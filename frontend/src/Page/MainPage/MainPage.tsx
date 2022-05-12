import React, { useEffect } from 'react';
import MainBody from './Component/MainBody';
import MainHeader from '../../SharedComponent/Header';

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
