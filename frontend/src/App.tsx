import React from 'react';
import logo from './logo.svg';
import {
  Route,
  BrowserRouter,
  Routes,
  HistoryRouterProps,
} from 'react-router-dom';
import MainPage from './Page/MainPage/MainPage';
import LoginPage from './Page/LoginPage/LoginPage';
import './App.css';
import ResultPage from './Page/ResultPage/ResultPage';
import ClubPage from './Page/ClubPage/ClubPage';
import MyPage from './Page/MyPage/MyPage';
import { Post } from './Page/ClubPage/Component/NoticeBoard';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/home*" element={<MainPage />} />
        <Route path="/result" element={<ResultPage />} />
        <Route path="/information*" element={<ClubPage />} />
        <Route path="/board*" element={<ClubPage />} />
        <Route path="/post*" element={<ClubPage />} />
        <Route path="/photo*" element={<ClubPage />} />
        <Route path="/management*" element={<ClubPage />} />
        <Route path="/mypage*" element={<MyPage />} />
      </Routes>
    </>
  );
}

export default App;
