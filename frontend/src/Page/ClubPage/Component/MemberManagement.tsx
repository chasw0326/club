import React, { useState, useEffect, SyntheticEvent } from 'react';
import { getAPI, deleteAPI, putAPI } from '../../../hooks/useFetch';
import cancel from '../../../image/cancel.svg';

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

    console.log(status);
  };

  const modifyPosition = (e: SyntheticEvent) => {
    const _target = e.target as HTMLSpanElement;

    console.log(_target.dataset.userid);

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
        <div className="ClubSetting__text--title">
          동아리 신청<hr className="ClubSetting__hr--horizon"></hr>
        </div>

        <div className="ClubSetting__div--accept-wrap">
          {nonMemberList?.map((val: any, idx: number) => {
            return (
              <div className="ClubSetting__div--accept-item-wrap">
                <span className="ClubSetting__span--nonmember-name">
                  {'이름 ' +
                    val?.nickname +
                    'id ' +
                    val?.userId +
                    '직위 ' +
                    val?.joinStateCode}
                </span>
                <span
                  className="ClubSetting__span--accept"
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

    console.log(res);

    setModal();
  };

  const delegateMaster = () => {
    putAPI({}, 'json', `/api/clubs/${clubID}/master/${selectedUser.userId}`);
    setModal();
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
          <div className="MemberManagement__button--delegation">일반 멤버</div>
        </div>
        <div className="blur"></div>
      </>
    );
  } else return null;
};
export default MemberManagement;
