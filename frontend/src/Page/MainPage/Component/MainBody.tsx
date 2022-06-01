import React, { useState, useEffect, useContext, SyntheticEvent } from 'react';
import {
  clubItem,
  category,
  myClub,
  clubInfo,
  postInfo,
} from '../../../type/type';
import { Navigate, useNavigate } from 'react-router-dom';
import ClubItem from '../../../SharedComponent/ClubItem';
import '../Style/body.scss';
import sad from '../../../image/sad.svg';
import { store } from '../../../hooks/store';
import { getAPI } from '../../../hooks/useFetch';
import thumbnail from '../../../image/thumbnail.svg';

const categoryList: any = Object.freeze({
  전체: 0,
  '문화/예술/공연': 1,
  '봉사/사회활동': 2,
  '학술/교양': 3,
  '창업/취업': 4,
  어학: 5,
  체육: 6,
  친목: 7,
});

const MainBody = () => {
  const [page, setPage] = useState(0);
  const [pageEnd, setPageEnd] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [categoryState, setCategory] = useState<category[]>([]);
  const [curCategory, setCurCategory] = useState(0);
  const [clubList, setClubList] = useState<clubItem[]>([]);
  const categorizing = async () => {
    setPage(0);
    setPageEnd(false);
    let api = ``;
    if (curCategory === 0) api = `/api/clubs`;
    else api = `/api/clubs?category=${curCategory}`;
    const [status, res] = await getAPI(api);
    setClubList([]);
    setClubList(res);
  };

  const categorySetting = async () => {
    const [status, res]: any = await getAPI('/api/category');
    const temp: category = { id: 0, name: '전체', description: '전체' };
    res.splice(0, 0, temp);
    setCategory((categoryState) => [...categoryState, ...res]);
  };

  const [target, setTarget]: any = useState(null);

  const fetchData = async () => {
    setIsLoading(true);
    const [status, res] = await getAPI(`/api/clubs?page=${page}`);
    if (res.length < 5) setPageEnd(true);
    setClubList(res);
    setIsLoading(false);
  };

  const updateClubList = async () => {
    let api = '';

    if (window.location.pathname.split('/')[2]) {
      api = `/api/clubs?name=${
        window.location.pathname.split('/')[2]
      }&page=${page}`;
    } else {
      if (curCategory === 0) api = `/api/clubs?page=${page}`;
      else api = `/api/clubs?category=${curCategory}&page=${page}`;
    }

    const [status, res] = await getAPI(api);
    setClubList((clubList) => [...clubList, ...res]);
    if (res.length < 5) {
      setPageEnd(true);
    }
  };

  const fetchDataByName = async () => {
    setIsLoading(true);
    const api = `/api/clubs?name=${window.location.pathname.split('/')[2]}`;
    const [status, res] = await getAPI(api);
    setClubList(res);
    setIsLoading(false);
  };

  const callback = async ([entry]: any, observer: any) => {
    if (entry.isIntersecting && clubList.length > 3) {
      setPage((page) => page + 1);
      console.log('옵저버 콜백');
      observer.disconnect();
      setTimeout(() => observer.observe(target), 1000);
    }
  };

  useEffect(() => {
    if (!window.location.pathname.split('/')[2]) {
      fetchData();
    } else {
      fetchDataByName();
    }
    categorySetting();
  }, []);

  useEffect(() => {
    if (window.location.pathname.split('/')[2]) {
      fetchDataByName();
      setPage(0);
    }
  }, [window.location.pathname]);

  useEffect(() => {
    if (page > 0) {
      updateClubList();
    }
  }, [page]);

  useEffect(() => {
    if (!window.location.pathname.split('/')[2]) {
      categorizing();
    }
  }, [curCategory]);

  useEffect(() => {
    let observer: any;
    if (target) {
      observer = new IntersectionObserver(callback, { threshold: 1.0 });
      observer.observe(target);
    }
    return () => observer && observer.disconnect();
  }, [curCategory, target]);

  return (
    <>
      <div className="MainBody-CategoryFrame">
        {categoryState?.map((val: any, idx: any) => {
          return (
            <React.Fragment key={val.id}>
              <div
                className={
                  categoryList[val.name] === curCategory
                    ? 'MainBody__div--category-frame-active'
                    : 'MainBody__div--category-frame'
                }
              >
                {' '}
                <div
                  className="MainBody__div--category-box"
                  onClick={() => {
                    setCurCategory(categoryList[val?.name]);
                  }}
                >
                  {val.name}
                </div>
              </div>
              &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
            </React.Fragment>
          );
        })}
        <div className="MainBody-mainInformation-title"></div>
      </div>
      <hr className="MainBody-horizon"></hr>
      <div className="MainBody">
        <div className="MainBody-itemFrame">
          {isLoading ? (
            <div className="MainBody-loading"></div>
          ) : clubList?.length === 0 ? (
            <div className="MainBody-noResult">
              <img src={sad} width="100px" height="100px"></img>동아리가
              존재하지 않아요!
            </div>
          ) : (
            <>
              {' '}
              {clubList?.map((val: any, idx: number) => {
                return <ClubItem key={idx} item={val}></ClubItem>;
              })}
              {pageEnd ? null : (
                <div ref={setTarget} className="Target-Element"></div>
              )}
            </>
          )}
        </div>
        <MainInformation></MainInformation>
      </div>
    </>
  );
};

