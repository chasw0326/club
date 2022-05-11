import React, { useState, useEffect } from 'react';
import { getAPI, deleteAPI } from '../../../hooks/useFetch';

const MemberManagement = () => {
  const [nonMemberList, setNonMemberList]: any = useState([]);
  const [target, setTarget]: any = useState(null);

  const clubID = window.location.pathname.split('/')[2];

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
                  {val?.name + val?.userId}
                </span>
                <span
                  className="ClubSetting__span--accept"
                  data-userid={val?.userId}
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
      </div>
    </>
  );
};

export default MemberManagement;
