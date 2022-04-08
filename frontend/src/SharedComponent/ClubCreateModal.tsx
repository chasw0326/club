import React, { useContext } from 'react';
import { store } from '../hooks/store';
import cancel from '../image/cancel.svg';

const ClubCreateModal = ({
  modalState,
  setModal,
}: {
  modalState: boolean;
  setModal: any;
}) => {
  const [globalCategory, setGlobalCategory] = useContext(store);

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

            <select
              className="MainHeader__input--box"
              name="pets"
              id="pet-select"
            >
              <option value="">카테고리 선택</option>
              {globalCategory.categories?.map((val: any, idx: number) => {
                return <option value={val.name}>{val.name}</option>;
              })}
            </select>
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
        </div>

        <div className="blur"></div>
      </>
    );
  } else return null;
};

export default ClubCreateModal;
