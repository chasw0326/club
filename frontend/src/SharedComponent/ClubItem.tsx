import React from 'react';
import { clubItem } from '../type/type';
import { useNavigate } from 'react-router-dom';
import './Style/shared.scss';

const ClubItem = ({ item }: { item: clubItem }) => {
  const navigate = useNavigate();

  return (
    <div
      className="MainBody-itemFrame-clubItem"
      onClick={() => {
        navigate(`/club/${item?.title}`);
      }}
    >
      <div className="MainBody-itemFrame-clubItem-title">{item?.title}</div>
      <div className="MainBody-itemFrame-clubItem-description">
        {item?.description}
      </div>
      <div className="MainBody-itemFrame-clubItem-description">
        {item?.personnel}
      </div>
      <div className="MainBody-itemFrame-clubItem-description">
        {item?.location}
      </div>
    </div>
  );
};

export default ClubItem;
