import React, { useEffect, useState } from 'react';

const RightComponent = ({ curCategory }: { curCategory: string }) => {
  const [nonMemberList, setNonMemberList]: any = useState([]);
  const [target, setTarget]: any = useState(null);
  const fetchData = async () => {
    const fData = await fetch(
      `${process.env.REACT_APP_TEST_API}/api/clubs/testClub/non-member`
    );

    const res = await fData.json();

    setNonMemberList(res);
  };

  useEffect(() => {
    if (target) {
      fetchData();
    }
  }, [target]);

  if (curCategory === '부원 관리') {
    return (
      <>
        <div className="ClubSetting__div--member-manage-wrap">
          <div className="ClubSetting__text--title">
            동아리 신청<hr className="ClubSetting__hr--horizon"></hr>
          </div>

          <div
            className="ClubSetting__div--accept-wrap"
            ref={setTarget}
            onClick={fetchData}
          >
            {nonMemberList?.map((val: any, idx: number) => {
              return (
                <div className="ClubSetting__div--accept-item-wrap">
                  <span className="ClubSetting__span--nonmember-name"></span>
                  {val?.name}
                  <span className="ClubSetting__span--accept">수락</span>
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
          <div className="MainHeader__text--current-password">동아리명</div>
          <input
            id="name"
            className="MainHeader__input--box"
            type="text"
          ></input>
          <div className="MainHeader__text--new-password">동아리 위치</div>
          <input
            id="nickname"
            className="MainHeader__input--box"
            type="text"
          ></input>
          <div className="MainHeader__text--introduction">동아리 소개</div>
          <textarea
            id="university"
            className="MainHeader__input--introduction"
          ></textarea>
          <div className="MainHeader__text--new-password-check">카테고리</div>
          <input
            id="introduction"
            className="MainHeader__input--box"
            type="text"
          ></input>
          <div className="MainHeader__text--new-password-check">
            동아리 대표 이미지
          </div>
          <input
            id="profile"
            className="MainHeader__input--file"
            type="file"
          ></input>
          <label className="MainHeader__label--file" htmlFor="profile">
            이미지 업로드
          </label>
          <div className="MainHeader__div--modify-submit">확인</div>
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