const MainInformation = () => {
  const [joinedClub, setJoinedClub] = useState<myClub[]>([]);
  const navigate = useNavigate();
  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/users/joined-club`);

    if (status === 200) setJoinedClub(res);
    else alert(res);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="MainInformation">
        <div className="MainInformation__text--myClub">나의 동아리</div>
        <div className="MainInformation__div--club-wrap">
          {joinedClub?.map((val: myClub, idx: number) => {
            return <MyClub key={val.id} clubInfo={val}></MyClub>;
          })}
        </div>
        <div className="MainInformation__text--latest">최근 소식</div>
        <div className="MainInformation__div--club-wrap">
          {joinedClub?.slice(0, 3).map((val: myClub, idx: number) => {
            return <LatestPost key={val.id} clubInfo={val}></LatestPost>;
          })}
        </div>
      </div>
    </>
  );
};

const MyClub = ({ clubInfo }: { clubInfo: myClub }) => {
  const navigate = useNavigate();

  const moveTargetClub = (e: SyntheticEvent) => {
    e.stopPropagation();
    const target = e.target as HTMLDivElement;
    navigate(`/information/${target.dataset.clubid}`);
  };

  return (
    <>
      <div className="MyClub__div" key={clubInfo.id}>
        <img
          className="MyClub__img--club-thumbnail"
          src={clubInfo.imageUrl ? clubInfo.imageUrl : thumbnail}
          width="50px"
          height="50px"
          data-clubid={clubInfo.id}
          onClick={moveTargetClub}
        ></img>
        <div className="MyClub__div--club-name">{clubInfo.name}</div>
      </div>
    </>
  );
};

const LatestPost = ({ clubInfo }: { clubInfo: myClub }) => {
  const [postList, setPostList] = useState<postInfo[]>([]);
  const navigate = useNavigate();
  const fetchData = async () => {
    const [status, res] = await getAPI(`/api/post?clubId=${clubInfo.id}`);

    if (res.length < 3) {
      setPostList(res);
    } else {
      const latestThree = res.slice(0, 3);
      setPostList(latestThree);
    }
  };

  const postClick = (e: SyntheticEvent) => {
    const target = e.target as HTMLDivElement;
    navigate(`/post/${clubInfo.id}/${target.dataset.postid}`);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div className="MyClub__div--latest-clubList" key={clubInfo.id}>
        <span className="MyClub__span--clubName">{clubInfo?.name}</span>
        {postList?.map((val: postInfo, idx: number) => {
          return (
            <React.Fragment key={val.postId}>
              <div className="MyClub__div--latest-post">
                <span className="MyClub__span--latest-title">{val.title}</span>
                <span
                  className="MyClub__span--latest-content"
                  onClick={postClick}
                  data-postid={val.postId}
                >
                  {val.content}
                </span>
                <span className="MyClub__span--date">
                  {val.createdAt.split('T')[0]}
                </span>
              </div>
            </React.Fragment>
          );
        })}
      </div>
    </>
  );
};

export default MainBody;
