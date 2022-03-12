import React from 'react';
import { clubItem } from '../../type/type';

const ClubItem = ({ item }: { item: clubItem }) => {
  return (
    <div className="MainBody-itemFrame-clubItem">
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
