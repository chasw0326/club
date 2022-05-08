import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import InformationBoard from './InformationBoard';
import '../Style/clubPage.scss';
import ManangementBoard from './ManagementBoard';
import {PostList, Post, WritePage} from './NoticeBoard';

const PhotoBoard = () => {
  return <div>사진 게시판이다.</div>;
};

const Board = ({ state, setCategory }: { state: any; setCategory: any }) => {
  
  const [postID, setPostID] = useState<number>(0);
  const [postInfo, setPostInfo] = useState({
    title : "",
    content : "",
    postId : 0,
  });

  useEffect(()=>{
    setCategory(window.location.pathname.split('/')[1]);
  }, [window.location.pathname])

  if (state === 'information') {
    return (
      <>
        <InformationBoard></InformationBoard>
      </>
    );
  } else if (state === 'board' ) {
    return <PostList setPostInfo={setPostInfo} setCategory={setCategory} ></PostList>
  } 
  else if(state === 'post' ) {
    return <Post postInfo={postInfo}></Post>
  }else if (state === 'photo') {
    return (
      <>
        <PhotoBoard></PhotoBoard>
      </>
    );
  } else if(state ==='management'){
    return <ManangementBoard></ManangementBoard>;
  } else if(state === 'write'){
    return <WritePage></WritePage>
  }
  else return null;
};

export default Board;