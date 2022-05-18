import React, { useState, useEffect, useContext, SyntheticEvent } from 'react';
import { clubItem, category } from '../../../type/type';
import { useAsync } from '../../../hooks/useFetch';
import { fetchState } from '../../../type/type';
import ClubItem from '../../../SharedComponent/ClubItem';
import '../Style/body.scss';
import { store } from '../../../hooks/store';
import { getAPI } from '../../../hooks/useFetch';

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
  const [globalState, setGlobalState] = useContext(store);
  const [categoryState, setCategory] = useState<category[]>([]);
  const [curCategory, setCurCategory] = useState(0);
  const [clubList, setClubList] = useState<clubItem[]>([]);

  const categorizing = async (e: SyntheticEvent) => {
    const target = e.target as HTMLDivElement;
    const [status, res] = await getAPI(
      `/api/clubs?category=${categoryList[target.innerHTML]}`
    );
    setClubList(res);
    setCurCategory(categoryList[target.innerHTML]);
  };

  const categorySetting = async () => {
    const [status, res]: any = await getAPI('/api/category');
    setCategory((categoryState) => [...categoryState, ...res]);
  };

  const [target, setTarget]: any = useState(null);

  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/clubs`);
    setClubList(res);
  };

  const updateClubList = async () => {
    let api = '';

    if (curCategory === 0) {
      api = '/api/clubs';
    } else api = `/api/clubs?category=${curCategory}`;

    const [status, res] = await getAPI(api);

    setClubList((clubList) => [...clubList, ...res]);
  };

  const callback = async ([entry]: any, observer: any) => {
    if (entry.isIntersecting && clubList.length > 4) {
      observer.unobserve(entry.target);
      await updateClubList();
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    fetchData();
    categorySetting();
  }, []);

  useEffect(() => {
    setGlobalState({ ...globalState, categories: categoryState });
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
            <div
              key={idx}
              className="MainBody__div--category-box"
              onClick={categorizing}
            >
              {val.name}
            </div>
          );
        })}
      </div>
      <hr></hr>
      <div className="MainBody">
        {clubList?.length === 0 ? (
          <div>검색결과 없음</div>
        ) : (
          <>
            <div className="MainBody-itemFrame">
              {clubList?.map((val: any, idx: number) => {
                return <ClubItem key={idx} item={val}></ClubItem>;
              })}
              <div ref={setTarget} className="Target-Element"></div>
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default MainBody;
