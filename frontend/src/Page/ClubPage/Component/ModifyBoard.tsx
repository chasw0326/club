import React, { useState, useEffect, useRef, SyntheticEvent, Ref } from 'react';
import { postAPI, putAPI } from '../../../hooks/useFetch';
import { postInfo } from '../../../type/type';
const ModifyBoard = ({ postInfo }: { postInfo: postInfo | any }) => {
  const [inputInfo, setInputInfo] = useState({
    title: '',
    content: '',
  });

  const inputRef = useRef<any>([]);

  const setInputValue = (e: any) => {
    const { id, value } = e.target;
    setInputInfo({ ...inputInfo, [id]: value });
  };

  const clubID = window.location.pathname.split('/')[2];

  const modifyPost = async () => {
    const [status, res] = await putAPI(
      inputInfo,
      'json',
      `/api/post/${postInfo.postId}?clubId=${clubID}`
    );

    if (status === 403) {
      alert(res.message);
    }

    window.location.reload();
  };

  useEffect(() => {
    if (postInfo)
      setInputInfo({
        ...inputInfo,
        title: postInfo?.title,
        content: postInfo?.content,
      });

    inputRef?.current?.forEach((val: any, idx: number) => {
      val.value = postInfo[val.id];
    });
  }, []);

  return (
    <>
      <div className="ClubPage__div--write-wrap">
        <div>
          <input
            id="title"
            className="ClubPage__input--write-title"
            type="text"
            onChange={setInputValue}
            ref={(el) => (inputRef.current[0] = el)}
          ></input>
        </div>
        <textarea
          id="content"
          className="ClubPage__textarea--write-area"
          onChange={setInputValue}
          ref={(el) => (inputRef.current[1] = el)}
        ></textarea>
        <div className="ClubPage__button--submit-post" onClick={modifyPost}>
          수정 완료
        </div>
      </div>
    </>
  );
};

export default ModifyBoard;
