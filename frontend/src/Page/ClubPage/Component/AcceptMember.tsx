import React, { useState, useEffect } from 'react';
import { getAPI, putAPI } from '../../../hooks/useFetch';

const AcceptMember = () => {
  const [nonMemberList, setNonMemberList]: any = useState([]);
  const [target, setTarget]: any = useState(null);

  const clubID = window.location.pathname.split('/')[2];

  const fetchData = async () => {
    const [statusNonMember, targetMember] = await getAPI(
      `/api/clubs/${clubID}/non-member`
    );

    setNonMemberList((nonMemberList: any) => [
      ...nonMemberList,
      ...targetMember,
    ]);
  };

  const accept = async (event: any) => {
    const userId = event.target.dataset.userid;

    const [status, res] = await putAPI(
      {},
      'json',
      `/api/clubs/${clubID}/member/${userId}`
    );

    if (status === 200) {
      alert('동아리 가입을 승인했습니다.');
    } else {
      alert(res.message);
    }

    const [statusMember, memberRes] = await getAPI(
      `/api/clubs/${clubID}/non-member`
    );

    setNonMemberList(memberRes);
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
};

export default AcceptMember;
