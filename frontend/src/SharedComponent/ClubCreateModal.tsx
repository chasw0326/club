import React, { useContext, useState } from 'react';
import { store } from '../hooks/store';
import cancel from '../image/cancel.svg';
import { postAPI } from '../hooks/useFetch';
import { clubInformation } from '../type/type';
import { isConstructorDeclaration } from 'typescript';

const ClubCreateModal = ({
  modalState,
  setModal,
}: {
  modalState: boolean;
  setModal: any;
}) => {

  const initialState: clubInformation  = {
    club: "",
    address: "",
    description: "",
    category : "",
  };

  const [clubInfo, setClubInfo] = useState(initialState);
  const [globalCategory, setGlobalCategory] = useContext(store);

  const setInput = (event: any) => {
    if (event.target.id === 'profile') {
      setClubInfo({ ...clubInfo, [event.target.id]: event.target.files[0] });
    } else setClubInfo({ ...clubInfo, [event.target.id]: event.target.value });
  };

  const upload = async () => {
    const [status, res] = await postAPI(clubInfo, 'json', '/api/clubs') 

    if(status === 200){
      alert('등록이 완료되었습니다.');
      setModal();
    }

    else{
      alert(res.message.exception);
    }

  };

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
              id="club"
              className="MainHeader__input--box"
              type="text"
              onChange={setInput}
            ></input>
            <div className="MainHeader__text--new-password">동아리 위치</div>
            <input
              id="address"
              className="MainHeader__input--box"
              type="text"
              onChange={setInput}
            ></input>
            <div className="MainHeader__text--introduction">동아리 소개</div>
            <textarea
              id="description"
              className="MainHeader__input--introduction"
              onChange={setInput}
            ></textarea>
            <div className="MainHeader__text--new-password-check">카테고리</div>

            <select
              className="MainHeader__input--box"
              name="pets"
              id="category"
              onChange={setInput}
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
              id="thumbnail"
              className="MainHeader__input--file"
              type="file"
              onChange={setInput}
            ></input>
            <label className="MainHeader__label--file" htmlFor="file">
              이미지 업로드
            </label>
            <div className="MainHeader__div--modify-submit" onClick={upload}>확인</div>
          </div>
        </div>

        <div className="blur"></div>
      </>
    );
  } else return null;
};

export default ClubCreateModal;
