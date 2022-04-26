import React, { useState, useEffect, useContext } from 'react';
import { clubItem, category } from '../../type/type';
import { useAsync } from '../../hooks/useFetch';
import { fetchState } from '../../type/type';
import ClubItem from '../../SharedComponent/ClubItem';
import '../Style/body.scss';
import { store } from '../../hooks/store';
import { getAPI } from '../../hooks/useFetch';

const categoryList: any = Object.freeze({
  '문화/예술/공연': 1,
  '봉사/사회활동': 2,
  '학술/교양': 3,
  '창업/취업': 4,
  어학: 5,
  체육: 6,
  친목: 7,
});

const MainBody = () => {
  const [globalCategory, setGlobalCategory] = useContext(store);
  const [categoryState  , setCategory] = useState<category[]>([]);
  const [curCategory, setCurCategory] = useState(0);
  const [clubList, setClubList] = useState<clubItem[]>([]);

  const categorizing = (e: any) => {
    setCurCategory(categoryList[e.target.innerHTML]);
    // const newList = clubList.filter(
    //   (val) => val.category === categoryList[e.target.innerHTML]
    // );
    setClubList([]);
  };

  const categorySetting = async ()=>{
    const [status, res] : any = await getAPI('/api/category');

    console.log(res);

    setCategory((categoryState) => [...categoryState, ...res]);

  }

  const abc = async () => {
    const rData = await fetch(
      `http://localhost:3001/api/clubs?category=${curCategory}`
    );

    const data = await rData.json();

    return data;
  };

  const [target, setTarget]: any = useState(null);

  const fakeFetch = async () => {
    const data = await abc();
    setClubList((clubList) => [...clubList, ...data]);
  };

  const callback = async ([entry]: any, observer: any) => {
    if (entry.isIntersecting) {
      observer.unobserve(entry.target);
      await fakeFetch();
      observer.observe(entry.target);
    }
  };

  useEffect(()=>{
    categorySetting();
  },[])

  useEffect(() => {
    setGlobalCategory({ ...globalCategory, categories: categoryState });
  }, [categoryState]);

  useEffect(() => {
    let observer: any;

    if (target) {
      observer = new IntersectionObserver(callback, { threshold: 1.0 });

      observer.observe(target);
    }

    return () => observer && observer.disconnect();
  }, [target, curCategory]);

  return (
    <>
      <hr></hr>
      <div className="MainBody-CategoryFrame">
        {categoryState?.map((val: any, idx: any) => {
          return (
            <div key={idx} className="MainBody__div--category-box" onClick={categorizing}>
              {val.name}
            </div>
          );
        })}
      </div>
      <hr></hr>
      <div className="MainBody">
        <div className="MainBody-itemFrame">
          {clubList?.map((val: any, idx: number) => {
            return <ClubItem key={idx} item={val}></ClubItem>;
          })}
          <div ref={setTarget} className="Target-Element"></div>
        </div>
      </div>
    </>
  );
};

export default MainBody;
