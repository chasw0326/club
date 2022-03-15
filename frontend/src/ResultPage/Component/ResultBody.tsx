import React from 'react';
import { clubItem } from '../../type/type';
import Category from './Category';
import ClubItem from '../../SharedComponent/ClubItem';
import '../Style/body.scss';

const ResultBody = ({ clubItem }: { clubItem: clubItem[] }) => {
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

export default ResultBody;
