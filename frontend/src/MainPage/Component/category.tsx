import React from 'react';

const Category = ({ categoryInfo }: { categoryInfo: any }) => {
  return (
    <>
      <hr></hr>
      <div className="MainBody-CategoryFrame">
        {categoryInfo?.map((val: any, idx: any) => {
          return <div className="MainBody__div--category-box">{val.name}</div>;
        })}
      </div>
      <hr></hr>
    </>
  );
};

export default Category;
