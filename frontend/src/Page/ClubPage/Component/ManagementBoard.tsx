import { userInfo } from 'os';
import React, { useEffect, useState, useRef } from 'react';
import { useContext } from 'react';
import { isConstructorDeclaration } from 'typescript';
import { store } from '../../../hooks/store';
import { getAPI, putAPI } from '../../../hooks/useFetch';
import { clubInformation } from '../../../type/type';

const RightComponent = ({ curCategory }: { curCategory: string }) => {
  const [nonMemberList, setNonMemberList]: any = useState([]);
  const [target, setTarget]: any = useState(null);
  const [globalCategory, setGlobalCategory] = useContext(store);
  const initialState: clubInformation = {
    name: '',
    address: '',
    description: '',
    category: '',
  };
  const [clubInfo, setClubInfo] = useState(initialState);
  const inputRef = useRef<any>([]);
  const clubID = window.location.pathname.split('/')[2];

  const fetchData = async () => {
    const [statusNonMember, targetMember] = await getAPI(
      '/api/clubs/testClub/non-member'
    );

    const [status, targetClub] = await getAPI(`/api/clubs/${clubID}`);

    let data: clubInformation = initialState;

    inputRef?.current?.forEach((val: any, idx: number) => {
      val.value = targetClub[val.id];
      data = { ...data, [val.id]: val.value };
    });

    console.log(targetMember);

    setClubInfo(data);
    setNonMemberList((nonMemberList: any) => [
      ...nonMemberList,
      ...targetMember,
    ]);
  };

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

  const accept = async (event: any) => {
    const userId = event.target.dataset.userid;

    const [status, res] = await putAPI(
      {},
      `/api/clubs/${clubID}/member/${userId}`
    );

    console.log(status);
  };

  useEffect(() => {
    fetchData();
  }, [curCategory]);

  if (curCategory === '부원 관리') {
    return (
      <>
        <div className="ClubSetting__div--member-manage-wrap">
          <div className="ClubSetting__text--title">
            동아리 신청<hr className="ClubSetting__hr--horizon"></hr>
          </div>

          <div className="ClubSetting__div--accept-wrap">
            {nonMemberList?.map((val: any, idx: number) => {
              return (
                <div className="ClubSetting__div--accept-item-wrap">
                  <span className="ClubSetting__span--nonmember-name">
                    {val?.name + val?.userId}
                  </span>
                  <span
                    className="ClubSetting__span--accept"
                    data-userid={val?.userId}
                    onClick={accept}
                  >
                    수락
                  </span>
                  <span className="ClubSetting__span--deny">거절</span>
                </div>
              );
            })}
          </div>
        </div>
      </>
    );
  } else {
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
  }
};

const ManangementBoard = () => {
  const [curCategory, setCurCategory] = useState('');

  const setCategory = (e: any) => {
    setCurCategory(e.target.innerHTML);
  };

  return (
    <>
      <div className="ManagementBoard__div--wrap">
        <div className="ManagementBoard__div--category-wrap">
          <div onClick={setCategory}>동아리 설정</div>
          <div onClick={setCategory}>부원 관리</div>
        </div>
        <RightComponent curCategory={curCategory}></RightComponent>
      </div>
    </>
  );
};

export default ManangementBoard;
