import React, { useEffect, useState, useRef } from 'react';
import { category, clubInformation } from '../../../type/type';
import { getAPI, putAPI } from '../../../hooks/useFetch';

const categoryList: any = Object.freeze({
  '문화/예술/공연': 1,
  '봉사/사회활동': 2,
  '학술/교양': 3,
  '창업/취업': 4,
  어학: 5,
  체육: 6,
  친목: 7,
});

const ClubSetting = () => {
  const initialState: clubInformation = {
    name: '',
    address: '',
    description: '',
    category: '',
  };
  const [categories, setCategories] = useState<category[]>([]);
  const [clubInfo, setClubInfo] = useState(initialState);
  const inputRef = useRef<any>([]);
  const clubID = window.location.pathname.split('/')[2];

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setClubInfo({ ...clubInfo, [event.target.id]: event.target.files[0] });
    } else if (event.target.id === 'category')
      setClubInfo({
        ...clubInfo,
        [event.target.id]: categoryList[event.target.value],
      });
    else setClubInfo({ ...clubInfo, [event.target.id]: event.target.value });
  };

  const modifyData = async () => {
    const clubID = window.location.pathname.split('/')[2];

    console.log(clubInfo);

    const [status, res] = await putAPI(
      clubInfo,
      'json',
      `/api/clubs/${clubID}`
    );

    console.log(res);
  };

  const fetchData = async () => {
    const [status, targetClub] = await getAPI(`/api/clubs/${clubID}`);
    const [categoryStatus, categoryRes] = await getAPI(`/api/category`);

    let data: clubInformation = initialState;

    inputRef?.current?.forEach((val: any, idx: number) => {
      val.value = targetClub[val.id];
      data = { ...data, [val.id]: val.value };
    });

    setCategories((categories) => [...categories, ...categoryRes]);
    setClubInfo(data);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="ClubSetting__div--wrap">
        <div className="ClubSetting__text--title">
          동아리 설정 <hr></hr>
        </div>
        <div className="ClubSetting__text">동아리명</div>
        <input
          id="name"
          className="ClubSetting__Input"
          onChange={setInput}
          type="text"
          ref={(el) => (inputRef.current[0] = el)}
        ></input>
        <div className="ClubSetting__text">동아리 위치</div>
        <input
          id="address"
          className="ClubSetting__Input"
          onChange={setInput}
          type="text"
          ref={(el) => (inputRef.current[1] = el)}
        ></input>
        <div className="ClubSetting__text">동아리 소개</div>
        <textarea
          id="description"
          className="ClubSetting__textarea--introduction"
          onChange={setInput}
          ref={(el) => (inputRef.current[2] = el)}
        ></textarea>
        <div className="ClubSetting__text">카테고리</div>
        <select
          className="ClubSetting__Input--category"
          id="category"
          onChange={setInput}
          ref={(el) => (inputRef.current[3] = el)}
        >
          <option value="">카테고리 선택</option>
          {categories?.map((val: any, idx: number) => {
            return <option value={val.name}>{val.name}</option>;
          })}
        </select>
        <div className="ClubSetting__text">동아리 대표 이미지</div>
        <input
          id="thumbnail"
          className="ClubSetting__input--file"
          type="file"
        ></input>
        <label className="ClubSetting__label--file" htmlFor="thumbnail">
          이미지 업로드
        </label>
        <div
          className="ClubSetting__div--modify-submit-button"
          onClick={modifyData}
        >
          확인
        </div>
      </div>
    </>
  );
};

export default ClubSetting;
