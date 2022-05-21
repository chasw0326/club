import React, { useState, useEffect } from 'react';
import { getAPI, putAPI, deleteAPI } from '../../../hooks/useFetch';
import { useNavigate } from 'react-router-dom';

const DeleteClub = () => {
  const clubID = window.location.pathname.split('/')[2];
  const navigate = useNavigate();
  const deleteClub = async (event: any) => {
    const userId = event.target.dataset.userid;

    const [status, res] = await deleteAPI(`/api/clubs/${clubID}`);

    if (status === 200) {
      alert('동아리가 삭제되었습니다.');
      navigate('/home');
    }
  };

  return (
    <>
      <div className="ClubSetting__div--member-manage-wrap">
        <div className="ClubSetting__text--title">
          동아리 삭제<hr className="ClubSetting__hr--horizon"></hr>
        </div>

        <div className="DeleteClub__button--delete-club" onClick={deleteClub}>
          동아리 삭제
        </div>
      </div>
    </>
  );
};

export default DeleteClub;
