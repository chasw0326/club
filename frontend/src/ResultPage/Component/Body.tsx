import React from 'react';
import { clubItem } from '../../type/type';
import Category from './category';
import ClubItem from './clubItem';
import '../Style/body.scss';

const MainBody = ({ clubItem }: { clubItem: clubItem[] }) => {
  return (
    <>
      <div className="MainBody">
        <Category></Category>
        <div className="MainBody-itemFrame">
          {clubItem?.map((val: any, idx: number) => {
            return <ClubItem key={idx} item={val}></ClubItem>;
          })}
        </div>
      </div>
    </>
  );
};

export default MainBody;
