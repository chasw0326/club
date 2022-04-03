import React, { useEffect, useState } from 'react';
import { clubItem } from '../type/type';
import { useNavigate } from 'react-router-dom';
import './Style/shared.scss';
import thumbnail from '../image/thumbnail.svg';

const ClubItem = ({ item }: { item: clubItem }) => {
  const navigate = useNavigate();

  return (
    <div
      className="MainBody-itemFrame-clubItem"
      onClick={() => {
        navigate(`/club/${item?.name}`);
      }}
    >
      <img width="200px" height="200px" src={thumbnail}></img>
      <div className='"MainBody-itemFrame-clubItem-right'>
        <div className="MainBody-itemFrame-clubItem-title">{item?.name}</div>
        <div className="MainBody-itemFrame-clubItem-description">
          {item?.description}
          {item?.category}
        </div>
        <div className="MainBody-itemFrame-clubItem-member">
          인원 {item?.club_members}명
        </div>
        <div className="MainBody-itemFrame-clubItem-address">
          위치 {item?.address}
        </div>
      </div>
    </div>
  );
};

export default ClubItem;
