import React from 'react';

const MyPageNav = ({ viewState }: { viewState: string }) => {
  if (viewState === '가입한 모임') {
    return (
      <>
        <div>클럽</div>
      </>
    );
  } else if (viewState === '작성한 글') {
    return (
      <>
        <div>작성글</div>
      </>
    );
  } else if (viewState === '작성한 댓글') {
    return (
      <>
        <div>작성댓글</div>
      </>
    );
  }

  return <div>none</div>;
};

export default MyPageNav;
