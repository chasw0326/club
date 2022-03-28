import React from 'react';

const MyPageNav = ({ viewState }: { viewState: string }) => {
  if (viewState === '가입한 모임') {
    return (
      <>
        <div>나랑께 클럽이랑께</div>
      </>
    );
  } else if (viewState === '작성한 글') {
    return (
      <>
        <div>나랑께 포스트랑께</div>
      </>
    );
  } else if (viewState === '작성한 댓글') {
    return (
      <>
        <div>나랑께 댓글이랑께</div>
      </>
    );
  }

  return <div>none</div>;
};

export default MyPageNav;
