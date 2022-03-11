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

function App() {
  return (
    <>
      <Routes>
        <Route path="/*" element={<LoginPage />} />
        <Route path="/home*" element={<MainPage />} />
      </Routes>
    </>
  );
}

export default App;
