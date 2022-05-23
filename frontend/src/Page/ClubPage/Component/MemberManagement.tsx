import React, { useState, useEffect, SyntheticEvent } from 'react';
import { getAPI, deleteAPI, putAPI } from '../../../hooks/useFetch';
import cancel from '../../../image/cancel.svg';

const position: any = Object.freeze({
  1: '마스터',
  2: '운영진',
  3: '일반멤버',
});

const MemberManagement = () => {
  const [nonMemberList, setNonMemberList]: any = useState([]);
  const [target, setTarget] = useState({ userId: '', nickname: '' });

  const clubID = window.location.pathname.split('/')[2];

  const [modalState, setModalState] = useState(false);

  const modalOnOff = () => {
    if (modalState) setModalState(false);
    else setModalState(true);
  };

  const fetchData = async () => {
    const [statusNonMember, targetMember] = await getAPI(
      `/api/clubs/${clubID}/member`
    );

    setNonMemberList((nonMemberList: any) => [
      ...nonMemberList,
      ...targetMember,
    ]);
  };

  const kick = async (event: any) => {
    const userId = event.target.dataset.userid;

    const [status, res] = await deleteAPI(
      `/api/clubs/${clubID}/member/${userId}`
    );
  };

  const modifyPosition = (e: SyntheticEvent) => {
    const _target = e.target as HTMLSpanElement;

    setTarget({
      ...target,
      userId: _target.dataset.userid!,
      nickname: _target.dataset.nickname!,
    });

    modalOnOff();
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="ClubSetting__div--member-manage-wrap">
        <div className="ClubSetting__text--title">부원 관리</div>
        <hr></hr>
        <div className="ClubSetting__div--accept-wrap">
          {nonMemberList?.map((val: any, idx: number) => {
            return (
              <div className="ClubSetting__div--accept-item-wrap">
                <span className="MemberManagement__span--delegate-wrap">
                  <span className="MemberManagement__span--position">
                    {position[val?.joinStateCode.toString()]}
                  </span>
                  <span className="MemberManagement__span--nickname">
                    {val?.nickname}
                  </span>
                </span>
                <span
                  className="MemberManagement__span--modify-position"
                  data-nickname={val?.nickname}
                  data-userid={val?.userId}
                  onClick={modifyPosition}
                >
                  직위변경
                </span>
                <span
                  className="ClubSetting__span--deny"
                  data-userid={val?.userId}
                  onClick={kick}
                >
                  강퇴
                </span>
              </div>
            );
          })}
        </div>
        <ChangePositionModal
          modalState={modalState}
          setModal={modalOnOff}
          selectedUser={target}
        ></ChangePositionModal>
      </div>
    </>
  );
};

const ChangePositionModal = ({
  modalState,
  setModal,
  selectedUser,
}: {
  modalState: boolean;
  setModal: any;
  selectedUser: any;
}) => {
  const clubID = window.location.pathname.split('/')[2];

  const delegateManager = async () => {
    console.log(selectedUser);

    const [status, res] = await putAPI(
      {},
      'json',
      `/api/clubs/${clubID}/manager/${selectedUser.userId}`
    );

    if (status === 200) {
      alert(`${selectedUser.nickname}님의 직책이 변경되었습니다.`);
      window.location.reload();
    }
  };

  const delegateMaster = async () => {
    const [status, res] = await putAPI(
      {},
      'json',
      `/api/clubs/${clubID}/master/${selectedUser.userId}`
    );

    if (status === 200) {
      alert(`${selectedUser.nickname}님의 직책이 변경되었습니다.`);
      window.location.reload();
    }
  };

  const delegateMember = async () => {
    const [status, res] = await putAPI(
      {},
      'json',
      `/api/clubs/${clubID}/member/${selectedUser.userId}`
    );

    if (status === 200) {
      alert(`${selectedUser.nickname}님의 직책이 변경되었습니다.`);
      window.location.reload();
    }
  };

  if (modalState) {
    return (
      <>
        <div className="MemberManagement__div--position-modify-modal">
          <img
            src={cancel}
            className="MyPage__button--cancel"
            onClick={setModal}
          ></img>
          <div className="MemberManagement__text--notice">
            {selectedUser.nickname}님의 직책을 변경합니다.
          </div>
          <div
            className="MemberManagement__button--delegation"
            onClick={delegateManager}
          >
            운영진 위임
          </div>
          <div
            className="MemberManagement__button--delegation"
            onClick={delegateMaster}
          >
            마스터 위임
          </div>
          <div
            className="MemberManagement__button--delegation"
            onClick={delegateMember}
          >
            일반 멤버
          </div>
        </div>
        <div className="blur"></div>
      </>
    );
  } else return null;
};
export default MemberManagement;
