import React, { useContext, useState, useEffect } from 'react';
import cancel from '../image/cancel.svg';
import { postAPI, getAPI } from '../hooks/useFetch';
import { clubInformation, category } from '../type/type';

const categoryList: any = Object.freeze({
  '문화/예술/공연': 1,
  '봉사/사회활동': 2,
  '학술/교양': 3,
  '창업/취업': 4,
  어학: 5,
  체육: 6,
  친목: 7,
});

const ClubCreateModal = ({
  modalState,
  setModal,
}: {
  modalState: boolean;
  setModal: any;
}) => {
  const initialState: clubInformation = {
    name: '',
    address: '',
    description: '',
    category: '',
  };

  const [clubInfo, setClubInfo] = useState(initialState);
  const [categories, setCategories] = useState<category[]>([]);

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setClubInfo({ ...clubInfo, [event.target.id]: event.target.files[0] });
    } else if (event.target.id === 'category') {
      setClubInfo({
        ...clubInfo,
        [event.target.id]: categoryList[event.target.value],
      });
    } else {
      setClubInfo({ ...clubInfo, [event.target.id]: event.target.value });
    }
  };

  const fetchData = async () => {
    const [categoryStatus, categoryRes] = await getAPI(`/api/category`);

    setCategories(categoryRes);
  };

  const upload = async () => {
    console.log(clubInfo);

    const [status, res] = await postAPI(clubInfo, 'json', '/api/clubs');

    if (status === 200) {
      alert('등록이 완료되었습니다.');
      setModal();
      window.location.reload();
    } else {
      alert(res.message.exception);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (modalState) {
    return (
      <>
        <div className="MainHeader__div--club-create-modal">
          <img
            src={cancel}
            className="MyPage__button--cancel"
            onClick={setModal}
          ></img>
          <div className="MainHeader__div--modify">
            <div className="MainHeader__text--title">동아리 생성</div>
            <div className="MainHeader__text">동아리명</div>
            <input
              id="name"
              className="MainHeader__Input"
              type="text"
              onChange={setInput}
            ></input>
            <div className="MainHeader__text">동아리 위치</div>
            <input
              id="address"
              className="MainHeader__Input"
              type="text"
              onChange={setInput}
            ></input>
            <div className="MainHeader__text">동아리 소개</div>
            <textarea
              id="description"
              className="MainHeader__textarea"
              onChange={setInput}
            ></textarea>
            <div className="MainHeader__text">카테고리</div>
            <select
              className="MainHeader__Input--category"
              id="category"
              onChange={setInput}
            >
              <option value="">카테고리 선택</option>
              {categories?.map((val: any, idx: number) => {
                return <option value={val.name}>{val.name}</option>;
              })}
            </select>
            <div className="MainHeader__text">동아리 대표 이미지</div>
            <input
              id="thumbnail"
              className="MainHeader__input--file"
              type="file"
              onChange={setInput}
            ></input>
            <label className="MainHeader__label--file" htmlFor="file">
              이미지 업로드
            </label>
            <div className="MainHeader__div--modify-submit" onClick={upload}>
              확인
            </div>
          </div>
        </div>

        <div className="blur"></div>
      </>
    );
  } else return null;
};

export default ClubCreateModal;
