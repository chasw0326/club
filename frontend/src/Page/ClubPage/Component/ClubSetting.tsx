import React, { useEffect, useState, useRef } from 'react';
import { clubInformation } from '../../../type/type';
import { useContext } from 'react';
import { store } from '../../../hooks/store';
import { getAPI, putAPI } from '../../../hooks/useFetch';

const ClubSetting = () => {
  const initialState: clubInformation = {
    name: '',
    address: '',
    description: '',
    category: '',
  };
  const [globalCategory, setGlobalCategory] = useContext(store);
  const [clubInfo, setClubInfo] = useState(initialState);
  const inputRef = useRef<any>([]);
  const clubID = window.location.pathname.split('/')[2];

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setClubInfo({ ...clubInfo, [event.target.id]: event.target.files[0] });
    } else setClubInfo({ ...clubInfo, [event.target.id]: event.target.value });
  };

  const modifyData = async () => {
    const clubID = window.location.pathname.split('/')[2];

    console.log(clubInfo);

    const [status, res] = await putAPI(clubInfo, `/api/clubs/${clubID}`);

    console.log(res);
  };

  const fetchData = async () => {
    const [status, targetClub] = await getAPI(`/api/clubs/${clubID}`);

    let data: clubInformation = initialState;

    inputRef?.current?.forEach((val: any, idx: number) => {
      val.value = targetClub[val.id];
      data = { ...data, [val.id]: val.value };
    });

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
        <div className="ClubSetting__text--current-password">동아리명</div>
        <input
          id="name"
          className="ClubSetting__input--box"
          onChange={setInput}
          type="text"
          ref={(el) => (inputRef.current[0] = el)}
        ></input>
        <div className="ClubSetting__text--new-password">동아리 위치</div>
        <input
          id="address"
          className="ClubSetting__input--box"
          onChange={setInput}
          type="text"
          ref={(el) => (inputRef.current[1] = el)}
        ></input>
        <div className="ClubSetting__text--introduction">동아리 소개</div>
        <textarea
          id="description"
          className="ClubSetting__input--introduction"
          onChange={setInput}
          ref={(el) => (inputRef.current[2] = el)}
        ></textarea>
        <div className="ClubSetting__text--new-password-check">카테고리</div>
        <select
          className="MainHeader__input--box"
          id="category"
          onChange={setInput}
          ref={(el) => (inputRef.current[3] = el)}
        >
          <option value="">카테고리 선택</option>
          {globalCategory.categories?.map((val: any, idx: number) => {
            return <option value={val.name}>{val.name}</option>;
          })}
        </select>
        <div className="ClubSetting__text--new-password-check">
          동아리 대표 이미지
        </div>
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
