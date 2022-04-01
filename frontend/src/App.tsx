import React from 'react';
import logo from './logo.svg';
import {
  Route,
  BrowserRouter,
  Routes,
  HistoryRouterProps,
} from 'react-router-dom';
import MainPage from './MainPage/MainPage';
import LoginPage from './LoginPage/LoginPage';
import './App.css';
import ResultPage from './ResultPage/ResultPage';
import ClubPage from './ClubPage/ClubPage';
import MyPage from './MyPage/MyPage';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/home*" element={<MainPage />} />
        <Route path="/result" element={<ResultPage />} />
        <Route path="/club*" element={<ClubPage />} />
        <Route path="/mypage*" element={<MyPage />} />
      </Routes>
    </>
  );
}

export default App;
